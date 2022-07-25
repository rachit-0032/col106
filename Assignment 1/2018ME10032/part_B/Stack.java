import java.util.EmptyStackException;

//import java.util.ArrayDeque;

// You should utilise your implementation of ArrayDeque methods to implement this
public class Stack implements StackInterface {	
	ArrayDeque obj = new ArrayDeque(); //to use the implementation of ArrayDeque using its object
	
	public void push(Object o){
    	//you need to implement this
		obj.insertLast(o);
    	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  	}

	public Object pop() throws EmptyStackException{
    	//you need to implement this
		Object lastEleRem;

		try{
			lastEleRem = obj.removeLast();
		}
		catch (EmptyDequeException e) {
			throw new EmptyStackException();
		}
		return lastEleRem;
    	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public Object top() throws EmptyStackException{
    	//you need to implement this
		Object lastEle;
		try{
			lastEle = obj.last();
		}
		catch (EmptyDequeException e) {
			throw new EmptyStackException();
		}
		return lastEle;
    	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public boolean isEmpty(){
    	//you need to implement this
		return obj.isEmpty();
    	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

    public int size(){
    	//you need to implement this
		return obj.size();
    	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

	// public static void main(String[] args) {
	// 	StackInterface myStack = new Stack();
	// 	int i=1;
	// 	myStack.push(i);
	// }
}