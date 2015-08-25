/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget.reminder;

import java.time.*;
import java.time.temporal.*;
import java.util.concurrent.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;
import rosthouse.lognforget.*;
import rosthouse.lognforget.util.*;

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
                createNotification(textWithoutModifier);
            } else {
                Platform.runLater(() -> {
                    createNotification(text);
                    MediaPlayer.playAudio("/audio/alert/" + Settings.getAlertClip());
                });
            }
        };
        service.schedule(tTask, duration.toMillis(), TimeUnit.MILLISECONDS);
        createNotification("Created timer, which will go off in " + duration.toString());
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

    private void createNotification(final String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setContentText(text);
        alert.getDialogPane().setStyle("-fx-background-color: transparent;");
        alert.show();
        FadeTransition fade = new FadeTransition(javafx.util.Duration.seconds(5), alert.getDialogPane());
        fade.setFromValue(1.0);
        fade.setToValue(0);
        fade.setCycleCount(1);
        fade.setOnFinished(e -> alert.hide());
        fade.play();
    }

}
