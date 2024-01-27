package cookbook;

public class IngredientsLinkedList {

	private IngredientNode root;
	
	public IngredientNode getRoot() {
		return root;
	}
	
	// if measurement and ingredient is given
	public void addIngredient(String measurement, String ingredient) {
		if (root == null) {
	        root = new IngredientNode(measurement, ingredient);
	    } else {
	        IngredientNode current = root;
	        while (current.getNext() != null) {
	            current = current.getNext();
	        }
	        current.setNext(new IngredientNode(measurement, ingredient));
	    }
	}

	// if only ingredient is given
	public void addIngredient(String ingredient) {
        if (root == null) {
            root = new IngredientNode("", ingredient);
        } else {
            IngredientNode current = root;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(new IngredientNode("", ingredient));
        }
    }
	
	public void deleteIngredient (String ingredient) {
		if (root == null) {
            return;
        }
        if (root.getIngredient().equals(ingredient)) {
            root = root.getNext();
            return;
        }
        IngredientNode current = root;
        while (current.getNext() != null) {
            if (current.getNext().getIngredient().equals(ingredient)) {
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
	}
	
	public static IngredientsLinkedList fromString(String ingredientsString) {
	    IngredientsLinkedList ingredientsList = new IngredientsLinkedList();
	    String[] ingredients = ingredientsString.split(", ");
	    for (String ingredient : ingredients) {
	        String[] parts = ingredient.split("\\s+(?=[a-zA-Z])", 2);
	        if (parts.length == 2) {
	            String measurement = parts[0];
	            String ingredientName = parts[1];
	            ingredientsList.addIngredient(measurement, ingredientName);
	        } else {
	            String ingredientName = parts[0];
	            ingredientsList.addIngredient(ingredientName);
	        }
	    }
	    return ingredientsList;
	}
	
	@Override
	public String toString() {
		IngredientNode current = root;
		String string = "";
		while (current != null) {
			string+=current.getMeasurement() + " " + current.getIngredient() + ", ";
	        current = current.getNext();
	    }
	    return string;
	}
	
}
