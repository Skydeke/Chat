package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;


public class Main extends Application {

    private Stage stage;
    private LoginController lc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        // FXML-Datei laden!

        // Szene
        lc = new LoginController(primaryStage, this);
        switchScene("/fxmls/LoginInterface.fxml", lc);
        /*
        InputStream is = getClass().getResourceAsStream("/fxmls/Interface.fxml");
        Controller lc = new Controller(primaryStage);
        loader.setController(lc);
        */
        // +++++++++++++++++++++++++++++++++++++++++++++
        // Stage konfigurieren
        // +++++++++++++++++++++++++++++++++++++++++++++

        // Titel setzen
        primaryStage.setTitle("DaJo - Chat! v1.0");

        // Szene setzen
        primaryStage.sizeToScene();

        // Stage anzeigen
        primaryStage.show();

        //Add the Icon
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));


    }

    @Override
    public void stop() {
        lc.stop();
        System.exit(0);
    }

    public void switchScene(String fxmlFile, Object controller) {

        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            loader.setController(controller);
            InputStream is = Main.class.getResourceAsStream(fxmlFile);
            if (is != null) {
                root = loader.load(is);
                if (stage.getScene() == null) {
                    this.stage.setScene(new Scene(root, 760, 400));
                } else {
                    this.stage.getScene().setRoot(root);
                }
                this.stage.getScene().getStylesheets().add(Main.class.getResource("/stylesheets/layout.css").toExternalForm());
            }else {
                System.out.println("Could not locate fxml: " + fxmlFile);
            }
        }catch (Exception e2){
            e2.printStackTrace();
        }

    }
}
