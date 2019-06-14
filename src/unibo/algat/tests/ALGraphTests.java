package unibo.algat.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import unibo.algat.graph.ALGraph;
import unibo.algat.graph.Graph;
import unibo.algat.graph.Node;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO Generalize the supplied implementations of Graph to be tested
 * TODO Verify the test suite has been correctly defined so far
 */
class ALGraphTests {
    /**
     * Ensures that inserting/deleting null-valued node elements into a graph
     * raises an exception.
     */
    @ParameterizedTest
    @NullSource
    void testInsertDeleteNodeNull(Node<Void> n) {
        final Graph<Void> g = new ALGraph<>();

        Executable insertNull = () -> g.insertNode(n);
        Executable deleteNull = () -> g.deleteNode(n);

        assertThrows(NullPointerException.class, insertNull);
        assertThrows(NullPointerException.class, deleteNull);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 31415, -31415})
    void testInsertDeleteNode(Integer id) {
        final Graph<Void> g = new ALGraph<>();

        g.insertNode(new Node<>(id));
        assertTrue(g.containsNode(new Node<>(id)));
        assertEquals(1, g.vertices().size());

        g.deleteNode(new Node<>(id));
        assertFalse(g.containsNode(new Node<>(id)));
        assertEquals(0, g.vertices().size());
    }

    /**
     * Ensures that invoking {@code contains} on a null-valued argument
     * produces an exception.
     */
    @ParameterizedTest
    @NullSource
    void testContainsNull(Node<Void> n) {
        final Graph<Void> g = new ALGraph<>();

        Executable containsNull = () -> g.containsNode(n);

        assertThrows(NullPointerException.class, containsNull);
    }

    @Test
    void testAdjacents() {
        final Graph<Void> g = new ALGraph<>();
        final Set<Node<Void>> expectedAdjList = new HashSet<>();

        Executable adjacentsNull = () -> g.adjacents(null);
        Executable adjacentsNoSuchElement = () -> g.adjacents(new Node<>(6));

        assertThrows(NullPointerException.class, adjacentsNull);
        assertThrows(NoSuchElementException.class, adjacentsNoSuchElement);

        g.insertNode(new Node<>(1));
        g.insertNode(new Node<>(2));
        g.insertNode(new Node<>(3));
        g.insertNode(new Node<>(4));
        g.insertNode(new Node<>(5));

        g.insertEdge(new Node<>(1), new Node<>(2));
        g.insertEdge(new Node<>(1), new Node<>(3));
        g.insertEdge(new Node<>(1), new Node<>(4));
        g.insertEdge(new Node<>(1), new Node<>(5));

        expectedAdjList.add(new Node<>(2));
        expectedAdjList.add(new Node<>(3));
        expectedAdjList.add(new Node<>(4));
        expectedAdjList.add(new Node<>(5));

        assertEquals(expectedAdjList, g.adjacents(new Node<>(1)));
    }

    @Test
    void testInsertDeleteEdgeNull() {
        final Graph<Void> g = new ALGraph<>();

        Executable insertNull = () -> g.insertEdge(null, new Node<>(3));
        Executable deleteNull = () -> g.deleteEdge(null, new Node<>(3));

        assertThrows(NullPointerException.class, insertNull);
        assertThrows(NullPointerException.class, deleteNull);
    }

    @Test
    void testInsertDeleteEdge() {
        final Graph<Void> g = new ALGraph<>();
        Node<Void> n1 = new Node<>(0), n2 = new Node<>(1);
        Executable insertNoNodes = () -> g.insertEdge(n1, n2);
        Executable deleteNoNodes = () -> g.deleteEdge(n1, n2);

        // Ensure that, when inserting/deleting an edge, an exception is
        // raised if its corresponding nodes are not present
        assertThrows(NoSuchElementException.class, insertNoNodes);
        assertThrows(NoSuchElementException.class, deleteNoNodes);

        // Now we insert the nodes, allowing the graph to actually contain edges
        g.insertNode(n1);
        g.insertNode(n2);
        g.insertEdge(n1, n2);

        assertTrue(g.containsEdge(n1, n2));
        g.deleteEdge(n1, n2);
        assertFalse(g.containsEdge(n1, n2));
    }
}
