/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget.reminder;

import java.util.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.util.*;

/**
 * Controller Class for the Reminder.
 *
 * @author Patrick Joos <patrick.joos@wuerth-itensis.ch>
 */
public class ReminderController implements EventHandler<DialogEvent> {

    private List<Alert> activeAlerts;

    public void createReminder(String text) {
        Alert alert = createAlert(text);
        alert.show();
        Transition trans = createTransitions(alert);
        trans.play();
    }

    Transition createTransitions(Alert alert) {
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();

        Duration transitionDuration = Duration.seconds(2);
        FadeTransition inFade = new FadeTransition(transitionDuration, alert.getDialogPane());
        inFade.setFromValue(0);
        inFade.setToValue(1.0);
        TranslateTransition inTranslation = new TranslateTransition(transitionDuration, alert.getDialogPane());
        inTranslation.setFromY(screen.getHeight() + alert.getHeight());
        inTranslation.setToY(screen.getHeight() - alert.getHeight());
        ParallelTransition inTransition = new ParallelTransition(inFade, inTranslation);

        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(5));

        TranslateTransition outTranslation = new TranslateTransition(transitionDuration, alert.getDialogPane());
        outTranslation.setFromY(screen.getHeight() + alert.getHeight());
        outTranslation.setToY(screen.getHeight() - alert.getHeight());
        FadeTransition outFade = new FadeTransition(transitionDuration, alert.getDialogPane());
        outFade.setFromValue(1.0);
        outFade.setToValue(0);
        outFade.setCycleCount(1);
        ParallelTransition outTransition = new ParallelTransition(outFade, outTranslation);
        SequentialTransition transition = new SequentialTransition(inTransition, pause, outTransition);
        transition.setOnFinished(e -> alert.hide());
        return transition;
    }

    Alert createAlert(String text) {
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.getDialogPane().setStyle("-fx-background-color: white;"
                + "-fx-border-color: blue;"
                + "-fx-border-insets: 10;"
                + "-fx-border-width: 3;");
        alert.getDialogPane().getScene().setFill(null);
        ImageView image = new ImageView("/images/alarm_clock.png");
        image.setFitHeight(64);
        image.setFitWidth(64);
        alert.setGraphic(image);
        alert.setX(screen.getMinX() + screen.getWidth() - alert.getWidth());
        alert.setY(screen.getMinY() + screen.getHeight() - alert.getHeight());
        return alert;
    }

    @Override
    public void handle(DialogEvent event) {

    }

}
