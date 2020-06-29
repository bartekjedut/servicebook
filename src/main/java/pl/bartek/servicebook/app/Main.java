package pl.bartek.servicebook.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.bartek.servicebook.controller.MainController;


public class Main extends Application {


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(getClass().getResource("/fxml/mainPane.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Service Book v1.0");
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            System.out.println("Zamknięto okno i zapisano dane");
            MainController.saveGarage();
            MainController.seveRecords();
            Platform.exit();
        });
    }
}
