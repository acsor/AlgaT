package unibo.algat.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import unibo.algat.graph.EdgeWeight;

public class WeightView extends Label {
	private DoubleProperty mWeight;
	private EdgeView mEdgeView;

	public WeightView(EdgeView edgeView, EdgeWeight<?> weight) {
		mWeight = new SimpleDoubleProperty(this, "weight", 0);
		mEdgeView = edgeView;

		setWeight(weight);
		textProperty().bind(mWeight.asString("%.2f"));
		// Add a layout offset in such a way to center the weight view
		translateXProperty().bind(widthProperty().divide(-2.0));
		translateYProperty().bind(heightProperty().divide(-2.0));

		getStyleClass().add("weight-view");
	}

	public EdgeView getEdgeView () {
		return mEdgeView;
	}

	public void setWeight (EdgeWeight<?> weight) {
        if (weight != null)
        	mWeight.bind(weight);
	}

	public double getWeight () {
		return mWeight.get();
	}
}
