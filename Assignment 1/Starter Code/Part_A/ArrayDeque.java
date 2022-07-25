//Implementing deque using growable array with doubling strategy
public class ArrayDeque implements DequeInterface {
  //Which variables are needed for the implementation
  Object[] deque = new Object[1]; //acts as an array implementation of deque; initiated with size = 1
  int front = 0, rear = 0; //act as pointers to some necessary locations
  //If this method is being called, it itself means that the earlier array is completely filled and thus f=r
  
  public void createDouble(){
    Object[] dequeDouble = new Object[2*deque.length]; //create an array of double the size
    int i = 0, j = 0;
    while (front < deque.length){
      dequeDouble[i] = deque[front];
      front += 1;
      i += 1;
    }
    while (j < rear){ //since the element at the position of rear has already been added using that front position
      dequeDouble[i] = deque[j];
      j += 1;
      i += 1;
    }
    
    //Revising the index positions of front and rear as per the new array
    front = 0;
    rear = deque.length; //since these two variables indicate the index and not the number!
    //Also, the rear varible points to the next empty location where the new element could be entered in the array
    //deque = null;
    deque = dequeDouble; //putting the original array into garbage as we have the new one in place
    //Copy all the elements of the original array into this new array, order-wise
}

  public void insertFirst(Object o){
    //you need to implement this
    //Working: It inserts element 'o' at the beginning of the deque
    if (size() == deque.length)
      createDouble(); //create an array with double the size and copy all the elements in order
    
    if(front == 0)
      front = deque.length - 1;
    else
      front -= 1;

    deque[front] = o; //adding the element to the front position
    
    // rear = rear + 1; //incrementing rear by 1 as a new member has been added at the end of the array
    // rear = (front + rear) % deque.length; //circling it back if it is needed
    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }

