package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;
    private Position charPos;
    private boolean isFinished=false;
    private Position endPosition;


    private int characterPositionRowIndex;
    private int characterPositionColumnIndex;

    public StringProperty characterPositionRow = new SimpleStringProperty("1"); //For Binding
    public StringProperty characterPositionColumn = new SimpleStringProperty("1"); //For Binding

    public MyViewModel(IModel model){
        this.model = model;
    }
    public Position getCharPos() {
        return charPos;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o==model){
//            characterPositionRowIndex = model.getCharacterPositionRow();
//            characterPositionRow.set(characterPositionRowIndex + "");
//            characterPositionColumnIndex = model.getCharacterPositionColumn();
//            characterPositionColumn.set(characterPositionColumnIndex + "");
            this.charPos= new Position(model.getCharacterPositionRow(),model.getCharacterPositionColumn());
            this.endPosition=model.getEndPosition();
            this.isFinished=model.isFinished();
            setChanged();
            notifyObservers();
        }
    }
    public Position getEndPosition() {
        return endPosition;
    }

    public boolean isFinished() {
        return isFinished;
    }
    public void loadMaze(File file){
        model.loadMaze(file);
    }
    public  void saveMaze(File file){
        model.saveMaze(file);
    }
    public void generateMaze(int width, int height){
        model.generateMaze(width, height);
    }

    public void moveCharacter(KeyCode movement){
        model.moveCharacter(movement);
    }

    public int[][] getMaze() {
        return model.getMaze();
    }

    public void solveMaze(){
        model.solveMaze();
    }
    public Solution getSolution(){
        return model.getSolution();
    }
    public int getCharacterPositionRow() {
        return charPos.getRowIndex();
    }

    public int getCharacterPositionColumn() {
        return charPos.getColumnIndex();
    }

    public  void stopServer(){
        model.stopServers();
    }
}
