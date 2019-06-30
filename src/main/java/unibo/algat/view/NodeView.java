package unibo.algat.view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import unibo.algat.graph.Node;

public class NodeView<T> extends Region {
	private Circle mNode;
	private Text mNodeId;

	public NodeView(GraphView<T> parent, Node<T> node) {
		mNode = new Circle(parent.getNodeRadius(), parent.getNodeFill());
		mNodeId = new Text(String.valueOf(node.getId()));

		// TODO How to correctly set the padding within a custom Region?
		setPadding(new Insets(parent.getNodeMargin()));
		getChildren().addAll(mNode, mNodeId);
	}

	public double getRadius () {
		return mNode.getRadius();
	}

	@Override
	public void layoutChildren () {
		final Insets bounds = getInsets();
		final double
			x = bounds.getLeft() + mNode.getRadius(),
			y = bounds.getTop() + mNode.getRadius(),
			width = getWidth() - bounds.getLeft() - bounds.getRight(),
			height = getHeight() - bounds.getTop() - bounds.getBottom();

		layoutInArea(mNode, x, y, width, height, 0, HPos.CENTER, VPos.CENTER);
		layoutInArea(mNodeId, x, y, width, height , 0, HPos.CENTER, VPos.CENTER);
	}

	@Override
	protected double computePrefWidth (double height) {
		final Insets padding = getPadding();

		return padding.getLeft() + 2 * mNode.getRadius() + padding.getRight();
	}

	@Override
	protected double computePrefHeight (double width) {
		final Insets padding = getPadding();

		return padding.getTop() + 2 * mNode.getRadius() + padding.getBottom();
	}
}
