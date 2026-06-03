A_Fish nearestFish()
{/*ALCODESTART::1779974932861*/

List<A_Fish> visible = agentsInRange(main.fishPopulation, V_visionRange);
return visible.isEmpty() ? null : getNearestAgent(visible);

/*** Naive code ***/
/*A_Fish nearest = null;
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
return nearest;*/
/*ALCODEEND*/}

A_Predator nearestPredator()
{/*ALCODESTART::1779981412507*/
List<A_Predator> visible = agentsInRange(main.predatorPopulation, V_visionRange);
return visible.isEmpty() ? null : getNearestAgent(visible);

/*** Naive code ***/
/*A_Predator nearest = null;
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
return nearest;*/
/*ALCODEEND*/}

ShapeRectangle nearestFood()
{/*ALCODESTART::1779981415967*/

ShapeRectangle closestFood = null;
double minDist = Double.MAX_VALUE;

for (ShapeRectangle n : main.food_Nodes) {
    // only consider open food nodes
    if (n.getWidth() > 30) {//n.isOpen()
        double d = distanceTo(n.getCenter().x, n.getCenter().y);

        if (d < minDist) {
            minDist = d;
            closestFood = n;
        }
    }
}

return closestFood;
/*ALCODEEND*/}

double countNeighbors()
{/*ALCODESTART::1780148179430*/

List<A_Fish> visible = agentsInRange(main.fishPopulation, V_schoolingRange);
return visible.size();

/*** Naive code ***/
/*int count = 0;

for (A_Fish f : main.fishPopulation) {
    if (f != this && distanceTo(f) < V_schoolingRange) {
        count++;
    }
}

return count;*/

/*ALCODEEND*/}

