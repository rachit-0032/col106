/*
COL106 - Assignment A3
Rachit Jain | 2018ME10032
*/

import java.util.*;

class Node { //AVL Node
    public int key; //would be the ID of the customer
    public int burgers; //count of burgers that are ordered by this customer
    public int counterNumber; //stores the billingCounter in which the queue went for processing
    // private int burgersLeft; //stores the count of burgers that have still not been processed by the chef on the griddle
    public int arrivingTime; //stores the time at which the customer arrives; need for outputting final waiting time!
    public int waitTime; //the FINISHING time at which the customer receives the food; sorry to name it waitTime xD
    public int customerState;
    
    public int balance;
    public int height;
    public Node left, right, parent;

    // Node(int k, Node p) {
    //   key = k;
    //   parent = p;
    // }

    Node (Node parentNode, int ID, int numBurgers, int time, int queueNum, int arrivalTime){
        parent = parentNode;
        key = ID;
        burgers = numBurgers;
        counterNumber = queueNum;
        arrivingTime = arrivalTime; 
        waitTime = time;
        customerState = queueNum; //since it is added into the billingCounter too
    }
  }


class GriddleNode { //Chef Queue Node
    public int key; //would be the ID of the customer
    public int burgers; //count of burgers that are ordered by this customer
    public int waitTime; //the time waited by the customer till the food was delivered to it

    GriddleNode (int ID, int numBurgers, int time){
        key = ID;
        burgers = numBurgers;
        waitTime = time;
    }
}


//Modified the implementation used from GitHub whose link was shared by an instructor on Piazza
class AVLTree {

  public Node root;

  public void insert(int ID, int numBurgers, int time, int queueNum, int arrivalTime) {

    if (root == null){ 
        root = new Node(null, ID, numBurgers, time, queueNum, arrivalTime);
        // return root;
        return ;
    }
    else {
      Node n = root;
      Node parent;

      while (true) {
        if (n.key == ID) 
            // return n; //denoting that it already exists
            return ;

        parent = n;

        boolean goLeft = n.key > ID;
        n = goLeft ? n.left : n.right;

        if (n == null) {
          if (goLeft) {
            parent.left = new Node(parent, ID, numBurgers, time, queueNum, arrivalTime);
            // temp = parent.left;
          } else {
            parent.right = new Node(parent, ID, numBurgers, time, queueNum, arrivalTime);
            // temp = parent.right;
          }
          rebalance(parent);
        //   return temp;
        return ;
        //   break; //ends the process; had there not been any return statements
        }
      }
    }
    // return temp;
  }

  //We do not need the delete function as I would not be deleting any node from the AVL tree as it acts as a repository for all customers that came

  private void rebalance(Node n) {
    setBalance(n);

    if (n.balance == -2) {
      if (height(n.left.left) >= height(n.left.right)) n = rotateRight(n);
      else n = rotateLeftThenRight(n);

    } else if (n.balance == 2) {
      if (height(n.right.right) >= height(n.right.left)) n = rotateLeft(n);
      else n = rotateRightThenLeft(n);
    }

    if (n.parent != null) {
      rebalance(n.parent);
    } else {
      root = n;
    }
  }

  private Node rotateLeft(Node a) {

    Node b = a.right;
    b.parent = a.parent;

    a.right = b.left;

    if (a.right != null) a.right.parent = a;

    b.left = a;
    a.parent = b;

    if (b.parent != null) {
      if (b.parent.right == a) {
        b.parent.right = b;
      } else {
        b.parent.left = b;
      }
    }

    setBalance(a, b);

    return b;
  }

  private Node rotateRight(Node a) {

    Node b = a.left;
    b.parent = a.parent;

    a.left = b.right;

    if (a.left != null) a.left.parent = a;

    b.right = a;
    a.parent = b;

    if (b.parent != null) {
      if (b.parent.right == a) {
        b.parent.right = b;
      } else {
        b.parent.left = b;
      }
    }

    setBalance(a, b);

    return b;
  }

  private Node rotateLeftThenRight(Node n) {
    n.left = rotateLeft(n.left);
    return rotateRight(n);
  }

  private Node rotateRightThenLeft(Node n) {
    n.right = rotateRight(n.right);
    return rotateLeft(n);
  }

  private int height(Node n) {
    if (n == null) return -1;
    return n.height;
  }

  private void setBalance(Node... nodes) {
    for (Node n : nodes) {
      reheight(n);
      n.balance = height(n.right) - height(n.left);
    }
  }

  private void reheight(Node node) {
    if (node != null) {
      node.height = 1 + Math.max(height(node.left), height(node.right));
    }
  }

  public Node search(int key) {
    // Node result = searchHelper(this.root, key);
    return searchHelper(this.root, key);
    // if (result != null) return true;

    // return false;
  }

  private Node searchHelper(Node root, int key) { //returns the node itself
    // root is null or key is present at root
    if (root == null || root.key == key) 
      return root;

    // key is greater than root's key
    if (root.key > key)
      return searchHelper(root.left, key); // call the function on the node's left child

    // key is less than root's key then
    // call the function on the node's right child as it is greater
    return searchHelper(root.right, key);
  }

