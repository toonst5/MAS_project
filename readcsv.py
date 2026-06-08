import matplotlib
matplotlib.use('TkAgg')

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# =========================================================
# LOAD + CLEAN
# =========================================================

file_path = "MAS_project/exportFile.csv"
df = pd.read_csv(file_path, sep=';', low_memory=False)

df.columns = df.columns.str.strip()

df['time'] = pd.to_numeric(df['time'], errors='coerce')

numeric_cols = [
    'fishPopulationSize',
    'fishPopulationSizeType1',
    'fishPopulationSizeType2',
    'fishPopulationSizeType3'
]

for col in numeric_cols:
    df[col] = pd.to_numeric(df[col], errors='coerce')

df['reason'] = df['reason'].astype(str).str.strip().str.lower()
df['shipState'] = df['shipState'].astype(str).str.strip().str.lower()

df = df.dropna(subset=['time'] + numeric_cols)

# =========================================================
# RUN DETECTION
# =========================================================

df['run'] = (df['time'].diff() < 0).cumsum()
runs = df['run'].unique()

print("\n=== UNIQUE SHIP STATES ===")
print(df['shipState'].unique())   # 🔥 CRITICAL DEBUG

# =========================================================
# ✅ CORRECT PHASE DETECTION (STATE-BASED)
# =========================================================

def label_phase_per_run(run_df, run_id):
    run_df = run_df.copy()

    print(f"\n--- RUN {run_id} ---")
    print("States:", run_df['shipState'].unique())

    # detect state changes (NOT guessing strings)
    states = run_df[['time', 'shipState']].drop_duplicates()

    print(states.head(10))

    ship_time = None
    pollution_end_time = None

    # detect transitions
    prev_state = None

    for _, row in states.iterrows():
        state = row['shipState']
        t = row['time']

        if prev_state is None:
            prev_state = state
            continue

        # first change = ship drop
        if ship_time is None and state != prev_state:
            ship_time = t
            print(f"Detected SHIP_DROP at t={t}")

        # second change = pollution gone
        elif ship_time is not None and pollution_end_time is None and state != prev_state:
            pollution_end_time = t
            print(f"Detected POLLUTION_END at t={t}")

        prev_state = state

    def get_phase(t):
        if ship_time is None or t < ship_time:
            return "Base"
        elif pollution_end_time is None or t < pollution_end_time:
            return "Ship_Drop"
        else:
            return "Pollution_Disappeared"

    run_df['phase'] = run_df['time'].apply(get_phase)
    return run_df


# APPLY PER RUN (IMPORTANT)
df = pd.concat([label_phase_per_run(df[df['run'] == r], r) for r in runs])

# ✅ FORCE CORRECT PHASE ORDER
phase_order = ["Base", "Ship_Drop", "Pollution_Disappeared"]

df['phase'] = pd.Categorical(df['phase'], categories=phase_order, ordered=True)


# DEBUG CHECK
print("\n=== PHASES DETECTED ===")
print(df['phase'].value_counts())

# =========================================================
# ✅ 1. AVERAGE POPULATION (KEEP THIS)
# =========================================================

common_time = np.linspace(df['time'].min(), df['time'].max(), 800)

avg_total = []
avg_t1 = []
avg_t2 = []
avg_t3 = []

for t in common_time:
    vals = df.iloc[(df['time'] - t).abs().argsort()[:len(runs)]]

    avg_total.append(vals['fishPopulationSize'].mean())
    avg_t1.append(vals['fishPopulationSizeType1'].mean())
    avg_t2.append(vals['fishPopulationSizeType2'].mean())
    avg_t3.append(vals['fishPopulationSizeType3'].mean())

plt.figure()

plt.plot(common_time, avg_total, label="Total", linewidth=2)
plt.plot(common_time, avg_t1, label="Type 1")
plt.plot(common_time, avg_t2, label="Type 2")
plt.plot(common_time, avg_t3, label="Type 3")

plt.title("Average population over time")
plt.xlabel("Time")
plt.ylabel("Population")
plt.legend()
plt.tight_layout()
# ===== ADD INTERVENTION LINES =====

# Compute average event times across runs
ship_times = []
pollution_times = []

