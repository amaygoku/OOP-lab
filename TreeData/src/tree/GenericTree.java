package tree;

import java.util.LinkedList;
import java.util.Queue;

public class GenericTree implements Tree {
    private TreeNode root;

    public GenericTree() {
        this.root = null;
    }

    @Override
    public void createRandomTree(int numberOfValues) {
        for (int i = 0; i < numberOfValues; i++) {
            int parentValue = (int) (Math.random() * 100); // Random parent value generation, adjust range as needed
            int newValue = (int) (Math.random() * 100); // Random new value generation, adjust range as needed

            // If it's the first node, it becomes the root
            if (root == null) {
                root = new TreeNode(newValue);
            } else {
                // Insert new value as a child of the randomly selected parent value
                insert(parentValue, newValue);
            }
        }
    }

    @Override
    public void insert(int parentValue, int newValue) {
        if (root == null) {
            root = new TreeNode(newValue);
            return;
        }

        TreeNode parentNode = search(root, parentValue);
        if (parentNode != null) {
            TreeNode newNode = new TreeNode(newValue);
            parentNode.addChild(newNode);
        } else {
            System.out.println("Parent value not found in the tree.");
        }
    }

    @Override
    public void delete(int value) {
        // Logic for deleting from a generic tree
        if (root == null) {
            return;
        }
        if (root.getValue() == value) {
            root = null; // If the root is to be deleted, set the tree to empty
            return;
        }
        deleteRecursive(root, value);
    }

    private boolean deleteRecursive(TreeNode currentNode, int value) {
        for (TreeNode child : currentNode.getChildren()) {
            if (child.getValue() == value) {
                currentNode.removeChild(child);
                return true;
            } else {
                boolean deleted = deleteRecursive(child, value);
                if (deleted) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TreeNode search(int value) {
        return search(root, value);
    }

    private TreeNode search(TreeNode currentNode, int value) {
        if (currentNode == null) {
            return null;
        }

        if (currentNode.getValue() == value) {
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

    @Override
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
