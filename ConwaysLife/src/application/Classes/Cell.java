package application.Classes;

public class Cell {
    private int x; //x position
    private int y; //y position
    private boolean life; //is the cell alive in current turn
    private boolean nextTurn; //here goes the computed life value for next turn
    private int age; //how many turns has it been alive?

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        life = false;
        nextTurn = false; //temporary value, just so it is initialized lol
        age = 0;
    }

    //setters
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setLife(boolean life) {
        this.life = life;
    }
    public void setNextTurn(boolean nextTurn) {
        this.nextTurn = nextTurn;
    }

    //getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean getLife(){
        return life;
    }
    public boolean getNextTurn(){
        return nextTurn;
    }
    public int getAge() {
        return age;
    }

    //other
    
    public boolean switchCurrentLifeValue() {
    	life = !life;
    	return life;
    }
    public void switchLifeTurn(){
        life = nextTurn;
    }
    public void age(){
        age++;
    }
}
