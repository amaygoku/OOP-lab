package tree;

import java.util.LinkedList;
import java.util.Queue;

public class BalancedTree implements Tree {
    private TreeNode root;

    public BalancedTree() {
        this.root = null;
    }

    public void createRandomTree(int numberOfValues) {
        for (int i = 0; i < numberOfValues; i++) {
            int parentValue = (int) (Math.random() * 100); // Random parent value generation
            int newValue = (int) (Math.random() * 100); // Random new value generation
            insert(parentValue, newValue);
        }
    }

    public void insert(int parentValue, int newValue) {
        if (root == null) {
            root = new TreeNode(newValue);
            return;
        }

        TreeNode parentNode = search(root, parentValue);
        if (parentNode != null) {
            TreeNode newNode = new TreeNode(newValue);
            parentNode.addChild(newNode);
            balanceTree(root);
        } else {
            System.out.println("Parent value not found in the tree.");
        }
    }

    private void balanceTree(TreeNode node) {
        // Logic to balance the tree
        // This is a placeholder, you need to implement a specific balancing algorithm
    }

    public void delete(int value) {
        // Logic for deleting from a balanced tree
        root = deleteRecursive(root, value);
    }

    private TreeNode deleteRecursive(TreeNode currentNode, int value) {
        if (currentNode == null) {
            return null;
        }

        if (value == currentNode.getValue()) {
            // Node to be deleted found

            if (currentNode.getChildren().isEmpty()) {
                return null;
            } else {
                // Implement your logic to delete node and rebalance the tree
                // This is a placeholder
            }
        }

        for (TreeNode child : currentNode.getChildren()) {
            TreeNode result = deleteRecursive(child, value);
            if (result != null) {
                return result;
            }
        }
        return currentNode;
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
