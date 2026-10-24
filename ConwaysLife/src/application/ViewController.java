package application;

import java.io.IOException;
import java.util.ArrayList;

import application.Classes.Board;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewController {
	private int speed=200;
	private float speedMult=0;
	private int generationsPassed = 0;
	private volatile boolean isPaused = true;
	float cellSize = 0;
	
	private Board simulationBoard;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Label currentSpeedMultText = new Label(Float.toString(speedMult));
	@FXML
	private Button playButton = new Button("Play");
	@FXML
	Label generationText = new Label();
	@FXML
	GridPane BoardGrid = new GridPane();
	
	
	
	public void createSimulationBoard(Board startBoard) {
		simulationBoard = startBoard;
		BoardGrid.getChildren().clear();
		cellSize = 650/simulationBoard.getSize();
		BoardGrid = createGridView();
		BoardGrid.setGridLinesVisible(true);
		
		
	}
	
	public GridPane createGridView() {
		boolean isCellAlive = false;
		BoardGrid.getChildren().clear();
		int cellAge = 0;
		
		for (int i=0; i< simulationBoard.getSize();i++) {
			for (int j = 0; j < simulationBoard.getSize(); j++) {
				
				isCellAlive = simulationBoard.getBoard()[j][i].getLife(); //why is it like that bruh
				cellAge = simulationBoard.getBoard()[j][i].getAge();
				if (isCellAlive) {
					if (cellAge <2) {
						BoardGrid.add(new Rectangle(cellSize,cellSize, Color.WHITE), i, j,1,1);
						
					} else if (cellAge>=2 && cellAge<5) {
						BoardGrid.add(new Rectangle(cellSize,cellSize, Color.YELLOW), i, j,1,1);
						
					}else if (cellAge >=5 && cellAge<10) {
						BoardGrid.add(new Rectangle(cellSize,cellSize, Color.ORANGE), i, j,1,1);
						
					}else if (cellAge >= 10 && cellAge<15) {
						BoardGrid.add(new Rectangle(cellSize,cellSize, Color.YELLOWGREEN), i, j,1,1);
						
					}else {
						BoardGrid.add(new Rectangle(cellSize,cellSize, Color.SLATEBLUE), i, j,1,1);
					}
				}else {
					BoardGrid.add(new Rectangle(cellSize,cellSize, Color.BLACK), i, j,1,1);
				}
			}
		}
		return BoardGrid;
	}
	
	public void newGeneration() {
		simulationBoard.newTurn();
		generationsPassed++;
	}
	
	public void Play(ActionEvent e) throws InterruptedException {
		if (isPaused) {
			playButton.setText("Pause");
			speedMult = 1;
			isPaused = false;
			simulate();
		}else {
			playButton.setText("Play");
			speedMult = 0;
			isPaused = true;
		}
		
		currentSpeedMultText.setText(Float.toString(speedMult));
	}
	
	public void Reset(ActionEvent event) throws IOException {
		isPaused = true;
		System.out.println("This button resets to start state");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Draw.fxml"));
		root = loader.load();
		
		DrawController DrawController = loader.getController();
		DrawController.updateViewBoard();
		DrawController.startOver(event);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void upVelocity(ActionEvent e){
		if (speedMult >=2.75) {
			speedMult = 3;
		}else{
			if (speedMult == 0) {
				isPaused = false;
				playButton.setText("Pause");
			}
			speedMult += 0.25;
		}
		currentSpeedMultText.setText(Float.toString(speedMult));
	}
	
	public void downVelocity(ActionEvent e) {
		System.out.println("This button slows down simulation");
		if (speedMult > 0) {
			speedMult -=0.25;
			if (speedMult ==0) {
				isPaused = true;
				playButton.setText("Play");
			}
		}else if(speedMult == 0 || (speedMult-0.25)<=0) {
			speedMult = 0;
		}
		
		currentSpeedMultText.setText(Float.toString(speedMult));
	}
	
	public void simulate() {
		Thread t1 = new Thread(new Runnable() {
			int waitSpeed = 0;
		    @Override
		    public void run() {
		    	while(!isPaused){
		    		newGeneration();
		    		Platform.runLater(new Runnable() {
		    		    @Override
		    		    public void run() {
		    		    	generationText.setText(Integer.toString(generationsPassed));
		    				BoardGrid = createGridView();
		    		    }
		    		});
		  			//printConsoleControlBoard();	
		  			waitSpeed = Math.round(speed/speedMult);
		  			try {
						Thread.sleep(waitSpeed);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    }
		});  
		t1.start();
		
		if (isPaused) {
			t1.interrupt();
		}else {
			t1.start();
		}
	}
	
	

}
