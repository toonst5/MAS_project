void event()
{/*ALCODESTART::1780848995238*/
IV_lifeSpan -= 1;
if (IV_lifeSpan <= 0) {
	main.remove_foodPopulation(this);
}
/*ALCODEEND*/}

