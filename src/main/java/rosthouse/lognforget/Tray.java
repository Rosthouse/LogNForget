/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget;

import com.melloware.jintellitype.JIntellitype;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import rosthouse.lognforget.options.Options;
import rosthouse.lognforget.util.Constants;

/**
 * Handles access to the Tray bar. Meaning it contains everything needed to set up a clickable tray icon in a task bar.
 * 
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class Tray {

    public Tray() {
        if (SystemTray.isSupported()) {
            setTrayIcon();
        } else {
            throw new UnsupportedOperationException("System Tray is not supported on this machine");
        }
    }

    private void setTrayIcon() {
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon icon = loadTrayIcon();
        PopupMenu menu = createPopupMenu(tray, icon);
        icon.setPopupMenu(menu);
        try {
            tray.add(icon);
        } catch (AWTException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TrayIcon loadTrayIcon() {
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/famfamfam/silk/accept.png"));
        TrayIcon icon = new TrayIcon(image, Constants.LOG_N_FORGET);
        return icon;
    }

    private PopupMenu createPopupMenu(SystemTray tray, TrayIcon icon) throws HeadlessException {
        PopupMenu menu = new PopupMenu(Constants.LOG_N_FORGET);
        MenuItem options = new MenuItem("Options");
        options.addActionListener((ActionEvent e) -> {
            openOptionsWindow();
        });
        MenuItem close = new MenuItem("Exit");
        close.addActionListener((ActionEvent e) -> {
            closeApplication(tray, icon);
        });
        menu.add(options);
        menu.add(close);
        return menu;
    }

    private void openOptionsWindow() {
        Platform.runLater(() -> {
            try {
                Options optionsWindow = new Options();
            } catch (IOException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void closeApplication(SystemTray tray, TrayIcon icon) {
        System.out.println("Ending " + Constants.LOG_N_FORGET);
        JIntellitype.getInstance().cleanUp();
        tray.remove(icon);
        Platform.exit();
    }

}
