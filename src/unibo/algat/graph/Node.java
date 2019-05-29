package unibo.algat.graph;

/**
 * A node class, to be handled by Graph<T>.
 */
public class Node<T> {
    private T mData;

    public Node() {

        mData = null;
    }

    public Node (T data) {

        mData = data;
    }

    /**
     * Sets the data associated with this node.
     */
    public void setData (T data) {
        mData = data;
    }

    /**
     * @return The data associated with this node, possibly null if none is
     * contained.
     */
    public T getData () {
        return mData;
    }

    @Override
    public boolean equals (Object other) {
        Node<T> casted;
        if(other instanceof Node){
            casted = (Node<T>)other;
            return mData.equals(other);
        }
        else return false;
    }

    @Override
    public String toString () {
        return mData.toString();
    }
}
