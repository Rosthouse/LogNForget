/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget.util;

import javafx.scene.media.AudioClip;
import rosthouse.lognforget.Settings;

/**
 *
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class MediaPlayer {

    public static void playAudio(String path) {
        AudioClip alert = new AudioClip(MediaPlayer.class.getResource(path).toString());
        alert.play();
    }

}