  public void insertLast(Object o){
    //you need to implement this
    //Working: It inserts element 'o' at the end of the deque
    if (size() == deque.length)
      createDouble(); //create an array with double the size and copy all the elements in order
    
    deque[rear] = o; //since rear always points to the position of the array with an empty initialisation, until it is full

    //Incrementing rear to the next, new position
    if(rear == deque.length - 1)
      rear = 0; //comes back to the front
    else
      rear = rear + 1; //incrementing rear by 1 as a new member has been added at the end of the array
    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  
  public Object removeFirst() throws EmptyDequeException{
    //you need to implement this
    //It should return and delete the first element in the array
    if (isEmpty())
      throw new EmptyDequeException("Deque is Empty!");
    
    Object firstEle = deque[front];
    deque[front]=null;

    if(front+1 == deque.length) //because if rear is at index 0, it means that we need to print out the element at the end of array and remove the circling effect
    //Also, rear points to the position where the next element could enter
      front = 0;
    else
      front += 1;
    return firstEle;
    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  
  //WORKING FINE!
  public Object removeLast() throws EmptyDequeException{
    //you need to implement this
    //It should return and delete the last element in the array
    if (isEmpty())
      throw new EmptyDequeException("Deque is Empty!");

    //Since the rear index gives the position of the index where the next element could be added, in case of a non-full deque, we must carefully check if rear-1 goes out of range for the deque
    else if(rear-1 == -1){ //because if rear is at index 0, it means that we need to print out the element at the end of array and remove the circling effect
    //Also, rear points to the position where the next element could enter
      Object lastEle = deque[deque.length - 1];
      rear = deque.length - 1;
      deque[rear] = null;
      return lastEle;
    }
    else{
      Object lastEle = deque[rear-1];
      rear -= 1;
      deque[rear] = null;
      return lastEle;
    }
    
    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }

  //WORKING FINE!
  public Object first() throws EmptyDequeException{
    //you need to implement this
    if (isEmpty())
      throw new EmptyDequeException("Deque is Empty!");
    return deque[front];
    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  
  //WORKING FINE!
  public Object last() throws EmptyDequeException{
    //you need to implement this
    //It should return the last element in the array
    if (isEmpty())
      throw new EmptyDequeException("Deque is Empty!");
    else if(rear-1 == -1) //because if rear is at index 0, it means that we need to print out the element at the end of array and remove the circling effect
    //Also, rear points to the position where the next element could enter
      return deque[deque.length - 1];
    return deque[rear-1];
    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  
  //WORKING FINE!
  public int size(){
    //you need to implement this
    if(!isEmpty()){
      if(front < rear)
        return rear - front;
      else{
        return deque.length + (rear - front);
      }
    }
    return 0; //array is empty!
    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  
  //WORKING FINE!
  public boolean isEmpty(){
    //you need to implement this
    if((front == rear) && (deque[rear] == null)){
      return true;
    }
    return false;
    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }

  //WORKING FINE!
  public String toString(){
    //you need to implement this
    String dequeString = "[";
    //System.out.println(dequeString);
    
    if(isEmpty()){
      return "[]";
    }

    else if (front < rear){
      int tempFront = front, tempRear = rear;
      while(tempFront < tempRear-1){ //since we want to add the last non-null element individually
        dequeString = dequeString + deque[tempFront] + ", ";
        tempFront += 1;
      }
      dequeString = dequeString + deque[tempRear-1] + "]";
    }

    //The below two loops could have been implemented unitedly in the same block
    else if (front == 0 && rear == 0){ //array is full at index 0; emptiness is already checked for
      int tempFront = front, tempRear = rear;
      while (tempFront < deque.length - 1){
        dequeString = dequeString + deque[tempFront] + ", ";
        tempFront += 1;
      }
      dequeString = dequeString + deque[tempFront] + "]";
    }

    else{ //it means that the front is indexed ahead than rear; not necessary that the string is full
      int i = 0, j = 0, tempFront = front, tempRear = rear;
      while (tempFront < deque.length){
        dequeString = dequeString + deque[tempFront] + ", ";
        tempFront += 1;
        // i += 1;
      }
      while (j < tempRear - 1){  //since we want to add the last non-null element individually
        dequeString = dequeString + deque[j] + ", ";
        j += 1;
        // i += 1;
      }
      //Since at index rear, we would have the last element, thus we add it individually, without any additional separators
      dequeString = dequeString + deque[tempRear-1] + "]"; //since rear contains the first index that can accomodate the new element at the last
    }
    return dequeString;
  //   //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }  

  //Checking my implementation through a main function on my own, taking into consideration all the cases that I can think of
  //All corner cases have been dealt with in my opinion
  // public static void main(String[] args) {
  //   ArrayDeque latest = new ArrayDeque();
    
  //   System.out.println("Is the array empty: " + latest.isEmpty());
  //   int a = latest.size();
  //   System.out.println("Size is: " + a);
  //   System.out.println("Actual Length of Array is: " + latest.length());

  //   latest.insertLast(5);
  //   System.out.println("Is the array empty: " + latest.isEmpty());
  //   a = latest.size();
  //   System.out.println("Size is: " + a);
  //   System.out.println("Actual Length of Array is: " + latest.length());

  //   latest.insertLast(6);
  //   System.out.println("Is the array empty: " + latest.isEmpty());
  //   a = latest.size();
  //   System.out.println("Size is: " + a);
  //   System.out.println("Actual Length of Array is: " + latest.length());

  //   for (int i=0; i<6; i++){
  //     latest.insertLast(i);
  //     System.out.println("Is the array empty: " + latest.isEmpty());
  //     a = latest.size();
  //     System.out.println("Size is: " + a);
  //     System.out.println("Actual Length of Array is: " + latest.length());
  //   }
  //   String ab = latest.toString();
  //   System.out.println(ab);
    
  //   //Checking Last
  //   try{
  //     int last = (int)latest.last();
  //     System.out.println(last);
  //   }
  //   catch (EmptyDequeException e){
  //     System.out.println("Deque is Empty"); //ARE WE ALLOWED TO WRITE ANY EXCEPTION COMMENT HERE?
  //   }

  //   //Checking First
  //   try{
  //     int first = (int)latest.first();
  //     System.out.println(first);
  //   }
  //   catch (EmptyDequeException e){
  //     System.out.println("Deque is Empty"); //ARE WE ALLOWED TO WRITE ANY EXCEPTION COMMENT HERE?
  //   }

  //   //Remove last element
  //   try{
  //     int lastEleRem = (int) latest.removeLast();
  //     System.out.println("Removed from Last: " + lastEleRem);
  //     a = latest.size();
  //     System.out.println("Size is: " + a);
  //     System.out.println("Actual Length of Array is: " + latest.length());
  //   }
  //   catch (EmptyDequeException e){
  //     System.out.println("Deque is Empty"); //ARE WE ALLOWED TO WRITE ANY EXCEPTION COMMENT HERE?
  //   }

  //   //Remove first element
  //   try{
  //     int firstEleRem = (int) latest.removeFirst();
  //     System.out.println("Removed from Front: " + firstEleRem);
  //     ab = latest.toString();
  //     System.out.println(ab);
  //     a = latest.size();
  //     System.out.println("Size is: " + a);
  //     System.out.println("Actual Length of Array is: " + latest.length());
  //   }
  //   catch (EmptyDequeException e){
  //     System.out.println("Deque is Empty"); //ARE WE ALLOWED TO WRITE ANY EXCEPTION COMMENT HERE?
  //   }
    
  //   //Inserting at Front
  //   latest.insertFirst(5);
  //   ab = latest.toString();
  //   System.out.println(ab);
  //   System.out.println("Is the array empty: " + latest.isEmpty());
  //   a = latest.size();
  //   System.out.println("Size is: " + a);
  //   System.out.println("Actual Length of Array is: " + latest.length());

  //   latest.insertFirst(6);
  //   ab = latest.toString();
  //   System.out.println(ab);
  //   System.out.println("Is the array empty: " + latest.isEmpty());
  //   a = latest.size();
  //   System.out.println("Size is: " + a);
  //   System.out.println("Actual Length of Array is: " + latest.length());

  //   latest.insertFirst(20);
  //   ab = latest.toString();
  //   System.out.println(ab);
  //   System.out.println("Is the array empty: " + latest.isEmpty());
  //   a = latest.size();
  //   System.out.println("Size is: " + a);
  //   System.out.println("Actual Length of Array is: " + latest.length());

  //   for (int i=0; i<6; i++){
  //     latest.insertFirst(i);
  //     System.out.println("Is the array empty: " + latest.isEmpty());
  //     a = latest.size();
  //     System.out.println("Size is: " + a);
  //     System.out.println("Actual Length of Array is: " + latest.length());
  //     ab = latest.toString();
  //     System.out.println(ab);
  //   }
  // }

  // public static void main(String[] args) {
  //   int  N = 10;
  //   DequeInterface myDeque = new ArrayDeque();
  //   for(int i = 0; i < N; i++) {
  //     myDeque.insertFirst(i);
  //     myDeque.insertLast(-1*i);
  //   }
 
  //   int size1 = myDeque.size();
  //   System.out.println("Size: " + size1);
  //   System.out.println(myDeque.toString());
  // }
}