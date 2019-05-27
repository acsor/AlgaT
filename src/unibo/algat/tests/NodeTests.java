package unibo.algat.tests;

import org.junit.jupiter.api.Test;
import unibo.algat.graph.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTests {
    @Test
    public void testSetGetData () {
        final String expOutput1 = "1234567890";
        final String expOutput2 = "0987654321";
        Node<String> n = new Node<>(expOutput1);

        assertEquals(expOutput1, n.getData());

        n.setData(expOutput2);
        assertEquals(expOutput2, n.getData());
    }
}
