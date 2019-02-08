package conversation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DmListViewCell extends ListCell<User> {

    VBox cell;
    @FXML
    Label nameLabel;
    @FXML
    Label statusLabel;
    private FXMLLoader mLLoader;

    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader();
                mLLoader.setController(this);

                try {
                    cell = mLLoader.load(getClass().getResourceAsStream("/fxmls/DmListCell.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            nameLabel.setText(item.getName());
            statusLabel.setText(item.getStatus());
            setText(null);
            setGraphic(cell);
        }

    }
}
