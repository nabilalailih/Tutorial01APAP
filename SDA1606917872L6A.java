/**
 * Created by Nabila Laili Halimah on 11/10/2017.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SDA1606917872L6A {

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String input;

        MyPriorityQueue binHeap = new MyPriorityQueue();

        while((input = read.readLine()) != null){
            String[] command = input.split(" ");

            if(command[0].equalsIgnoreCase("insert")){
                binHeap.add(Integer.parseInt(command[1]));
                System.out.println("elemen dengan nilai " + command[1] + " telah ditambahkan");
            }else if(command[0].equalsIgnoreCase("remove")){
                Node tem = binHeap.peek();
                Node check = binHeap.poll();
                if(check == null){
                    System.out.println("min heap kosong");
                }else {
                    System.out.println(tem.getElement() + " dihapus dari heap");
                }
            }else if(command[0].equalsIgnoreCase("num_percolate_up")){
                System.out.println("percolate up "+binHeap.returnPercolateUp(Integer.parseInt(command[1])));
            }else if(command[0].equalsIgnoreCase("num_percolate_down")){
                System.out.println("percolate down "+binHeap.returnPercolateDown(Integer.parseInt(command[1])));
            }else if(command[0].equalsIgnoreCase("num_element")){
                System.out.println("element " + binHeap.size());
            }
        }
    }

}
class Node<E extends Comparable<E>>{
    public E element;
    public int down, up;

    public Node(E element) {
        this.element = element;
        this.down = 0;
        this.up = 0;
    }

    public E getElement(){
        return this.element;
    }
}

class MyPriorityQueue<E extends Comparable<E>> {
    private static final int INITIAL_SIZE = 10;
    private ArrayList<Node<E>> data;
    private int size;

    /**
     * Creates a PriorityQueue with the default
     * initial capacity (10) that orders its elements
     * according to their natural ordering.
     */
    public MyPriorityQueue() {
        super();
        this.data = new ArrayList<Node<E>>(INITIAL_SIZE);
        this.size = 0;
    }

    /**
     * Retrieves, but does not remove, the head of this queue,
     * or returns null if this queue is empty.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    public Node<E> peek() {
        if(this.isEmpty()){
            return null;
        }else {
            return data.get(0);
        }
    }

    /**
     * Retrieves and removes the head of this queue,
     * or returns null if this queue is empty.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    public Node<E> poll() {
        Node<E> top = peek();

        if(top == null){
            return null;
        }
        swap(0, size-1);
        data.remove(size-1);
        size--;
        if(size > 0) {
            Node obj = data.get(0);
            obj.down ++;
        }
        return top;
    }

    /**
     * Inserts the specified element into this priority queue.
     *
     * @param element - the element to add
     * @return true if the insert operation succeed, false if not.
     */
    public boolean add(E element) {
        Node<E> node = new Node(element);

        data.add(node);
        int index = size;
        size++;
        percolateUp(index);
        return true;
    }

    /**
     * Returns the number of elements in this collection.
     *
     * @return the number of elements in this collection
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns true if this collection contains no elements.
     *
     * @return true if this collection contains no elements
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns an ArrayList containing all of the elements in this queue.
     * The elements are in no particular order.
     *
     * The returned array will be "safe" in that no references to it are maintained by this queue,
     * but this method only does a shallow copy so all the element reference is still pointed to
     * the same object.
     *
     * @return an array containing all of the elements in this queue
     */
    public ArrayList<Node<E>> toArray() {
        return new ArrayList<Node<E>>(this.data);
    }

    /**
     * Check whether an index is within the data array
     *
     * @param index - the index that want to be checked
     * @return true if the index is valid, false if not
     */
    private boolean isOutOfBound(int index) {
        if(index<0 || index>=this.size()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Get the index of the parent node (abstract) of desired index
     *
     * @param index - the index that want to know it's parent (kinda sad :") )
     * @return index of the parent if found, -1 if not
     */
    private int getParent(int index) {
        if(this.isOutOfBound(index) == true){
            return -1;
        }else {
            int indexParent = (index-1)/2;
            return indexParent;
        }
    }

    /**
     * Get the index of the right child node (abstract) of desired index
     *
     * @param index - the index that want to know it's right child
     * @return index of the right child if found, -1 if not
     */
    private int getRightChild(int index) {
        if(this.isOutOfBound(index) == true){
            return -1;
        }else{
            int indexRightChild = 2*(index +1);
            return indexRightChild;
        }

    }

    /**
     * Get the index of the left child node (abstract) of desired index
     *
     * @param index - the index that want to know it's left child
     * @return index of the left child if found, -1 if not
     */
    private int getLeftChild(int index) {
        if(this.isOutOfBound(index) == true){
            return -1;
        }else{
            int indexLeftChild = 2*index+1;
            return indexLeftChild;
        }
    }

    /**
     * Method to percolate up the element in desired index
     * @param index - the index of the element to percolate up
     */
    private void percolateUp(int index) {
        // Check whether the index valid
        if (isOutOfBound(index)==true || index==0) {
            return;
        }

        int parentIndex = getParent(index);
        Node<E> parent = data.get(parentIndex);
        Node<E> current = data.get(index);

        // Compare the value of gathered data
        if (index>0 && current.element.compareTo(parent.element) < 0) {
            swap(index, parentIndex);
            percolateUp(parentIndex);
            current.up++;
        } else {
            return;
        }
    }

    public int returnPercolateUp(E elem){
        Node<E> temp = new Node<E>(null);

        for(Node<E> i : data){
            if(i.element.compareTo(elem) == 0){
                temp = i;
            }
        }
        return temp.up;
    }

    private void percolateDown(int index) {
        if (isOutOfBound(index)==true){
            return;
        }
        int leftIndex = getLeftChild(index);
        int rightIndex = getRightChild(index);
        Node leftChild = null;
        Node rightChild = null;

        if(!isOutOfBound(leftIndex)) {
            leftChild = data.get(leftIndex);
        }
        if(!isOutOfBound(rightIndex)) {
            rightChild = data.get(rightIndex);
        }

        Node current = data.get(index);
        if ((rightChild != null) && (leftChild != null)) {
            if(((current.element).compareTo(rightChild.element) > 0) && ((current.element).compareTo(leftChild.element) > 0)) {
                if((leftChild.element).compareTo(rightChild.element) < 0) {
                    swap(leftIndex, index);
                    leftChild.down++;
                }
                else {
                    swap(rightIndex, index);
                    rightChild.up++;
                }
            }else{
                if((current.element).compareTo(rightChild.element) > 0){
                    swap(rightIndex, index);
                    rightChild.down++;
                }
                else if((current.element).compareTo(leftChild.element) > 0){
                    swap(leftIndex, index);
                    leftChild.down++;
                }
            }
        }
        else if((leftChild != null) && ((current.element).compareTo(leftChild.element) > 0)) {
            swap(leftIndex, index);
            leftChild.down++;
        }
        return;
    }

    /**
     * Swap the element of two indexes
     *
     * @param index1 - the first index to be swapped
     * @param index2 - the second index to be swapped
     * @return true if the swap succeed, false if not
     */
    private boolean swap(int index1, int index2) {
        if (isOutOfBound(index1) || isOutOfBound(index2)){
            return false;
        }

        Node<E> temp = data.get(index1);
        data.set(index1, data.get(index2));
        data.set(index2, temp);

        return true;
    }

    public int returnPercolateDown(E elem){
        Node<E> temp = new Node<E>(null);

        for(Node<E> i : data){
            if(i.element.compareTo(elem) == 0){
                temp = i;
            }
        }
        return temp.down;
    }

    public void print() {
        String hasil = "";
        for(int i = 0; i < data.size(); i++) {
            hasil+=" "+data.get(i).element;
        }
        System.out.println(hasil);
    }
}