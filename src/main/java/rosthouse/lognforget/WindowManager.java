/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rosthouse.lognforget.shortcut.ShortCutListener;

/**
 *
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class WindowManager {

    public static Stage loadWindow(String scenePath) {
        FXMLLoader loader = createLoader(scenePath);
        Parent root = createRootNode(loader);
        return createStage(root);
    }

    public static Parent createRootNode(FXMLLoader loader) throws IllegalArgumentException {
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ShortCutListener.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Couldn't find FXML file", ex);
        }
        return root;
    }

    public static Stage createStage(Parent root) {
        final Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.setFill(new Color(0, 0, 0, 0));
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }

    public static FXMLLoader createLoader(String scenePath) {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WindowManager.class.getResource(scenePath));
        return loader;
    }
    
}
