package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Tooltip;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    Button loginBtn;
    @FXML
    TextField ipAdresField;
    @FXML
    TextField passwordField;
    @FXML
    TextField usernameField;
    @FXML
    TextField portField;
    @FXML
    RadioButton clientRdBtn;
    @FXML
    RadioButton serverRdBtn;
    @FXML
    Label errorLabel;
    private Stage stage;
    private Main main;
    private Client client;

    public LoginController(Stage primaryStage, Main main) {
        stage = primaryStage;
        this.main = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //For Dev use only
        ipAdresField.setText("localhost");
        portField.setText("8818");

        //Test tooltip
        Tooltip tooltip1 = new Tooltip("Test");
        loginBtn.setTooltip(tooltip1);

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String ipAdress = ipAdresField.getText();
                String port = portField.getText();

                String username = usernameField.getText();
                String password = passwordField.getText();
                if (ipAdress != null && port != null && username != null && password != null){
                    try {
                        Controller c = null;
                        if (clientRdBtn.isSelected()) {
                            c = new Controller(stage);
                            try {
                                client = new Client(ipAdress, port, c);
                                c.setClient(client);
                                if (client.login(username, password)) {
                                    client.start();
                                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                        @Override
                                        public void handle(WindowEvent event) {
                                            client.stopThread();
                                        }
                                    });
                                    main.switchScene("/fxmls/Interface.fxml", c);
                                } else {
                                    errorLabel.setText(client.getErrorMsg());
                                }
                            } catch (IOException e) {
                                errorLabel.setText("Can not connect.");
                            }
                        } else if (serverRdBtn.isSelected()) {
                            //Server-Start-Code- here
                            System.out.println("Server selected");
                            c = new Controller(stage);
                            try {
                                client = new Client(ipAdress, port, c);
                                c.setClient(client);
                                if (client.login(username, password)) {
                                    client.start();
                                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                        @Override
                                        public void handle(WindowEvent event) {
                                            client.stopThread();
                                        }
                                    });
                                    main.switchScene("/fxmls/Interface.fxml", c);
                                } else {
                                    errorLabel.setText(client.getErrorMsg());
                                }
                            } catch (IOException e) {
                                errorLabel.setText("Can not connect.");
                            }
                        }
                    } catch (NullPointerException e) {
                        System.out.println("WTF! ERROR!! Try-Catch-LoginController.java");
                        e.printStackTrace();
                    }
                }else {
                    errorLabel.setText("Fill all fields.");
                }
            }
        });
    }

    public void stop() {
        if (client != null){
            client.stopThread();
        }
    }
}
