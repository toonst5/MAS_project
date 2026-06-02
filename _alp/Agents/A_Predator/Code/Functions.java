A_Fish nearestFishNaive()
{/*ALCODESTART::1780433174134*/

A_Fish nearest = null;
if (main != null)
{
	double bestDist = Double.MAX_VALUE;
	
	for (A_Fish f : main.fishPopulation) {
        double d = distanceTo(f);
        if (d < bestDist) {
            bestDist = d;
            nearest = f;
	    }
	}
}
return nearest;


/*ALCODEEND*/}

