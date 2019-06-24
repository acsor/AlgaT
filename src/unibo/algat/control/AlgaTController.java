package unibo.algat.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import unibo.algat.AlgaT;
import unibo.algat.lesson.Lesson;
import unibo.algat.lesson.LessonLoader;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Set;

public class AlgaTController {
	@FXML private MainMenuController mMainMenuController;
	@FXML private TabPane mTabPane;
	@FXML private Tab mLessonsTab;
	@FXML private TreeView<LessonTreeNode> mTreeView;
	@FXML private Button mStartLesson;

	private ResourceBundle mInterface;
	private LessonTreeNode mSelected;
	/**
	 * <p>Monitors the lessons (main, non-closeable) tab, deactivating the
	 * "Close active tab" menu item when the former is visible.</p>
	 */
	private final ChangeListener<Tab> mSelectedTabListener
		= new ChangeListener<> () {
		@Override
		public void changed (
			ObservableValue<? extends Tab> observable, Tab oldValue,
			Tab newValue
		) {
			String windowTitle;

			if (newValue == mLessonsTab) {
				// Deactivate or activate the "Close tab" menu item
				mMainMenuController.mCloseTab.setDisable(true);
				windowTitle = mInterface.getString("gui.app.title");
			} else {
				mMainMenuController.mCloseTab.setDisable(false);
				windowTitle = String.format(
					"%s - %s", newValue.getText(),
					mInterface.getString("gui.app.title")
				);
			}

			// Set the updated window title
			AlgaT.getInstance().setWindowTitle(windowTitle);
		}
	};
	/**
	 * <p>Handles closing of lessons tab when the Cltr+W combination, or
	 * "Close active tab" menu item, is issued.</p>
	 */
	private final EventHandler<ActionEvent> mOnCloseTabHandler =
		new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				Tab selected = mTabPane.getSelectionModel().getSelectedItem();

				// I want to assume selected will not be null. If it turns out
				// to be, one might exploit the occasion to fix a bug
				if (selected != mLessonsTab) {
					mTabPane.getTabs().remove(selected);
				}
			}
		};
	/**
	 * <p>Handles the selection of a new lesson in the lesson tree view.</p>
	 */
	private final ChangeListener<TreeItem<LessonTreeNode>>
		mSelectedLessonListener = new ChangeListener<>() {
		@Override
		public void changed(
			ObservableValue<? extends TreeItem<LessonTreeNode>> observable,
			TreeItem<LessonTreeNode> oldValue,
			TreeItem<LessonTreeNode> newValue
		) {
			mSelected = newValue.getValue();

			if (mSelected.lesson != null) {
				mStartLesson.setDisable(false);
			} else {
				mStartLesson.setDisable(true);
			}
		}
	};

	public AlgaTController () {
		mInterface = ResourceBundle.getBundle("res.Interface");
	}

	@FXML
	private void initialize() {
		mTabPane.getSelectionModel().selectedItemProperty().addListener(
			mSelectedTabListener
		);
		mMainMenuController.mCloseTab.setOnAction(mOnCloseTabHandler);
		mTreeView.getSelectionModel().selectedItemProperty().addListener(
			mSelectedLessonListener
		);
		mTreeView.setRoot(buildLessonTree(LessonLoader.lessons(), mInterface));

		mTabPane.getTabs().add(mLessonsTab);
	}

	/**
	 * <p>Handles the opening of a new lesson tab when the "Start lesson" button
	 * is pressed.</p>
	 */
	@FXML
	private void onTabOpen(ActionEvent event) throws IOException {
		// TODO Do not re-raise any exception -- handle it by displaying a
		//  dialog to the user indicating the failure
		if (mSelected != null && mSelected.lesson != null) {
			mTabPane.getTabs().add(
				new Tab(
					mSelected.lesson.getName(),
					LessonViewFactory.lessonView(mSelected.lesson)
				)
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
	 * hierarchy containing available the lessons.</p>
	 *
	 * <p>{@code LessonTreeNode} effectively acts as a "union". When used
	 * as an interior node, only the {@code topic} field will be set, while when
	 * being used as a leaf node, {@code lesson} will be set instead.</p>
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
