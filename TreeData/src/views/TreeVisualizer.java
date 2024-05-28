package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tree.*;

public class TreeVisualizer {
    private Pane treePane;
    private Tree tree;
    private String treeType;
    private BorderPane mainLayout;
    private Stage primaryStage;
    private Scene mainMenuScene;

    public TreeVisualizer(String treeType, Stage primaryStage, Scene mainMenuScene) {
    	this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
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
                tree = new BalancedTree(0); // Assume this is a balanced tree implementation
                break;
            case "BalancedBinaryTree":
                tree = new BalancedBinaryTree(0);
                break;
        }
    }

    private void setupUI() {
        treePane = new Pane();
        treePane.setPrefSize(800, 600);

        VBox controls = new VBox(10);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.TOP_LEFT);
        controls.setStyle("-fx-background-color: #98FB98;");
        
        Button createButton = new Button("Create");
        createButton.setOnAction(e -> createRandomTree());

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(e -> insertNode());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteNode());

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchNode());
        
        Button traverseButton = new Button("Traverse");
        traverseButton.setOnAction(e -> traverseNode());
        
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateNode());
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> back());

        // Styling the buttons
        for (Button button : new Button[]{createButton, insertButton, deleteButton, searchButton, traverseButton, updateButton,backButton}) {
            button.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-size: 16;");
            button.setPadding(new Insets(20)); // Increased padding
            button.setMinHeight(50); // Set minimum height
            button.setMaxWidth(Double.MAX_VALUE);
            button.setAlignment(Pos.CENTER);
        }

        controls.getChildren().addAll(createButton, insertButton, deleteButton, searchButton,traverseButton, updateButton,backButton);

        // Create the bottom bar with Undo and Redo buttons
        HBox bottomBar = new HBox(20); // Spacing between buttons
        bottomBar.setPadding(new Insets(5)); // Padding around the HBox
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setStyle("-fx-background-color: lightgray;");

        Button undoButton = new Button("Undo");
        undoButton.setStyle("-fx-font-size: 20;");
        undoButton.setPadding(new Insets(5));
        undoButton.setMinHeight(20);
        undoButton.setMinWidth(100); // Minimum width for the button
        undoButton.setAlignment(Pos.CENTER);

        Button redoButton = new Button("Redo");
        redoButton.setStyle("-fx-font-size: 20;");
        redoButton.setPadding(new Insets(5));
        redoButton.setMinHeight(20);
        redoButton.setMinWidth(100); // Minimum width for the button
        redoButton.setAlignment(Pos.CENTER);

        Button pauseButton = new Button("Pause");
        pauseButton.setStyle("-fx-font-size: 20;");
        pauseButton.setPadding(new Insets(5));
        pauseButton.setMinHeight(20);
        pauseButton.setMinWidth(100); // Minimum width for the button
        pauseButton.setAlignment(Pos.CENTER);

        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: 20;");
        continueButton.setPadding(new Insets(5));
        continueButton.setMinHeight(20);
        continueButton.setMinWidth(100); // Minimum width for the button
        continueButton.setAlignment(Pos.CENTER);

        Button backwardButton = new Button("Backward");
        backwardButton.setStyle("-fx-font-size: 20;");
        backwardButton.setPadding(new Insets(5));
        backwardButton.setMinHeight(20);
        backwardButton.setMinWidth(100); // Minimum width for the button
        backwardButton.setAlignment(Pos.CENTER);

        Button forwardButton = new Button("Forward");
        forwardButton.setStyle("-fx-font-size: 20;");
        forwardButton.setPadding(new Insets(5));
        forwardButton.setMinHeight(20);
        forwardButton.setMinWidth(100); // Minimum width for the button
        forwardButton.setAlignment(Pos.CENTER);

        bottomBar.getChildren().addAll(undoButton, redoButton, pauseButton, continueButton, backwardButton, forwardButton);

        mainLayout = new BorderPane();
        mainLayout.setCenter(treePane);
        mainLayout.setRight(controls);
        mainLayout.setBottom(bottomBar);
    }

    private void back() {
    	
        primaryStage.setScene(mainMenuScene);
        primaryStage.setFullScreen(true); // Set full screen mode

    }

	public BorderPane getView() {
        return mainLayout;
    }
    
    private void createRandomTree() {
        if (tree instanceof BalancedTree || tree instanceof BalancedBinaryTree) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.initOwner(primaryStage);
            dialog.setTitle("Maximum Distance");
            dialog.setHeaderText("Enter the maximum difference in distance:");
            dialog.setContentText("Maximum difference:");
            dialog.showAndWait().ifPresent(maxDiff -> {
                try {
                    int maxDifference = Integer.parseInt(maxDiff);
                    if (tree instanceof BalancedTree) {
                        ((BalancedTree) tree).setMaximumDifference(maxDifference);
                    } else if (tree instanceof BalancedBinaryTree) {
                        ((BalancedBinaryTree) tree).setMaximumDifference(maxDifference);
                    }

                    createRandomTreeWithMaxDifference();
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid integer.");
                }
            });
        } else {
            // For other types of trees, simply create a random tree without asking for the maximum difference
            createRandomTreeWithoutMaxDifference();
        }
    }

    private void createRandomTreeWithMaxDifference() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(primaryStage);
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

    private void createRandomTreeWithoutMaxDifference() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(primaryStage);
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
        dialog.initOwner(primaryStage);
        dialog.setTitle("Insert Node");
        dialog.setHeaderText("Insert a new node");

        // Check if the root node exists
        if (tree.getRoot() == null) {
            if (tree instanceof BalancedTree || tree instanceof BalancedBinaryTree) {
                TextInputDialog maxDiffDialog = new TextInputDialog();
                maxDiffDialog.initOwner(primaryStage);                
                maxDiffDialog.setTitle("Maximum Difference");
                maxDiffDialog.setHeaderText("Enter the maximum difference in distance:");

                maxDiffDialog.showAndWait().ifPresent(maxDiffInput -> {
                    try {
                        int maxDifference = Integer.parseInt(maxDiffInput.trim());

                        if (tree instanceof BalancedTree) {
                            ((BalancedTree) tree).setMaximumDifference(maxDifference);
                        } else if (tree instanceof BalancedBinaryTree) {
                            ((BalancedBinaryTree) tree).setMaximumDifference(maxDifference);
                        }

                        dialog.setContentText("Enter value for the root node:");
                        dialog.showAndWait().ifPresent(rootValueInput -> {
                            try {
                                int rootValue = Integer.parseInt(rootValueInput.trim());
                                if (tree instanceof BalancedTree) {
                                    ((BalancedTree) tree).insert(0, rootValue); // Inserting as root
                                } else if (tree instanceof BalancedBinaryTree) {
                                    ((BalancedBinaryTree) tree).insert(0, rootValue); // Inserting as root
                                }
                                drawTree();
                            } catch (NumberFormatException e) {
                                showAlert("Invalid Input", "Please enter a valid integer value for the root node.");
                            }
                        });

                    } catch (NumberFormatException e) {
                        showAlert("Invalid Input", "Please enter a valid integer value for the maximum difference.");
                    }
                });
            } else {
                dialog.setContentText("Enter value for the root node:");
                dialog.showAndWait().ifPresent(input -> {
                    try {
                        int newValue = Integer.parseInt(input.trim());
                        if (tree instanceof GenericTree) {
                            ((GenericTree) tree).insert(0, newValue); // Inserting as root
                        } else if (tree instanceof BinaryTree) {
                            ((BinaryTree) tree).insert(0, newValue); // Inserting as root
                        } else {
                            showAlert("Unsupported Operation", "Insert operation is not supported for this tree type.");
                        }
                        drawTree();
                    } catch (NumberFormatException e) {
                        showAlert("Invalid Input", "Please enter a valid integer value for the root node.");
                    }
                });
            }
        } else {
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
                        } else if (tree instanceof BalancedTree) {
                            ((BalancedTree) tree).insert(parentValue, newValue);
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
    }



    private void deleteNode() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(primaryStage);
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
        dialog.initOwner(primaryStage);
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
    
    private void traverseNode() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("BFS", "DFS");
        dialog.initOwner(primaryStage);
        dialog.setTitle("Traverse Nodes");
        dialog.setHeaderText("Select traversal method");
        dialog.setContentText("Choose traversal method:");

        dialog.showAndWait().ifPresent(selected -> {
            if (selected.equals("BFS")) {
                bfsTraversal();
            } else if (selected.equals("DFS")) {
                dfsTraversal();
            }
        });
    }
    
    private void updateNode() {}

    private void bfsTraversal() {
    	tree.bfsTraverse();
    }

    private void dfsTraversal() {
    	tree.dfsTraverse();
    }



    private void drawTree() {
        treePane.getChildren().clear();
        if (tree instanceof BinaryTree || tree instanceof BalancedBinaryTree) {
            TreeNode root = tree.getRoot();
            if (root != null) {
                drawTreeRecursive(root, treePane.getWidth() / 2, 30, treePane.getWidth() / 4, 50);
            }
        } else if (tree instanceof GenericTree || tree instanceof BalancedTree) {
            TreeNode root =  tree.getRoot();
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

        // Display the weight of the node
        Text text = new Text(x - 5, y + 5, String.valueOf(node.getValue()));
        treePane.getChildren().add(text);
    }


    private void drawGenericTree(TreeNode node, double x, double y, double xOffset, double yOffset) {
        if (node == null) return;

        double angleStep = 150.0 / node.getChildren().size();
        double angle = 30;

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
        
        Text text = new Text(x - 5, y + 5, String.valueOf(node.getValue()));
        treePane.getChildren().add(text);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(primaryStage); // Set the owner to the primary stage
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
