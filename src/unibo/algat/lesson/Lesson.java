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
     * The nested topic categories this lesson belongs to.<br>
     * As an example, if the current lesson is "Topological sorting", it may be
     * classified as {@code Sorting > Graph}. In that case, {@code
     * mNestedTopics} will contain the values {@code "Graphs" > "Sorting"} in
     * a nested fashion.
     */
    protected Stack<String> mNestedTopics;

    public Lesson(String name, String ... topics) {
        if (name != null) {
            mName = name;
        } else {
            throw new NullPointerException("name argument was null");
        }

        mNestedTopics = new Stack<>();

        for (int i = 0; i < topics.length; i++) {
            mNestedTopics.push(topics[i]);
        }
    }

    /**
     * @return This lesson name.
     */
    public String name () {
        return mName;
    }

    /**
     * @return A nested "list" of topics this lesson belongs to.
     */
    public Stack<String> topics() {
        return mNestedTopics;
    }
}
