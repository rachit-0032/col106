import java.util.EmptyStackException;

/*
    Implementing a stack using Linked List with help of Generic Java syntax
    push(4)
    4
    push(5)
    5 4
    pop()
    4
    Since in a singly-linked list deletion at the tail takes O(n) time, we can implement the list in kind of a 
    reversed order such that deletion could be done from the so-called head!
*/

public class MyStack <E> {
    
    private class Node{
        E data; //element
        /*
        We would have used a previous node in case we would have implemented stack using doubly 
        linked list
        */
        Node next; //next element in the list
    }

    private Node head;

    public MyStack(){
        head = null;
    }

    //Adding element in the stack, at the top
    public void push (E item){
        Node temp = new Node(); //create a empty node 'temp'
        temp.data = item;
        temp.next = head; //adds the element to the sequence we currently have
        head = temp; //makes the new element added at the top
    }

    public E pop(){
        if(this.empty() == true)
            throw new EmptyStackException();
        E poppedElement = head.data;
        head = head.next;
        return poppedElement;
    }

    public E peek(){
        if(this.empty() == true)
            throw new EmptyStackException();
        return head.data;
    }

    public boolean empty (){
        if(this.head == null)
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        /*
            We have to put Integer to tell the program the 
            data type we are about to enter, else it points out that there are "unchecked and unsafe operations" 
            in the program
        */
        MyStack <Integer> LL= new MyStack <Integer> (); 
        System.out.println("Class Successfully Built!");

        LL.push(5);
        LL.push(15);
        LL.push(25);
        Integer b = LL.peek();
        System.out.println(b);
        Integer a = LL.pop();
        System.out.println(a);
        b = LL.peek();
        System.out.println(b);
        boolean flag = LL.empty();
        System.out.println(flag);
        LL.peek();
    }

}


