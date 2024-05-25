package tree;

import java.util.LinkedList;
import java.util.Queue;

public class BalancedBinaryTree implements Tree {
    private TreeNode root;

    public BalancedBinaryTree() {
        this.root = null;
    }

    public void createRandomTree(int numberOfValues) {
        for (int i = 0; i < numberOfValues; i++) {
            int value = (int) (Math.random() * 100); // Random value generation
            insert(value);
        }
    }

    public void insert(int newValue) {
        if (root == null) {
            root = new TreeNode(newValue);
        } else {
            insertBalanced(root, newValue);
        }
    }

    private void insertBalanced(TreeNode node, int newValue) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.poll();

            if (currentNode.getChildren().size() < 2) {
                currentNode.addChild(new TreeNode(newValue));
                return;
            } else {
                for (TreeNode child : currentNode.getChildren()) {
                    queue.add(child);
                }
            }
        }
    }

    public void delete(int value) {
        // Logic for deleting from a balanced binary tree
    }

    public TreeNode search(int value) {
        return search(root, value);
    }

    private TreeNode search(TreeNode node, int value) {
        if (node == null) {
            return null;
        }
        if (node.getValue() == value) {
            return node;
        }

        for (TreeNode child : node.getChildren()) {
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

	@Override
	public void insert(int parentValue, int newValue) {
		// TODO Auto-generated method stub
		
	}
}
