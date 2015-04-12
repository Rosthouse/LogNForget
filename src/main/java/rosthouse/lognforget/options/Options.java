/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget.options;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rosthouse.lognforget.WindowManager;
import rosthouse.lognforget.shortcut.ShortCutListener;
import rosthouse.lognforget.shortcut.ShortCutListenerController;

/**
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class Options {

    private final Stage stage;

    public Options() throws IOException {
        stage = WindowManager.loadWindow("/fxml/Options.fxml");
        stage.setAlwaysOnTop(true);
        stage.show();
    }

}
