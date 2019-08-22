package unibo.algat.view;

import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class GraphGridLayout implements GraphLayout {
	private Map<NodeView, Integer> mNodePos;
	private int mCols;
	/**
	 * A priority queue, indicating at which slot place the next element.
	 * This strategy allows recycling a previous position when it is removed
	 * from the graph view.
	 */
	private PriorityQueue<Integer> mNext;

	public GraphGridLayout() {
        this(1);
	}

	public GraphGridLayout(int columns) {
		clear();
		setColumns(columns);
	}

	@Override
	public Point2D layout (NodeView node) {
		int row, col;
		Integer position = mNodePos.get(node);

		if (position == null) {
			position = mNext.poll();
			mNodePos.put(node, position);

			if (mNext.isEmpty())
                mNext.add(mNodePos.size());
		}

		row = position / mCols;
		col = position % mCols;

		return new Point2D(
			col * node.computePrefWidth(-1), row * node.computePrefHeight(-1)
		);
	}

	@Override
	public int getPosition(NodeView view) {
		if (mNodePos.containsKey(view)) {
			return mNodePos.get(view);
		} else {
			throw new IllegalArgumentException("View not yet laid out");
		}
	}

	@Override
	public void remove (NodeView view) {
        Integer removed = mNodePos.remove(view);

        if (removed != null)
        	mNext.add(removed);
	}

	@Override
	public void clear() {
		mNodePos = new HashMap<>();
		mNext = new PriorityQueue<>(1);

		mNext.add(0);
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

