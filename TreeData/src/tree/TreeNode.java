package tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	int value;
    List<TreeNode> children;

    public TreeNode(int value) {
        this.value = value;
        this.children = new ArrayList<>();
    }
    public int getValue() {
        return value;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public void removeChild(TreeNode child) {
        children.remove(child);
    }
}
