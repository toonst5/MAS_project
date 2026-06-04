void ShipSinks()
{/*ALCODESTART::1779708162939*/
shipClass.setVisible(true);
shipActive = true;
double shipW = 120;
double shipH = 100;

double shipX = oceanNode.getX() + shipW+ uniform(0, oceanNode.getWidth() -  2* shipW);
double shipY = oceanNode.getY() + shipH+ uniform(0, oceanNode.getHeight() - 2* shipH);

//Point p = oceanNode.randomPointInside();
shipClass.setPos(shipX, shipY);
double shipCenterX = shipX + shipW / 2;
double shipCenterY = shipY + shipH / 2;
double contamRadius = 350;
double killRadius = 200;
//works -> one food target gets slowly back to it's orginal -> fishes do not go there
for (ShapeRectangle f : food_Nodes) {
    double foodCenterX = f.getX() + f.getWidth() / 2;
    double foodCenterY = f.getY() + f.getHeight() / 2;

    double d = Math.sqrt(
        Math.pow(foodCenterX - shipCenterX, 2) +
        Math.pow(foodCenterY - shipCenterY, 2)
    );
	//double foodRadiusApprox = Math.max(f.getWidth(), f.getHeight()) / 2;
	
    if (d <= contamRadius) {
        contaminatedFood.add(f);
    }
}
//initial killing impact -> seems to work as well
for ( int i = fishPopulation.size() -1; i >= 0; i--) {
	A_Fish fi = fishPopulation.get(i);
	//double fishCenterX = fi.getX() + fi.getWidth() /2;
	//double fishCenterY = fi.getY() + fi.getHeight() /2;
	double d = Math.sqrt(
        Math.pow(fi.getX() - shipCenterX, 2) +
        Math.pow(fi.getY() - shipCenterY, 2)
    );
    if (d <= killRadius) {
        remove_fishPopulation(fi);
    }
}
/*ALCODEEND*/}

void food_growth()
{/*ALCODESTART::1780414200953*/


for (ShapeRectangle n : this.food_Nodes) {
	if(n.getWidth()<100)
	{
		n.setSize(
		    n.getWidth() + 6,
		    n.getHeight() + 6
		);
		
		n.setPos(
		    n.getX() - 3,
		    n.getY() - 3
		);
	}
}
/*ALCODEEND*/}

void exportUpdate()
{/*ALCODESTART::1780502818703*/
exportFile.println(
	time()
	+ "; "
	+ fishPopulation.size()
	+ "; "
	+ predatorPopulation.size()
)
/*ALCODEEND*/}

