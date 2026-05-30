void senseEnvironment()
{/*ALCODESTART::1779710345943*/
IV_nearestFood = nearestFood();
IV_nearestPredator = nearestPredator();
IV_nearestFish = nearestFish();
IV_nearestShelter = null
/*ALCODEEND*/}

void lifeCycle()
{/*ALCODESTART::1779710449694*/
IV_age += 1;
IV_energy -= 2;


if (IV_energy <= 0 || IV_age >= V_maxAge) {
    //behavior.takeTransitionTo(behavior.DEAD);
}


/*ALCODEEND*/}

