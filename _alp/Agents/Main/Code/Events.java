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

void update_counters()
{/*ALCODESTART::1780570266541*/
type1count = 0;
type2count = 0;
type3count = 0;

for (A_Fish f : fishPopulation) {
    if (f.V_type == 1) type1count++;
    if (f.V_type == 2) type2count++;
    if (f.V_type == 3) type3count++;
}

/*ALCODEEND*/}

