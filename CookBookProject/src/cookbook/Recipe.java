package cookbook;

public class Recipe {

	private String name;
	private IngredientsLinkedList ingredients;
	private String mealType;
	private int preparationTime; 
	private String instruction;
	
	public Recipe() {
		ingredients = new IngredientsLinkedList();
	}
	
	public Recipe(String name, IngredientsLinkedList ingredients, String mealType, int preparationTime, String instruction) {
		super();
		this.name = name;
		this.ingredients = ingredients;
		this.mealType = mealType;
		this.preparationTime = preparationTime;
		this.instruction = instruction;
	}
	
	public String getName() {
		return name;
	}
	public IngredientsLinkedList getIngredients() {
		return ingredients;
	}
	public String getMealType() {
		return mealType;
	}
	public int getPreparationTime() {
		return preparationTime;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIngredients(IngredientsLinkedList ingredients) {
		this.ingredients = ingredients;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public static Recipe fromStringSave(String recipeString) {
	    String[] parts = recipeString.split("/");
	    if (parts.length != 5) { // checks if a valid recipe string
	        return null;
	    }

	    String name = parts[0];
	    String mealType = parts[1];
	    int preparationTime = Integer.parseInt(parts[2]);
	    IngredientsLinkedList ingredients = IngredientsLinkedList.fromString(parts[3]);
	    String instruction = parts[4];

	    Recipe recipe = new Recipe();
	    recipe.setName(name);
	    recipe.setMealType(mealType);
	    recipe.setPreparationTime(preparationTime);
	    recipe.setIngredients(ingredients);
	    recipe.setInstruction(instruction);
	    return recipe;
	}
	
	public String toStringSave() {
		return name+"/"+ mealType+"/"+preparationTime+"/"+ingredients.toString()+"/"+instruction+"\n/end";
	}
	
	@Override
	public String toString() {
		return name + " (" + mealType +")";
	}
	
}
