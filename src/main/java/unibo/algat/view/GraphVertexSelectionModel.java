package unibo.algat.view;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>A {@link MultipleSelectionModel} implementation for a {@code
 * GraphView} vertices, where indices correspond to the position assigned
 * by the current {@link GraphLayout}.</p>
 */
class GraphVertexSelectionModel extends MultipleSelectionModel<VertexView> {
	private GraphView<?> mGraphV;

	private ObservableList<Integer> mIndices;
	private ObservableList<VertexView> mItems;
	private IntegerProperty mItemCount;

	GraphVertexSelectionModel(GraphView<?> graphView) {
		mGraphV = graphView;

		mIndices = FXCollections.observableList(new LinkedList<>());
		mItems = FXCollections.observableList(new LinkedList<>());
		mItemCount = new SimpleIntegerProperty(this, "itemCount");

		// Safer code at the expense of some memory
		mIndices.addListener(
			(InvalidationListener) o -> mItemCount.set(mItems.size())
		);
	}

	int getItemCount () {
		return mItemCount.get();
	}

	ReadOnlyIntegerProperty itemCountProperty () {
		return mItemCount;
	}

	@Override
	public ObservableList<Integer> getSelectedIndices() {
		return FXCollections.unmodifiableObservableList(mIndices);
	}

	@Override
	public ObservableList<VertexView> getSelectedItems() {
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
		for (VertexView v: mGraphV.mVertices.values())
			select(v);
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
			for (VertexView v: mGraphV.mVertices.values()) {
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
	public void select(VertexView obj) {
		if (!mItems.contains(obj)) {
			mItems.add(obj);
			mIndices.add(mGraphV.mLayout.getPosition(obj));

			obj.setSelected(true);
		}
	}

	@Override
	public void clearSelection(int index) {
		if (mIndices.contains(index)) {
			for (VertexView v: mGraphV.mVertices.values()) {
				if (mGraphV.mLayout.getPosition(v) == index) {
					v.setSelected(false);
					mItems.remove(v);

					break;
				}
			}

			mIndices.remove(Integer.valueOf(index));
		}
	}

	void clearSelection (VertexView obj) {
		if (mItems.contains(obj)) {
			mItems.remove(obj);
			mIndices.remove(Integer.valueOf(mGraphV.mLayout.getPosition(obj)));
		}
	}

	@Override
	public void clearSelection() {
		List.copyOf(mItems).forEach(v -> v.setSelected(false));

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
