package tree;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class TreeNode {
    int value;
    List<TreeNode> children;
    private Circle circle;
    private List<Line> lines;
    private double x, y;

    public TreeNode(int value) {
        this.value = value;
        this.children = new ArrayList<>();
        this.circle = new Circle();
        this.lines = new ArrayList<>();
    }

    public Circle getCircle() {
        return circle;
    }

    public List<Line> getLines() {
        return lines;
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