    public void updateTime(int ID, int newTime){
        Node toBeUpdated = search(ID);
        toBeUpdated.waitTime = newTime;
    }
  
    public void updateCustomerState(int ID, int newCustomerState){
        Node toBeUpdated = search(ID);
        toBeUpdated.customerState = newCustomerState;
    }


    public int totalWaitTime(){
        if (root == null)
            return 0; //since no customer came
        int totalTime = totalWaitTimeHelper(root);
        // System.out.println(totalTime);
        return totalTime;
    }


    public int totalWaitTimeHelper(Node tempRoot) //and NOT the finishing time
    { 
        if (tempRoot == null) 
            return 0; 
        return (tempRoot.waitTime - tempRoot.arrivingTime) + totalWaitTimeHelper(tempRoot.left) + totalWaitTimeHelper(tempRoot.right); 
    }

}


class HeapNode_QueueSize{
    int key1; //this would be the basis of comparison at first
    int counterNumber; //this would be the basis of comparison at second
    // int context; //if context == 1, then I am currently comparing for billingQueue, but if context == -1, then I am comparing for addition into the ChefQueue

    //Constructor
    HeapNode_QueueSize (int key, int queueNo){
        key1 = key;
        counterNumber = queueNo;
    }

    //Comparator Function for minHeap
    public boolean customComparator (HeapNode_QueueSize h2){

        //Returning TRUE --> calling node  (h1) preferred
        //Returing FALSE --> argument node (h2) preffered

        if (this.key1 < h2.key1){ //the first key is preferred without a doubt
            return true;
        }

        else if (this.key1 == h2.key1){
            if (this.counterNumber < h2.counterNumber){ //since I would be keeping second key as the billing counter number always!
                return true; //here returing true implies that the first node, h1, is more preferred
            }
        }

        return false; //second node is preferred
    }
}


class HeapNode_Time{
    int key1; //this would be the basis of comparison at first
    int counterNumber; //this would be the basis of comparison at second

    //Constructor
    HeapNode_Time (int key, int queueNo){
        key1 = key;
        counterNumber = queueNo;
    }

    //Comparator Function for minHeap
    public boolean customComparator (HeapNode_Time h2){

        //Returning TRUE --> calling node  (h1) preferred
        //Returing FALSE --> argument node (h2) preffered

        if (this.key1 < h2.key1){ //the first key is preferred without a doubt
            return true;
        }

        else if (this.key1 == h2.key1){
            if (this.counterNumber > h2.counterNumber){ //since I would be keeping second key as the billing counter number always!
                return true; //here returing true implies that the first node, h1, is more preferred
            }
        }

        return false; //second node is preferred
    }
}


//To maintain the sizes of each queue in the billing counter to get the most preferred queue for next entry, using a heap
class billingCounterQueueSizes_HEAP{

    private HeapNode_QueueSize[] priorityQ;
    private int heapSize = 0;
    private int[] whereInHeap; //indices represent queue number while values represent position in the heap
    //the above is created because when we would have to reduce a customer from the queue, we need to know the position where that queue exists in the heap
  
  
    public billingCounterQueueSizes_HEAP(int totalSize){
        heapSize = totalSize;
        // System.out.println(heapSize);
        priorityQ = new HeapNode_QueueSize[this.heapSize + 1];
        whereInHeap = new int[this.heapSize + 1];

        priorityQ[0] = new HeapNode_QueueSize(-1, -1); //to initialise the 0th index of the array just for implementation as done in class
        whereInHeap[0] = -1;

        for(int i=1; i<=heapSize; i++){
            priorityQ[i] = new HeapNode_QueueSize(0, i); //initialising the queue number for the second case which would be true for all our heaps in this problem
            whereInHeap[i] = i;
        }
        // System.out.println(priorityQ[6].key1 + 1);
    }
    

    private int parent (int position){
        return position/2;
    }

    private int leftChild (int position){
        return 2*position;
    }
  
    private int rightChild (int position){
        return 2*position + 1;
    }

    private void swap(int position1, int position2)
    {
        //First correctly map the positions of the queue in the array maintained
        whereInHeap[priorityQ[position1].counterNumber] = position2; //thought carefully
        whereInHeap[priorityQ[position2].counterNumber] = position1; //thought carefully

        //Swap the HeapNodes in the priority queue itself
        HeapNode_QueueSize temp = priorityQ[position1];
        priorityQ[position1] = priorityQ[position2];
        priorityQ[position2] = temp;
    }
  
    public HeapNode_QueueSize minElement(){
        return priorityQ[1]; //gives the queue number in which customer is to be added
    }

