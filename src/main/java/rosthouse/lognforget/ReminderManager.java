/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.PopOver;
import rosthouse.lognforget.reminder.LogEvent;
import rosthouse.lognforget.reminder.LogEventHandler;
import rosthouse.lognforget.shortcut.ShortCutListenerController;
import rosthouse.lognforget.util.MediaPlayer;
import rosthouse.lognforget.util.WindowManager;

/**
 * Handles all Reminders.
 *
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class ReminderManager implements LogEventHandler {

    private ScheduledExecutorService service;

    public ReminderManager() {
        service = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void handle(LogEvent event) {
        String logText = event.getText();
        if (logText.startsWith("+")) {
            createReminder(logText);
        }
    }

    public void createReminder(String text) {
        Duration duration = getDurationFromNowUntilReminder(text);
        final String textWithoutModifier = removeModifierFromText(text);
        Runnable tTask = () -> {
            if (Platform.isFxApplicationThread()) {
                openWindow(text);
            } else {
                Platform.runLater(() -> openWindow(text));
            }
        };
        service.schedule(tTask, duration.toMillis(), TimeUnit.MILLISECONDS);
    }

    static String removeModifierFromText(String text) {
        int firstIndexOfSpace = text.indexOf(" ");
        return text.substring(firstIndexOfSpace + 1);
    }

    static Duration getDurationFromNowUntilReminder(String text) {
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

    private void openWindow(String text) {
        Stage stage = WindowManager.loadWindow("/fxml/Editor.fxml");
        stage.addEventHandler(LogEvent.LOG_EVENT, this);
        PopOver timerPopOver = createPopOver(text);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        timerPopOver.show(stage);
        MediaPlayer.playAudio("/audio/alert/" + Settings.getAlertClip());
    }

    private PopOver createPopOver(String text1) {
        PopOver timerPopOver = new PopOver();
        timerPopOver.setArrowLocation(PopOver.ArrowLocation.LEFT_BOTTOM);
        Pane timerPopOverContent = new AnchorPane();
        timerPopOverContent.getChildren().add(new Label(text1));
        timerPopOver.setContentNode(timerPopOverContent);
        timerPopOver.setAutoHide(true);
        return timerPopOver;
    }

}
