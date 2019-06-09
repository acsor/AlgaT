package unibo.algat.graphics;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import unibo.algat.lesson.Lesson;
import unibo.algat.lesson.LessonLoader;

import java.util.ResourceBundle;
import java.util.Set;

public class LessonsViewController {
	@FXML
	private TreeView<LessonTreeNode> treeView;

	@FXML
	private void initialize() {
		final ResourceBundle r = ResourceBundle.getBundle("res.Interface");

        treeView.setRoot(
            buildLessonTree(LessonLoader.lessons(), r)
		);
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
        	new LessonTreeNode(resources.getString("gui.lessons"))
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

		public LessonTreeNode(String topic) {
			this.topic = topic;
			lesson = null;
		}

		public LessonTreeNode(Lesson lesson) {
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
