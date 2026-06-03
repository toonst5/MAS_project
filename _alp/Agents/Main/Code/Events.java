void ShipSinks()
{/*ALCODESTART::1779708162939*/
shipClass.setVisible(true);
/*ALCODEEND*/}

void event()
{/*ALCODESTART::1780414200953*/


for (ShapeRectangle n : this.food_Nodes) {
	if(n.getWidth()<200)
	{
		n.setSize(
		    n.getWidth() + 10,
		    n.getHeight() + 10
		);
		
		n.setPos(
		    n.getX() - 5,
		    n.getY() - 5
		);
	}
}
/*ALCODEEND*/}

void exportUpdate()
{/*ALCODESTART::1780502818703*/
exportFile.println(
	time()
	+ ", fishPopSize:"
	+ fishPopulation.size()
	+ ", predPopSize:"
	+ predatorPopulation.size()
)
/*ALCODEEND*/}

