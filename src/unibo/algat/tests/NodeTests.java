package unibo.algat.tests;

import org.junit.jupiter.api.Test;
import unibo.algat.graph.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NodeTests {
    @Test
    void testSetGetData () {
        final String expOutput1 = "1234567890";
        final String expOutput2 = "0987654321";
        Node<String> n = new Node<>();

        n.setData(expOutput1);
        assertEquals(expOutput1, n.getData());

        n.setData(expOutput2);
        assertEquals(expOutput2, n.getData());
    }

    void testEquals () {
        Node<Integer> n1 = new Node<>(10), n2 = new Node<>(10),
                n3 = new Node<>(20);

        assertEquals(n1, n2);
        assertNotEquals(n1, n3);
        assertNotEquals(n2, n3);
    }
}
