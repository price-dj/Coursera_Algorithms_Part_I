
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node pre;     // sentinel before first item
    private Node post;    // sentinel after last item
    private int size;
    
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    
    // construct an empty deque
    public Deque() {
        pre  = new Node();
        post = new Node();
        pre.next = post;
        post.prev = pre;
        
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return size;
    }
    
    // add the item to the front - same as stack push
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("null item");
        }
        Node first = pre.next;
        Node x = new Node();
        x.item = item;
        x.prev = pre;
        x.next = first;
        pre.next = x;
        first.prev = x;
        size++;
    }
    
    // add the item to the end - same as queue enqueue
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("null item");
        }
        Node last = post.prev;
        Node x = new Node();
        x.item = item;
        x.next = post;
        x.prev = last;
        post.prev = x;
        last.next = x;
        size++;
    }
    
    // remove and return the item from the front - same as stack pop
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty deque cannot removeFirst()");
        }
        Node x = pre.next;
        Item item = x.item;
        Node y = x.next;
        pre.next = y;
        y.prev = pre;
        size--;
        return item;
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty deque cannot removeLast()");
        }
        Node x = post.prev;
        Item item = x.item;
        Node y = x.prev;
        post.prev = y;
        y.next = post;
        size--;
        return item;
        
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() { return new ListIterator(); }
    
    private class ListIterator implements Iterator<Item> {
        
        private Node current = pre.next;  // the node that is returned by next()
        
        private int index = 0;

        public boolean hasNext()      { return index < size; }
        
        public void remove() { 
            throw new UnsupportedOperationException("remove() not supported in iterator"); 
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            Item item = current.item;
            current = current.next; 
            index++;
            return item;
       }

    }
    
    
	
    public static void main(String[] args) {
         Deque<String> dq = new Deque<String>();
         boolean q = dq.isEmpty();
         System.out.println(q);
         dq.addFirst("fish");
         dq.addFirst("bob");
         dq.addLast("fred");
         dq.addLast("fred");
         dq.addLast("tom");
         dq.addFirst("fish");
         
         for (Iterator<String> iterator = dq.iterator(); iterator.hasNext();) {
             String s = iterator.next();
             System.out.println(s.toString());
         }
         int size = dq.size();
         System.out.println(size);
         
         dq.removeLast();
         dq.removeFirst();
         dq.removeFirst();
         dq.removeFirst();
         dq.removeFirst();
         
         
         for (Iterator<String> iterator = dq.iterator(); iterator.hasNext();) {
             String s = iterator.next();
             System.out.println(s.toString());
         }
         size = dq.size();
         System.out.println(size);
    }

}
