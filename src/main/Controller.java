package main;

import conversation.DmListViewCell;
import conversation.Message;
import conversation.MessageListViewCell;
import conversation.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    ListView dmListView;
    @FXML
    TextArea sendField;
    @FXML
    Button sendButton;
    @FXML
    ListView chatListView;
    private ObservableList<User> userData;
    private ObservableList<Message> chatMessages;
    private Client client;

    public Controller(Stage stage) {
        userData = FXCollections.observableArrayList();
        chatMessages = FXCollections.observableArrayList();
        userData.add(new User("Public Person", "Fucking Status!", 0));
        userData.add(new User("JK", "Programmiert", 1));
        chatMessages.add(new Message(false, "Hey, what tf??!", 0));
        chatMessages.add(new Message(true, "Hello, who is this?", 1));
        chatMessages.add(new Message(false, "Ich bin ein ganz  laaaaaaaaaaaaangerr Text und \n das hier sind Sonderzeichen äöüß*", 2));
    }

    public void setClient(Client c) {
        this.client = c;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Setup the custom Cell Content
        dmListView.requestFocus();
        dmListView.setItems(userData);
        dmListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> studentListView) {
                return new DmListViewCell();
            }
        });
        chatListView.setItems(chatMessages);
        chatListView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> studentListView) {
                return new MessageListViewCell();
            }
        });
    }
}
