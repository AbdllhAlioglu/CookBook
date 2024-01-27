package cookbook;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.*;

public class Cookbook extends JFrame implements MouseListener {
	
	private LinkedList<Recipe> recipebook = new LinkedList<>();
	private static Cookbook cb = new Cookbook();
	private static final String RECIPE_FILE = "recipes.txt";
	private AddRecipe addRecipe = new AddRecipe();
	private Search searchRecipe = new Search(); 
	
	private JButton btnAdd = new JButton("Add");
	private JButton btnEdit = new JButton("Edit");
	private JButton btnDelete = new JButton("Delete");
	private JButton btnSearch = new JButton("Search");
	private JButton btnSave = new JButton("Save");
	private JButton btnSort = new JButton("Sort");
	
	private DefaultListModel<Recipe> dlmRecipes = new DefaultListModel<>();
	private JList<Recipe> listRecipes = new JList<>(dlmRecipes);
	private JScrollPane sp = new JScrollPane(listRecipes);
	
	public static void main(String[] args) {
		cb.setVisible(true);
	}
	
	public Cookbook() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Cookbook");
		setLayout(new BorderLayout());
		setSize(500, 300);
		setLocationRelativeTo(null);
		
		JPanel pnlButtons = new JPanel(new FlowLayout());
		pnlButtons.add(btnAdd);
		pnlButtons.add(btnDelete);
		pnlButtons.add(btnEdit);
		pnlButtons.add(btnSearch);
		pnlButtons.add(btnSort);
		pnlButtons.add(btnSave);
		
		add(pnlButtons, BorderLayout.SOUTH);
		add(sp, BorderLayout.CENTER);
		
		btnAdd.addMouseListener(this);
		btnDelete.addMouseListener(this);
		btnEdit.addMouseListener(this);
		btnSearch.addMouseListener(this);
		listRecipes.addMouseListener(this);
		btnSave.addMouseListener(this);
		btnSort.addMouseListener(this);
		
		loadRecipes();
	}

	private void loadRecipes() {
	    try (BufferedReader reader = new BufferedReader(new FileReader(RECIPE_FILE))) {
	        String line;
	        StringBuilder recipeBuilder = new StringBuilder();
	        while ((line = reader.readLine()) != null) {
	            if (line.equals("/end")) {
	                Recipe recipe = Recipe.fromStringSave(recipeBuilder.toString());
	                if (recipe != null) {
	                    recipebook.add(recipe);
	                    dlmRecipes.addElement(recipe);
	                }
	                recipeBuilder.setLength(0);
	            } else {
	                recipeBuilder.append(line).append("\n");
	            }
	        }
	        System.out.println("Recipes loaded from file: " + RECIPE_FILE);
	    } catch (IOException e) {
	        System.err.println("Error loading recipes from file: " + e.getMessage());
	    }
	}
	
	private void saveRecipes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECIPE_FILE))) {
            for (Recipe recipe : recipebook) {
                String recipeString = recipe.toStringSave();
                writer.write(recipeString);
                writer.newLine();
            }
            System.out.println("Recipes saved to file: " + RECIPE_FILE);
        } catch (IOException e) {
            System.err.println("Error saving recipes to file: " + e.getMessage());
        }
    }
	
	@Override
	public void mouseClicked(MouseEvent e) {
			
		if(e.getSource() == btnAdd) {
			addRecipe.clear();
			addRecipe.recipebook = this.recipebook;
			addRecipe.dlmRecipes = this.dlmRecipes;
			addRecipe.index = -1;
			addRecipe.setVisible(true);	
		}
		
		if (e.getSource() == btnSave) {
            saveRecipes();
            JOptionPane.showMessageDialog(null, 
            		"Recipes are saved succesfully.");
        }
		
		if(e.getSource() == btnEdit) {
			addRecipe.clear();
			addRecipe.recipebook = this.recipebook;
			addRecipe.dlmRecipes = this.dlmRecipes;
			addRecipe.index = listRecipes.getSelectedIndex();
			
			if(addRecipe.index==-1) {
				JOptionPane.showMessageDialog(null, "Please select a recipe to edit.",
						"No Recipe Selected", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			Recipe r = listRecipes.getSelectedValue();
			addRecipe.txtName.setText(r.getName());
			addRecipe.txtTime.setText(String.valueOf(r.getPreparationTime()));
			addRecipe.txtInstructions.setText(r.getInstruction());
			//borrowed code from chat-GPT starts
			IngredientsLinkedList ingredients = r.getIngredients();
			StringBuilder ingredientsText = new StringBuilder();
			if (ingredients != null) {
				IngredientNode current = ingredients.getRoot();
				while (current != null) {
					String measurement = current.getMeasurement();
					String ingredient = current.getIngredient();
					if (!measurement.isEmpty()) {
						ingredientsText.append(measurement).append(" ");
					}
			        ingredientsText.append(ingredient).append(", ");
			        current = current.getNext();
			    	}
				}
			//borrowed code from chat-GPT ends
			addRecipe.txtIngredients.setText(ingredientsText.toString());
			addRecipe.cmbMealType.setSelectedItem(r.getMealType());
			addRecipe.setVisible(true);
		}
		
		if (e.getSource() == btnSearch) {  
			searchRecipe.recipebook=this.recipebook;
			searchRecipe.setVisible(true);
	    }
		
		if (e.getSource() == listRecipes && e.getClickCount() == 2) {
            int index = listRecipes.getSelectedIndex();
            if (index != -1) {
                Recipe selectedRecipe = dlmRecipes.getElementAt(index);
                RecipeDetails recipeWindow = new RecipeDetails(selectedRecipe);
                recipeWindow.setVisible(true);
            }
        }
		
		if (e.getSource() == btnSort) {
		    int yn = JOptionPane.showConfirmDialog(null,
		    		"Are you sure you want to sort the recipes?",
		    		"Sort Recipes", JOptionPane.YES_NO_OPTION);
		    if (yn == JOptionPane.YES_OPTION) {
		        RecipeBST bst = new RecipeBST();
		        for (Recipe recipe : recipebook) {
		            bst.insert(recipe);
		        }

		        LinkedList<Recipe> sortedRecipes = new LinkedList<>();
		        bst.inOrderTraversal(sortedRecipes);

		        dlmRecipes.clear();
		        for (Recipe recipe : sortedRecipes) {
		            dlmRecipes.addElement(recipe);
		        }
		        JOptionPane.showMessageDialog(null, "Recipes are sorted succesfully.");
		    }
		}
		
		if(e.getSource() == btnDelete) {
			int selectedIndex = listRecipes.getSelectedIndex();
	        if (selectedIndex != -1) {
	            int yn = JOptionPane.showConfirmDialog(null, 
	            		"Are you sure you want to delete the selected recipe?",
	                    "Delete Recipe", JOptionPane.YES_NO_OPTION);
	            if (yn == JOptionPane.YES_OPTION) {
	            	Recipe deletedRecipe = dlmRecipes.get(selectedIndex);
	                recipebook.remove(deletedRecipe);
	            	dlmRecipes.remove(selectedIndex);
	            	JOptionPane.showMessageDialog(null, "Recipe deleted succesfully.");
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, 
	            		"Please select a recipe to delete.", "No Recipe Selected",
	                    JOptionPane.WARNING_MESSAGE);
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
