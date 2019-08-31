package unibo.algat.tests;

import org.junit.jupiter.api.Test;
import unibo.algat.graph.Vertex;

import static org.junit.jupiter.api.Assertions.*;

class VertexTests {
    @Test
    void testSetGetData () {
        final String expOutput1 = "1234567890";
        final String expOutput2 = "0987654321";
        Vertex<String> n = new Vertex<>(0);

        n.setData(expOutput1);
        assertEquals(expOutput1, n.getData());

        n.setData(expOutput2);
        assertEquals(expOutput2, n.getData());
    }

    @Test
    void testEquals () {
        Vertex<Integer> n1 = new Vertex<>(0, 10), n2 = new Vertex<>(0, 10),
                n3 = new Vertex<>(1, 10);

        assertEquals(n1, n2);
        assertNotEquals(n1, n3);
        assertNotEquals(n2, n3);
    }

    @Test
    void testToString () {
        Vertex<Integer> n = new Vertex<>(0, 10);

        assertNotNull(n.toString());
    }
}

