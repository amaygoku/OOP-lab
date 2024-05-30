package views;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.animation.FillTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.StrokeTransition;
import javafx.animation.Timeline;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;




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
    private TreeNode highlightedNode; // New attribute to store the node to be highlighted
    private List<TreeNode> highlightedNodes = new ArrayList<>(); // List of nodes to highlight
    private Timeline timeline; // Timeline for the traversal animation
    private Set<TreeNode> permanentlyHighlightedNodes = new HashSet<>();
    private Set<Line> highlightedEdges = new HashSet<>();

    

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
	    highlightedNodes.clear(); // Clear highlighted nodes
	    permanentlyHighlightedNodes.clear(); // Clear permanently highlighted nodes
	    highlightedEdges.clear(); // Clear highlighted edges
	    if (tree instanceof BalancedTree || tree instanceof BalancedBinaryTree) {
	        TextInputDialog dialog = new TextInputDialog();
	        dialog.setTitle("Maximum Distance");
	        dialog.setHeaderText("Enter the maximum difference in distance:");
	        dialog.setContentText("Maximum difference:");
	        dialog.showAndWait().ifPresent(maxDiff -> {
	            try {
	                int maxDifference = Integer.parseInt(maxDiff);
	                ((BalancedTree) tree).setMaximumDifference(maxDifference);
	                createRandomTreeWithMaxDifference();
	            } catch (NumberFormatException e) {
	                showAlert("Invalid Input", "Please enter a valid integer.");
	            }
	        });
	    } else {
	        createRandomTreeWithoutMaxDifference();
	    }
	}

	private void createRandomTreeWithMaxDifference() {
	    highlightedEdges.clear(); // Clear highlighted edges
	    TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Create Random Tree");
	    dialog.setHeaderText("Enter number of values:");
	    dialog.setContentText("Number of values:");

	    dialog.showAndWait().ifPresent(numValues -> {
	        try {
	            int numberOfValues = Integer.parseInt(numValues);
	            ((BalancedTree) tree).createRandomTree(numberOfValues);
	            drawTree();
	        } catch (NumberFormatException e) {
	            showAlert("Invalid Input", "Please enter a valid integer.");
	        }
	    });
	}

	private void createRandomTreeWithoutMaxDifference() {
	    highlightedEdges.clear(); // Clear highlighted edges
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
    private double getNodePositionX(TreeNode node) {
        return node.getX();
    }

    private double getNodePositionY(TreeNode node) {
        return node.getY();
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
        dialog.setTitle("Search Node");
        dialog.setHeaderText("Search for a node");
        dialog.setContentText("Please enter a value:");

        dialog.showAndWait().ifPresent(value -> {
            try {
                int intValue = Integer.parseInt(value);
                TreeNode result = tree.search(intValue);
                if (result != null) {
                    highlightedNode = result; // Set the highlighted node
                    showAlert("Search Result", "Node found with value: " + result.getValue());
                } else {
                    highlightedNode = null; // No node to highlight
                    showAlert("Search Result", "Node not found.");
                }
                drawTree(); // Redraw the tree to reflect the highlighted node
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid integer.");
            }
        });
    }

    
    private void updateNode() {}
    
    private void traverseNode() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("BFS", "DFS");
        dialog.setTitle("Traverse Nodes");
        dialog.setHeaderText("Select traversal method");
        dialog.setContentText("Choose traversal method:");

        dialog.showAndWait().ifPresent(selected -> {
            highlightedNodes.clear();
            permanentlyHighlightedNodes.clear();
            highlightedEdges.clear(); // Clear highlighted edges
            if (selected.equals("BFS")) {
                bfsTraversal(tree.getRoot());
            } else if (selected.equals("DFS")) {
                dfsTraversal(tree.getRoot());
            }
            animateTraversal();
        });
    }

    private void bfsTraversal(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            highlightedNodes.add(node);
            queue.addAll(node.getChildren());
        }
    }

    private void dfsTraversal(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            highlightedNodes.add(node);
            Collections.reverse(node.getChildren()); // To maintain correct order
            stack.addAll(node.getChildren());
        }
    }


    
    
    
    private void animateTraversal() {
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline();
        for (int i = 0; i < highlightedNodes.size(); i++) {
            TreeNode node = highlightedNodes.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i), e -> {
                permanentlyHighlightedNodes.add(node);
                drawTree(); // Redraw tree to highlight nodes
            });
            timeline.getKeyFrames().add(keyFrame);

            if (i > 0) {
                TreeNode parentNode = highlightedNodes.get(i - 1);
                TreeNode childNode = highlightedNodes.get(i);
                KeyFrame edgeKeyFrame = new KeyFrame(Duration.seconds(i), e -> {
                    Line edge = new Line(getNodePositionX(parentNode), getNodePositionY(parentNode), getNodePositionX(childNode), getNodePositionY(childNode));
                    highlightedEdges.add(edge);
                    drawTree(); // Redraw tree to highlight edges
                });
                timeline.getKeyFrames().add(edgeKeyFrame);
            }
        }
        timeline.play();
    }

    private boolean isHighlightedEdge(Line line) {
        for (Line edge : highlightedEdges) {
            if ((edge.getStartX() == line.getStartX() && edge.getStartY() == line.getStartY() &&
                 edge.getEndX() == line.getEndX() && edge.getEndY() == line.getEndY()) ||
                (edge.getStartX() == line.getEndX() && edge.getStartY() == line.getEndY() &&
                 edge.getEndX() == line.getStartX() && edge.getEndY() == line.getStartY())) {
                return true;
            }
        }
        return false;
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

        node.setX(x);
        node.setY(y);

        boolean parentNodeHighlighted = permanentlyHighlightedNodes.contains(node); // Check if parent node is highlighted

        for (TreeNode child : node.getChildren()) {
            double newX = x + (child == node.getChildren().get(0) ? -xOffset : xOffset);
            double newY = y + yOffset;
            Line line = new Line(x, y, newX, newY);

            boolean childNodeHighlighted = permanentlyHighlightedNodes.contains(child); // Check if child node is highlighted

            if (parentNodeHighlighted && childNodeHighlighted) {
                line.setStroke(Color.RED); // Highlight the edge if both parent and child nodes are highlighted
            }

            treePane.getChildren().add(line);
            drawTreeRecursive(child, newX, newY, xOffset / 2, yOffset);
        }

        Circle circle = new Circle(x, y, 15);
        if (parentNodeHighlighted) {
            circle.setFill(Color.YELLOW); // Keep the node highlighted
        } else {
            circle.setFill(Color.WHITE);
        }
        circle.setStroke(Color.BLACK);
        treePane.getChildren().add(circle);

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
        if (permanentlyHighlightedNodes.contains(node)) {
            circle.setFill(Color.YELLOW); // Keep the node highlighted
        } else {
            circle.setFill(Color.WHITE);
        }
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