    private void percolateUp(int position){
        // System.out.println("BillingCounter | Position: " + position + ", Heap Size: " + heapSize);
        if(position == 1) //at the top of the heap, then there is no where to percolate up to
            return ;
        else if(priorityQ[position].customComparator(priorityQ[parent(position)])){ //true implies current node is more preferred here
            // System.out.println("Yes1111");
            swap(position, parent(position));
            percolateUp(parent(position));
            // return true;
        }
        // return false; //if there was some error in the function
    }

    private void percolateDown(int position){
        // System.out.println("BillingCounter | Position: " + position + ", Heap Size: " + heapSize);
        if(leftChild(position) > heapSize) //at a leaf node
            return ; //percolateDown Completed
        else if(leftChild(position) == heapSize){ //only left child exists
            if(priorityQ[leftChild(position)].customComparator(priorityQ[position])){
                swap(leftChild(position), position);
                // return ; //since there are no nodes beyond this
            }
        }
        else{ //two children exists for the node
            if(priorityQ[leftChild(position)].customComparator(priorityQ[rightChild(position)])){
                // System.out.println("Yes1");
                if(priorityQ[leftChild(position)].customComparator(priorityQ[position])){
                    swap(leftChild(position), position);
                    percolateDown(leftChild(position));
                    // return true; //marks completion
                }
            }
            else if(priorityQ[rightChild(position)].customComparator(priorityQ[position])){
                // System.out.println("Came here");
                swap(rightChild(position), position);
                percolateDown(rightChild(position));
                // return true;
            }
        }
        // return false; //if there was some error in the function
    }

    //If a new customer arrives, I need increase the number of customers in the respective queue
    public void addCustomer(){
        priorityQ[1].key1++; //because the customer is to be added into the queue where the minimum customers are right now and thus the top element in the priorityQ
        // System.out.println("Adding Customer:");
        // printHeap();
        // System.out.println();
        percolateDown(1); //since the number is increased by 1
        // printHeap();
        // System.out.println();
        // System.out.println();
    }


    //Before deleting a customer, I need to know as to which queue is corresponds do
    public void delCustomer(int queueNo){
        HeapNode_QueueSize temp = priorityQ[whereInHeap[queueNo]];
        if(temp.counterNumber == queueNo){
            // System.out.println("You are deleting from the correct queue");
            temp.key1--; //since one person gets removed from the queue
            // System.out.println(whereInHeap[queueNo]);
            
            // System.out.println("Deleting Customer:");
            // printHeap();
            // System.out.println();
            percolateUp(whereInHeap[queueNo]); //since the number is decreased by 1
            // printHeap();
            // System.out.println();
            // System.out.println();
        }
    }
  
    // Function to print the contents of the heap
    public void printHeap()
    {
        for (int i = 1; i <= heapSize; i++) {
            System.out.print(priorityQ[i].key1 + "," + priorityQ[i].counterNumber + " ");
        }
    }
}


//To store the time and queue number for first person in each queue so that while removing elements, we know which one to process first
class firstPersonInQueues_HEAP{

    private HeapNode_Time[] priorityQ;
    // private int size = 0;
    private int heapSize = 0;
    private int[] whereInHeap; //indices represent queue number while values represent position in the heap
    //the above is created because when we would have to reduce a customer from the queue, we need to know the position where that queue exists in the heap
  
  
    public firstPersonInQueues_HEAP(int totalSize)
    {
        heapSize = totalSize;
        // System.out.println(heapSize);
        priorityQ = new HeapNode_Time[this.heapSize + 1];
        whereInHeap = new int[this.heapSize + 1];

        priorityQ[0] = new HeapNode_Time(Integer.MAX_VALUE, -1); //to initialise the 0th index of the array just for implementation as done in class
        whereInHeap[0] = -1;

        for(int i=1; i<=heapSize; i++){
            priorityQ[i] = new HeapNode_Time(Integer.MAX_VALUE, i); //initialising the queue number for the second case which would be true for all our heaps in this problem
            whereInHeap[i] = i;
        }
        // System.out.println(priorityQ[6].key1 + 1);
    }
    

    private int parent (int position){
        return position/2;
    }

    private int leftChild (int position){
        return 2*position;
    }
  
    private int rightChild (int position){
        return 2*position + 1;
    }

    private void swap(int position1, int position2)
    {
        //First correctly map the positions of the queue in the array maintained
        whereInHeap[priorityQ[position1].counterNumber] = position2; //thought carefully
        whereInHeap[priorityQ[position2].counterNumber] = position1; //thought carefully

        //Swap the HeapNodes in the priority queue itself
        HeapNode_Time temp = priorityQ[position1];
        priorityQ[position1] = priorityQ[position2];
        priorityQ[position2] = temp;
    }
  
    public HeapNode_Time minElement(){
        return priorityQ[1]; //gives the queue number in which customer is to be added
    }
    

    private void percolateUp(int position){
        if(position == 1) //at the top of the heap, then there is no where to percolate up to
            return ;
        else if(priorityQ[position].customComparator(priorityQ[parent(position)])){ //true implies current node is more preferred here
            swap(position, parent(position));
            percolateUp(parent(position));
        }
    }


