void senseEnvironment()
{/*ALCODESTART::1779710345943*/
IV_foodTarget = nearestFood();
IV_nearestPredator = nearestPredator();
IV_nearestFish = nearestFish();
IV_nearestShelter = nearestShelter();
/*ALCODEEND*/}

void lifeCycle()
{/*ALCODESTART::1779710449694*/
IV_age += 1;
IV_energy -= 5;


if (IV_energy <= 0 || IV_age >= V_maxAge) {
    main.remove_fishPopulation(this);
}




/*ALCODEEND*/}

