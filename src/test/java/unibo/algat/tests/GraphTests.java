package unibo.algat.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import unibo.algat.graph.Graph;
import unibo.algat.graph.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Abstract base class gathering test code for a generic {@link Graph}
 * implementation.</p>
 * <p>{@code GraphTests} relies on {@link #graphFactory} to delegate the
 * construction of the actual {@link Graph} instance to be tested.</p>
 */
public abstract class GraphTests {
	protected Graph<Void> mGraph;

	@BeforeEach
	protected void initialize () {
		mGraph = graphFactory();
	}

	/**
	 * <p>Ensures that inserting/deleting null-valued nodes into a graph raises
	 * an exception.</p>
	 */
	@ParameterizedTest
	@NullSource
	void testInsertDeleteNodeExceptions (Node<Void> n) {
		Executable insertNull = () -> mGraph.insertNode(n);
		Executable deleteNull = () -> mGraph.deleteNode(n);

		assertThrows(NullPointerException.class, insertNull);
		assertThrows(NullPointerException.class, deleteNull);
	}

	/**
	 * <p>Ensures {@link Graph#insertNode} and {@link Graph#deleteNode} work as
	 * intended when fed admissible values.</p>
	 */
	@ParameterizedTest
	@ValueSource(ints = {0, 3, 4})
	void testInsertDeleteNode(Integer id) {
		assertTrue(mGraph.insertNode(new Node<>(id)));
		assertFalse(mGraph.insertNode(new Node<>(id)));

		assertTrue(mGraph.deleteNode(new Node<>(id)));
		assertFalse(mGraph.deleteNode(new Node<>(id)));
	}

	/**
	 * Ensures that invoking {@link Graph#containsNode} on a null-valued
	 * argument produces an exception.
	 */
	@ParameterizedTest
	@NullSource
	void testContainsNodeExceptions (Node<Void> n) {
		Executable containsNull = () -> mGraph.containsNode(n);

		assertThrows(NullPointerException.class, containsNull);
	}

	/**
	 * <p>Ensures {@link Graph#containsNode} works as intended when fed
	 * admissible values.</p>
	 */
	@ParameterizedTest
	@ValueSource(ints = {0, 3, 4})
	void testContainsNode (int id) {
		// Double insertion/removals are inserted to guarantee extra reliability
		mGraph.insertNode(new Node<>(id));
		mGraph.insertNode(new Node<>(id));
		assertTrue(mGraph.containsNode(new Node<>(id)));
		assertEquals(1, mGraph.nodes().size());

		mGraph.deleteNode(new Node<>(id));
		mGraph.deleteNode(new Node<>(id));
		assertFalse(mGraph.containsNode(new Node<>(id)));
		assertEquals(0, mGraph.nodes().size());
	}

	/**
	 * <p>Esnures {@link Graph#adjacents} raises the agreed-upon exceptions
	 * when fed inappropriate values.</p>
	 */
	@Test
	void testAdjacentsExceptions () {
		Executable adjacentsNull = () -> mGraph.adjacents(null);
		Executable adjacentsNoSuchElement = () -> mGraph.adjacents(
			new Node<>(6)
		);

		assertThrows(NullPointerException.class, adjacentsNull);
		assertThrows(NoSuchElementException.class, adjacentsNoSuchElement);
	}

	/**
	 * <p>Ensures {@link Graph#adjacents} works as intended when fed
	 * admissible values.</p>
	 */
	@Test
	void testAdjacents() {
		final Set<Node<Void>> expectedAdjList = new HashSet<>();

		for (int id: Arrays.asList(1, 2, 3, 4, 5))
			assertTrue(mGraph.insertNode(new Node<>(id)));

		assertTrue(mGraph.insertEdge(new Node<>(1), new Node<>(2)));
		assertTrue(mGraph.insertEdge(new Node<>(1), new Node<>(3)));
		assertTrue(mGraph.insertEdge(new Node<>(1), new Node<>(4)));
		assertTrue(mGraph.insertEdge(new Node<>(1), new Node<>(5)));

		expectedAdjList.add(new Node<>(2));
		expectedAdjList.add(new Node<>(3));
		expectedAdjList.add(new Node<>(4));
		expectedAdjList.add(new Node<>(5));

		assertEquals(expectedAdjList, mGraph.adjacents(new Node<>(1)));
	}

	/**
	 * <p>Ensures that any declared exception is thrown when
	 * {@link Graph#insertEdge} and {@link Graph#deleteEdge} are fed
	 * inappropriate argument values.</p>
	 */
	@Test
	void testInsertDeleteEdgeExceptions () {
		Node<Void> n1 = new Node<>(0), n2 = new Node<>(1);

		Executable insertNoElement = () -> mGraph.insertEdge(n1, n2);
		Executable deleteNoElement = () -> mGraph.deleteEdge(n1, n2);
		Executable insertNull = () -> mGraph.insertEdge(null, new Node<>(3));
		Executable deleteNull = () -> mGraph.deleteEdge(null, new Node<>(3));

		assertThrows(NoSuchElementException.class, insertNoElement);
		assertThrows(NoSuchElementException.class, deleteNoElement);

		assertThrows(NullPointerException.class, insertNull);
		assertThrows(NullPointerException.class, deleteNull);
	}

	/**
	 * <p>Ensures {@link Graph#insertEdge} and {@link Graph#deleteEdge} work
	 * as intended when fed admissible values.</p>
	 */
	@Test
	void testInsertDeleteEdge() {
		Node<Void> n1 = new Node<>(0), n2 = new Node<>(1);

		mGraph.insertNode(n1);
		mGraph.insertNode(n2);

		assertTrue(mGraph.insertEdge(n1, n2));
		// From the second time onward we try to insert the same edge,
		// insertEdge() should return false (same for deleteEdge() below)
		assertFalse(mGraph.insertEdge(n1, n2));

		assertTrue(mGraph.deleteEdge(n1, n2));
		assertFalse(mGraph.deleteEdge(n1, n2));
	}

	/**
	 * <p>Ensures invoking {@link Graph#containsEdge} with inappropriate
	 * values raises the specified exceptions.</p>
	 */
	@Test
	void testContainsEdgeExceptions () {
		Executable noSuchElement = () -> mGraph.containsEdge(
			new Node<>(1), new Node<>(2)
		);
		Executable nullPointerLeft = () -> mGraph.containsEdge(
			null, new Node<>(1)
		);
		Executable nullPointerRight = () -> mGraph.containsEdge(
			new Node<>(1), null
		);
		Executable nullPointerBoth = () -> mGraph.containsEdge(null, null);

		assertThrows(NoSuchElementException.class, noSuchElement);
		assertThrows(NullPointerException.class, nullPointerLeft);
		assertThrows(NullPointerException.class, nullPointerRight);
		assertThrows(NullPointerException.class, nullPointerBoth);
	}

	/**
	 * <p>Ensures {@link Graph#containsEdge} works as intended when fed
	 * admissible values.</p>
	 */
	@Test
	void testContainsEdge () {
		Node<Void> n1 = new Node<>(0), n2 = new Node<>(1);

		mGraph.insertNode(n1);
		mGraph.insertNode(n2);

		mGraph.insertEdge(n1, n2);
		assertTrue(mGraph.containsEdge(n1, n2));

		mGraph.deleteEdge(n1, n2);
		assertFalse(mGraph.containsEdge(n1, n2));
	}

	/**
	 * <p>Overridden by derived {@code GraphTests} classes to let test one
	 * specific implementation of {@link Graph}.</p>
	 * @return A {@link Graph} instance to be tested.
	 */
	protected abstract Graph<Void> graphFactory();
}
