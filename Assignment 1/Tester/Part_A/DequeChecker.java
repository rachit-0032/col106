
import java.io.*;

public class DequeChecker {
  
  public static int numTest = 0 ;
  public static int numPassed =  0 ;
  public static int MAX_ITERATIONS = 10000;
  
  public static void printToFile(PrintWriter pw, int testNo, String status,String testName) {
    pw.printf("%d \t%s \t%s %d\n",testNo,status,testName,numPassed);
  }
  
  public static void sizeTest(PrintWriter pw, int desiredSize, DequeInterface myDeque) {
    numTest++;
    String result  = "Fail";
    try {
      int s = myDeque.size();
      result = (s == desiredSize ? "Pass" : "Fail");
    }
    catch (Exception e) {
    }
    
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"sizeTest");
  }
  
  public static void removeLastExceptionTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Fail";
    try {
      myDeque.removeLast();
    }
    catch (EmptyDequeException e) {
      //System.out.println("exception: " + e.toString());
      result = "Pass";
    }
    catch (Exception e) {
    }
    
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"removeLastExceptionTest");
  }
  
  public static void removeFirstExceptionTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Fail";
    try {
      myDeque.removeFirst();
    }
    catch (EmptyDequeException e) {
      result = "Pass";
    }
    catch (Exception e) {
    }
    
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"removeFirstExceptionTest");
  }
  
  // check this
  public static void emptyTheQueueFromLastTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Pass";
    try{
      int numIterations = 0;
      while(!myDeque.isEmpty()){
        numIterations++;
        int last = (int)myDeque.removeLast();
        if (numIterations >= MAX_ITERATIONS) {
          result="Fail";
          break;
          
        }
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"emptyTheQueueFromLastTest");
  }

  // check this
  public static void emptyTheQueueFromFirstTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Pass";
    try{
      int numIterations = 0;
      while(!myDeque.isEmpty()){
        numIterations++;
        int last = (int)myDeque.removeFirst();
        if (numIterations >= MAX_ITERATIONS) {
          result="Fail";
          break;
          
        }
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"emptyTheQueueFromFirstTest");
  }

  public static void insertFirstRemoveLastTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Pass";
    //Assuming deque is empty
    try {
      for(int i  = 0; i < 100; i++) {
        myDeque.insertFirst(i);
      }

      for (int i = 0; i < 100; i++) {
        int j = (int)myDeque.removeLast();
        if (i != j) {
          result = "Fail";
        }
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"insertFirstRemoveLastTest");
  }

  public static void insertFirstRemoveFirstTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Pass";
    //Assuming deque is empty
    try {
      for(int i  = 0; i < 100; i++) {
        myDeque.insertFirst(i);
      }
      for (int i = 0; i < 100; i++) {
        int j = (int)myDeque.removeFirst();
        if (j != 99-i) {
          result = "Fail";
        }
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"insertFirstRemoveFirstTest");
  }

  public static void insertLastRemoveFirstTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Pass";
    //Assuming deque is empty
    try {
      for(int i  = 0; i < 100; i++) {
        myDeque.insertLast(i);
      }
      for (int i = 0; i < 100; i++) {
        int j = (int)myDeque.removeFirst();
        if (j != i) {
          result = "Fail";
        }
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"insertLastRemoveFirstTest");
  }
  
  public static void insertLastRemoveLastTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Pass";
    //Assuming deque is empty
    try {
      for(int i  = 0; i < 100; i++) {
        myDeque.insertLast(i);
      }
      for (int i = 0; i < 100; i++) {
        int j = (int)myDeque.removeLast();
        if (j != 99-i) {
          result = "Fail";
        }
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"insertLastRemoveLastTest");
  }
  
  public static void insertLastRemoveLastRemoveFirstTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Pass";
    //Assuming deque is empty
    try {
      for(int i  = 0; i < 200; i++) {
        myDeque.insertLast(i);
      }
      for (int i = 0; i < 100; i++) {
        int j = (int)myDeque.removeLast();
        if (j != 199-i) {
          result = "Fail";
        }
      }
      for (int i = 0; i < 100; i++) {
        int j = (int)myDeque.removeFirst();
        if (j != i) {
          result = "Fail";
        }
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"insertLastRemoveLastRemoveFirstTest");
  }
  
  public static void insertLastRemoveFirstRemoveLastTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Pass";
    //Assuming deque is empty
    try {
      for(int i  = 0; i < 200; i++) {
        myDeque.insertLast(i);
      }
      for (int i = 0; i < 100; i++) {
        int j = (int)myDeque.removeFirst();
        if (j != i) {
          result = "Fail";
        }
      }
      for (int i = 0; i < 100; i++) {
        int j = (int)myDeque.removeLast();
        if (j != 199-i) {
          result = "Fail";
        }
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"insertLastRemoveFirstRemoveLastTest");
  }
  
  public static void insertLastTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Pass";
    
    try {
      for(int i  = 0; i < 200; i++) {
        myDeque.insertLast(i);
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"insertLastTest");
    sizeTest(pw,200,myDeque);
  }

  public static void insertFirstTest(PrintWriter pw, DequeInterface myDeque) {
    numTest++;
    String result = "Pass";
    try {
      for(int i  = 0; i < 200; i++) {
        myDeque.insertFirst(i);
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"insertFirstTest");
    sizeTest(pw,200,myDeque);
  }

  public static void main(String[] args) throws IOException {
    String fileName = "DequeTestResult.txt";
    FileWriter fileWriter = new FileWriter(fileName);
    PrintWriter printWriter = new PrintWriter(fileWriter);
    //printWriter.print("Some String");
    //printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
    //printWriter.close();
    
    int  N = 100;
    DequeInterface myDeque = new ArrayDeque();
    printWriter.println("TestNo\tResult\tTestName\tTotalPass");
    
    //Test on empty queue
    sizeTest(printWriter,0,myDeque);
    // removeLastExceptionTest(printWriter,myDeque);
    removeFirstExceptionTest(printWriter,myDeque);
    
    //other tests
    myDeque = new ArrayDeque();
    insertFirstTest(printWriter,myDeque);
    emptyTheQueueFromFirstTest(printWriter,myDeque);
    myDeque = new ArrayDeque();
    insertLastTest(printWriter,myDeque);
    emptyTheQueueFromLastTest(printWriter,myDeque);
    myDeque = new ArrayDeque();
    insertFirstRemoveFirstTest(printWriter,myDeque);
    myDeque = new ArrayDeque();
    insertFirstRemoveLastTest(printWriter,myDeque);
    myDeque = new ArrayDeque();
    insertLastRemoveFirstTest(printWriter,myDeque);
    myDeque = new ArrayDeque();
    insertLastRemoveLastTest(printWriter,myDeque);
    myDeque = new ArrayDeque();
    insertLastRemoveLastRemoveFirstTest(printWriter,myDeque);
    myDeque = new ArrayDeque();
    insertLastRemoveFirstRemoveLastTest(printWriter,myDeque);
    printWriter.close();
    
    String fileName2 = "studentdeque.txt";
    FileWriter fileWriter2 = new FileWriter(fileName2);
    PrintWriter printWriter2 = new PrintWriter(fileWriter2);
    printWriter2.println(numPassed);
    printWriter2.close();
    /*
     * 
     for(int i = 0; i < N; i++) {
     myDeque.insertFirst(i);
     myDeque.insertLast(-1*i);
     }
     
     int size1 = myDeque.size();
     System.out.println("Size: " + size1);
     System.out.println(myDeque.toString());
     
     if(size1 != 2*N){
     System.err.println("Incorrect size of the queue.");
     }
     
     //Test first() operation
     try{
     int first = (int)myDeque.first();
     int size2 = myDeque.size(); //Should be same as size1
     if(size1 != size2) {
     System.err.println("Error. Size modified after first()");
     }
     }
     catch (EmptyDequeException e){
     System.out.println("Empty queue");
     }
     
     //Remove first N elements
     for(int i = 0; i < N; i++) {
     try{
     int first = (Integer)myDeque.removeFirst();
     }
     catch (EmptyDequeException e) {
     System.out.println("Cant remove from empty queue");
     }
     
     }
     
     
     int size3 = myDeque.size();
     System.out.println("Size: " + myDeque.size());
     System.out.println(myDeque.toString());
     
     if(size3 != N){
     System.err.println("Incorrect size of the queue.");
     }
     
     try{
     int last = (int)myDeque.last();
     int size4 = myDeque.size(); //Should be same as size3
     if(size3 != size4) {
     System.err.println("Error. Size modified after last()");
     }
     }
     catch (EmptyDequeException e){
     System.out.println("Empty queue");
     }
     
     //empty the queue  - test removeLast() operation as well
     while(!myDeque.isEmpty()){
     try{
     int last = (int)myDeque.removeLast();
     }
     catch (EmptyDequeException e) {
     System.out.println("Cant remove from empty queue");
     }
     }
     
     int size5 = myDeque.size();
     if(size5 != 0){
     System.err.println("Incorrect size of the queue.");
     }
     */
  }
}
