/**
 * $HeadURL$
 */
package rosthouse.lognforget.shortcut;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * TODO
 *
 * @author jopa
 * @copyright (c) 2007-2015, Wuerth ITensis AG
 * @created 10.03.2015 12:46:54
 *
 * @$Revision$
 *
 * @$LastChangedBy$
 * @$LastChangedDate$
 * @$Id$
 */
public class ShortCutListenerController extends Pane {

    @FXML
    private TextField logField;

    @FXML
    public void onKeyTyped(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            this.getParent().setVisible(false);
        }
    }

}
