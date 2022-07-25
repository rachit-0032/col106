
import java.io.*;

public class StackChecker {
  
  public static int numTest = 0 ;
  public static int numPassed =  0 ;
  public static int MAX_ITERATIONS = 10000;
  
  public static void printToFile(PrintWriter pw, int testNo, String status,String testName) {
    pw.printf("%d \t%s \t%s %d\n",testNo,status,testName,numPassed);
  }
  
  public static void sizeTest(PrintWriter pw, int desiredSize, StackInterface myStack) {
    numTest++;
    String result  = "Fail";
    try {
      int s = myStack.size();
      result = (s == desiredSize ? "Pass" : "Fail");
    }
    catch (Exception e) {
    }
    
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"sizeTest");
  }
  
  public static void popExceptionTest(PrintWriter pw, StackInterface myStack) {
    numTest++;
    String result = "Fail";
    try {
      myStack.pop();
    }
    catch (EmptyStackException e) {
      //System.out.println("exception: " + e.toString());
      result = "Pass";
    }
    catch (Exception e) {
    }
    
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"popExceptionTest");
  }
    
  // check this
  public static void emptyStack(PrintWriter pw, StackInterface myStack) {
    numTest++;
    String result = "Pass";
    try{
      int numIterations = 0;
      while(!myStack.isEmpty()){
        numIterations++;
        int last = (int)myStack.pop();
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
    printToFile(pw,numTest,result,"emptyStack");
  }

  public static void pushPopTest(PrintWriter pw, StackInterface myStack) {
    numTest++;
    String result = "Pass";
    //Assuming stack is empty
    try {
      for(int i  = 0; i < 100; i++) {
        myStack.push(i);
      }
      for (int i = 0; i < 100; i++) {
        int j = (int)myStack.pop();
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
    printToFile(pw,numTest,result,"pushPopTest");
  }
  
  public static void pushTest(PrintWriter pw, StackInterface myStack) {
    numTest++;
    String result = "Pass";
    
    try {
      for(int i  = 0; i < 200; i++) {
        myStack.push(i);
      }
    }
    catch (Exception e) {
      result = "Fail";
    }
    if (result == "Pass") {
      numPassed++ ;
    }
    printToFile(pw,numTest,result,"pushTest");
    sizeTest(pw,200,myStack);
  }

  public static void main(String[] args) throws IOException {
    String fileName = "StackTestResult.txt";
    FileWriter fileWriter = new FileWriter(fileName);
    PrintWriter printWriter = new PrintWriter(fileWriter);
    //printWriter.print("Some String");
    //printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
    //printWriter.close();
    
    int  N = 100;
    StackInterface myStack = new Stack();
    printWriter.println("TestNo\tResult\tTestName\tTotalPass");
    
    //Test on empty queue
    sizeTest(printWriter,0,myStack);
    popExceptionTest(printWriter,myStack);
    
    //other tests
    myStack = new Stack();
    pushTest(printWriter,myStack);
    emptyStack(printWriter,myStack);
    myStack = new Stack();
    pushPopTest(printWriter,myStack);
    printWriter.close();

    String fileName2 = "studentstack.txt";
    FileWriter fileWriter2 = new FileWriter(fileName2);
    PrintWriter printWriter2 = new PrintWriter(fileWriter2);
    printWriter2.println(numPassed);
    printWriter2.close();
    // printWriter2.printf("%d\n",numPassed);
    // System.out.println(numPassed);    
  }
}
