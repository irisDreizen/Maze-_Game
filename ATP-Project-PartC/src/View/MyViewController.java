package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.Solution;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements IView, Observer {

    @FXML
    private MyViewModel viewModel;
    public MazeDisplayer mazeDisplayer;
    public javafx.scene.control.TextField txtfld_rowsNum;
    public javafx.scene.control.TextField txtfld_columnsNum;
    public javafx.scene.control.Label lbl_rowsNum;
    public javafx.scene.control.Label lbl_columnsNum;
    public javafx.scene.layout.BorderPane BorderPane;
    public javafx.scene.layout.Pane pane;
    //private IntegerProperty height;
    //private IntegerProperty weidth;
    public javafx.scene.control.Button btn_generateMaze;
    public javafx.scene.control.Button btn_solution;
    public boolean isSolutionPushed=false;
    public boolean isCharcterPushed=false;
    public boolean isGenerartePushed=false;
    private Stage myStage;
    String musicFile = "resources/audio/Sababa.mp3";     // For example
    Media sound = new Media(new File(musicFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);


    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Pane getPane() {
        return pane;
    }

//    public void init() {
//
//        /****for resize ********/
//        weidth = new SimpleIntegerProperty(650);
//        height = new SimpleIntegerProperty(650);
//        weidth.bind(BorderPane.widthProperty());
//        height.bind(BorderPane.heightProperty());
//        pane.prefHeightProperty().bind(BorderPane.heightProperty());
//        pane.prefWidthProperty().bind(BorderPane.widthProperty());
//        mazeDisplayer.heightProperty().bind(pane.heightProperty());
//        mazeDisplayer.widthProperty().bind(pane.widthProperty());
//        mazeDisplayer.heightProperty().addListener((observable, oldValue, newValue) -> {
//            if(viewModel.getMaze() != null)
//                displayMaze(viewModel.getMaze());
//        });
//        mazeDisplayer.widthProperty().addListener((observable, oldValue, newValue) -> {
//            if(viewModel.getMaze() != null)
//                displayMaze(viewModel.getMaze());
//        });
//    }

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
      //  bindProperties(viewModel);
    }

//    private void bindProperties(MyViewModel viewModel) {
//        lbl_rowsNum.textProperty().bind(viewModel.characterPositionRow);
//        lbl_columnsNum.textProperty().bind(viewModel.characterPositionColumn);
//    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            if(isSolutionPushed){
                isSolutionPushed=false;
                displaySolution(viewModel.getSolution());
                btn_solution.setDisable(false);
            }
            else if(isCharcterPushed){
                isCharcterPushed=false;
                if(viewModel.isFinished()==true){
                    stopMusic();
                }
                displayCharcter();
            }
            else{
                displayMaze(viewModel.getMaze(), viewModel.getEndPosition());
               // btn_generateMaze.setDisable(false);
            }

        }
    }
    public  void displaySolution(Solution sol){
        mazeDisplayer.setSolution(sol);
    }


    public  void displayCharcter(){
        mazeDisplayer.setCharacterPosition(viewModel.getCharPos().getRowIndex(),viewModel.getCharPos().getColumnIndex() );
    }

    //@Override
    public void displayMaze(int[][] maze, Position p) {
        mazeDisplayer.setMaze(maze);
        int characterPositionRow = viewModel.getCharacterPositionRow();
        int characterPositionColumn = viewModel.getCharacterPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        mazeDisplayer.setEndPosition(p);
        this.characterPositionRow.set(characterPositionRow + "");
        this.characterPositionColumn.set(characterPositionColumn + "");
    }

    public static boolean isNumeric(String str){
        try{
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public void generateMaze() {
        isGenerartePushed=true;
        if(!isNumeric(txtfld_rowsNum.getText())){
            showAlert("please enter integers only");
        }
        else if(!isNumeric(txtfld_columnsNum.getText())){
            showAlert("please enter integers only");
        }
        else{
            int heigth = Integer.valueOf(txtfld_rowsNum.getText());
            int width = Integer.valueOf(txtfld_columnsNum.getText());
            if(heigth<0 || width<0){
                showAlert("please enter positive parameters");
            }
            else if(heigth<5 || width<5){
                showAlert("minimal maze size is 5*5");
            }
            else {
                btn_generateMaze.setDisable(true);
                viewModel.generateMaze(width, heigth);
                btn_solution.setDisable(false);
                mediaPlayer.play();
            }

        }

    }
    public void stopMusic(){
        mediaPlayer.stop();
    }

    public void solveMaze() {
        if(isGenerartePushed){
            isSolutionPushed=true;
            btn_solution.setDisable(true);
            btn_generateMaze.setDisable(true);
            viewModel.solveMaze();
        }
        else{
            showAlert("first you need to generate maze");
        }
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }
    public void instructions(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Instructions");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("instructions.fxml").openStream());
            Scene scene = new Scene(root, 800, 350);
            scene.getStylesheets().add(getClass().getResource("instructions.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }
    public void exitButtom(){
        stopServer();
        System.exit(0);
    }
    public void KeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode().getName().equals("Ctrl")){
            mazeDisplayer.zoom();
        }
        else if(isGenerartePushed){
            isCharcterPushed=true;
            viewModel.moveCharacter(keyEvent.getCode());
            keyEvent.consume();
            //System.out.printf(keyEvent.getCode().getName());
        }
    }

    public void setStage(Stage stage){
       myStage=stage;
    }

    public void loadMaze(){
        FileChooser file = new FileChooser();
        file.setInitialDirectory(new File(System.getProperty("user.home")));
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("maze", "*.maze"));
        File load= file.showOpenDialog(myStage);

        if(load!=null && load.canExecute()){
            viewModel.loadMaze(load);
            mazeDisplayer.setMaze(viewModel.getMaze());
        }

    }
    public void saveMaze(){
        if(isGenerartePushed){
            FileChooser file = new FileChooser();
            file.setInitialDirectory(new File(System.getProperty("user.home")));
            file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("maze", "*.maze"));
            file.setInitialFileName(" ");
            viewModel.saveMaze(file.showSaveDialog(myStage));
        }
        else{
            showAlert("you didn't generate maze!");
        }
    }
    //region String Property for Binding
    public StringProperty characterPositionRow = new SimpleStringProperty();

    public StringProperty characterPositionColumn = new SimpleStringProperty();

    public String getCharacterPositionRow() {
        return characterPositionRow.get();
    }

    public StringProperty characterPositionRowProperty() {
        return characterPositionRow;
    }

    public String getCharacterPositionColumn() {
        return characterPositionColumn.get();
    }

    public StringProperty characterPositionColumnProperty() {
        return characterPositionColumn;
    }

    public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;
        myStage.setScene(scene);
        myStage.setResizable(true);
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {

                mazeDisplayer.widthProperty().bind(pane.widthProperty());

            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                mazeDisplayer.heightProperty().bind(pane.heightProperty());
            }
        });
        myStage.setScene(scene);
        myStage.hide();
        myStage.show();
    }

    public  void stopServer(){
        if(isGenerartePushed){
            viewModel.stopServer();
        }
    }

    public void About(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("About");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 800, 350);
            scene.getStylesheets().add(getClass().getResource("about.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }

    public void Properties(ActionEvent actionEvent){
        try {
            Stage stage = new Stage();
            stage.setTitle("Properties");
            FXMLLoader fxmlLoader = new FXMLLoader();
            StackPane root = new StackPane();
            Scene scene = new Scene(root, 800, 350);
            stage.setScene(scene);
            VBox vBox=new VBox();
            GridPane gridPane=new GridPane();

            String AmazeInfo=Configurations.getMazeGeneratorName();
            System.out.println(AmazeInfo);
            String ASearch =Configurations.getSearchAlgorithmName();
            String threadInfo=""+Configurations.getNumberOfThreads();
            Label search=new Label("searching algorithm:"+ ASearch);
            Label maze =new Label("searching algorithm:"+ AmazeInfo);
            Label numTh=new Label("searching algorithm:"+ threadInfo);
            gridPane.add(search,1,0);
            gridPane.add(maze,1,1);
            gridPane.add(numTh,1,2);
            vBox.getChildren().add(gridPane);
            root.getChildren().add(vBox);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }

    public void newGame(){
        try {
            stopMusic();
            showAlert("please enter new parameters and push:generate maze");
            btn_generateMaze.setDisable(false);
            btn_solution.setDisable(true);
        } catch (Exception e) {

        }
    }
    public void mouseClicked(MouseEvent mouseEvent) {
        this.mazeDisplayer.requestFocus();
    }

}


