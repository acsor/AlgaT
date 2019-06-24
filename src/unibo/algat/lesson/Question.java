package unibo.algat.lesson;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Class encapsulating data about a question and providing utility
 * methods.</p>
 */
public class Question {
    private int mLessonId;
    private int mId;
    private String mText;
    private Set<Choice> mChoices;
    private Choice mCorrectChoice;

    Question(int lessonId, int id, String text) {
        mLessonId = lessonId;
        mId = id;
        mText = text;
        mChoices = new HashSet<>();
        mCorrectChoice = null;
    }

    /**
     * @return This question id.
     */
    public int getId () {
        return mId;
    }

    /**
     * @return The lesson id this question is associated to.
     */
    public int getLessonId () {
        return mLessonId;
    }

    /**
     * @param choice Choice value to set as one of the available ones.
     * @throws NullPointerException if {@code choice} is {@code null}.
     */
    public void addChoice (Choice choice) {
        if (choice != null)
            mChoices.add(choice);
        else
            throw new NullPointerException("choice param was null");
    }

    /**
     * @param choice Choice value to set as the correct one.
     * @throws NullPointerException if {@code choice} is {@code null}
     * @throws IllegalStateException if {@code choice} hadn't yet been
     * inserted into the available list of choices.
     */
    public void setCorrectChoice (Choice choice) {
        if (choice != null) {
            if (mChoices.contains(choice))
                mCorrectChoice = choice;
            else
                throw new IllegalStateException(
                    "The given choice argument is not contained by the " +
                        "list of available choices"
                );
        } else {
            throw new NullPointerException("choice param was null");
        }
    }

    /**
     * @param choice Choice value to check whether is the correct one.
     * @return {@code true} if {@code choice} was the correct answer, {@code
     * false} otherwise.
     * @throws IllegalStateException If no correct choice has been marked yet
     * by {@code Question.setCorrectChoice()}.
     */
    public boolean isCorrect (Choice choice) {
        if (mCorrectChoice != null) {
            return mCorrectChoice.equals(choice);
        } else {
            throw new IllegalStateException(
                "No correct choice has yet been set"
            );
        }
    }

    /**
     * @return The list of available choices for the current question.
     */
    public Set<Choice> choices () {
        return mChoices;
    }

    @Override
    public int hashCode () {
        return mId;
    }

    @Override
    public boolean equals (Object other) {
        Question casted;

        if (other instanceof Question) {
            casted = (Question) other;

            return mLessonId == casted.mLessonId && mId == casted.mId;
        }

        return false;
    }

    @Override
    public String toString () {
        return String.format(
            "[%d:%d] \"%.10s\", %d choices", mLessonId, mId, mText,
            mChoices.size()
        );
    }

    /**
     * A nested class of {@code Question}, encapsulating data about a possible
     * answer.
     */
    public static class Choice {
        private int mId;
        private String mText;

        public Choice(int id, String text) {
            this.mId = id;
            this.mText = text;
        }

        public int getId() {
            return mId;
        }

        public String getText() {
            return mText;
        }

        @Override
        public int hashCode () {
            return mId;
        }

        @Override
        public boolean equals (Object other) {
            Choice o;

            if (other instanceof Choice) {
                o = (Choice) other;

                return mId == o.mId;
            }

            return false;
        }

        @Override
        public String toString () {
            return String.format("[%d] %.10s", mId, mText);
        }
    }
}
