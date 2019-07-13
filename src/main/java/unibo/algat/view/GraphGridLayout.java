package unibo.algat.view;

import javafx.geometry.Point2D;
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
		mNodePos = new HashMap<>();
		mCurrPos = 0;

		setColumns(columns);
	}

	@Override
	public Point2D layout(NodeView node) {
		int row, col;
		Integer position = mNodePos.get(node);

		if (position == null) {
			mNodePos.put(node, mCurrPos);
			position = mCurrPos;
			mCurrPos++;
		}

		row = position / mCols;
		col = position % mCols;

		return new Point2D(
			col * node.computePrefWidth(-1), row * node.computePrefHeight(-1)
		);
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

