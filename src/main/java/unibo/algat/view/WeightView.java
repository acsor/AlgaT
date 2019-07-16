package unibo.algat.view;

import javafx.scene.control.Label;

public class WeightView extends Label {
	private EdgeView mEdgeView;

	public WeightView(EdgeView edgeView) {
		mEdgeView = edgeView;

		getStyleClass().add("weight-view");
		textProperty().bind(edgeView.weightProperty().asString());
	}

	public EdgeView getEdgeView () {
		return mEdgeView;
	}
}
