package application.Classes;

public class VisualController {
    private Board board;

    public VisualController(Board board) {
        this.board = board;
    }

    public void printBoard(){
        for(int i = 0; i<board.getSize(); i++){
            for (int j = 0; j<board.getSize(); j++){
                if (!board.getBoard()[i][j].getLife()){
                    System.out.print("  ");
                }else if (board.getBoard()[i][j].getLife()){
                    System.out.print("[@]");
                }
            }
            System.out.println();
        }
    }
    
}
