package application.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    int size; //size of side
    Cell[][] board; //matrix of cells
    int generations = 0; //total generations played
    int speedMult=1; //for time intervals? Might move it elsewhere
    //Rulestring
    List<Integer> B;
    List<Integer> S;
    
    public Board() {}
    public Board(Cell[][] newBoard, int birth, int survive) {
    	board = newBoard;
    	B = extractRuleArray(birth);
        S = extractRuleArray(survive);
    	
    }
    

    public Board(int side, int birth, int survive){
        size = side;
        board = new Cell[size][size]; //will always be a square, ig

        Random rand = new Random();

        for(int i = 0; i<size; i++){ //could be done linearly?
            for (int j = 0; j<size; j++){
                Cell defaultCell = new Cell(i,j);
                board[i][j] = defaultCell;
                //board[i][j].setLife(rand.nextBoolean());
            }
        }


        B = extractRuleArray(birth);
        S = extractRuleArray(survive);
    }

    //setters and getters. Don't know how many i'll use, better safe than sorry ig
    public Cell[][] getBoard() {
        return board;
    }
    public int getGenerations() {
        return generations;
    }
    public int getSize() {
        return size;
    }
    public int getSpeedMult() {
        return speedMult;
    }
    public List<Integer> getB() {
        return B;
    }
    public List<Integer> getS() {
        return S;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }
    public void setGenerations(int generations) {
        this.generations = generations;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public void setSpeedMult(int speedMult) {
        this.speedMult = speedMult;
    }
    public void setB(List<Integer> b) {
        B = b;
    }
    public void setS(List<Integer> s) {
        S = s;
    }

    //ruleString

    public int validateRule(int c) throws IllegalArgumentException{
        if (c>9) throw new IllegalArgumentException("Invalid rule");
        int num = c;
        return num;
    }

    public List<Integer> extractRuleArray(int r){
        int rule=0;
        List<Integer> ruleSet = new ArrayList<>();
        /*for ( char i : r.toCharArray()) {
            rule = (int) i;
            ruleSet.add(rule);
        }*/
            while(r > 0) {
                rule = r % 10;
                validateRule(rule);
                ruleSet.add(rule);
                r /= 10;
            }

        return ruleSet;
    }

    //start game in random conditions

    public void randomStart(){

        Random rand = new Random();
        
        for(int i = 0; i<size; i++){
            for (int j = 0; j<size; j++){
                board[i][j].setLife(rand.nextBoolean());
            }
        }
    }

    // create new board of given size
    public void newBoardSize(int s){
        size = s;
        board = new Cell[size][size];

        for(int i = 0; i<size; i++){
            for (int j = 0; j<size; j++){
                Cell defaultCell = new Cell(i,j);
                board[i][j] = defaultCell;
                //board[i][j].setLife(rand.nextBoolean());
            }
        }

    }

    //to look at next turn

    public int countAliveNeigbours(int x, int y){

        int count = 0;

        for(int i=-1; i<=1 ;i++){
            for(int j=-1;j<=1;j++){
                int posX = x+i;
                int posY = y+j;

                if ((i!=0 || j!=0)
                    &&(posX>=0 && posX<size) 
                    &&(posY>=0 && posY<size)) {
                    
                    if (board[posX][posY].getLife()) count++;
                }
            }
        }
        return count;

    }

    public boolean willSurviveNextTurn(int x, int y){
        int count = 0;
        count = countAliveNeigbours(x, y);

        for (Integer rule : S) {
            if(count==rule) return true;
        }
       /*if (count==2) {
            return true;
       }

       if (count == 3) {
            return true;
       }*/

        return false;
    }

    public boolean willBornNextTurn(int x, int y){
        int count = 0;

        count = countAliveNeigbours(x, y);

        for (Integer rule : B) {
            if(count==rule) return true;
        }

        /*if (count == 3) {
            return true;
        }*/

        return false;
    }

    public void newTurn(){ //this is a O(n²), where n is size(), code... i wonder if i can make it better?
        boolean isAliveNext=false;
        generations++;
        
        for (int row=0; row<size; row++) {
            for(int col=0; col<size; col++){ //compute all states for next turn
                if (board[row][col].getLife()){

                    isAliveNext = willSurviveNextTurn(row, col);
                    board[row][col].setNextTurn(isAliveNext);
                    if (!isAliveNext) board[row][col].setAge(0);

                }else{
                    board[row][col].setNextTurn(willBornNextTurn(row, col));
                }
            }
            
        }

        for (int row=0; row<size; row++) { //update all states after analysis.
            for(int col=0; col<size; col++){
                board[row][col].switchLifeTurn();
                if (board[row][col].getLife()) board[row][col].age();
            }
            
        }

    }

    //to be used in the real ui!!

    public void switchCellLife(int x, int y){ //in draw mode, each cell is a button that calls this func when pressed
        board[x][y].setLife(!board[x][y].getLife());
    }

    public void eraseInformation(){ //cleans up any alive cells on board
        for (int i = 0; i<size; i++){
            for (int j = 0; j<size; j++){
                board[i][j].setLife(false);
            }
        }
    }

    public void importConfigMatrix(Cell[][] configMatrix){
        if (configMatrix.length>size) {
            newBoardSize(configMatrix.length);   
        }

        int distance = size-configMatrix.length;
        int placeX = 0;
        int placeY = 0;

        eraseInformation(); //clean up board

        for (int i = 0; i<configMatrix.length; i++){
            placeX = i + distance;

            for (int j = 0; j<configMatrix.length; j++){
                placeY = j + distance;

                board[placeX][placeY] = configMatrix[i][j];
            }
        }
    }

    //NOTA, PREGUNTARLE A NOEL CÓMO ES MEJOR MODELAR PARES ORDENADOS.

        /*public void importConfigArray(some array){

            sea (x,y) un par ordenado en array, ir a posicion board[x][y] y llamar .setLife(true)
        }*/
}


