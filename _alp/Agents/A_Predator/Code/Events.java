void lifeCycle()
{/*ALCODESTART::1780433027831*/
IV_energy -= 2;
IV_energy = max(0, IV_energy);

/*if (IV_energy <= 10) {
	sharkImage.setVisible(!sharkImage.isVisible());
} else {
	sharkImage.setVisible(true);
}*/
if (IV_energy <= 0){
	main.remove_predatorPopulation(this);
}

//traceln("Energy: "+IV_energy);
/*ALCODEEND*/}

