import java.io.FileInputStream;
import java.util.EmptyStackException;
import java.util.Scanner;

public class FabricBreakup {	
	public static void main(String args[]){
		//Implement FabricBreakup puzzle using Stack interface
		StackInterface myStack = new Stack(); //stores scores of shirts
		StackInterface bestScoreStack = new Stack(); //stores best shirt score till that element
		//We need suhc a new stack since we always have to keep track of the best scored shirt that is available in the stack at all times

		try { //For FileInputStream putting out an exception
			FileInputStream fstream = new FileInputStream(args[0]); //since we are reading from the file
			Scanner in = new Scanner(fstream);
			//Scanner in = new Scanner(System.in); //had I had to run via terminal input

			int numOper = in.nextInt();
			int maxScore = -1; //initialising with lest than minimum liking which shall be updated as shirts enter the pile

			//Continue reading the input and keep executing code until number of operations are exhausted
			for(int i=0; i<numOper; i++){
				int operNum = in.nextInt();
				if(in.nextInt() == 1){ //command is to add to the pile
					int currScore = in.nextInt(); //read the score of the shirt
					if(currScore > maxScore){
						maxScore = currScore; //we store the highest score shirt
						bestScoreStack.push(maxScore); //keeps track of the next highest score in the stack
					}
					myStack.push(currScore); //add the shirt into the pile
					//System.out.println("Pushing Score " + currScore);
				}
				else{ //command is to party
					int toppled = 0; //stores the number of shirts to be toppled from the pile
					//System.out.println("Party Karni Hai");
					if (myStack.isEmpty()){
						System.out.println(operNum + " " + "-1");
					}
					else{
						try {
							int shirtScore = (int)myStack.top();
							while(shirtScore != maxScore){
								try {
									int wasteVal = (int)myStack.pop(); //score of the shirt being removed
									//System.out.println("Popping Score" + wasteVal);
								} catch (EmptyStackException e) {
									System.out.println("Empty Stack - 2!");
								}
								toppled += 1;
								try {
									shirtScore = (int)myStack.top(); //updating the next top shirt score that is available in the stack
								} catch (EmptyStackException e) {
									System.out.println("Empty Stack - 3!");
								}
							}

							//You have the max score at the top!
							try {
								int wasteVal = (int)myStack.pop();
							} catch (EmptyStackException e) {
								System.out.println("Empty Stack - 4!");
							} //remove the shirt since you wear it at the party
		
							try {
								int wasteVal = (int)bestScoreStack.pop();
							} catch (EmptyStackException e) {
								System.out.println("Empty Stack - 3!");
							} //since shirt is gone from the pile
		
							if(bestScoreStack.isEmpty()){
								maxScore = -1;
							}
							else{
								try {
									maxScore = (int)bestScoreStack.top();
								} catch (EmptyStackException e) {
									System.out.println("Empty Stack- 4!");
								} //next highest score comes into the picture
							}
							System.out.println(operNum + " " + toppled);		
						} catch (Exception e) {
							System.out.println("Empty Stack - 1");
						}	
					}
				} 
			}//repeats for the number of operations to be executed
		} catch (Exception e) {
			System.out.println("File not Available!");
		}
	}
}
