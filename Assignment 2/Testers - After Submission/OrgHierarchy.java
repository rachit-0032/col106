import java.io.*; 
import java.util.*;

/* 
	METHODOLOGY:

	To satisfy the given criteria, I plan to use two data structures, one for its ease of searching amongst all employees
	which would be sustained by a search tree data structure. Since we also need to make the time complexity of O(logn) where
	n is the number of employees, we need to have a height balanced tree. Thus, AVL trees work for this implementation.

	Moreover, we need to have a generic tree implementation which could maintain the order of the nodes as shown in the 
	example presented for the problem. Multiple children have to be maintained for each node and thus a generic tree
	would serve our purpose for the same.

	To link both these data structures, we can use the AVL Tree to search for the Employee ID, have the node attached
	from the generic tree with the AVL Tree which connects the two data structures.

	-- 

	NOTE:
	To make all operations log(n), we need to have everything in a tree format or better. Since hashmaps are not taught, trees 
	are left to be implemented. Even if we stored all children values in a tree, then adding those children in another sub-tree of a parent,
	when a manager is fired, would take O(nlogn) since n insertion operations of logn each. To cheaply merge two tree, we could have
	have used the concept of "Union Find" which could have allowed us to do most opearations in logn, but then the level order would have been missing
	and that would have been difficult to traverse too. Also, since this has not been taught, the implementation would had to be understood
	from internet and thus made the code not a self-thought one. Nevertheless, I have mentioned the alternative approach to keep a record of 
	the understanding of the assignment that I had gained while doing the same.
*/


//Generic Tree Node
class GenericNode {
	int id; //since the ID has been told ot be in integer format
	int level; //to save the level at which the node currently exists
	Vector <GenericNode> child = new Vector <GenericNode> (); //to create growable array which could hold all the children under a particular parent node
	GenericNode parent;
}

//AVL Tree Node
class AVLNode{
	int id; //employeee-id
	int height; //for rebalancing
	GenericNode pointerNode; //points to the node in the generic tree
	AVLNode left; //left-child
	AVLNode right; //right-child

	AVLNode(GenericNode ele){ //constructor; since element from the generic tree shall always be given
		pointerNode = ele;
		id = ele.id;
		height = 0;
	}
}


public class OrgHierarchy implements OrgHierarchyInterface{

//root node
	GenericNode GenericRoot;
	AVLNode AVLRoot;
	int globalSize = 0;

	public boolean isEmpty(){
		//your implementation
		if(GenericRoot == null)
			return true;
		else
			return false;
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
	} 

