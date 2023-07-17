import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListMap;

public class SkipListSet <T extends Comparable<T>> implements SortedSet<T> {
    public int level;

    static final public int height = 5;

    //ConcurrentSkipListMap
    public SkipListSetItem head; // northwest element

    public SkipListSetItem tail; // southeast element

    public int size = 0;

    public SkipListSet(Collection<T> collection)
    {

    }
    public SkipListSet()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

//    private void createNewList(Collection<T> collection)
//    {
//        ArrayList<SkipListSetItem> list = new ArrayList<SkipListSetItem>();
//        for(T c : collection)
//        {
//            list.add(new SkipListSetItem(c));
//        }
//        for(SkipListSetItem s : list)
//        {
//            add(s);
//        }
//
//    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return null;
    }

    @Override
    public T first() {
        return (T) this.head.data;
    }

    @Override
    public T last() {
        return (T) this.tail.data;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.tail == null && this.head == null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        SkipListSetItem newItem = new SkipListSetItem(t);
        int counter = height - 1;
        int arrayCounter = 0;
        int i = 0;
        SkipListSetItem[] traverseList = new SkipListSetItem[height - 1];
        if(this.head == null && this.tail == null)
        {
            this.head = newItem;
            SkipListSetItem[] newList = new SkipListSetItem[height - 1];
            for(int k =  0 ; k < height - 1; k++)
            {
                newList[k] = new SkipListSetItem(t);
            }
            SkipListSetItem current = this.head;
            while(arrayCounter != height - 1)
            {
                current.up = newList[arrayCounter];
                current = current.up;
                arrayCounter++;
            }
            arrayCounter--;
            while(arrayCounter != -1)
            {
                current.down = newList[arrayCounter];
                current = current.down;
                arrayCounter--;
            }
            this.tail = current;
            size++;
            return true;
        }
        if(this.head.compareTo(t) > 1)
        {
            SkipListSetItem<T> current = this.head;
            SkipListSetItem[] upList = new SkipListSetItem[height];
            while(current.down != null)
                current = current.down;
            newItem.next = current;
            current.prev = newItem;
            current.up = null;
            current.down = null;
            this.head = newItem;
            for(int j = 0; j < height; j++)
            {
                upList[j] = new SkipListSetItem(t);
            }
            counter = height;
            current = this.head;
            int j = 0;
            while(counter != 0)
            {
                current.down = upList[j];
                j++;
                counter--;
                current = current.down;
            }
            counter = height;
            while(counter != 0)
            {
                current.up = upList[j];
                j--;
                counter--;
                current = current.up;
            }
            return true;
        }
        if(this.tail.compareTo(t) < 0)
        {
            //rando height the tail;
        }
        SkipListSetItem left =  this.head;
        SkipListSetItem right = this.head.next;
        while(counter != 0){
            if(right == null)
            {
                traverseList[i] = left;
                counter--;
                left = left.down;
                right = left.next;
                continue;

            }
            if(left.compareTo(t) > 0) // left is less than t
            {
                left = right;
                right = left.next;
                continue;
            }
            if(right.compareTo(t) > 0 && left.compareTo(t) < 0) //perfect spot
            {
                left = left.down;
                right = left.next;
                traverseList[i] = left;
                counter--;
                continue;
            }
        }
        newItem.prev = left;
        newItem.next = right;



        return false;
    }



    @Override
    public boolean remove(Object o) {
        return false;
    }//deletes one object

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }//sentiel value this bitch

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }// foreach element, add

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    } // delete the elements outside of the set, rebalance list

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    private SkipListSetItem<T> findElement(T t)
    {
        if(this.isEmpty())
            return null;
        SkipListSetItem<T> current = this.head;
        SkipListSetItem<T> foundItem = null;
        int counter = height;
        while(counter != 0)
        {
            if(current.compareTo(t) == 0)
            {
                foundItem = current;
                break;
            }
            if(current.compareTo(t) > 0)
            {
                if(current.next == null)
                {
                    current = current.down;
                    counter--;
                }
                current = current.next;
            }
            if(current.compareTo(t) < 0)
            {
                current = current.prev.down;
                counter--;
            }

        }
        if(foundItem == null)
        {
            return null;
        }
        while(foundItem.down != null)
        {
            foundItem = foundItem.down;
        }
        return foundItem;
    }
    private class  SkipListIterator<T extends Comparable<T>> implements Iterator<T>
    {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }
    private class SkipListSetItem<T extends Comparable<T>>
    {
        public T data;
        private SkipListSetItem next;
        private SkipListSetItem prev;
        private SkipListSetItem up;

        private SkipListSetItem down;

        private int girth;

        public SkipListSetItem(T data)
        {
            this.data = data;
            next = null;
            prev = null;
            up = null;
            down = null;
            int girth = 0;
        }
        public int compareTo(T t)
        {
            return data.compareTo(t);
        }
        public String toString()
        {
            return data.toString();
        }


    }

}