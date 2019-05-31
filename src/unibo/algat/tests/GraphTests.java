package unibo.algat.tests;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import unibo.algat.graph.AListGraph;
import unibo.algat.graph.Graph;
import unibo.algat.graph.Node;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GraphTests {
    @ParameterizedTest
    @MethodSource("graphFactory")
    @ValueSource(ints = {0, 31415, -31415})
    void testInsertDeleteNode(Graph<Integer> g, Integer v) {
        g.insertNode(new Node<>(v));
        assertTrue(g.containsNode(new Node<>(v)));
        assertEquals(1, g.vertices().size());

        g.deleteNode(new Node<>(v));
        assertFalse(g.containsNode(new Node<>(v)));
        assertEquals(0, g.vertices().size());
    }

    /**
     * Ensures that inserting/deleting null-value node elements into a graph
     * raises an exception.
     */
    @ParameterizedTest
    @MethodSource("graphFactory")
    @NullSource
    void testInsertDeleteNullNode(Graph<Integer> g, Node<Integer> n) {
        Executable insertNull = () -> g.insertNode(n);
        Executable deleteNull = () -> g.deleteNode(n);

        assertThrows(IllegalArgumentException.class, insertNull);
        assertThrows(IllegalArgumentException.class, deleteNull);
    }

    /**
     * Ensures that invoking {@code contains} on a null-valued argument
     * produces an exception.
     */
    @ParameterizedTest
    @MethodSource("graphFactory")
    @NullSource
    void testContainsNullNode(Graph<Integer> g, Node<Integer> n) {
        Executable containsNull = () -> g.containsNode(n);

        assertThrows(IllegalArgumentException.class, containsNull);
    }

    @ParameterizedTest
    @MethodSource("graphFactory")
    void testInsertDeleteEdge(Graph<Integer> g) {
        Node<Integer> n1 = new Node<>(10), n2 = new Node<>(1010);
        Executable insertNoNodes = () -> g.insertEdge(n1, n2);

        // Ensure that, when adding an edge, an exception is raised if its
        // corresponding nodes are not present
        assertThrows(NoSuchElementException.class, insertNoNodes);

        assertFalse(g.containsEdge(n1, n2));
        g.deleteEdge(n1, n2);
        assertFalse(g.containsEdge(n1, n2));

        // Now we insert the nodes, allowing the graph to actually contain edges
        g.insertNode(n1);
        g.insertNode(n2);

        assertTrue(g.containsEdge(n1, n2));
        g.deleteEdge(n1, n2);
        assertFalse(g.containsEdge(n1, n2));
    }

    /**
     * This factory method allows abstracting away the testing of different
     * implementations of {@code Graph}, so that the testing code is written
     * once for all the possible implementations.
     * @return A fresh {@code Graph} implementation to be consumed by test
     * cases.
     */
    static Stream<Graph<Integer>> graphFactory () {
        return Stream.of(new AListGraph<>());
    }
}