	public int size(){
		//your implementation
		//Since it does not deal with the size of the employees under a specific ID, we have a global integer to maintain this
		return globalSize;
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public int level(int id) throws IllegalIDException, EmptyTreeException{
		//your implementation
		if(isEmpty())
			throw new EmptyTreeException("The organisational hierarchy is empty!");
		else if(searchAVLNode(AVLRoot, id) == null){
			throw new IllegalIDException("The given ID does not exist in the organisational hierarchy!");
		}
		else{ 
			AVLNode temp = searchAVLNode(AVLRoot, id); //takes care of the situation when the ID is illegal
			return temp.pointerNode.level;
		}
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	} 

	public void hireOwner(int id) throws NotEmptyException{
		//your implementation
		if(GenericRoot != null)
			throw new NotEmptyException("The Owner already exists");
		else{
			GenericRoot = new GenericNode(); //initialising the root node
			GenericRoot.id = id;
			GenericRoot.level = 1;

			// System.out.println("Generic Node made " + GenericRoot.id + " " + GenericRoot);
			AVLRoot = new AVLNode(GenericRoot); //initialising the root node
			//all other nodes added into the generic tree shall now be added to the AVL Tree through the insert method of the latter
			globalSize += 1; //organisation size increases by 1
		}
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	/*
	We need to have a max function to find the greater height of two sub-trees, and a height difference code to find the difference
	in height of two sub-trees. A separate function allows us to use this operation efficiently, without worrying upon copying the same code again and again.
	*/
	private int max(int height1, int height2) {
        if (height1 > height2)
			return height1;
		else
			return height2;
    }


	private int heightDiff(AVLNode AVLRoot) {
        if (AVLRoot == null)
            return -1; //leaf node, as per our convention
        return height(AVLRoot.left) - height(AVLRoot.right);
    }


	//We need a separate height function since a null node has a height of -1, while a new single node has a height of 0 as per our convention.
	private int height(AVLNode AVLRoot){
		if(AVLRoot == null) //you are at a leaf node
			return -1;
		return AVLRoot.height;
	}


	//To find the minimum value which is bigger than the current node under consideration
	private AVLNode leastSuccessor(AVLNode AVLRoot){  
        AVLNode temp = AVLRoot;  
        while (temp.left != null) //keep going left till you can
        	temp = temp.left;  
        return temp;  
    }  


	/*
	Before implementing the addNode function for an AVL Tree which would be used to add a node to the AVL Tree, we need to define the
	methods which would allow us to do RE-BALANCING to ensure that the AVL condition is always satisfied. We need two functions, leftRotation
	and rightRotation, for this purpose.
	*/

	/* The arrangement for left rotation shown reference below:
			z								y
		/		\		rotation		/		\
		T1		y		---->   		z		T3
			/		\				/		\	
			T2		T3				T1		T2

	*/

	private AVLNode leftRotation(AVLNode z) { //as per class convention, z is the first unbalanced node in the ancestors where we need rebalancing
		AVLNode y = z.right; //for left rotation, we know that y lies on its right
		AVLNode T2 = y.left; //since only the left sub-tree would later be changed to z, hence we store it before hand
		y.left = z; //making y the head node
		z.right = T2; //making T2 the right sub-tree of Z
 
		/*
		Once rotation is done, we must ensure that the heights are updated. We know that after insertion, the height of the initially
		unbalanced node remains same.
		*/
		y.height = z.height; //since the height remains same upon insertion at the position of the root that was initally unbalanced
		//y.height = max(height(y.left), height(y.right)) + 1; //even this would have given the same result
		z.height = max(height(z.left), height(z.right)) + 1; //as per the definition of height of a tree
		
		return y;
	}


	/* The arrangement for left rotation shown reference below:
				z								y
			/		\		rotation		/		\
			y		T3		---->   		T1		z
		/		\								/		\	
		T1		T2								T2		T3
	*/
	private AVLNode rightRotation(AVLNode z) { //as per class convention, z is the first unbalanced node in the ancestors where we need rebalancing
		AVLNode y = z.left; //for right rotation, we know that y lies on its left
		AVLNode T2 = y.right; //has to be re-assigned
		y.right = z; //making y the head node
		z.left = T2; //making T2 the left sub-tree of Z
 
		/*
		Once rotation is done, we must ensure that the heights are updated. We know that after insertion, the height of the initially
		unbalanced node remains same.
		*/
		y.height = z.height; //since the height remains same upon insertion at the position of the root that was initally unbalanced
		//y.height = max(height(y.left), height(y.right)) + 1;
		z.height = max(height(z.left), height(z.right)) + 1;
		
		return y;
	}
 

	//Adding the element inside the AVL Tree and linking it to the generic tree
	private AVLNode addAVLNode(AVLNode AVLRoot, GenericNode ele){

		if(AVLRoot == null){ //we have reached an empty leaf node, where the 
			AVLNode temp = new AVLNode(ele);
			return temp;
		}
		else{
			// System.out.println("Non null!");
			if (AVLRoot.id < ele.id){
				AVLRoot.right = addAVLNode(AVLRoot.right, ele);
			}
			else if (AVLRoot.id > ele.id){
				AVLRoot.left = addAVLNode(AVLRoot.left, ele);
			}
			//Same employee ID cannot exist
		}

		//All the code below is done for each node above the point we have inserted the node to be added in the AVL tree; in the bottom-up manner
		AVLRoot.height = 1 + max(height(AVLRoot.left), height(AVLRoot.right)); //updating height of each node as it comes
		
		//Till above here, we can say that we have done the implementation of a Binary Search Tree.
		//This BST would be transformed into an AVL Tree when we add the concept of rebalancing in the same!

		int heightDiff = heightDiff(AVLRoot); //tells us the difference in the heights of the left and right sub-tree for the current node
		//Remember that this heightDiff is between the left and the right node and not the other way round!
		//Example: If heightDiff is negative, it implies that the right side is heavier and it could be either the case of Right-Right or Right-Left
		//If the ID of the incoming node is smaller in the above case, then it automatically implies that the case under consideration is left to be Right-Left

		//Single Rotation: This would involve two cases, when both x and y are lined up below z on the same side, either left or right
		/*
					z					z
				 /							\
				y			OR 				 y
			 /									\
			x									 x
		*/
		
		if (heightDiff > 1){ //implies that the root is left-side heavy
			//Left-Left
			if(ele.id < AVLRoot.left.id)
            	return rightRotation(AVLRoot);
			//Left-Right --> implies zig-zag --> Double Rotation
			else if (ele.id > AVLRoot.left.id){ //although the equality case does not arise in our problem due to uniqueness of IDs	
				AVLRoot.left = leftRotation(AVLRoot.left);
				return rightRotation(AVLRoot);
			}
		}
        else if (heightDiff < -1){ //implies that the root is right-side heavy
			//Right-Left --> implies zig-zag --> Double Rotation
			if (ele.id < AVLRoot.right.id){
				AVLRoot.right = rightRotation(AVLRoot.right);
           		return leftRotation(AVLRoot);
			}
			//Right-Right
			else if (ele.id > AVLRoot.right.id)
            	return leftRotation(AVLRoot);
		}

		return AVLRoot; //finally returning the root of the re-balanced tree
	}


	//Searches for the given boss-ID in the AVL Tree to get a link to the corresponding node in the Generic Tree
	private AVLNode searchAVLNode(AVLNode AVLRoot, int id) throws IllegalIDException{
		// System.out.println("AVL Root in Search is: " + AVLRoot.id);
		// System.out.println("BossID is " + bossid);
		
		if(AVLRoot == null){
			return null;
			// throw new IllegalIDException("The given ID does not exist in the organisational hierarchy!");
		}
		else if (AVLRoot != null && AVLRoot.id == id){
			return AVLRoot;
		}
		else if (AVLRoot.id < id){
			return searchAVLNode(AVLRoot.right, id);
		}
		else if (AVLRoot.id > id){
			return searchAVLNode(AVLRoot.left, id);
		}
		return AVLRoot;
	}


	//This code is used to hire an employee in the organisational hierarchy
	public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{
		//your implementation
		
		//first search for the bossid in your AVL Tree
		if(isEmpty())
			throw new EmptyTreeException("The organisational hierarchy is empty!");
		else{ 
			//First check if the ID already existed in the tree
			AVLNode checkExistence = searchAVLNode(AVLRoot, id); 
			if (checkExistence != null)
				throw new IllegalIDException("The given ID already exists in the organisational hierarchy!");

			AVLNode temp = searchAVLNode(AVLRoot, bossid); //Search for the node
			
			//ADDED AFTER SUBMISSION
			if (temp == null)
				throw new IllegalIDException("The given boss-ID does not exist in the organisational hierarchy!");
			//###

			//creating a generic node which then be linked to the AVL Node
			GenericNode genTemp = new GenericNode();
			genTemp.id = id;
			genTemp.level = temp.pointerNode.level + 1; //as the level of the child is 1 more than that of the parent
			genTemp.parent = temp.pointerNode;

			temp.pointerNode.child.add(genTemp); //adding a node with the new employee id in the Generic Tree

			AVLRoot = addAVLNode(AVLRoot, genTemp);			
			globalSize += 1; //organisation size increases by 1
		}
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	} 

  
	//Deleting a node from the AVL Tree
    private AVLNode deleteNode(AVLNode AVLRoot, int id)  
    {  
		//the exception for below is already handled in the fireEmployee code
        if (AVLRoot == null)  
            return AVLRoot;  
  
		//Seaching for the node to be deleted
        if (id < AVLRoot.id)  
            AVLRoot.left = deleteNode(AVLRoot.left, id);  
        else if (id > AVLRoot.id)  
            AVLRoot.right = deleteNode(AVLRoot.right, id);  
        else{ //reached the node to be deleted
			//NOTE: In an AVL Tree, if a node has only one child, then its child is a leaf node

            //We first handle the case of a single child at max
			if(AVLRoot.left == null && AVLRoot.right == null){
				AVLRoot = null; //since the leaf node is directly deleted
				//we shall change the height later
			}
			else if (AVLRoot.left == null){ //meaning that the right node is non-null
				AVLRoot = AVLRoot.right;
			}
			else if (AVLRoot.right == null){ //meaning that the left node is non-null
				AVLRoot = AVLRoot.left;
			}
            else{ //both children are non-null 
				/*
				The idea would be to remove the this element by first switching it with its least successor and then going to the 
				right branch to find delete it recursively
				*/
                AVLNode temp = leastSuccessor(AVLRoot.right);  
				//Now switch carefully. Switching the whole node would make all the associated children also go away to the bottom
				//Hence we only make the necessary changes; height can be re-balanced later
				AVLRoot.id = temp.id;
				AVLRoot.pointerNode = temp.pointerNode; //so that the sub-nodes in the left and right pointers do not get deleted!

				//Deleting the element form the right sub-tree
                AVLRoot.right = deleteNode(AVLRoot.right, temp.id);  
            }  
        }  
  
		//Rebalancing: in the bottom up fashion
        if (AVLRoot == null) //leaf node
            return AVLRoot;  
   
        AVLRoot.height = max(height(AVLRoot.left), height(AVLRoot.right)) + 1;  

		//Check if the root is unbalanced
        int balance = heightDiff(AVLRoot);  
  
        // If this node becomes unbalanced, then there are 4 cases  

        if (balance < -1){ //right-heavy
			if (heightDiff(AVLRoot.left) <= 0) //Right-Right; we use equality here, since we prefer to go with single rotations in the tie-breaker case
				return leftRotation(AVLRoot);
			else if (heightDiff(AVLRoot.left) > 0){ //Right-Left
				AVLRoot.right = rightRotation(AVLRoot.right);  
            	return leftRotation(AVLRoot); 
			}
		}  
		else if (balance > 1){ //left-heavy
			if (heightDiff(AVLRoot.left) >= 0) //Left-Left; we use equality here, since we prefer to go with single rotations in the tie-breaker case
				return rightRotation(AVLRoot);
			else if (heightDiff(AVLRoot.left) < 0){ //Left-Right
				AVLRoot.left = leftRotation(AVLRoot.left);  
           		return rightRotation(AVLRoot);  
			}
		}
  
        return AVLRoot;  //returning the root node after deltion of the node and rebalancing the tree
    }  


	//To check if the AVL Tree is correct or not by manually verifiying the inorder traversal for the AVL Tree, which should be a sorted array
	// void inOrder(AVLNode node)  
    // {  
    //     if (node != null)  
    //     {  
    //         inOrder(node.left); 
	// 		System.out.print(node.id + " ");   
    //         inOrder(node.right);  
    //     }  
    // } 


	public void fireEmployee(int id) throws IllegalIDException,EmptyTreeException{
		//your implementation
		//this would simply involve deleting the element from the tree
		if(isEmpty())
			throw new EmptyTreeException("The organisational hierarchy is empty!");
		if(GenericRoot.id == id)
			throw new IllegalIDException("The given ID is for the owner of the organisation who cannot be fired as per guidelines!");
		if(searchAVLNode(AVLRoot, id) == null){
			throw new IllegalIDException("The given ID does not exist in the organisational hierarchy!");
		}
		else{
			// AVLNode temp = searchAVLNode(AVLRoot, id);
			// boolean flag = temp.pointerNode.parent.child.removeElement(temp.pointerNode); //would give true since the element has to be there, else it would have been handled before
			// inOrder(AVLRoot); //to check if the AVL Tree is in the right order or not
			// System.out.println();
			AVLRoot = deleteNode(AVLRoot, id);
			// inOrder(AVLRoot);
			// System.out.println();
			globalSize--; //since an employee is being fired from the organisation
		}
	 	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}


	public void fireEmployee(int id, int manageid) throws IllegalIDException,EmptyTreeException{
		//your implementation
		//this involves shifting of the children of the node having ID as id and merging them with the children of the manageid.
		
		if(isEmpty())
			throw new EmptyTreeException("The organisational hierarchy is empty!");
		if(GenericRoot.id == id)
			throw new IllegalIDException("The given ID is for the owner of the organisation who cannot be fired as per guidelines!");
		if(searchAVLNode(AVLRoot, id) == null){
			throw new IllegalIDException("The given ID does not exist in the organisational hierarchy!");
		}
		else{
			AVLNode temp = searchAVLNode(AVLRoot, id);
			Vector<GenericNode> changeOfManager = temp.pointerNode.child; //storing the employees of the manager being deleted, so that we could merge them with other children of the manageid employee
			AVLNode tempManager = searchAVLNode(AVLRoot, manageid); //searching for the node under which the children have to be added

			//Changing the manager for each of the employees that are being shifted
			for(int i=0; i<changeOfManager.size(); i++){
				changeOfManager.get(i).parent = tempManager.pointerNode;
			}
			
			//Adding all employees as sub-ordinates to the other manager
			tempManager.pointerNode.child.addAll(changeOfManager); 

			//Deleting the original employee id
			// boolean flag = temp.pointerNode.parent.child.removeElement(temp.pointerNode); //would give true since the element has to be there, else it would have been handled before

			// inOrder(AVLRoot);
			// System.out.println();
			AVLRoot = deleteNode(AVLRoot, id); //###### ISMEIN ERROR HAI
			// inOrder(AVLRoot);
			// System.out.println();

			globalSize--; //since an employee is being fired from the organisation			
		}
		
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	} 


	public int boss(int id) throws IllegalIDException,EmptyTreeException{
		//your implementation

		//Should be the parent node
		//This method shall be used in the lowestCommonBoss method

		if(isEmpty())
			throw new EmptyTreeException("The organisational hierarchy is empty!");
		else if(GenericRoot.id == id)
			return -1; //as per guidelines; if the id is for the owner itself
		else if(searchAVLNode(AVLRoot, id) == null){
			throw new IllegalIDException("The given ID does not exist in the organisational hierarchy!");
		}
		else{ 
			AVLNode temp = searchAVLNode(AVLRoot, id); //takes care of the situation when the ID is illegal
			return temp.pointerNode.parent.id;
		}
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public int lowestCommonBoss(int id1, int id2) throws IllegalIDException,EmptyTreeException{
		//your implementation

		//Keep going above from the node which has a larger level, and then simultaneously move one 
		//step above to get to get the common base which happens when both the nodes point to the same parent node.
		//This has to happen atleast at the root node for sure!
		
		if(isEmpty())
			throw new EmptyTreeException("The organisational hierarchy is empty!");
		if(searchAVLNode(AVLRoot, id1) == null || searchAVLNode(AVLRoot, id2) == null){
			throw new IllegalIDException("The given ID does not exist in the organisational hierarchy!");
		}
		else{ 
			if(id1 == GenericRoot.id || id2 == GenericRoot.id){
				return -1; //as per guidelines given
			}
			int level1 = level(id1);
			int level2 = level(id2);
			
			AVLNode temp1 = searchAVLNode(AVLRoot, id1); //takes care of the situation when the ID is illegal
			AVLNode temp2 = searchAVLNode(AVLRoot, id2); //takes care of the situation when the ID is illegal

			GenericNode firstID = temp1.pointerNode;
			GenericNode secondID = temp2.pointerNode;
			GenericNode commonBoss = firstID; //just for the purpose of initialising

			// System.out.println(firstID.id);
			// System.out.println(secondID.id);
			// AVLNode temp3 = searchAVLNode(AVLRoot, 10); //takes care of the situation when the ID is illegal
			// System.out.println(temp3.pointerNode.id);

			if(level1 > level2){
				commonBoss = firstID;
				while(level1 != level2){
					commonBoss = commonBoss.parent;
					level1--;
				}

				//ADDED THE IF ELSE AFTER SUBMISSION; EARLIER IT WAS WHILE LOOP ONLY
				//If the inital ids that were input as arguments, is the LCB of the other, then we need to return its parent
				if(temp2.id == commonBoss.id)
					return commonBoss.parent.id;
				else{
					while(commonBoss != secondID){
						commonBoss = commonBoss.parent;
						secondID = secondID.parent;
					}
				}
			}

			else if(level1 <= level2){
				commonBoss = secondID;
				// System.out.println("The levels are: " + level1 + " " + level2);
				while(level1 != level2){
					commonBoss = commonBoss.parent;
					// System.out.println("The parent of second ID is: " + commonBoss.id);
					level2--;
				}

				//ADDED THE IF ELSE AFTER SUBMISSION; EARLIER IT WAS WHILE LOOP ONLY
				//If the inital ids that were input as arguments, is the LCB of the other, then we need to return its parent
				if(temp1.id == commonBoss.id){
					// System.out.println("Temp 2: " + secondID + " | Common Boss: " + commonBoss);
					return commonBoss.parent.id;
				}
				
				while(commonBoss != firstID){
						commonBoss = commonBoss.parent;
						firstID = firstID.parent;
						// System.out.println("  The parent is: " + firstID.id + " " + commonBoss.id);
				}
			}
			return commonBoss.id;
		}
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	public String toString(int id) throws IllegalIDException, EmptyTreeException{
		//your implementation

		//This will use the concept of entering elements in a queue to get level-order traversal. 
		//This would allow us to go through the required elements in the necessary fashion.

		//We would have to first add all the elements inside a vector and then use collection.sort() to get them in a sorted order

	
		if(isEmpty())
			throw new EmptyTreeException("The organisational hierarchy is empty!");
		if(searchAVLNode(AVLRoot, id) == null){
			throw new IllegalIDException("The given ID does not exist in the organisational hierarchy!");
		}
		else{ 
			//First search the ID in the AVL Tree to get a link to that node in the Generic Tree
			AVLNode temp = searchAVLNode(AVLRoot, id); //if it doesn't exist, that case is already taken care in searchAVLTree
			GenericNode genTemp = temp.pointerNode;

			//Thing left to do is to do breadth-search i.e. level-order traversal for temp.pointerNode which gives the corresponding node in the Generic Tree
			Vector <GenericNode> levelOrderTraversal = new Vector <GenericNode> ();
			
			levelOrderTraversal.add(genTemp); //This adds the root of the node under consideration
						
			String levelWise = "";

			//Now we need to add all the children in the breadth-first order --> use the queue technique
			while (!levelOrderTraversal.isEmpty()){
				//Add children for all the 
				Vector <Integer> finalRes = new Vector <Integer> ();
				int n = levelOrderTraversal.size();
				while(n>0){					
					//Add all the children for the first element in the vector
					for(int i=0; i<levelOrderTraversal.get(0).child.size(); i++){
						levelOrderTraversal.add(levelOrderTraversal.get(0).child.get(i));
					}

					//Adding all the elements at the current level, one-by-one, to the string
						finalRes.add(levelOrderTraversal.get(0).id);

					//Remove the element from the start of the vector
					levelOrderTraversal.remove(0); //This puts back only the current elements in the vector to start from the first position

					n--; //since we have to repeat the above procedure till we add all children nodes of the current parent nodes in the vector
				}

				Collections.sort(finalRes); //to sort the vector
				
				for (int i=0; i<finalRes.size()-1; i++){
					levelWise = levelWise + finalRes.get(i) + " ";
				}
				levelWise = levelWise + finalRes.get(finalRes.size()-1);

				if (levelOrderTraversal.size() != 0)
					levelWise = levelWise + ",";
			}
			// return "[" + levelWise + "]"; //to ensure that an extra space is not present at the start and end of the String to be submitted
			return levelWise;
		}
	}

}

//END OF PROGRAM
