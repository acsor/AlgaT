package unibo.algat.view;

import javafx.geometry.Insets;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

public class GraphGridLayout implements GraphLayout {
	private Map<Node, Integer> mNodePos;
	private int mCurrPos;
	private int mCols;

	public GraphGridLayout() {
		this(1);
	}

	public GraphGridLayout(int columns) {
		setColumns(columns);
		mNodePos = new HashMap<>();
		mCurrPos = 0;
	}

	@Override
	public void layout(NodeView node, Insets bounds) {
		int position;
		int row, col;

		if (!mNodePos.containsKey(node)) {
			mNodePos.put(node, mCurrPos);
			position = mCurrPos;
			mCurrPos++;
		} else {
			position = mNodePos.get(node);
		}

		row = position / mCols;
		col = position % mCols;

		node.setLayoutX(bounds.getLeft() + col * node.prefWidth(-1));
		node.setLayoutY(bounds.getTop() + row * node.prefHeight(-1));
	}

	public void setColumns (int columns) {
		if (columns > 0) {
			mCols = columns;
		} else {
			throw new IllegalArgumentException("columns must be > 0");
		}
	}

	public int getColumns () {
		return mCols;
	}
}

