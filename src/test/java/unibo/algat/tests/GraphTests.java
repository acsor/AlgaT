package unibo.algat.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import unibo.algat.graph.Graph;
import unibo.algat.graph.Vertex;

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
	 * <p>Ensures that inserting/deleting null-valued vertices into a graph
	 * raises an exception.</p>
	 */
	@ParameterizedTest
	@NullSource
	void testInsertDeleteVertexExceptions (Vertex<Void> n) {
		Executable insertNull = () -> mGraph.insertVertex(n);
		Executable deleteNull = () -> mGraph.deleteVertex(n);

		assertThrows(NullPointerException.class, insertNull);
		assertThrows(NullPointerException.class, deleteNull);
	}

	/**
	 * <p>Ensures {@link Graph#insertVertex} and {@link Graph#deleteVertex} work as
	 * intended when fed admissible values.</p>
	 */
	@ParameterizedTest
	@ValueSource(ints = {0, 3, 4})
	void testInsertDeleteVertex(Integer id) {
		assertTrue(mGraph.insertVertex(new Vertex<>(id)));
		assertFalse(mGraph.insertVertex(new Vertex<>(id)));

		assertTrue(mGraph.deleteVertex(new Vertex<>(id)));
		assertFalse(mGraph.deleteVertex(new Vertex<>(id)));
	}

	/**
	 * Ensures that invoking {@link Graph#containsVertex} on a null-valued
	 * argument produces an exception.
	 */
	@ParameterizedTest
	@NullSource
	void testContainsVertexExceptions (Vertex<Void> n) {
		Executable containsNull = () -> mGraph.containsVertex(n);

		assertThrows(NullPointerException.class, containsNull);
	}

	/**
	 * <p>Ensures {@link Graph#containsVertex} works as intended when fed
	 * admissible values.</p>
	 */
	@ParameterizedTest
	@ValueSource(ints = {0, 3, 4})
	void testContainsVertex (int id) {
		// Double insertion/removals are inserted to guarantee extra reliability
		mGraph.insertVertex(new Vertex<>(id));
		mGraph.insertVertex(new Vertex<>(id));
		assertTrue(mGraph.containsVertex(new Vertex<>(id)));
		assertEquals(1, mGraph.vertices().size());

		mGraph.deleteVertex(new Vertex<>(id));
		mGraph.deleteVertex(new Vertex<>(id));
		assertFalse(mGraph.containsVertex(new Vertex<>(id)));
		assertEquals(0, mGraph.vertices().size());
	}

	/**
	 * <p>Esnures {@link Graph#adjacents} raises the agreed-upon exceptions
	 * when fed inappropriate values.</p>
	 */
	@Test
	void testAdjacentsExceptions () {
		Executable adjacentsNull = () -> mGraph.adjacents(null);
		Executable adjacentsNoSuchElement = () -> mGraph.adjacents(
			new Vertex<>(6)
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
		final Set<Vertex<Void>> expectedAdjList = new HashSet<>();

		for (int id: Arrays.asList(1, 2, 3, 4, 5))
			assertTrue(mGraph.insertVertex(new Vertex<>(id)));

		assertTrue(mGraph.insertEdge(new Vertex<>(1), new Vertex<>(2)));
		assertTrue(mGraph.insertEdge(new Vertex<>(1), new Vertex<>(3)));
		assertTrue(mGraph.insertEdge(new Vertex<>(1), new Vertex<>(4)));
		assertTrue(mGraph.insertEdge(new Vertex<>(1), new Vertex<>(5)));

		expectedAdjList.add(new Vertex<>(2));
		expectedAdjList.add(new Vertex<>(3));
		expectedAdjList.add(new Vertex<>(4));
		expectedAdjList.add(new Vertex<>(5));

		assertEquals(expectedAdjList, mGraph.adjacents(new Vertex<>(1)));
	}

	/**
	 * <p>Ensures that any declared exception is thrown when
	 * {@link Graph#insertEdge} and {@link Graph#deleteEdge} are fed
	 * inappropriate argument values.</p>
	 */
	@Test
	void testInsertDeleteEdgeExceptions () {
		Vertex<Void> n1 = new Vertex<>(0), n2 = new Vertex<>(1);

		Executable insertNoElement = () -> mGraph.insertEdge(n1, n2);
		Executable deleteNoElement = () -> mGraph.deleteEdge(n1, n2);
		Executable insertNull = () -> mGraph.insertEdge(null, new Vertex<>(3));
		Executable deleteNull = () -> mGraph.deleteEdge(null, new Vertex<>(3));

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
		Vertex<Void> n1 = new Vertex<>(0), n2 = new Vertex<>(1);

		mGraph.insertVertex(n1);
		mGraph.insertVertex(n2);

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
			new Vertex<>(1), new Vertex<>(2)
		);
		Executable nullPointerLeft = () -> mGraph.containsEdge(
			null, new Vertex<>(1)
		);
		Executable nullPointerRight = () -> mGraph.containsEdge(
			new Vertex<>(1), null
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
		Vertex<Void> n1 = new Vertex<>(0), n2 = new Vertex<>(1);

		mGraph.insertVertex(n1);
		mGraph.insertVertex(n2);

		mGraph.insertEdge(n1, n2);
		assertTrue(mGraph.containsEdge(n1, n2));

		mGraph.deleteEdge(n1, n2);
		assertFalse(mGraph.containsEdge(n1, n2));
	}

	/**
	 * Tests {@link Graph#clear()} method.
	 */
	@Test
	void testClear () {
		for (int i = 0; i < 10; i++)
			mGraph.insertVertex(new Vertex<Void>(i));

		assertEquals(mGraph.vertices().size(), 10);

		mGraph.clear();
		assertEquals(mGraph.vertices().size(), 0);
	}

	/**
	 * <p>Overridden by derived {@code GraphTests} classes to let test one
	 * specific implementation of {@link Graph}.</p>
	 * @return A {@link Graph} instance to be tested.
	 */
	protected abstract Graph<Void> graphFactory();
}
