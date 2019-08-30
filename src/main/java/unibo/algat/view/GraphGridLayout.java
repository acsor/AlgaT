package unibo.algat.view;

import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class GraphGridLayout implements GraphLayout {
	private Map<VertexView, Integer> mVertexPos;
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
	public Point2D layout (VertexView vertex) {
		int row, col;
		Integer position = mVertexPos.get(vertex);

		if (position == null) {
			position = mNext.poll();
			mVertexPos.put(vertex, position);

			if (mNext.isEmpty())
                mNext.add(mVertexPos.size());
		}

		row = position / mCols;
		col = position % mCols;

		return new Point2D(
			col * vertex.computePrefWidth(-1), row * vertex.computePrefHeight(-1)
		);
	}

	@Override
	public int getPosition(VertexView view) {
		if (mVertexPos.containsKey(view)) {
			return mVertexPos.get(view);
		} else {
			throw new IllegalArgumentException("View not yet laid out");
		}
	}

	@Override
	public void remove (VertexView view) {
        Integer removed = mVertexPos.remove(view);

        if (removed != null)
        	mNext.add(removed);
	}

	@Override
	public void clear() {
		mVertexPos = new HashMap<>();
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

