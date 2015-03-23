/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget;

import java.util.prefs.Preferences;
import rosthouse.lognforget.util.Constants;
import rosthouse.lognforget.util.ModKeyMapping;

/**
 *
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class Settings {
    
    private static final Preferences pref = Preferences.userNodeForPackage(Settings.class);
    
    public static int getShortCutKey(){
        return pref.getInt(Constants.SHORTCUT_KEY_PREFERENCE, 'A');
    }
    
    public static void setShortCutKey(int key){
        pref.putInt(Constants.SHORTCUT_KEY_PREFERENCE, key);
    }
    
    public static ModKeyMapping getModKey(){
        return ModKeyMapping.valueOf(pref.get(Constants.MODIFIER_PREFERENCE, ModKeyMapping.WIN.name()));
    }
    
    public static void setModKey(ModKeyMapping mapping){
        setModKey(mapping.name());
    }
    
    public static void setModKey(String mapping){
        pref.put(Constants.MODIFIER_PREFERENCE, mapping);
    }
    
}
