/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget.reminder;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;
import rosthouse.lognforget.Settings;
import rosthouse.lognforget.util.MediaPlayer;

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
                createNotification(text);
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

    private void createNotification(String text) {
        Platform.runLater(() -> {
            Stage owner = new Stage(StageStyle.UNDECORATED);
            StackPane root = new StackPane();
            root.setStyle("-fx-background-color: TRANSPARENT");
            Scene scene = new Scene(root, 1, 1);
            scene.setFill(Color.TRANSPARENT);
            owner.setScene(scene);
            owner.setWidth(1);
            owner.setHeight(1);
            owner.toBack();
            owner.show();
            ImageView image = new ImageView("/images/alarm_clock.png");
            image.setFitHeight(64);
            image.setFitWidth(64);
            Notifications notification = Notifications.create();
            notification.title("Reminder");
            notification.text(text);
            notification.graphic(image);
            notification.hideAfter(javafx.util.Duration.seconds(10));
            notification.show();
            owner.hide();
        });
    }

}
