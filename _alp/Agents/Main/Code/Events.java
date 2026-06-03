void ShipSinks()
{/*ALCODESTART::1779708162939*/
shipClass.setVisible(true);
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