for r in runs:
    run_df = df[df['run'] == r]

    states = run_df[['time', 'shipState']].drop_duplicates()

    prev = None
    ship_t = None
    poll_t = None

    for _, row in states.iterrows():
        state = row['shipState']
        t = row['time']

        if prev is None:
            prev = state
            continue

        if ship_t is None and state != prev:
            ship_t = t
        elif ship_t is not None and poll_t is None and state != prev:
            poll_t = t

        prev = state

    if ship_t is not None:
        ship_times.append(ship_t)

    if poll_t is not None:
        pollution_times.append(poll_t)

# compute averages
avg_ship = np.mean(ship_times)
avg_poll = np.mean(pollution_times)

print(f"Average SHIP DROP time: {avg_ship}")
print(f"Average POLLUTION END time: {avg_poll}")

# ===== DRAW LINES =====
plt.axvline(avg_ship, color='black', linestyle='--', label="Ship drop")
plt.axvline(avg_poll, color='purple', linestyle=':', label="Pollution disappears")

plt.show()




# =========================================================
# ✅ 2. DEATH CAUSES (ONLY THIS, CLEAN)
# =========================================================

df['delta'] = df.groupby('run')['fishPopulationSize'].diff()
deaths = df[df['delta'] < 0]


death_causes = (
    deaths
    .groupby(['phase', 'reason'])
    .size()
    .unstack(fill_value=0)
    .reindex(phase_order)   # ✅ enforce order in output
)


death_causes = death_causes.div(death_causes.sum(axis=1), axis=0) * 100

print("\n=== FINAL DEATH CAUSES ===")
print(death_causes)

death_causes.plot(kind='bar', stacked=True)

plt.title("Death causes (%) per phase")
plt.ylabel("%")
plt.xticks(rotation=0)
plt.tight_layout()
plt.show()

# =========================================================
# ✅ COMBINED DEATH CAUSES (PER TYPE, SINGLE GRAPH)
# =========================================================

print("\n=== COMBINED TYPE DEATH CAUSES ===")

types = {
    "Type 1": "fishPopulationSizeType1",
    "Type 2": "fishPopulationSizeType2",
    "Type 3": "fishPopulationSizeType3"
}

# Prepare container
results = {}

for type_name, col in types.items():
    df[f'delta_{col}'] = df.groupby('run')[col].diff()

    type_deaths = df[
        (df[f'delta_{col}'] < 0) & 
        (df['delta'] < 0)
    ]

    # remove reproduction
    type_deaths = type_deaths[type_deaths['reason'] != 'reproduction']

    death_counts = (
        type_deaths
        .groupby(['phase', 'reason'])
        .size()
        .unstack(fill_value=0)
        .reindex(phase_order)
    )

    # convert to %
    death_pct = death_counts.div(death_counts.sum(axis=1), axis=0) * 100

    results[type_name] = death_pct

# =========================================================
# ✅ PLOT: GROUPED + STACKED BARS
# =========================================================

phases = phase_order
x = np.arange(len(phases))  # 3 phases

width = 0.2  # bar width

plt.figure()

colors = {
    "age": "#1f77b4",
    "energy": "#ff7f0e",
    "predator": "#2ca02c"
}

for i, (type_name, data) in enumerate(results.items()):
    offset = (i - 1) * width  # center 3 bars

    bottom = np.zeros(len(phases))

    for reason in ["age", "energy", "predator"]:
        values = data[reason].values if reason in data else np.zeros(len(phases))

        plt.bar(
            x + offset,
            values,
            width,
            bottom=bottom,
            label=reason if i == 0 else "",  # legend only once
            color=colors[reason]
        )

        bottom += values

# labels
plt.xticks(x, phases)
plt.xlabel("Phase")
plt.ylabel("% of deaths")
plt.title("Death causes per phase and fish type")

# add second legend for types
from matplotlib.patches import Patch

type_legend = [
    Patch(facecolor='gray', label='Left = Type 1'),
    Patch(facecolor='gray', label='Middle = Type 2'),
    Patch(facecolor='gray', label='Right = Type 3')
]

handles, labels = plt.gca().get_legend_handles_labels()

plt.legend(handles + type_legend, labels + [t.get_label() for t in type_legend],
           bbox_to_anchor=(1.05, 1), loc='upper left')

plt.tight_layout()
plt.show()