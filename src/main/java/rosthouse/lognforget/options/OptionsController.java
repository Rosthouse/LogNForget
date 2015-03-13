package rosthouse.lognforget.options;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class OptionsController implements Initializable {
    
    @FXML
    AnchorPane parent;
    @FXML
    TextField shortCut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void onCancelClicked(){
        parent.getScene().getWindow().hide();
    }
    
    @FXML
    public void onShortcutFieldClicked(){
        
    }
    
}
