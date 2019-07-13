package unibo.algat.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.prefs.Preferences;

public class PreferencesController {
    private final ResourceBundle mInterface;
    private Preferences mPreferences;
    @FXML private Button mSaveButton;
    @FXML private Button mCancelButton;
    @FXML private ComboBox<ComboItem> mLanguageChoice;
    private ComboItem DEFAULT_VALUE;
    private final String LANGUAGEPREF_KEY = "language";

    private final Comparator<Locale> mLocaleComparator = new Comparator<>() {
        public int compare(Locale locale1, Locale locale2) {
            return locale1.getDisplayLanguage().compareTo(
                locale2.getDisplayLanguage()
            );
        }
    };

    private final ChangeListener<ComboItem> mSelectedListener = new ChangeListener<>(){
        @Override
        public void changed(ObservableValue<? extends ComboItem> observable, ComboItem oldValue, ComboItem newValue) {
            mSaveButton.setDisable(newValue == DEFAULT_VALUE);
        }
    };

    public PreferencesController () {
        mInterface = ResourceBundle.getBundle("Interface");
        mPreferences = Preferences.userRoot().node("/settings");
    }

    @FXML
    private void initialize () {
    	List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
    	locales.sort(mLocaleComparator);

        for (Locale l: locales) {
            mLanguageChoice.getItems().add(
                new ComboItem(l, l.getDisplayLanguage(), l.getDisplayCountry())
            );
        }
        mLanguageChoice.getSelectionModel().selectFirst();
        DEFAULT_VALUE = mLanguageChoice.getValue();
        mLanguageChoice.valueProperty().addListener(mSelectedListener);
        mSaveButton.setDisable(true);
    }

    private void registerLanguage() {
        Preferences newpref = mPreferences.node("language"); //is it ok hardcoded like this?
                                                                       // (it's the node name)
        newpref.put(LANGUAGEPREF_KEY, mLanguageChoice.getValue().mLocale.toLanguageTag());
        //null-pointer access should be impossible as the only chance for
        //mLanguageChoice.getValue() to be null coincides with when save is disabled
        Locale.setDefault(mLanguageChoice.getValue().mLocale);
    }

    @FXML
    private void savePressed () {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(mInterface.getString("gui.dialog.preferences.warning.save.window"));
        alert.setHeaderText(mInterface.getString("gui.dialog.preferences.warning.save.title"));

        alert.setHeight(500);
        alert.setWidth(1200);
        alert.setResizable(true);

        Label text = new Label(mInterface.getString("gui.dialog.preferences.warning.save.text"));
        text.setWrapText(true);

        alert.getDialogPane().setContent(text);
        //alert.setContentText(mInterface.getString("gui.dialog.preferences.warning.save.text")); <- this version doesn't wrap text

        Optional<ButtonType> res = alert.showAndWait();
        if(res.isPresent()){
            if (res.get() == ButtonType.OK) {
                registerLanguage();
                Stage stage = (Stage) mLanguageChoice.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void cancelPressed() {
        Stage stage = (Stage) mLanguageChoice.getScene().getWindow();
        stage.close();
    }

    /**
     * <p>Class encapsulating data shown on the locale selection combo box.</p>
     */
    class ComboItem {
        Locale mLocale;
        String mLang;
        String mCountry;

        ComboItem(Locale locale, String lang, String country) {
            mLocale = locale;
            mLang = lang;
            mCountry = country;
        }

        @Override
        public String toString() {
            if (mCountry.isEmpty())
                return mLang;
            else
                return String.join(", ", mLang, mCountry);
        }

        @Override
        public boolean equals(Object obj) {
            return this == (ComboItem) obj;
        }
    }
}
