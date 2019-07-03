package unibo.algat.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

public class PreferencesController {
    private final ResourceBundle mInterface;
    @FXML private Label mLanguageField;



    public PreferencesController() {
        mInterface = ResourceBundle.getBundle("Interface");

    }

}
