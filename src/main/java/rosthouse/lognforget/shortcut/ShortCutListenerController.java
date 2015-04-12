package rosthouse.lognforget.shortcut;

import java.net.URL;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import rosthouse.lognforget.shortcut.loggers.FileLogger;
import rosthouse.lognforget.shortcut.loggers.Logger;

/**
 * Controlls GUIs created by the fxml file editor.fxml.
 *
 * @author Rosthouse
 * @created 10.03.2015 12:46:54
 */
public class ShortCutListenerController extends Pane implements Initializable {

    @FXML
    private StackPane parent;
    @FXML
    private TextField logField;
    private Logger logger;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logField.requestFocus();
        this.logger = new FileLogger();
    }

    @FXML
    public void onKeyDown(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            closeWindow();
        }
    }

    public void closeWindow() {
        parent.getScene().getWindow().hide();
    }

    @FXML
    public void onEnter() {
        String text = logField.getText();
        if (text.startsWith("+")) {
            createReminder(text);
        } else {
            logger.logText(text);
        }
        closeWindow();
    }

    void setLogger(Logger logger) {
        this.logger = logger;
    }

    private void createReminder(String text) {
        Duration duration = getDurationFromNowUntilReminder(text);
        String textWithoutModifier = removeModifierFromText(text);

        Timer timer = new Timer();
        TimerTask tTask = new TimerTask() {

            @Override
            public void run() {
                System.out.println(text);
            }
        };
        timer.schedule(tTask, duration.toMillis());
    }

    String removeModifierFromText(String text) {
        int firstIndexOfSpace = text.indexOf(" ");
        return text.substring(firstIndexOfSpace + 1);
    }

    Duration getDurationFromNowUntilReminder(String text) {
        String duration = text.substring(0, text.indexOf(" "));
        ZonedDateTime time = ZonedDateTime.now();
        int durationLenght = Integer.parseInt(duration.substring(1, duration.length() - 1));
        ChronoUnit unit;
        Character timeUnit = duration.charAt(duration.length() - 1);
        switch (timeUnit) {
            case 'm':
                unit = ChronoUnit.MINUTES;
                break;
            case 's':
                unit = ChronoUnit.SECONDS;
                break;
            case 'h':
                unit = ChronoUnit.HOURS;
                break;
            default:
                throw new IllegalArgumentException(String.format("The text does not contain a valid duration: %s", duration));
        }
        ZonedDateTime target = time.plus(durationLenght, unit);
        return Duration.between(time, target);
    }

}
