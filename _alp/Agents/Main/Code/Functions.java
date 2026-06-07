boolean isFoodContaminated(ShapeRectangle food)
{/*ALCODESTART::1780561168804*/
return contaminatedFood.contains(food);
/*ALCODEEND*/}

ArrayList<ShapeRectangle> getAvailableFoodNodes()
{/*ALCODESTART::1780562475461*/
ArrayList<ShapeRectangle> availableFood = new ArrayList<>();

for (ShapeRectangle fn : food_Nodes) {
    if (!isFoodContaminated(fn)) {
        availableFood.add(fn);
    }
}

return availableFood;
/*ALCODEEND*/}

double remove_fishPopulation(A_Fish agent,String reason)
{/*ALCODESTART::1780849610182*/
remove_fishPopulation(agent);
exportFile.println(
	time()
	+ "; "
	+ fishPopulation.size()
	+ "; "
	+ reason
);
/*ALCODEEND*/}

A_Fish add_fishPopulation(String reason)
{/*ALCODESTART::1780849824341*/
A_Fish f=add_fishPopulation();
exportFile.println(
	time()
	+ "; "
	+ fishPopulation.size()
	+ "; "
	+ reason
);

return f;
/*ALCODEEND*/}

