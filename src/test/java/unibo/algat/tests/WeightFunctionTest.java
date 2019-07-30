package unibo.algat.tests;

import unibo.algat.graph.ALGraph;
import unibo.algat.graph.Graph;
import unibo.algat.graph.Node;
import unibo.algat.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Abstract base class providing a common graph instance to aid in testing
 * weight functions.</p>
 */
public abstract class WeightFunctionTest {
	protected final Graph<Integer> mGraph;
	protected final List<Node<Integer>> mNodes;
	protected final List<Pair<Node<Integer>, Node<Integer>>> mEdges;

	public WeightFunctionTest() {
		mGraph = new ALGraph<>();
		mNodes = Arrays.asList(
			new Node<>(0, 10), new Node<>(1, 20), new Node<>(2, 30),
			new Node<>(3, 40), new Node<>(4, 50)
		);
		mEdges = Arrays.asList(
			new Pair<>(mNodes.get(0), mNodes.get(1)),
			new Pair<>(mNodes.get(0), mNodes.get(4)),
			new Pair<>(mNodes.get(1), mNodes.get(2)),
			new Pair<>(mNodes.get(1), mNodes.get(3)),
			new Pair<>(mNodes.get(1), mNodes.get(4)),
			new Pair<>(mNodes.get(4), mNodes.get(0))
		);

		for(Node<Integer> n: mNodes)
			mGraph.insertNode(n);

		for (Pair<Node<Integer>, Node<Integer>> edge: mEdges)
			mGraph.insertEdge(edge.getFirst(), edge.getSecond());
	}
}
