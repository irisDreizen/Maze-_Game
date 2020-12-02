import Model.MyModel;
import View.MyViewController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

import static javafx.application.Application.launch;

public class Main extends Application {
    @Override

    public void start(Stage primaryStage) throws Exception {
        MyModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        //--------------
        primaryStage.setTitle("My Application!");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View/MyView2.fxml").openStream());
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        //--------------
        MyViewController view = fxmlLoader.getController();
        view.setStage(primaryStage);
        view.setResizeEvent(scene);
        view.setViewModel(viewModel);
        viewModel.addObserver(view);

        //--------------
        SetStageCloseEvent(primaryStage,view);
        init();
        primaryStage.show();
    }

    private void SetStageCloseEvent(Stage primaryStage, MyViewController view) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit? the game won't be save");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    view.stopServer();
                    System.exit(0);
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });


    }

    public static void main(String[] args) {
        launch(args);
    }
}
