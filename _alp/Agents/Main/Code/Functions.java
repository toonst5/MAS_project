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
{/*ALCODESTART::1780847745978*/
remove_fishPopulation(agent);
/*ALCODEEND*/}

