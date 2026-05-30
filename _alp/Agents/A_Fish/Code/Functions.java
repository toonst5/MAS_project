A_Fish nearestFish()
{/*ALCODESTART::1779974932861*/

A_Fish nearest = null;
if (main != null)
{
	double bestDist = Double.MAX_VALUE;
	
	for (A_Fish f : main.fishPopulation) {
	    if (f != this) {
	        double d = distanceTo(f);
	        if (d < bestDist) {
	            bestDist = d;
	            nearest = f;
	        }
	    }
	}
}
return nearest;
/*ALCODEEND*/}

A_Predator nearestPredator()
{/*ALCODESTART::1779981412507*/

A_Predator nearest = null;
if (main != null)
{
	double bestDist = Double.MAX_VALUE;
	
	for (A_Predator f : main.predatorPopulation) {
	    double d = distanceTo(f);
	    if (d < bestDist) {
	        bestDist = d;
	        nearest = f;
	    }
	}
}
return nearest;
/*ALCODEEND*/}

Agent nearestFood()
{/*ALCODESTART::1779981415967*/
return null;
/*ALCODEEND*/}

double countNeighbors()
{/*ALCODESTART::1780148179430*/

int count = 0;

for (A_Fish f : main.fishPopulation) {
    if (f != this && distanceTo(f) < V_schoolingRange) {
        count++;
    }
}

return count;

/*ALCODEEND*/}

