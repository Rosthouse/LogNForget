/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget.util;

import com.melloware.jintellitype.JIntellitype;
import javafx.scene.input.KeyCode;

/**
 * Mapping enum which maps {@link KeyCode} to a JIntelitype modifier.
 *
 * @author Rosthouse <rosthouse@gmail.com>
 */
public enum ModKeyMapping {

    WIN(JIntellitype.MOD_WIN, KeyCode.WINDOWS),
    ALT(JIntellitype.MOD_ALT, KeyCode.ALT),
    ALT_GR(JIntellitype.MOD_ALT, KeyCode.ALT_GRAPH),
    SHIFT(JIntellitype.MOD_SHIFT, KeyCode.SHIFT),
    CTRL(JIntellitype.MOD_CONTROL, KeyCode.CONTROL);
    private int jintelitypeKey;
    private KeyCode fxKeyCode;

    private ModKeyMapping(int jintelitypeKey, KeyCode fxKeyCode) {
        this.jintelitypeKey = jintelitypeKey;
        this.fxKeyCode = fxKeyCode;
    }

    public static ModKeyMapping getMapping(int jintelitypeKey) {
        for (ModKeyMapping value : ModKeyMapping.values()) {
            if (value.getJIntelitypeKey() == jintelitypeKey) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("Key %d was not found in Mapping", jintelitypeKey));
    }
    
    public static ModKeyMapping getMapping(KeyCode javaFxKeyCode) {
        for (ModKeyMapping value : ModKeyMapping.values()) {
            if (value.getKeyCode() == javaFxKeyCode) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("Key %s was not found in Mapping", javaFxKeyCode.getName()));
    }

    public int getJIntelitypeKey() {
        return this.jintelitypeKey;
    }

    public KeyCode getKeyCode() {
        return fxKeyCode;
    }

}
