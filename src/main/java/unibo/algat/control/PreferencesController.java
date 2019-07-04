package unibo.algat.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.*;

public class PreferencesController {
    private final ResourceBundle mInterface;

    @FXML private Button mSaveButton;
    @FXML private Button mCancelButton;
    // TODO Monitor mLanguageChoice for selections. mSaveButton, which is
    //  initially supposed to be disabled, should be reactivated once a new
    //  selection is made on the combo box
    @FXML private ComboBox<ComboItem> mLanguageChoice;

    private final Comparator<Locale> mLocaleComparator = new Comparator<>() {
        public int compare(Locale locale1, Locale locale2) {
            return locale1.getDisplayLanguage().compareTo(
                locale2.getDisplayLanguage()
            );
        }
    };

    public PreferencesController () {
        mInterface = ResourceBundle.getBundle("Interface");
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
    }

    @FXML
    private void savePressed () {
        // TODO Store the preference in a persistent storage (e.g. disk --
        //  actually the {@link java.util.prefs} package abstracts this away).
        Locale.setDefault(mLanguageChoice.getValue().mLocale);
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
    }
}
