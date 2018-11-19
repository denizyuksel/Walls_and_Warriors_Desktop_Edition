package entity;

import javafx.geometry.Point2D;
import java.util.ArrayList;

public class GameBoard {
    char[][] board;
    int dimensionX;
    int dimensionY;
    boolean[][] marks;
    ArrayList<Wall> wallsPlaced;
    ArrayList<Wall> avaibleWalls;
    ArrayList<Knight> placedKnights;

    public GameBoard(char[][] initialBoard, int x , int y){
        dimensionX = x;
        dimensionY = y;
        wallsPlaced = new ArrayList<Wall>();
        placedKnights = new ArrayList<Knight>();
        clearMarks();
        preapareTheBoard( initialBoard,x,y);
        placedKnights = findKnights(board);



    }

    public char[][] getBoard(){
        return board;
    }
    public void setBoard(char[][] board){

    }

    public void setWallsPlaced(ArrayList<Wall> wallsPlaced) {
        this.wallsPlaced = wallsPlaced;
    }

    public ArrayList<Wall> getWallsPlaced() {
        return wallsPlaced;
    }

    public ArrayList<Knight> getPlacedKnights() {
        return placedKnights;
    }

    public void setKnights(ArrayList<Knight> knights) {
        this.placedKnights = knights;
    }

    public int getDimensionY() {
        return dimensionY;
    }

    public int getDimensionX() {
        return dimensionX;
    }

    public void setDimensionY(int dimensionY) {
        this.dimensionY = dimensionY;
    }

    public void setDimesionX(int dimesionX) {
        this.dimensionX = dimesionX;
    }
    public void clearMarks(){
        for(int i =0; i < dimensionX; i++ ){
            for(int k =0; k < dimensionY; k++){
                marks[i][k] = false;
            }
        }
    }
    public void setMark(int x, int y){
        marks[x][y] = true;
    }
    public boolean chechkMark(int x, int y){
        return marks[x][y];
    }
    public char getContent(int x, int y){
        return board[x][y];
    }

    private void preapareTheBoard(char[][] initial,int x,int y){
            for(int i = 0; i<x;i++){
                for(int j = 0; j<y;j++){
                    board[i][j] = initial[i][j];

                }
            }


    }
    public Wall findAndRemoveWall(int x, int y){
        Wall temp;
        for(int i =0; i< wallsPlaced.size(); i++){
          if( wallsPlaced.get(i).passingFromThisPoint(new Point2D(x,y))){
              temp = wallsPlaced.get(i);
              ArrayList<Point2D> points = temp.calculateWallCoordinates();
              for(int j = 0; j < points.size();j++ ){
                    board[(int)points.get(j).getX()][(int)points.get(j).getY()] = 'e';
              }


              return temp;
          }
        }
        return temp = new Wall(1,new Point2D(-10,-10));
    }

    public void addWall(Wall wall, int x ,int y ){
        wall.moveWall(new Point2D(x,y));

        for(int i = 0; i < wall.calculateWallCoordinates().size();i++){
            int tempX = (int) wall.calculateWallCoordinates().get(i).getX();
            int tempY =(int)  wall.calculateWallCoordinates().get(i).getY();
            board[tempX][tempY] = 'w';
        }

    }
    public void addTower(Wall wall, int x ,int y ){
        wall.moveWall(new Point2D(x,y));
        for(int i = 0; i < wall.calculateWallCoordinates().size();i++){
            int tempX = (int)wall.calculateWallCoordinates().get(i).getX();
            int tempY = (int)wall.calculateWallCoordinates().get(i).getY();
            board[tempX][tempY] = 'T';
        }
        wallsPlaced.add(wall);

    }
    public boolean checkIfValid(Wall wall, int x ,int y){
        ArrayList<Point2D> temp = wall.calculateFutureWallCoordinates(x,y);
        boolean valid = true;
        for(int i =0; i<temp.size();i++){
            int tempX = (int) temp.get(i).getX();
            int tempY = (int) temp.get(i).getY();
            if(board[tempX][tempY] != 'E') valid = false;
        }
        return valid;
    }
    public  ArrayList<Knight> findKnights(char[][] initialState){
        ArrayList<Knight> knights = new ArrayList<Knight>();
        for(int i = 0; i < initialState.length; i++ ){
            for(int j = 0; j < initialState[0].length;j++){
                char temp = initialState[i][j];
                if(temp == 'R'){
                    knights.add(new Knight(new Point2D(i,j), Knight.KnightColor.RED,false,false    ));
                }
                else if ( temp == 'B' ){
                    knights.add(new Knight(new Point2D(i,j), Knight.KnightColor.BLUE,false,false    ));
                }
            }
        }
        return knights;
    }
    public  Wall getTower(char[][] initialState){
        for(int i = 0; i < initialState.length; i++ ){
            for(int j = 0; j < initialState[0].length;j++){
                char temp = initialState[i][j];
                if(temp == 'T'){
                    return new Wall(5, new Point2D(i,j));

                }

            }
        }
        return new Wall(5, new Point2D(-10,-10));


    }

}
