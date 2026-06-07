void ShipSinks()
{/*ALCODESTART::1779708162939*/
//shipActive = true;
shipZone.setVisible(true); // shipNode IS the visual now

spawnRates.add(5.);
spawnNodes.add(shipZone);

totalSpawnRate = spawnRates.stream().mapToDouble(Double::doubleValue).sum();

/*ALCODEEND*/}

void food_growth()
{/*ALCODESTART::1780414200953*/


for (ShapeRectangle n : this.food_Nodes) {
	if(n.getWidth()<100)
	{
		n.setSize(
		    n.getWidth() + 6,
		    n.getHeight() + 6
		);
		
		n.setPos(
		    n.getX() - 3,
		    n.getY() - 3
		);
	}
}
/*ALCODEEND*/}

void exportUpdate()
{/*ALCODESTART::1780502818703*/
exportFile.println(
	time()
	+ "; "
	+ fishPopulation.size()
	+ "; "
	+ type1count
	+ "; "
	+ type2count
	+ "; "
	+ type3count
	+ "; "
	+ predatorPopulation.size()
)
/*ALCODEEND*/}

void update_counters()
{/*ALCODESTART::1780570266541*/
type1count = 0;
type2count = 0;
type3count = 0;

for (A_Fish f : fishPopulation) {
    if (f.V_type == 1) type1count++;
    if (f.V_type == 2) type2count++;
    if (f.V_type == 3) type3count++;
}

/*ALCODEEND*/}

void spawnFood()
{/*ALCODESTART::1780647158930*/
if (spawnNodes.isEmpty()) return;

double total = totalSpawnRate;
double roll = uniform(0, total);
double cumul = 0;

for (int i = 0; i < spawnNodes.size(); i++) {
    cumul += spawnRates.get(i);
    if (roll < cumul) {
        A_Food food = add_foodPopulation();
        food.jumpTo(spawnNodes.get(i));
        return;
    }
}

/*ALCODEEND*/}

