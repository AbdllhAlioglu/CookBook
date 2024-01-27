package cookbook;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.*;

@SuppressWarnings("serial")
public class AddRecipe extends JFrame implements MouseListener{

	LinkedList<Recipe> recipebook;
	JTextField txtName = new JTextField();
    JComboBox<String> cmbMealType = new JComboBox<String>(
            new String[] { "Breakfast", "Lunch", "Dinner", "Snack", "Dessert", "Soup", "Salad", "Other" });
    JTextField txtTime = new JTextField();
    JTextArea txtInstructions = new JTextArea();
    JTextArea txtIngredients = new JTextArea();
    JButton btnSubmit = new JButton("Submit");
    JButton btnCancel = new JButton("Cancel");
	DefaultListModel<Recipe> dlmRecipes = new DefaultListModel<Recipe>();
	int index = -1;
	
    public AddRecipe() {
    	setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setTitle("New Recipe");
        setLayout(new GridLayout(6, 2));
        
        add(new JLabel("Name: "));
        add(txtName);
        add(new JLabel("Type: "));
        add(cmbMealType);
        add(new JLabel("Preparation Time (minutes): "));
        add(txtTime);
        add(new JLabel("Ingredients (seperate each with comma): "));
        add(txtIngredients);
        JScrollPane scrollPane1 = new JScrollPane(txtIngredients);
        add(scrollPane1);
        add(new JLabel("Instructions: "));
        add(txtInstructions);
        JScrollPane scrollPane2 = new JScrollPane(txtInstructions);
        add(scrollPane2);
        add(btnSubmit);
        add(btnCancel);
        
        txtInstructions.setLineWrap(true);
        txtInstructions.setWrapStyleWord(true);
        txtIngredients.setLineWrap(true);
        txtIngredients.setWrapStyleWord(true);
        
        btnSubmit.addMouseListener(this);
        btnCancel.addMouseListener(this);
    }

	public void clear() {
		txtName.setText("");
        cmbMealType.setSelectedIndex(0);
        txtTime.setText("");
        txtInstructions.setText("");
        txtIngredients.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {	
		if(e.getSource()==btnCancel) {
			int yn = JOptionPane.showConfirmDialog(null, "You are about the cancel submitting your recipe, current daha entered will be lost. \nAre you sure?", "Warning!", JOptionPane.YES_NO_OPTION);
			if(yn==JOptionPane.YES_OPTION) {
				clear();
				setVisible(false);
			}
		}	
		if(e.getSource()==btnSubmit) {
			Recipe r = new Recipe();
		    r.setName(txtName.getText());
		    r.setPreparationTime(Integer.parseInt(txtTime.getText()));
		    r.setMealType(cmbMealType.getSelectedItem().toString());
		    r.setInstruction(txtInstructions.getText());
		    IngredientsLinkedList ingredients = new IngredientsLinkedList();
		    String[] ingredientList = txtIngredients.getText().split(",\\s*");
		    for (String ingredientString : ingredientList) {
		        String[] parts = ingredientString.split("\\s(?=[a-zA-Z])", 2);
		        if (parts.length == 2) {
		            String measurement = parts[0];
		            String ingredient = parts[1];
		            ingredients.addIngredient(measurement, ingredient);
		        } else {
		            String ingredient = parts[0];
		            ingredients.addIngredient(ingredient);
		        }
		    }
		    r.setIngredients(ingredients);  
	        if (index == -1) {
				dlmRecipes.addElement(r);
				recipebook.add(r);
				JOptionPane.showMessageDialog(null, "Recipe succesfully added.");
				setVisible(false);
			}
			else {
				Recipe oldRecipe = dlmRecipes.get(index);
				int recipeIndex = recipebook.indexOf(oldRecipe);
		        recipebook.set(recipeIndex, r);
		        
		        dlmRecipes.remove(index);
				dlmRecipes.add(index, r);
				JOptionPane.showMessageDialog(null, "Recipe updated.");
				setVisible(false);
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