    private void percolateDown(int position){
        // System.out.println("FPIQ | Position: " + position + ", Heap Size: " + heapSize);
        if(leftChild(position) > heapSize) //at a leaf node
            return ; //percolateDown Completed
        else if(leftChild(position) == heapSize){ //only left child exists
            if(priorityQ[leftChild(position)].customComparator(priorityQ[position])){
                swap(leftChild(position), position);
                // return ; //since there are no nodes beyond this
            }
        }
        else{ //two children exists for the node
            if(priorityQ[leftChild(position)].customComparator(priorityQ[rightChild(position)])){
                // System.out.println("Yes1");
                if(priorityQ[leftChild(position)].customComparator(priorityQ[position])){
                    swap(leftChild(position), position);
                    percolateDown(leftChild(position));
                    // return true; //marks completion
                }
            }
            else if(priorityQ[rightChild(position)].customComparator(priorityQ[position])){
                // System.out.println("Came here");
                swap(rightChild(position), position);
                percolateDown(rightChild(position));
            }
        }
    }

    public void addTopCustomerDetails(int time, int queueNum){
        priorityQ[whereInHeap[queueNum]].key1 = time;
        // System.out.println("Adding Top Customer Details:");
        // printHeap();
        // System.out.println();
        percolateUp(whereInHeap[queueNum]);  //since the max value of integer is stored in case it is empty
        percolateDown(whereInHeap[queueNum]); 
        // printHeap();
        // System.out.println();
        // System.out.println();
    }

    public void deleteTopCustomerDetails(int queueNum){
        priorityQ[whereInHeap[queueNum]].key1 = Integer.MAX_VALUE; //representing that there is no other element in the queue right now; if there is, it would automatically be added
        // System.out.println("Deleting Top Customer Details:");
        // printHeap();
        // System.out.println();
        percolateUp(whereInHeap[queueNum]); 
        percolateDown(whereInHeap[queueNum]); //since the max value of integer is stored in case it is empty
        // printHeap();
        // System.out.println();
        // System.out.println();
    }
  
    // Function to print the contents of the heap
    public void printHeap()
    {
        for (int i = 1; i <= heapSize; i++) {
            System.out.print(priorityQ[i].key1 + "," + priorityQ[i].counterNumber + " ");
        }
    }
}


public class MMBurgers implements MMBurgersInterface {

    //Initialising Global variables needed
    int globalClock = 0;
    Vector<Queue<GriddleNode>> billingCounters = new Vector<>(); //represents the billing counters
    int griddleSize = 0; //number of burgers that can be made simultaneously
    int totalCustomers = 0;
    double totalWaitTime = 0.0F;

    //Data Structures Used
    Queue<GriddleNode> griddleQueue = new ArrayDeque<GriddleNode>(); //for storing the customers whose orders are still in the chef queue waiting for their order to be processed
    AVLTree GlobalRepository = new AVLTree(); //global repository of all the customer IDs that came to the restaurant till the end of the simulation
    billingCounterQueueSizes_HEAP currentQSizes;
    firstPersonInQueues_HEAP firstPeopleToBeExecuted;
    LinkedList<Integer> pattySequence = new LinkedList<Integer>(); //storing time by when the patty would be made
    // Iterator<Integer> immediatePattyToBeRemoved = pattySequence.listIterator(); //points to the burger about to be removed from the griddle
    LinkedList<Integer> immediatePattyToBeRemoved = new LinkedList<Integer>(); //storing those patties which are next in line to be removed and delivered at the next instant
    Queue<GriddleNode> deliveryQueue = new ArrayDeque<GriddleNode>(); //to store the queue of customers for whom the burgers are made!
    
    //Extra Variables
    int totalQueues;
    int griddleWaitingCount = 0;
    int burgersOnGriddle = 0;
    // int latestCustTime = 0; //to get the value of the element previously added in queue

