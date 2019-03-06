package impl.list;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.List;

/**
 * created by 002908 on 2019/3/6
 */
public class MyLinkedList<E> extends AbstractSequentialList<E> implements List<E>{

    transient int size = 0;

    transient Node<E> first;

    transient Node<E> last;

    public MyLinkedList(){

    }

    @Override
    public boolean add(E e){
        linkLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if ( o == null){
            for (Node<E> x = first; x.next!= null; x = x.next){
                if (x.item == null){
                    unlink(x);
                    return true;
                }
            }
        }else {
            for (Node<E> x = first; x.next!= null; x = x.next){
                if (o.equals(x.item)){
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index > size) return false;
        Object[] arr = c.toArray();
        int numNew = arr.length;
        if (numNew == 0){
            return false;
        }

        //获取插入位置的 前后节点
        Node<E> pred, succ;
        if (index == size){
            succ = null;
            pred = last;
        }else {
            succ = node(index);
            pred = succ.prev;
        }

        for (Object o : arr) {
            @SuppressWarnings("unchecked")
            E e = (E)o;
            Node<E> newNode = new Node<>(pred,e,null);
            if (pred == null){
                first = newNode;
            }else {
                pred.next = newNode;
            }
            pred = newNode;
        }

        if (succ == null){
            last = pred;
        }else {
            succ.prev = pred;
            pred.next = succ;
        }

        size += numNew;
        return true;
    }

    private E unlink(Node<E> x) {
        final E e = x.item;
        final Node<E> prev = x.prev;
        final Node<E> next = x.next;
        if (prev == null){
            first = next;
        }else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null){
            last = prev;
        }else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        return e;
    }


    private void linkLast(E e) {
        final Node<E> l = last;
        Node<E> newNode = new Node<>(l,e,null);
        last = newNode;
        if (l == null){
            first = newNode;
        }else {
            l.next = newNode;
        }
        size++;
    }

    private static class Node<E>{
        E item;
        Node<E> prev;
        Node<E> next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
