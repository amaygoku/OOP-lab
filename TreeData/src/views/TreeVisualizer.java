package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import tree.*;

public class TreeVisualizer {
    private Pane treePane;
    private Tree tree;
    private String treeType;
    private BorderPane mainLayout;

    public TreeVisualizer(String treeType) {
        this.treeType = treeType;
        initializeTree();
        setupUI();
    }

    private void initializeTree() {
        switch (treeType) {
            case "BinaryTree":
                tree = new BinaryTree();
                break;
            case "GenericTree":
                tree = new GenericTree();
                break;
            case "BalancedTree":
                tree = new BalancedBinaryTree(); // Assume this is a balanced tree implementation
                break;
            case "BalancedBinaryTree":
                tree = new BalancedBinaryTree();
                break;
        }
    }

    private void setupUI() {
        treePane = new Pane();
        treePane.setPrefSize(800, 600);

        VBox controls = new VBox(10);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.TOP_LEFT);
        controls.setStyle("-fx-background-color: lightpink;");
        
        Button createButton = new Button("Create");
        createButton.setOnAction(e -> createRandomTree());

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(e -> insertNode());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteNode());

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchNode());


        // Styling the buttons
        for (Button button : new Button[]{createButton, insertButton, deleteButton, searchButton}) {
            button.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-size: 16;");
            button.setMaxWidth(Double.MAX_VALUE);
            button.setAlignment(Pos.CENTER);
        }

        controls.getChildren().addAll(createButton, insertButton, deleteButton, searchButton);

        mainLayout = new BorderPane();
        mainLayout.setCenter(treePane);
        mainLayout.setRight(controls);
    }

    public BorderPane getView() {
        return mainLayout;
    }
    
    private void createRandomTree() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Random Tree");
        dialog.setHeaderText("Enter number of values:");
        dialog.setContentText("Number of values:");

        dialog.showAndWait().ifPresent(numValues -> {
            try {
                int numberOfValues = Integer.parseInt(numValues);
                tree.createRandomTree(numberOfValues);
                drawTree();
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid integer.");
            }
        });
    }

    private void insertNode() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Insert Node");
        dialog.setHeaderText("Insert a new node");
        dialog.setContentText("Enter parent value and new value (comma-separated):");

        dialog.showAndWait().ifPresent(input -> {
            try {
                String[] values = input.split(",");
                if (values.length == 2) {
                    int parentValue = Integer.parseInt(values[0].trim());
                    int newValue = Integer.parseInt(values[1].trim());
                    if (tree instanceof GenericTree) {
                        ((GenericTree) tree).insert(parentValue, newValue);
                    } else if (tree instanceof BinaryTree) {
                        ((BinaryTree) tree).insert(parentValue, newValue);
                    } else if (tree instanceof BalancedBinaryTree) {
                        ((BalancedBinaryTree) tree).insert(parentValue, newValue);
                    } else {
                        showAlert("Unsupported Operation", "Insert operation is not supported for this tree type.");
                    }
                    drawTree();
                } else {
                    showAlert("Invalid Input", "Please enter two comma-separated values.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid integer values.");
            }
        });
    }

    private void deleteNode() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Node");
        dialog.setHeaderText("Delete a node");
        dialog.setContentText("Please enter a value:");

        dialog.showAndWait().ifPresent(value -> {
            try {
                int intValue = Integer.parseInt(value);
                tree.delete(intValue);
                drawTree();
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid integer.");
            }
        });
    }

    private void searchNode() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Node");
        dialog.setHeaderText("Search for a node");
        dialog.setContentText("Please enter a value:");

        dialog.showAndWait().ifPresent(value -> {
            try {
                int intValue = Integer.parseInt(value);
                TreeNode result = tree.search(intValue);
                if (result != null) {
                    showAlert("Search Result", "Node found with value: " + result.getValue());
                } else {
                    showAlert("Search Result", "Node not found.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid integer.");
            }
        });
    }

    private void drawTree() {
        treePane.getChildren().clear();
        if (tree instanceof BinaryTree || tree instanceof BalancedBinaryTree) {
            TreeNode root = ((BinaryTree) tree).getRoot();
            if (root != null) {
                drawTreeRecursive(root, treePane.getWidth() / 2, 30, treePane.getWidth() / 4, 50);
            }
        } else if (tree instanceof GenericTree) {
            TreeNode root = ((GenericTree) tree).getRoot();
            if (root != null) {
                drawGenericTree(root, treePane.getWidth() / 2, 30, treePane.getWidth() / 4, 50);
            }
        }
    }

    private void drawTreeRecursive(TreeNode node, double x, double y, double xOffset, double yOffset) {
        if (node == null) return;

        for (TreeNode child : node.getChildren()) {
            double newX = x + (child == node.getChildren().get(0) ? -xOffset : xOffset);
            double newY = y + yOffset;
            Line line = new Line(x, y, newX, newY);
            treePane.getChildren().add(line);
            drawTreeRecursive(child, newX, newY, xOffset / 2, yOffset);
        }

        Circle circle = new Circle(x, y, 15);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        treePane.getChildren().add(circle);
    }

    private void drawGenericTree(TreeNode node, double x, double y, double xOffset, double yOffset) {
        if (node == null) return;

        double angleStep = 360.0 / node.getChildren().size();
        double angle = 0;

        for (TreeNode child : node.getChildren()) {
            double newX = x + xOffset * Math.cos(Math.toRadians(angle));
            double newY = y + yOffset * Math.sin(Math.toRadians(angle));
            Line line = new Line(x, y, newX, newY);
            treePane.getChildren().add(line);
            drawGenericTree(child, newX, newY, xOffset / 2, yOffset);
            angle += angleStep;
        }

        Circle circle = new Circle(x, y, 15);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        treePane.getChildren().add(circle);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
