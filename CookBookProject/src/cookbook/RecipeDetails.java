package cookbook;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class RecipeDetails extends JFrame {

	public RecipeDetails(Recipe recipe) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(recipe.getName());
        setSize(500, 300);
        setLocationRelativeTo(null);

        JTextArea txtRecipeDetails = new JTextArea();
        txtRecipeDetails.setEditable(false);
        JScrollPane sp = new JScrollPane(txtRecipeDetails);

        StringBuilder sb = new StringBuilder();
        sb.append("Meal Type: ").append(recipe.getMealType()).append("\n");
        sb.append("Preparation Time: ").append(recipe.getPreparationTime()).append(" mins\n\n");
        sb.append("Ingredients:\n");

        IngredientsLinkedList ingredientsList = recipe.getIngredients();

        IngredientNode current = ingredientsList.getRoot();
        while (current != null) {
            String measurement = current.getMeasurement();
            String ingredient = current.getIngredient();
            if (!measurement.isEmpty()) {
                sb.append(measurement).append(" ");
            }
            sb.append(ingredient).append("\n");
            current = current.getNext();
        }

        sb.append("\nInstructions:\n").append(recipe.getInstruction());

        txtRecipeDetails.setText(sb.toString());

        add(sp);
        
        txtRecipeDetails.setLineWrap(true);
        txtRecipeDetails.setWrapStyleWord(true);
    }
	
}

