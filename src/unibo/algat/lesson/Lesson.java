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
public class Lesson {
    private int mId;
    private String mName;
    /**
     * The nested topic categories this lesson belongs to.<br>
     * As an example, if the current lesson is "Topological sorting", it may be
     * classified as {@code Sorting > Graph}. In that case, {@code
     * mNestedTopics} will contain the values {@code "Graphs" > "Sorting"} in
     * a nested fashion.
     */
    private Stack<String> mNestedTopics;

    Lesson(int id, String name, String ... topics) {
        mId = id;

        if (name != null)
            mName = name;
        else
            throw new NullPointerException("name argument was null");

        mNestedTopics = new Stack<>();

        for (String topic: topics) {
            mNestedTopics.push(topic);
        }
    }

    public int getId () {
        return mId;
    }

    /**
     * @return This lesson name.
     */
    public String getName() {
        return mName;
    }

    /**
     * @return A nested "list" of topics this lesson belongs to.
     */
    public Stack<String> getTopics () {
        return mNestedTopics;
    }

    @Override
    public boolean equals (Object other) {
        Lesson o;

        if (other instanceof Lesson) {
            o = (Lesson) other;

            return mId == o.mId;
        }

        return false;
    }

    @Override
    public String toString () {
        return mName;
    }
}
