void ShipSinks()
{/*ALCODESTART::1779708162939*/
shipClass.setVisible(true);
/*ALCODEEND*/}

void event()
{/*ALCODESTART::1780414200953*/


for (ShapeRectangle n : this.food_Nodes) {
	if(n.getWidth()<100)
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

