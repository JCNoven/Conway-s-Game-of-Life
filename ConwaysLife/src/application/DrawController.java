package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Classes.Board;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DrawController implements Initializable{
	private int size = 30; //set default 30, so make it easier
	private int birthRule = 3; //default 3, as conway's original game
	private int surviveRule = 23; // default 23, as conway's original game
	private Board drawBoard = new Board (size, birthRule, surviveRule);
	private float cellSize = 0;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField sizeField = new TextField();
	@FXML
	private TextField birthRuleField = new TextField();
	@FXML
	private TextField surviveRuleField = new TextField();
	@FXML
	private Label messageLabel = new Label();
	@FXML
	private GridPane BoardGrid = new GridPane();
	@FXML
	private ChoiceBox<String> someRuleChoices;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createRuleChoices();
		someRuleChoices.setOnAction(this::setRuleChoice);
		someRuleChoices.setValue("Conway's Game of life");
		updateViewBoard();
		startOver(null);
	}
	
	
	
	public void createRuleChoices() {
		ArrayList<String> RuleChoices = new ArrayList<>();
		
		RuleChoices.add((String) "Conway's Game of life");
		RuleChoices.add((String) "Coral"); //name by Carykh
		RuleChoices.add((String) "Ant Colony"); // name by Carykh B3/S234
		RuleChoices.add((String) "A world on Fire"); // name by Carykh B34/S23
		RuleChoices.add((String) "Blinkers"); // name by Adam P. Goucher (maybe?) Listed in his catalogue
		RuleChoices.add((String) "HighLife"); //B36/S23
		RuleChoices.add((String) "Day and Night"); // B3678/S34678
		RuleChoices.add((String) "Maze"); // B3/S12345
		RuleChoices.add((String) "Mazectric"); // B3/S1234
		RuleChoices.add((String) "Mazectric with Mice"); // B37/S1234
		RuleChoices.add((String) "Life without death"); // B37/S012345678
		RuleChoices.add((String) "Bacteria"); // B34/S456
		RuleChoices.add((String) "Diamoeba");//B35678/S5678
		RuleChoices.add((String) "Pedestrian Life"); //B3/S238
		RuleChoices.add((String) "Custom"); //so we can display it
		
		someRuleChoices.getItems().addAll(RuleChoices);	
	}
	
	public void setRuleChoice(ActionEvent event) {
		String ruleChoice = someRuleChoices.getValue();
		
		//get ready for the world's longest piece of shit switch!!!
		
		switch (ruleChoice) {
			case "Conway's Game of life":
				birthRule = 3;
				surviveRule = 23;
				break;
				
			case "Coral":
				birthRule = 3;
				surviveRule = 45678;
				break;
				
			case "Ant Colony":
				birthRule = 3;
				surviveRule = 234;
				break;
				
			case "A world on Fire":
				birthRule = 34;
				surviveRule = 23;
				break;
				
			case "Blinkers":
				birthRule = 345;
				surviveRule = 2;
				break;
				
			case "HighLife":
				birthRule = 36;
				surviveRule = 23;
				break;
				
			case "Day and Night": // B3678/S34678
				birthRule = 3678;
				surviveRule = 234678;
				break;
				
			case "Maze": // B3/S12345
				birthRule = 3;
				surviveRule = 12345;
				break;
				
			case "Mazectric": // B3/S1234
				birthRule = 3;
				surviveRule = 1234;
				break;
				
			case "Mazectric with Mice": // B37/S1234
				birthRule = 37;
				surviveRule = 12345;
				break;
				
			case "Life without death": // B3/S012345678
				birthRule = 3;
				surviveRule = 12345678;
				break;
				
			case "Bacteria": // B34/S456
				birthRule = 34;
				surviveRule = 456;
				break;
				
			case "Diamoeba": //B35678/S5678
				birthRule = 35678;
				surviveRule = 5678;
				break;
				
			case "Pedestrian Life": //B3/S238
				birthRule = 3;
				surviveRule = 238;
				break;
			default:
				birthRule = 3;
				surviveRule = 23;
				break;
		}
		
		birthRuleField.setText(Integer.toString(birthRule));
		surviveRuleField.setText(Integer.toString(surviveRule));
		messageLabel.setText("Don't forget to check your rulestring!");
	}
	
	
	public void updateViewBoard() {
		BoardGrid = createViewBoard();
		BoardGrid.setGridLinesVisible(false);
	}
		
	public GridPane createViewBoard() {
		cellSize = 650/drawBoard.getSize();
		boolean isCellAlive = false;
		BoardGrid.getChildren().clear();
		
		for (int i=0; i< drawBoard.getSize();i++) {
			for (int j = 0; j < drawBoard.getSize(); j++) {
				cellCreator(i,j);
			}
				
		}
		return BoardGrid;
		
	}
	
	public boolean changeCellLife(int x, int y) {
		return drawBoard.getBoard()[x][y].switchCurrentLifeValue();
	}
	
	public void cellCreator(int x, int y) {
		
		
		
		boolean isCellAlive = drawBoard.getBoard()[y][x].getLife(); //why is it like that bruh
		
		Rectangle cell = new Rectangle(cellSize,cellSize, Color.WHITE);				
		if (isCellAlive) {
			cell.setFill(Color.WHITE);
		}else {
			cell.setFill(Color.BLACK);
		}
		
		BoardGrid.add(cell, x, y,1,1);
		
		cell.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent event) {
		        int x = BoardGrid.getColumnIndex(cell);
		        int y = BoardGrid.getRowIndex(cell);
		        boolean life = changeCellLife(y,x);
		        if (life) {
		        	cell.setFill(Color.WHITE);
		        }else {
		        	cell.setFill(Color.BLACK);
		        } 
		    }
		});
		
	
		cell.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
		    public void handle(DragEvent event) {
		    	int x = BoardGrid.getColumnIndex(cell);
		        int y = BoardGrid.getRowIndex(cell);
		        boolean life = changeCellLife(y,x);
		        if (life) {
		        	cell.setFill(Color.WHITE);
		        }else {
		        	cell.setFill(Color.BLACK);
		        } 
		        event.consume();
		    }
		});
		
		
		cell.setOnMouseDragDone(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent event) {
		    	int x = BoardGrid.getColumnIndex(cell);
		        int y = BoardGrid.getRowIndex(cell);
		        boolean life = changeCellLife(y,x);
		        if (life) {
		        	cell.setFill(Color.WHITE);
		        }else {
		        	cell.setFill(Color.BLACK);
		        } 
		        event.consume();
		    }
		});
		
		
		
		cell.setOnDragDetected(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent event) {
		    	int x = BoardGrid.getColumnIndex(cell);
		        int y = BoardGrid.getRowIndex(cell);
		        boolean life = changeCellLife(y,x);
		        if (life) {
		        	cell.setFill(Color.WHITE);
		        }else {
		        	cell.setFill(Color.BLACK);
		        } 
		        event.consume();
		    }
		});
	}
		
	
	public void acceptSize(ActionEvent event) {
		try {
			size = Integer.parseInt(sizeField.getText());
			if (size<10 || size>100) {
				messageLabel.setText("Board size can only be between 10 and 100");
				size = 30; //set back to default
				sizeField.setText(Integer.toString(size));
			}else {
				messageLabel.setText("Let's go!");
			}
			
		} catch (NumberFormatException notNumber) {
			messageLabel.setText("Board Size can only be numbers");
			size = 30;
			sizeField.setText(Integer.toString(size));
		}
		catch (Exception e1) {
			messageLabel.setText("Unknown error while setting board size");
			size = 30;
			sizeField.setText(Integer.toString(size));
		}
		
		resizeBoard(size);
		messageLabel.setText("Board size saved!");
		
	}
	
	public void acceptRules(ActionEvent event) {
		try {
			birthRule = Integer.parseInt(birthRuleField.getText());
			surviveRule = Integer.parseInt(surviveRuleField.getText());
			messageLabel.setText("Let's go!");
			
		} catch (NumberFormatException notNumber) {
			messageLabel.setText("Rules can only be numbers");
			birthRule = 3;
			surviveRule = 23;
			birthRuleField.setText(Integer.toString(birthRule));
			surviveRuleField.setText(Integer.toString(surviveRule));
		}
		catch (Exception e1) {
			messageLabel.setText("Unknown error while setting board size");
			birthRule = 3;
			surviveRule = 23;
			birthRuleField.setText(Integer.toString(birthRule));
			surviveRuleField.setText(Integer.toString(surviveRule));
		}
		
		setRules(birthRule, surviveRule);
		messageLabel.setText("Rulestrings saved, let's go!");
	}
	
	public void resizeBoard(int newSize) {
		drawBoard.newBoardSize(newSize);
		updateViewBoard();
		BoardGrid.setGridLinesVisible(true);
	}
	
	public void setBirthRule(int birth) {
		drawBoard.setB(drawBoard.extractRuleArray(birth));
	}
	
	public void setSurviveRule(int surv) {
		drawBoard.setS(drawBoard.extractRuleArray(surv));
	}
	
	public void setRules(int birth, int surv) {
		setBirthRule(birth);
		setSurviveRule(surv);
	}
	
	public void startOver(ActionEvent event) {
		drawBoard.eraseInformation();
		updateViewBoard();
		BoardGrid.setGridLinesVisible(true);
	
	}
	
	public void randomizeStart(ActionEvent event) {
		drawBoard.randomStart();
		updateViewBoard();
		BoardGrid.setGridLinesVisible(true);
		
	}
	
	public void startSimulation(ActionEvent event) throws IOException{
		
		System.out.println("This button takes you to simulation screen");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("View.fxml"));
		root = loader.load();
		
		ViewController viewController = loader.getController();
		viewController.createSimulationBoard(drawBoard);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		
	}

	
	
	

}
