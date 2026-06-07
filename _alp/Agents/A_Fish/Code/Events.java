void lifeCycle()
{/*ALCODESTART::1779710449694*/
IV_age += 1;
IV_energy -= IV_energyDrain;


if (IV_energy <= 0){
	main.remove_fishPopulation(this, "energy");
}
if (IV_age >= V_maxAge) {
    main.remove_fishPopulation(this, "age");
}




/*ALCODEEND*/}