    public boolean isEmpty(){
        //your implementation
	    
        for(int i=0; i<billingCounters.size(); i++){
            if (billingCounters.get(i).size() != 0){
                // System.out.println("Currently, the system is not empty! FALSE!");
                return false;
            }
        }

        //if it is not returned false, it means that there is no one at the billing counter; now check for all other DS
        // if(griddleQueue.size() != 0 || pattySequence.size() != 0 || immediatePattyToBeRemoved.size() != 0 || deliveryQueue.size() != 0){
        if(griddleQueue.size() != 0 || deliveryQueue.size() != 0){
            // System.out.println("Currently, the system is not empty! FALSE!");
            return false; //if there is anything anywhere
        }
        
        // if(griddleQueue.isEmpty())
        //     return true; //is executed only if athe final griddle queue is found to be empty!

        // System.out.println("Currently, there is nothing to simulate! TRUE!");
        return true;
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 
    
    public void setK(int k) throws IllegalNumberException{
        //your implementation
	    
        if(billingCounters.size() != 0)
            throw new IllegalNumberException("The billing counters already exists and have to stay constant throughout the simulation!");
        else if (k<=0)
            throw new IllegalNumberException("Number of billing counters have to be greater than 0!");
        

        currentQSizes = new billingCounterQueueSizes_HEAP(k); //to store the current sizes for each queue in the billingCounter
        firstPeopleToBeExecuted = new firstPersonInQueues_HEAP(k); //to store the first people whose order is to be executed in the billingCounter


        //Add K Queues in the vector representing all the billing counters
        for (int i=0; i<=k; i++){ //we shall use 1-index start for the vector too
            Queue<GriddleNode> temp = new ArrayDeque<GriddleNode>(); //since Queue is an interface, objects of the type Queue cannot be created
            billingCounters.add(temp);
        }

        totalQueues = k;

        // billingCounters.get(0).add(5);
        // System.out.println(vecOfQueues.get(0).peek());
        // System.out.println(vecOfQueues.get(1).peek());

        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    }   
    
    public void setM(int m) throws IllegalNumberException{
        //your implementation
        if(griddleQueue.size() != 0)
            throw new IllegalNumberException("The griddle size already exists and has to stay constant throughout the simulation!");
        else if (m<=0)
            throw new IllegalNumberException("Size of the griddle has to be greater than 0, else no order can ever be executed!");
        else{
            griddleSize = m;
        }
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public void advanceTime(int t) throws IllegalNumberException{
        //your implementation
        if (t < globalClock)
            throw new IllegalNumberException("The entered time has already passed!");
        else if (t<0)
            throw new IllegalNumberException("Enter some real time; any positive integer would work!");
        

        // System.out.println("Execution Starting at: " + globalClock);
        // System.out.println();

        //We need to execute all the current decisions that we have till the input time, for every unit increase in time!
        while(globalClock <= t){
            GriddleNode deletedCustomer;

            //FIRST: Customer gets removed from the billingCounter and goes into the ChefQueue
            while(firstPeopleToBeExecuted.minElement().key1 == globalClock){ //till I have the same execution time, execute it!
                HeapNode_Time removingCustomer = firstPeopleToBeExecuted.minElement();
                int deletedFromQueue = removingCustomer.counterNumber;
                // int deletedTime = removingCustomer.key1;
                
                //Remove from BillingCounter
                deletedCustomer = billingCounters.get(deletedFromQueue).remove();
                // System.out.println("Time at which Customer " + deletedCustomer.key + " is removed from the counter number: " + deletedFromQueue + " is: " + deletedCustomer.waitTime);
                if(billingCounters.get(deletedFromQueue).size() != 0){
                    //We need to update the execution time for the incoming head of the queue; it would be max of either prevCustTime + queue_number or its own waitTime+queue_number
                    // System.out.println("Yes it comes here!");
                    billingCounters.get(deletedFromQueue).peek().waitTime = Math.max(billingCounters.get(deletedFromQueue).peek().waitTime, deletedCustomer.waitTime+deletedFromQueue);
                }
                // System.out.println("Time of Execution:" + deletedCustomer.waitTime);

                // int deletedBurger = deletedCustomer.burgers;
                int deletedID = deletedCustomer.key;

                //Adjusting the Queue Sizes
                currentQSizes.delCustomer(deletedFromQueue);

                //Delete from the firstPeople List
                firstPeopleToBeExecuted.deleteTopCustomerDetails(removingCustomer.counterNumber); //delete the element

                //Add the new top element details in there, if there are any
                if(billingCounters.get(deletedFromQueue).size() != 0){
                    firstPeopleToBeExecuted.addTopCustomerDetails(billingCounters.get(deletedFromQueue).peek().waitTime, deletedFromQueue);
                }

                //Add the customer in the griddleQueue
                griddleQueue.add(deletedCustomer);
                GlobalRepository.updateCustomerState(deletedID, totalQueues+1); //since the customer is waiting for the food now

                
                //Add the burgers of the added customer into the LinkedList
                for(int i=1; i <= deletedCustomer.burgers; i++){
                    if(pattySequence.size() < griddleSize){ //until we can add more patties into the griddle

                        pattySequence.addLast(globalClock+10); //since 10 units are taken for each patty to be made and they are added as soon as the customer arrives 
                        // System.out.println("Incoming patty would be made at: " + pattySequence.getLast()); 
                        // burgersOnGriddle++;
                        // System.out.println("Now burgers on the griddle are: " + burgersOnGriddle);
                        burgersOnGriddle = pattySequence.size();
                        // System.out.println("One Added! Number of Burgers on the Griddle now are: " + burgersOnGriddle);
                        // System.out.println("Now burgers on the griddle are: " + burgersOnGriddle);
                    }
                    else{
                        // System.out.println(immediatePattyToBeRemoved.next());
                        // int immediateTime = immediatePattyToBeRemoved.next();
                        // pattySequence.addLast(immediateTime + 10); //since time 10 is added to the time corresponding to the patty that will be removed that shall create a space for this
                        // if(immediatePattyToBeRemoved.hasNext()){
                        //     int immediateTime = immediatePattyToBeRemoved.next();
                        //     System.out.println("NOT WORKING!");
                        // }
                        // pattySequence.addLast(immediateTime + 10); //since time 10 is added to the time corresponding to the patty that will be removed that shall create a space for this
                        // griddleWaitingCount++; //since griddle is already full

                        int immediateTime = pattySequence.removeFirst(); //the patty that shall be removed first
                        immediatePattyToBeRemoved.addLast(immediateTime);
                        // System.out.println("One Added in Immediate Sequence! Number of Burgers here are: " + immediatePattyToBeRemoved.size());
                        pattySequence.addLast(immediateTime + 10); //since time 10 is added to the time corresponding to the patty that will be removed that shall create a space for this
                        // System.out.println("Newest Immediate Patty would be made at: " + immediatePattyToBeRemoved.getLast() + " | Incoming patty would be made at: " + pattySequence.getLast()); 
                        griddleWaitingCount++; //since griddle is already full
                    }     
                }
            }

            // System.out.println("The Immediate Patties are at time: " + globalClock + " are: " + immediatePattyToBeRemoved.size());

            //Removing Cooked Patties
            if(immediatePattyToBeRemoved.size() != 0){
                while(immediatePattyToBeRemoved.getFirst() == globalClock){ //execution has to take place for these
                    // System.out.println("Immediate Patty To Be Removed At: " + immediatePattyToBeRemoved.getFirst() + " at time: " + globalClock);
                    int waitTimeTillNow = immediatePattyToBeRemoved.removeFirst();
                    // System.out.println("One Deleted from Immediate Sequence! Number of Burgers here are: " + immediatePattyToBeRemoved.size());
                    if(griddleWaitingCount>0){
                        griddleWaitingCount--; //because the waiting burger took its position
                    }
                    //#####
                    // else{
                    //     //patty got removed, while no patty to replace it
                    //     // burgersOnGriddle--;
                    //     burgersOnGriddle = pattySequence.size();
                    // }
                    griddleQueue.peek().burgers--; //since 1 burger has been made
                    if(griddleQueue.peek().burgers == 0){
                        
                        griddleQueue.peek().waitTime = waitTimeTillNow + 1; //the time corresponding to the last burger for that customer + the extra delivery time at which it would be delivered to the customer
                        // System.out.println("Customer with id " + griddleQueue.peek().key + " is ready to be delivered food at: " + griddleQueue.peek().waitTime);
                        GriddleNode removedCustomer = griddleQueue.remove();
                        deliveryQueue.add(removedCustomer); //since the food is yet to be delivered
                        
                        // GlobalRepository.updateCustomerState(removedCustomer.key, totalQueues+1); //since item is delivered in the next moment
                        // GlobalRepository.updateTime(removedCustomer.key, removedCustomer.waitTime); //since final time is needed once delivery is done
                    }

                    if(immediatePattyToBeRemoved.size() == 0){ //if there are no more immediate patties to be removed, i.e. no more burgers came to take previous positions
                        break;
                    }
                }
            }
            if(immediatePattyToBeRemoved.size() == 0 && pattySequence.size() != 0){ //new burgers did not replace the old ones and the immediateSequence is already emptied
                while(pattySequence.getFirst() == globalClock){
                    int waitTimeTillNow = pattySequence.removeFirst();
                    burgersOnGriddle = pattySequence.size(); //since one burger is removed
                    // System.out.println("One Removed! Number of Burgers on the Griddle now are: " + burgersOnGriddle);
                    griddleQueue.peek().burgers--;

                    if(griddleQueue.peek().burgers == 0){
                        griddleQueue.peek().waitTime = waitTimeTillNow + 1; //extra 1 for delivery time
                        GriddleNode removedCustomer = griddleQueue.remove();
                        deliveryQueue.add(removedCustomer); //since the food is yet to be delivered

                        // GlobalRepository.updateCustomerState(removedCustomer.key, totalQueues+2); //since item is delivered in the next moment
                        // GlobalRepository.updateTime(removedCustomer.key, removedCustomer.waitTime); //since final time is needed once delivery is done
                    }

                    if(pattySequence.size() == 0){ //if there are no more immediate patties to be removed, i.e. no more burgers came to take previous positions
                        break;
                    }



                    // int waitTimeTillNow = pattySequence.removeFirst();
                    // immediatePattyToBeRemoved.addLast(waitTimeTillNow);
                    
                    // burgersOnGriddle = pattySequence.size(); //since burgers are getting reduced
                    // System.out.println("Now burgers on the griddle are: " + burgersOnGriddle);

                    //Directly remove them from the griddle as their time has come and update neccessary items

                    
                }
            }

            //Deliver the orders for customer for whom the orders have been made
            if(deliveryQueue.size() != 0){
                while(deliveryQueue.peek().waitTime == globalClock){
                    GriddleNode deliveredCustomer = deliveryQueue.remove();
                    Node customer = GlobalRepository.search(deliveredCustomer.key);
                    customer.waitTime = deliveredCustomer.waitTime;
                    customer.customerState = totalQueues + 2;
                    totalWaitTime += (customer.waitTime - customer.arrivingTime);
                    // System.out.println("The food is delivered to customer " + deliveredCustomer.key + " at time: " + deliveredCustomer.waitTime);
                    // GlobalRepository.updateCustomerState(deliveredCustomer.key, totalQueues+2); //since item is delivered at this moment
                    // GlobalRepository.updateTime(deliveredCustomer.key, deliveredCustomer.waitTime); //since that extra 1 unit was already compensated while inserting it into this queue
                    
                    if(deliveryQueue.size() == 0){ //if there are no more immediate patties to be removed, i.e. no more burgers came to take previous positions
                        break;
                    }
                }
            }
            
            // GriddleNode recentCustomerGriddle = griddleQueue.

            //If the system is already empty, then do not keep running till advance time t, rather simply make globalClock = t
            if (isEmpty()){
                globalClock = t;
                break; //to stop the code from running if there is nothing to be done
            }

            // System.out.println("Execution Ended at: " + globalClock);
            // System.out.println();
        

            if (globalClock != t){
                // int time1 = Integer.MAX_VALUE;
                // int time2 = Integer.MAX_VALUE;
                // int time3 = Integer.MAX_VALUE;
                // int time4 = Integer.MAX_VALUE;

                // if(firstPeopleToBeExecuted.minElement().key1 != Integer.MAX_VALUE){ //minimum sized-queue has some elements
                //     time1 = firstPeopleToBeExecuted.minElement().key1; //time can come for the person at the billing counter
                //     // System.out.println("Execution Time for the next first Customer: " + time1);
                // }
                // if(immediatePattyToBeRemoved.size() != 0){
                //     time2 = immediatePattyToBeRemoved.getFirst();
                //     // System.out.println("Next Immediate Patty would be made at: " + time2);
                // }
                // if(pattySequence.size() != 0){
                //     time3 = pattySequence.getFirst();
                //     // System.out.println("Next Patty would be made at: " + time3);
                // }
                // if(deliveryQueue.size() != 0){
                //     time4 = deliveryQueue.peek().waitTime;
                //     // System.out.println("Next Delivery to be done at: " + time4);
                // }
                
                // globalClock = Math.min(Math.min(Math.min(time1, time2), time3), time4);
                // System.out.println("The minimum execution time comes out to be: " + globalClock);
                
                globalClock++; 
            }
            else //if we have executed everything till here, then we do not increase the global time, rather stay at the same time
                break;
        }

	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        //your implementation
        
        // System.out.println("Global Time: " + globalClock + " | Next Event Time: " + t);
        
        // globalClock = Math.min(globalClock, t); //since a new event has taken place

        if(numb<=0) //Post by Instructor on Piazza says that number of burgers have to be positive and greater than 0!
            throw new IllegalNumberException("Number of Burgers entered has to be greater than 0");
        else if (t<globalClock)
            throw new IllegalNumberException("The entered time has already passed!");
        else if (GlobalRepository.search(id) != null)
            throw new IllegalNumberException("The entered ID does not exist as it did not enter the restaurant anytime!");
        //ANOTHER can be added to check if the given ID already exists in the AVL Tree and throw exception if it already does!


        if(t > globalClock){
            // System.out.println("Yes, things need to be executed first (UPON ARRIVAL)!");
            advanceTime(t); //ensures to execute everything that is in the process till now.
        }

            
        if(t == globalClock){ //t == globalClock; this cannot come in else part since customer has to be added after advancing time
            int addToQueue = currentQSizes.minElement().counterNumber;
            // System.out.println("Added to Queue Number: " + addToQueue);
            // System.out.println("Global Clock is: " + globalClock);
            if(billingCounters.get(addToQueue).peek() == null){ //if it is the first customer to be added 
                //Use the time as the time at which the customer query would be executed
                GriddleNode addingCustomer = new GriddleNode(id, numb, t+addToQueue);
                // latestCustTime = addingCustomer.waitTime; //for the next customer when it arrives

                billingCounters.get(addToQueue).add(addingCustomer); //added to the actual billing counters too
                
                // System.out.println("Adding Customer: " + id);
                currentQSizes.addCustomer(); //added into the heap which stores size of the queue
                
                
                firstPeopleToBeExecuted.addTopCustomerDetails(t+addToQueue, addToQueue); //we are saving the execution time itself
                // System.out.println("Time of Execution for Customer " + id + " is this: " + addingCustomer.waitTime);
                // System.out.println();
                GlobalRepository.insert(id, numb, t+addToQueue, addToQueue, t); //customer added to the AVL Tree
                totalCustomers++;
            }
            else{
                // prevCustTime = billingCounters.get(addToQueue).waitTime; //since another person is already in the queue, the time at which the new customer would be executed, should be k more than prevCustTime
                GriddleNode addingCustomer = new GriddleNode(id, numb, t+addToQueue);
                billingCounters.get(addToQueue).add(addingCustomer); //added to the actual billing counters too
                currentQSizes.addCustomer(); //added into the heap which stores size of the queue
                //firstPeopleToBeExecuted is not utilised here since there is already someone there!
                // System.out.println("Time of Execution for this: " + addingCustomer.waitTime);
                // System.out.println();
                GlobalRepository.insert(id, numb, t+addToQueue, addToQueue, t); //customer added to the AVL Tree
                // latestCustTime = addingCustomer.waitT ime; //updating this time with the exection time for the customer added
                //We shall update this waitTime with the execution time at the START of the QUEUE since the end is not maintained in the inbuilt-implementation
                totalCustomers++;
            }  
        }

        //Insert the current data as a node in the AVL Tree
        // tree.insert(id, numb, t, queueNum); //adding the element in the AVL Tree
        // tree.search(id);
        
        
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public int customerState(int id, int t) throws IllegalNumberException{
        //your implementation

        // System.out.println("Global Time: " + globalClock + " | Customer State Asked At: " + t);

        // globalClock = Math.min(globalClock, t); //since a new event has taken place

        if (t < globalClock)
            throw new IllegalNumberException("The entered time has already passed!");

        if(t > globalClock){
            // System.out.println("Yes, things need to be executed first (UPON ASKING CUSTOMER STATE)!");
            advanceTime(t); //ensures to execute everything that is in the process till now.
        }
        
        if(GlobalRepository.search(id) == null)
            return 0; //as the function is asked to return 0 if the customer ID does not already exist
        
        

        // System.out.println("After Advancing | Global Time: " + globalClock + " | Next Event Time: " + t);

	    Node toBeUpdated = GlobalRepository.search(id);
        return toBeUpdated.customerState;
        // if(toBeUpdated.customerState == -1){
        //     // System.out.println("Currently the Customer State is: " + totalQueues + 1);
        //     return totalQueues + 1; //since size of the vector is K+1
        // }
        // else if(toBeUpdated.customerState == -2){
        //     // System.out.println("Currently the Customer State is: " + totalQueues + 2);
        //     return totalQueues + 2; //since size of the vector is K+1
        // }
        // else{
        //     // System.out.println("Currently the Customer State is: " + toBeUpdated.customerState);
        //     return toBeUpdated.customerState;
        // }

        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public int griddleState(int t) throws IllegalNumberException{
        //your implementation

        // System.out.println("Global Time: " + globalClock + " | Next Event Time: " + t);

        // globalClock = Math.min(globalClock, t); //since a new event has taken place

        if (t < globalClock)
            throw new IllegalNumberException("The entered time has already passed!");

        if(t > globalClock){
            // System.out.println("Yes, things need to be executed first (UPON ASKING GRIDDLE STATE)!");
            advanceTime(t); //ensures to execute everything that is in the process till now.
        }

        // System.out.println("Currently the Griddle has these burgers: " + burgersOnGriddle);
        return burgersOnGriddle;
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public int griddleWait(int t) throws IllegalNumberException{
        //your implementation

        // System.out.println("Global Time: " + globalClock + " | Next Event Time: " + t);

        // globalClock = Math.min(globalClock, t); //since a new event has taken place

        if (t < globalClock)
            throw new IllegalNumberException("The entered time has already passed!");

        if(t > globalClock){
            // System.out.println("Yes, things need to be executed first (UPON ASKING GRIDDLE WAIT)!");
            advanceTime(t); //ensures to execute everything that is in the process till now.
        }

        // System.out.println("Currently there are so many burgers in waiting: " + griddleWaitingCount);
        return griddleWaitingCount;
	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

    public int customerWaitTime(int id) throws IllegalNumberException{
        //your implementation

        if (GlobalRepository.search(id) == null)
            throw new IllegalNumberException("The entered ID does not exist as it did not enter the restaurant anytime!");

	    Node customer = GlobalRepository.search(id);
        // System.out.println("Customer " + id + " waited throughout the simulation for: " + (customer.waitTime - customer.arrivingTime));

        if(customer.customerState == totalQueues + 2){ //i.e. the customer has still not been delivered the food
            return customer.waitTime - customer.arrivingTime;
        }

        else{ //since the process has still not been completed for this customer till now!
            System.out.println("The simulation has not ended till now! There are customers in the restaurant for whom the food is still not delivered! This query cannot be asked before that!");
            return globalClock - customer.arrivingTime;
        }
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

	public float avgWaitTime(){
        //your implementation

        // totalWaitTime = GlobalRepository.totalWaitTime();
        // System.out.println(totalWaitTime);

        float avgTime = (float) totalWaitTime/totalCustomers;

        if (!isEmpty()){
            System.out.println("The simulation has not ended till now! There are customers in the restaurant for whom the food is still not delivered! This query cannot be asked before that!");
            return 0;
        }
        // System.out.println("Total Customers: " + totalCustomers + " | Average Waiting Time: " + avgTime);
        else
            return avgTime;

	    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
    } 

}
