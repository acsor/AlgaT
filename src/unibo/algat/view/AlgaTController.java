package unibo.algat.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import unibo.algat.lesson.Lesson;
import unibo.algat.lesson.LessonLoader;

import java.util.ResourceBundle;
import java.util.Set;

public class AlgaTController {
	@FXML private TabPane mTabPane;
	@FXML private Tab mLessonTab;
	@FXML private TreeView<LessonTreeNode> mTreeView;
	@FXML private Button mStartLesson;

	private LessonTreeNode mSelected;
	/**
	 * <p>Handles the selection of a new lesson in the lesson tree view.</p>
	 */
	private final ChangeListener<TreeItem<LessonTreeNode>> mSelectedListener =
		new ChangeListener<>() {
			@Override
			public void changed(
				ObservableValue<? extends TreeItem<LessonTreeNode>> observable,
				TreeItem<LessonTreeNode> oldValue,
				TreeItem<LessonTreeNode> newValue
			) {
				mSelected = newValue.getValue();

				if (mSelected != null && mSelected.lesson != null) {
					mStartLesson.setDisable(false);
				} else {
					mStartLesson.setDisable(true);
				}
			}
	};

	public void initialize() {
		final ResourceBundle r = ResourceBundle.getBundle("res.Interface");

		mStartLesson.setDisable(true);
		mTreeView.getSelectionModel().selectedItemProperty().addListener(
			mSelectedListener
		);
		mTreeView.setRoot(buildLessonTree(LessonLoader.lessons(), r));
	}

	/**
	 * <p>Handles the opening of a new lesson tab when the "Start lesson"
	 * button is pressed.</p>
	 */
	@FXML
	private void openNewTab(ActionEvent event) {
        if (mSelected != null && mSelected.lesson != null) {
        	mTabPane.getTabs().add(
        		new Tab(mSelected.lesson.getName())
			);
		}
	}

	/**
	 * @param lessons {@code Set} of lessons to build the hierarchy from.
	 * @param resources {@code ResourceBundle} aiding in the content
	 *                                           localization.
	 * @return A {@code TreeItem} hierarchy, representing the data from the
	 * {@code lessons} parameter in a tree-like form.
	 */
	private TreeItem<LessonTreeNode> buildLessonTree (
		Set<Lesson> lessons, ResourceBundle resources
	) {
		final TreeItem<LessonTreeNode> root = new TreeItem<>(
			new LessonTreeNode(resources.getString("gui.algat.treeRoot"))
		);
		TreeItem<LessonTreeNode> curr;
		boolean found;
		LessonTreeNode lookedFor;
		ObservableList<TreeItem<LessonTreeNode>> children;

		root.setExpanded(true);

		for (Lesson l: lessons) {
			curr = root;

			// Consume the topics from each lesson and add them as interior
			// nodes of the tree
			for (String topic: l.getTopics()) {
				children = curr.getChildren();
				lookedFor = new LessonTreeNode(topic);
				found = false;

				for (TreeItem<LessonTreeNode> child: children) {
					if (child.getValue().equals(lookedFor)) {
						curr = child;
						found = true;
					}
				}

				if (!found) {
					children.add(
						children.size(),
						new TreeItem<>(new LessonTreeNode(topic))
					);
					curr = children.get(children.size() - 1);
				}
			}

			// Add the leaf node
			curr.getChildren().add(new TreeItem<>(new LessonTreeNode(l)));
		}

		return root;
	}

	/**
	 * <p>Utility class encapsulating data in nodes of a {@code TreeItem}
	 * hierarchy that contains the available program lessons.</p>
	 */
	static class LessonTreeNode {
		String topic;
		Lesson lesson;

		LessonTreeNode(String topic) {
			this.topic = topic;
			lesson = null;
		}

		LessonTreeNode(Lesson lesson) {
			this.lesson = lesson;
			topic = lesson.getName();
		}

		@Override
		public boolean equals (Object other) {
			LessonTreeNode o;

			if (other instanceof LessonTreeNode) {
				o = (LessonTreeNode) other;

				return topic.equals(o.topic) && lesson == o.lesson;
			}

			return false;
		}

		@Override
		public String toString () {
			return (lesson == null) ? topic : lesson.getName();
		}
	}
}
