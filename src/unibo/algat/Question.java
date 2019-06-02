package unibo.algat;

import java.util.HashSet;
import java.util.Set;

/**
 * Class encapsulating data about a question related to a specific lesson.
 */
public class Question {
    private String mText;
    private Set<String> mChoices;
    private String mCorrectChoice;

    public Question(String questionText) {
        mText = questionText;
        mChoices = new HashSet<>();
    }

    /**
     * @param choice Choice value to set as one of the available ones.
     */
    public void addChoice (String choice) {
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
    public void setCorrectChoice (String choice) {
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
     * @param choice Choice value to check whether is the correct one
     * @return {@code true} if {@code choice} was the correct answer, {@code
     * false} otherwise.
     * @throws IllegalStateException If no correct choice has been marked yet
     * by {@code Question.setCorrectChoice()}.
     */
    public boolean isCorrect (String choice) {
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
    public Set<String> choices () {
        return mChoices;
    }
}
