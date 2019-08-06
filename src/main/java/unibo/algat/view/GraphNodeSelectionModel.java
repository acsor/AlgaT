package unibo.algat.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

import java.util.LinkedList;

/**
 * <p>A {@link MultipleSelectionModel} implementation for a {@code
 * GraphView} nodes, where indices correspond to the position assigned
 * by the current {@link GraphLayout}.</p>
 */
public class GraphNodeSelectionModel extends MultipleSelectionModel<NodeView> {
	private GraphView<?> mGraphV;

	private ObservableList<Integer> mIndices;
	private ObservableList<NodeView> mItems;

	GraphNodeSelectionModel (GraphView<?> graphView) {
		mGraphV = graphView;

		mIndices = FXCollections.observableList(new LinkedList<>());
		mItems = FXCollections.observableList(new LinkedList<>());
	}

	@Override
	public ObservableList<Integer> getSelectedIndices() {
		return FXCollections.unmodifiableObservableList(mIndices);
	}

	@Override
	public ObservableList<NodeView> getSelectedItems() {
		return FXCollections.unmodifiableObservableList(mItems);
	}

	@Override
	public void selectIndices(int index, int... indices) {
		select(index);

		for (int i: indices)
			select(i);
	}

	@Override
	public void selectAll() {
		int index;

		for (NodeView v: mGraphV.mNodes.values()) {
			index = mGraphV.mLayout.getPosition(v);

			if (!mIndices.contains(index))
				select(index);
		}
	}

	@Override
	public void clearAndSelect(int index) {
		mIndices.clear();
		mItems.clear();

		select(index);
	}

	@Override
	public void select(int index) {
		if (!mIndices.contains(index)) {
			for (NodeView v: mGraphV.mNodes.values()) {
				if (mGraphV.mLayout.getPosition(v) == index) {
					mItems.add(v);
					mIndices.add(index);

					v.setSelected(true);

					break;
				}
			}
		}
	}

	@Override
	public void select(NodeView obj) {
		if (!mItems.contains(obj)) {
			mItems.add(obj);
			mIndices.add(mGraphV.mLayout.getPosition(obj));

			obj.setSelected(true);
		}
	}

	@Override
	public void clearSelection(int index) {
		if (mIndices.contains(index)) {
			for (NodeView v: mGraphV.mNodes.values()) {
				if (mGraphV.mLayout.getPosition(v) == index) {
					v.setSelected(false);
					mItems.remove(v);

					break;
				}
			}

			mIndices.remove(Integer.valueOf(index));
		}
	}

	public void clearSelection (NodeView obj) {
		if (mItems.contains(obj)) {
			mItems.remove(obj);
			mIndices.remove(Integer.valueOf(mGraphV.mLayout.getPosition(obj)));
		}
	}

	@Override
	public void clearSelection() {
		mItems.forEach(v -> v.setSelected(false));

		mIndices.clear();
		mItems.clear();
	}

	@Override
	public boolean isSelected(int index) {
		return mIndices.contains(index);
	}

	@Override
	public boolean isEmpty() {
		return mIndices.isEmpty();
	}

	@Override
	public void selectPrevious() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void selectNext() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void selectFirst() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void selectLast() {
		throw new UnsupportedOperationException("Not implemented");
	}
}
