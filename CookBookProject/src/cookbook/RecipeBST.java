package cookbook;

import java.util.LinkedList;

class RecipeBST {
    
	private NodeBST root;

    private class NodeBST {
        private Recipe recipe;
        private NodeBST left;
        private NodeBST right;

        public NodeBST(Recipe recipe) {
            this.recipe = recipe;
        }
    }

    public void insert(Recipe recipe) {
        NodeBST newNode = new NodeBST(recipe);

        if (root == null) {
            root = newNode;
        } else {
            NodeBST current = root;
            NodeBST parent;

            while (true) {
                parent = current;
                if (recipe.getPreparationTime() < current.recipe.getPreparationTime()) {
                    current = current.left;
                    if (current == null) {
                        parent.left = newNode;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    public void inOrderTraversal(LinkedList<Recipe> sortedRecipes) {
        inOrderTraversal(root, sortedRecipes);
    }

    private void inOrderTraversal(NodeBST node, LinkedList<Recipe> sortedRecipes) {
        if (node != null) {
            inOrderTraversal(node.left, sortedRecipes);
            sortedRecipes.add(node.recipe);
            inOrderTraversal(node.right, sortedRecipes);
        }
    }
}


