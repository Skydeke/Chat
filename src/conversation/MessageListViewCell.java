package conversation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class MessageListViewCell extends ListCell<Message> {

    @FXML
    HBox cell;
    @FXML
    BubbledLabel messageLabel;
    private FXMLLoader mLLoader;

    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader();
                mLLoader.setController(this);

                try {
                    mLLoader.load(getClass().getResourceAsStream("/fxmls/MessageListCell.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (item.isSendByMe()) {
                    messageLabel.setBubbleSpec(BubbleSpec.FACE_LEFT_BOTTOM);
                    messageLabel.setText(item.getMessage());
                    messageLabel.setBackground(new Background(new BackgroundFill(Color.rgb(170,183,184),
                            null, null)));
                    cell.setAlignment(Pos.CENTER_LEFT);

                } else {
                    messageLabel.setBubbleSpec(BubbleSpec.FACE_RIGHT_BOTTOM);
                    messageLabel.setText(item.getMessage());
                    messageLabel.setBackground(new Background(new BackgroundFill(Color.rgb(170,183,184),
                            null, null)));
                    cell.setAlignment(Pos.CENTER_RIGHT);
                }
            }
            setText(null);
            setGraphic(cell);
        }

    }
}
