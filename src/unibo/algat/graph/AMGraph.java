package unibo.algat.graph;


import java.util.*;

public class AMGraph<T> implements Graph<T> {
    private Node<T>[][] mEntries;
    private HashMap<Node<T>, Integer> nodeToInt;
    private Integer mCapacity, fillLevel;

    public AMGraph(int capacity){
        if(capacity > 0){
            mEntries = (Node<T>[][]) new Object[capacity][capacity];
            Arrays.fill(mEntries, null);
            mCapacity = capacity;
        } else {
            throw new IllegalArgumentException("capacity needs to be positive");
        }
    }

    @Override
    public void insertNode(Node<T> node) {
        if(node != null){
            if(fillLevel < mCapacity) {
                nodeToInt.putIfAbsent(node, fillLevel + 1);
                fillLevel++;
                for(int i = 0; i <= mCapacity; i++){
                    mEntries[fillLevel][i] = null;
                    mEntries[i][fillLevel] = null;
                }
            }
            //else throw new SizeLimitExceededException("size limit exceeded");
        } else {
            throw new NullPointerException("node was null");
        }
    }

    @Override
    public void deleteNode(Node<T> node) {
        if(node != null){
            if(nodeToInt.containsKey(node)){
                int tmp = nodeToInt.remove(node);
                for(int i = 0; i <= mCapacity; i++){
                    mEntries[tmp][i] = null;
                    mEntries[i][tmp] = null;
                }
                fillLevel--;
            }
        } else {
            throw new NullPointerException("node was null");
        }
    }

    @Override
    public boolean containsNode(Node<T> needle) {
        if(needle != null){
            if(nodeToInt.containsKey(needle))
                return true;
            else return false;
        } else {
            throw new NullPointerException("needle was null");
        }
    }

    @Override
    public Set<Node<T>> vertices() {
        return nodeToInt.keySet();
    }

    @Override
    public Set<Node<T>> adjacents(Node<T> node) {
        HashSet<Node<T>> a = new HashSet<>();
        if(node != null){
            if(nodeToInt.containsKey(node)) {
                int tmp = nodeToInt.get(node);
                for(int i = 0; i <= tmp; i++)
                    a.add(mEntries[tmp][i]);
            } else {
                throw new NoSuchElementException("node is not in this graph");
            }
        } else {
            throw new NullPointerException("node was null");
        }
        return a;
    }

    @Override
    public void insertEdge(Node<T> a, Node<T> b) {
        if(a != null && b != null){
            if(nodeToInt.containsKey(a) && nodeToInt.containsKey(b))
                mEntries[nodeToInt.get(a)][nodeToInt.get(b)] = b;
            else throw new NoSuchElementException("either a or b are not in graph");
        } else {
            throw new NullPointerException("either a or b are null");
        }
    }

    @Override
    public void deleteEdge(Node<T> a, Node<T> b) {
        if(a != null && b != null){
            if(nodeToInt.containsKey(a) && nodeToInt.containsKey(b))
                mEntries[nodeToInt.get(a)][nodeToInt.get(b)] = null;
            else throw new NoSuchElementException("either a or b are not in graph");
        } else {
            throw new NullPointerException("either a or b are null");
        }
    }

    @Override
    public boolean containsEdge(Node<T> a, Node<T> b) {
        if(a != null && b != null){
            if(nodeToInt.containsKey(a) && nodeToInt.containsKey(b))
                return mEntries[nodeToInt.get(a)][nodeToInt.get(b)] != null;
            else throw new NoSuchElementException("either a or b are not in graph");
        } else {
            throw new NullPointerException("either a or b were null");
        }
    }
}
