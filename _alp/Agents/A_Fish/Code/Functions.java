A_Fish nearestFishCluster()
{/*ALCODESTART::1779974932861*/

List<A_Fish> visible = agentsInRange(main.fishPopulation, IV_visionRange);
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
List<A_Predator> visible = agentsInRange(main.predatorPopulation, IV_visionRange);
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

A_Food nearestFood()
{/*ALCODESTART::1779981415967*/
List<A_Food> visible = agentsInRange(main.foodPopulation, IV_visionRange);
return visible.isEmpty() ? null : getNearestAgent(visible);
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

Node nearestShelter()
{/*ALCODESTART::1780500380710*/
Node closestShelter = null;
double minDist = Double.MAX_VALUE;

for (Node n : main.shelter_Nodes) 
{
    double d = n.getNearestPoint(getX(), getY(), IV_Pointer);
	int inside = 0;
    for (A_Fish f : main.fishPopulation) {
        if (n.contains(f.getX(), f.getY())) {
            inside++;
        }
    }
	
    if (d < minDist & inside<15) {
        minDist = d;
        closestShelter = n;
    }
}

return closestShelter;
/*ALCODEEND*/}

double applyForce(double fx,double fy)
{/*ALCODESTART::1780671637893*/
IV_fx += fx / IV_mass;
IV_fy += fy / IV_mass;
/*ALCODEEND*/}

double updateMovement()
{/*ALCODESTART::1780671669917*/
// Apply acceleration to velocity
IV_vx += IV_fx;
IV_vy += IV_fy;

// Clamp to max speed
double speed = Math.sqrt(IV_vx * IV_vx + IV_vy * IV_vy);
if (speed > IV_maxSpeed) {
    IV_vx = (IV_vx / speed) * IV_maxSpeed;
    IV_vy = (IV_vy / speed) * IV_maxSpeed;
}

// Move agent
traceln("vx:"+IV_vx+" vy:"+IV_vy);
jumpTo(getX() + IV_vx, getY() + IV_vy);

// Update orientation
if (speed > 0.01) {
    setRotation(Math.atan2(IV_vy, IV_vx));
}

// Reset forces
IV_fx = 0;
IV_fy = 0;
/*ALCODEEND*/}

double[] steerForce(double target_vx,double target_vy)
{/*ALCODESTART::1780671713772*/
// Normalize desired and scale to maxSpeed
double mag = Math.sqrt(target_vx * target_vx + target_vy * target_vy);
if (mag > 0) {
    target_vx = (target_vx / mag) * IV_maxSpeed;
    target_vx = (target_vy / mag) * IV_maxSpeed;
}

// Steering = desired - current velocity
double steerX = target_vx - IV_vx;
double steerY = target_vy - IV_vy;

// Clamp to maxForce
double steerMag = Math.sqrt(steerX * steerX + steerY * steerY);
if (steerMag > IV_maxForce) {
    steerX = (steerX / steerMag) * IV_maxForce;
    steerY = (steerY / steerMag) * IV_maxForce;
}

return new double[]{steerX, steerY};
/*ALCODEEND*/}

double[] separate(List<A_Fish> neighbors)
{/*ALCODESTART::1780671792322*/
double fx = 0, fy = 0;
int count = 0;

for (A_Fish n : neighbors) {
    double dx = getX() - n.getX();
    double dy = getY() - n.getY();
    double dist = Math.sqrt(dx * dx + dy * dy);

    if (dist > 0 && dist < IV_separationDist) {
        // Weighted by distance (closer = stronger)
        fx += dx / dist;
        fy += dy / dist;
        count++;
    }
}

if (count > 0) {
    fx /= count;
    fy /= count;
    return steerForce(fx, fy);
}
return new double[]{0, 0};
/*ALCODEEND*/}

double[]
 align(List<A_Fish> neighbors)
{/*ALCODESTART::1780671854432*/
double avgVx = 0, avgVy = 0;

for (A_Fish n : neighbors) {
    avgVx += n.IV_vx;
    avgVy += n.IV_vy;
}

if (!neighbors.isEmpty()) {
    avgVx /= neighbors.size();
    avgVy /= neighbors.size();
    return steerForce(avgVx, avgVy);
}
return new double[]{0, 0};
/*ALCODEEND*/}

double[]
 cohere(List<A_Fish> neighbors)
{/*ALCODESTART::1780671935984*/
double cx = 0, cy = 0;

for (A_Fish n : neighbors) {
    cx += n.getX();
    cy += n.getY();
}

if (!neighbors.isEmpty()) {
    cx /= neighbors.size();
    cy /= neighbors.size();
    // Direction toward center
    return steerForce(cx - getX(), cy - getY());
}
return new double[]{0, 0};
/*ALCODEEND*/}

Point getRandomPointInRange()
{/*ALCODESTART::1780673954728*/
// Function: getRandomPointInRange
// Return type: Point

Point candidate = main.oceanNode.randomPointInside();

double dx = candidate.getX() - getX();
double dy = candidate.getY() - getY();
double distance = Math.sqrt(dx * dx + dy * dy);

if (distance > IV_visionRange) {
    // Normalize and scale to IV_visionRange
    double scale = IV_visionRange / distance;
    dx *= scale;
    dy *= scale;
    return new Point(getX() + dx, getY() + dy);
}

return candidate;

/*ALCODEEND*/}

