package tree;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree implements Tree  {
    private TreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    public void createRandomTree(int numberOfValues) {
        if (numberOfValues <= 0) return;

        // Generate a random value for the root node
        int rootValue = (int) (Math.random() * 100); // Random root value generation, adjust range as needed
        root = new TreeNode(rootValue);

        // Generate the remaining values and insert them as children of the root
        for (int i = 1; i < numberOfValues; i++) {
            int newValue = (int) (Math.random() * 100); // Random new value generation, adjust range as needed
            insert(rootValue, newValue);
        }
    }
    public void insert(int parentValue, int newValue) {
        if (root == null) {
            root = new TreeNode(newValue);
            return;
        }

        TreeNode parentNode = search(root, parentValue);
        if (parentNode != null) {
            if (parentNode.getChildren().size() < 2) {
                parentNode.addChild(new TreeNode(newValue));
            } else {
                System.out.println("Parent node already has two children.");
            }
        } else {
            System.out.println("Parent value not found in the tree.");
        }
    }

    public void delete(int value) {
        // Logic for deleting from a binary tree
        root = deleteRecursive(root, value);
    }

    private TreeNode deleteRecursive(TreeNode currentNode, int value) {
        if (currentNode == null) {
            return null;
        }

        if (value == currentNode.getValue()) {
            // Node to be deleted found

            // Node with only one child or no child
            if (currentNode.getChildren().isEmpty()) {
                return null;
            } else if (currentNode.getChildren().size() == 1) {
                return currentNode.getChildren().get(0);
            }

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            TreeNode rightChild = currentNode.getChildren().get(1);
            currentNode.value = findMinValue(rightChild);

            // Delete the inorder successor
            rightChild = deleteRecursive(rightChild, currentNode.getValue());
            currentNode.getChildren().set(1, rightChild);
            return currentNode;
        }

        if (!currentNode.getChildren().isEmpty() && value < currentNode.getValue()) {
            currentNode.getChildren().set(0, deleteRecursive(currentNode.getChildren().get(0), value));
            return currentNode;
        }

        if (currentNode.getChildren().size() == 2) {
            currentNode.getChildren().set(1, deleteRecursive(currentNode.getChildren().get(1), value));
        }
        return currentNode;
    }

    private int findMinValue(TreeNode node) {
        int minValue = node.getValue();
        while (!node.getChildren().isEmpty() && node.getChildren().get(0) != null) {
            node = node.getChildren().get(0);
            minValue = node.getValue();
        }
        return minValue;
    }

    public TreeNode search(int value) {
        return search(root, value);
    }

    private TreeNode search(TreeNode currentNode, int value) {
        if (currentNode == null || currentNode.getValue() == value) {
            return currentNode;
        }

        for (TreeNode child : currentNode.getChildren()) {
            TreeNode result = search(child, value);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public void traverse() {
        if (root != null) {
            traverseBFS();
        }
    }

    private void traverseBFS() {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.poll();
            System.out.print(currentNode.getValue() + " ");

            for (TreeNode child : currentNode.getChildren()) {
                queue.add(child);
            }
        }
    }

    public TreeNode getRoot() {
        return root;
    }
}
