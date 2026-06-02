void lifeCycle()
{/*ALCODESTART::1780433027831*/
setIV_energy -= 2;
IV_energy = max(0, IV_energy);

if (IV_energy <= 10) {
	sharkImage.setVisible(IV_energy%2 == 0);
}
if (IV_energy <= 0){
	main.remove_predatorPopulation(this);
}

//traceln("Energy: "+IV_energy);
/*ALCODEEND*/}

