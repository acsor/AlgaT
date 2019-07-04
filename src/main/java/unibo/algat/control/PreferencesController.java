package unibo.algat.control;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.*;

public class PreferencesController {
    private final ResourceBundle mInterface;
    @FXML private ComboBox<ComboItem> mLanguageChoice;
    private static final String EMPTY_STRING = "";


    public PreferencesController() {
        mInterface = ResourceBundle.getBundle("Interface");
    }

    @FXML
    private void initialize() {
        Locale list[] = Locale.getAvailableLocales();
        ComboItem items[] = new ComboItem[list.length];
        sortLocalesOnToString(list);

        for (int i=0; i < list.length; i++){
            Locale current = list[i];
            items[i] = new ComboItem(current, current.getDisplayLanguage(), current.getDisplayCountry());
            mLanguageChoice.getItems().add(items[i]);
        }
        /*
        String[] e = mInterface.getString(
                "gui.dialog.preferences.languageSelection").split(",");
        for (String i : e)
            mLanguageChoice.getItems().add(i);
*/
    }

    @FXML
    private void savePressed() {
        Locale.setDefault(mLanguageChoice.getValue().getLocale());
    }

    @FXML
    private void cancelPressed() {
        Stage stage = (Stage) mLanguageChoice.getScene().getWindow();
        stage.close();
    }

    public static void sortLocalesOnToString(Locale[] locales) {
        Comparator<Locale> localeComparator = new Comparator<>() {
            public int compare(Locale locale1, Locale locale2) {
                return locale1.getDisplayLanguage().compareTo(locale2.getDisplayLanguage());
            }
        };
        Arrays.sort(locales, localeComparator);
    }


    public class ComboItem {
        private String language;
        private String country;
        private Locale locale;

        public ComboItem(Locale newlocale, String newlanguage, String newcountry) {
            locale = newlocale;
            language = newlanguage;
            country = newcountry;
        }

        public Locale getLocale() {
            return locale;
        }

        @Override
        public String toString() {
            if (country != EMPTY_STRING)
                return language + ", " + country;
            else return language;
        }
    }

}
