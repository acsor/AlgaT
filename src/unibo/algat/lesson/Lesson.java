package unibo.algat.lesson;

import java.util.Stack;

/**
 * Basic abstract class encapsulating data about a generic lesson, to be
 * displayed in one of the main screens.<br><br>
 *
 * The usefulness of the derived {@code Lesson} classes stems from their
 * coupling with the {@link java.util.prefs} mechanism, whereby they are
 * provided as arguments to such calls as
 * {@code Preferences.user/systemNodeForPackage()}.
 */
public abstract class Lesson {
    /* TODO The localization support, which I strive for, might change this. */
    protected String mName;
    /**
     * The argument categories this lesson belongs to. For example, if the
     * current lesson is "Topological sorting", it may be classified as {@code
     * Graphs > Sorting}. In that case, {@code mArguments} will contain
     * the values {@code "Graphs" -> "Sorting"} in a nested fashion.
     */
    protected Stack<String> mArguments;

    public Lesson(String name, String ... arguments) {
        if (name != null) {
            mName = name;
        } else {
            throw new NullPointerException("name argument was null");
        }

        mArguments = new Stack<>();

        for (int i = 0; i < arguments.length; i++) {
            mArguments.push(arguments[i]);
        }
    }

    public String name () {
        return mName;
    }

    public Stack<String> arguments () {
        return mArguments;
    }
}
