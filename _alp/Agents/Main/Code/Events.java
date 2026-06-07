void food_growth()
{/*ALCODESTART::1780414200953*/


for (ShapeRectangle n : this.food_Nodes) {
	if(n.getWidth()<50)
	{
		n.setSize(
		    n.getWidth() + 2,
		    n.getHeight() + 2
		);
		
		n.setPos(
		    n.getX() - 1,
		    n.getY() - 1
		);
	}
}
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

