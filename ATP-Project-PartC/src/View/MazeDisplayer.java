package View;

import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {
    private int[][] maze;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    private Position endPosition;
    private Solution solution;

    public MazeDisplayer(){
        widthProperty().addListener(e->redrawMaze());
        heightProperty().addListener(e->redrawMaze());
    }
    public void setMaze(int[][] maze) {
        this.maze = maze;
        redrawMaze();
    }
    public void setSolution(Solution sol){
        this.solution=sol;
        redrawSolution();

    }

    public void setCharacterPosition(int row, int column) {
        deleteCharacter(characterPositionRow,characterPositionColumn);
        characterPositionRow = row;
        characterPositionColumn = column;
        redrawChar();
    }

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }
    public void setEndPosition(Position p){
        endPosition=p;
    }

    public void redrawSolution(){
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / maze.length;
        double cellWidth = canvasWidth / maze[0].length;
        ArrayList<AState> solArray = this.solution.getSolutionPath();
        for(int i=0; i<solArray.size(); i++){
            Position state = ((MazeState)(solArray.get(i))).getP();
            int rowState = state.getRowIndex();
            int colState = state.getColumnIndex();
            GraphicsContext gc = getGraphicsContext2D();
            gc.setFill(Color.RED);
            gc.fillRect(colState * cellWidth, rowState * cellHeight, cellWidth, cellHeight);


        }
    }

    public void redrawMaze() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;

            try {
                Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
                Image characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));

                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        if (maze[i][j] == 1) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }

                    }
                }
              //  gc.fillRect( * cellHeight, j * cellWidth, cellHeight, cellWidth);


                //Draw Character
                //gc.setFill(Color.RED);
                //gc.fillOval(characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                gc.drawImage(characterImage, characterPositionColumn * cellWidth, characterPositionRow * cellHeight, cellWidth,cellHeight );
               // gc.drawImage(characterImage, endPosition.getColumnIndex() * cellWidth, endPosition.getColumnIndex() * cellHeight, cellWidth,cellHeight );
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
        }
    }
    public void redrawChar(){
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / maze.length;
        double cellWidth = canvasWidth / maze[0].length;
        GraphicsContext gc = getGraphicsContext2D();
        Image characterImage = null;
        try {
            characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        gc.drawImage(characterImage, characterPositionColumn * cellWidth, characterPositionRow * cellHeight, cellWidth,cellHeight );
        if(characterPositionColumn==maze[0].length-1){
            String musicFile = "resources/audio/kaboom.mp3";     // For example
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            YouDidIt();
        }

    }


    public void YouDidIt() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("stopPictureFXML.fxml").openStream());
            Scene scene = new Scene(root, 350, 350);
            scene.getStylesheets().add(getClass().getResource("stopPicture.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }
    private void showAlert(String alertMessage) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }
    public void deleteCharacter(double row,double col){
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / maze.length;
        double cellWidth = canvasWidth / maze[0].length;
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(col*cellWidth, row*cellHeight, cellWidth, cellHeight);
    }

    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();


    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }
    public void zoom(){
        setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double zoomFactor = 1.05;
                double deltaY = event.getDeltaY();
                if(deltaY<0){
                    zoomFactor = 0.95;
                    setScaleX(getScaleX()*zoomFactor);
                    setScaleY(getScaleY()*zoomFactor);
                    event.consume();
                }
                else{
                    zoomFactor=1.05;
                    setScaleX((getScaleX()*zoomFactor));
                    setScaleY((getScaleY()*zoomFactor));
                    event.consume();
                }
            }
        });
    }
}
