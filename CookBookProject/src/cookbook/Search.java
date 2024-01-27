package cookbook;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Search extends JFrame implements MouseListener {

	LinkedList<Recipe> recipebook = new LinkedList<>();
	DefaultListModel<Recipe> dlmRecipes = new DefaultListModel<>();
	JList<Recipe> listRecipes = new JList<>(dlmRecipes);
	JScrollPane sp = new JScrollPane(listRecipes);
	JTextField txtIngredients = new JTextField(20);
    JButton btnSearch = new JButton("Search");
    
    public Search() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Search Recipes");
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        JPanel pnlSearch = new JPanel(new FlowLayout());
        pnlSearch.add(new JLabel("Ingredients: "));
        pnlSearch.add(txtIngredients);
        pnlSearch.add(btnSearch);
        
        btnSearch.addMouseListener(this);
        listRecipes.addMouseListener(this);
        
        add(pnlSearch, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
    }
    
    private boolean containsAllIngredients(IngredientsLinkedList ingredients, String searchIngredients) {
        String[] searchArray = searchIngredients.split(",");
        for (String searchIngredient : searchArray) {
            searchIngredient = searchIngredient.trim();
            boolean found = false;
            IngredientNode current = ingredients.getRoot();
            while (current != null) {
                if (current.getIngredient().equalsIgnoreCase(searchIngredient)) {
                    found = true;
                    break;
                }
                current = current.getNext();
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
	
		if (e.getSource() == btnSearch) {
            String searchIngredients = txtIngredients.getText();
            if (searchIngredients.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                		"Please enter ingredients to search.", "Empty Search",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            dlmRecipes.clear();

            for (Recipe recipe : recipebook) {
                IngredientsLinkedList ingredients = recipe.getIngredients();
                if (ingredients != null && containsAllIngredients(ingredients, searchIngredients)) {
                    dlmRecipes.addElement(recipe);
                }
            }

            if (dlmRecipes.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                		"No recipes found with the given ingredients.", "No Recipes Found",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                		"Recipes found: " + dlmRecipes.size(), "Recipes Found",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
		
		if (e.getSource() == listRecipes && e.getClickCount() == 2) {
            int index = listRecipes.getSelectedIndex();
            if (index != -1) {
                Recipe selectedRecipe = dlmRecipes.getElementAt(index);
                RecipeDetails recipeWindow = new RecipeDetails(selectedRecipe);
                recipeWindow.setVisible(true);
            }
        }
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
}
