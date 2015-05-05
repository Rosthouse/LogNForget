/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget.reminder;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller Class for the Reminder.
 *
 * @author Patrick Joos <patrick.joos@wuerth-itensis.ch>
 */
public class ReminderController implements Initializable {

    @FXML
    private Button bntPostPone;
    @FXML
    private Button btnShutDownReminder;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void onShutdownReminderClicked() {

    }

    @FXML
    public void onPostPoneClicked() {

    }

    public void setTime(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        lblTime.setText(now.format(formatter));
    }

    public void setText(String text) {
        lblText.setText(text);
    }

}
