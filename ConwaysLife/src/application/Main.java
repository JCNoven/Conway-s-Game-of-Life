package application;
	
import application.Classes.Board;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			Parent root = loader.load();	
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
		/*15/10/26 Note to self
		 I finished coding the game itself a few days ago, my job hasn't been giving me much time to keep going tho
		 I will finally start the UI today. Properly this time. I feel nervous as shit lol
		 I just need to do things one step at a time, just like when I was first learning how to code.
		 I can't just do some pseudocode first and then code it all in one sitting like i can now, 
		 this is a new skill i dont know.
		 I just need to calm down. One step at a time. */
		
		
		
		//THIS CODE BELOW WAS USED TO MAKE SURE THE GAME OF LIFE WORKS
		//IT DOES :3
		
		/*System.out.println("\n\nHello! I am a very cute computer and I love myself :3\n\n\n");
        Board ConwaysLife = new Board(33, 3, 23);

        //VisualController UI = new VisualController(ConwaysLife);

        ConwaysLife.randomStart(10);
        int x = 0;

        for(int i = 0; i< ConwaysLife.getSize(); i++){
            for (int j = 0; j<ConwaysLife.getSize(); j++){
                if (ConwaysLife.getBoard()[i][j].getLife()) {
                    System.out.println("Alive");   
                }else{
                    System.out.println("Dead");
                }

           }      
        }
        
        
        while (true){

            System.out.println("========= GENERATION " + x + " ==============");
            //UI.updateBoard(ConwaysLife.getBoard());
            for(int i = 0; i< ConwaysLife.getSize(); i++){ //this func could be done linearly
                for (int j = 0; j<ConwaysLife.getSize(); j++){
                    if (ConwaysLife.getBoard()[i][j].getLife()) {
                        System.out.print("⬜");  
                        //System.out.print(ConwaysLife.getBoard()[i][j].getAge());

                    }else{
                        //System.out.print(" ");
                        System.out.print("⬛");
                    }
                }
                System.out.println();
            }
            ConwaysLife.newTurn();
            //UI.printBoard();
            try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            x++;
        }*/
    }
	
	
}
