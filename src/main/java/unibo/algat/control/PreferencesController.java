package unibo.algat.control;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class PreferencesController {
    private final ResourceBundle mInterface;
    @FXML private ChoiceBox<String> mLanguageChoice;



    public PreferencesController() {
        mInterface = ResourceBundle.getBundle("Interface");
    }

    @FXML
    private void initialize() {
        String[] e = mInterface.getString(
                "gui.dialog.preferences.languageSelection").split(",");
        for (String i : e)
            mLanguageChoice.getItems().add(i);

    }

    @FXML
    private void savePressed() {

        Locale.setDefault(Locale.ITALIAN);
    }

    @FXML
    private void cancelPressed() {

    }

}
