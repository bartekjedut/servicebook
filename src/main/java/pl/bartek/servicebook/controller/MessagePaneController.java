package pl.bartek.servicebook.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MessagePaneController {

    @FXML
    private TextArea messageTextArea;

    @FXML
    private TextArea recordViewTextArea;

    public TextArea getMessageTextArea() {
        return messageTextArea;
    }

    public TextArea getRecordViewTextArea() {
        return recordViewTextArea;
    }
}
