void senseEnvironment()
{/*ALCODESTART::1779710345943*/
IV_nearestFood = findNearest(main.foodPopulation);
IV_nearestPredator = findNearest(main.predatorPopulation);
IV_nearestFish = findNearest(main.fishPopulation);
IV_nearestShelter = findNearest(main.shelterPopulation);
/*ALCODEEND*/}

void lifeCycle()
{/*ALCODESTART::1779710449694*/
IV_age += 1;
IV_energy -= 2;


if (IV_energy <= 0 || IV_age >= V_maxAge) {
    behavior.takeTransitionTo(behavior.DEAD);
}


/*ALCODEEND*/}

