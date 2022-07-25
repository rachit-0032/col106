import java.util.EmptyStackException;
import java.util.Scanner;

public class FabricBreakup {	
	public static void main(String args[]){
		//Implement FabricBreakup puzzle using Stack interface
		StackInterface myStack = new Stack(); //stores scores of shirts
		StackInterface bestScoreStack = new Stack(); //stores best shirt score till that element

		Scanner in = new Scanner(System.in);
		int numOper = in.nextInt();
		int maxScore = -1; //initialising with lest than minimum liking which shall be updated as shirts enter the pile

		//Continue reading the input and keep executing code until number of operations are exhausted
		for(int i=0; i<numOper; i++){
			int operNum = in.nextInt();
			if(in.nextInt() == 1){ //command is to add to the pile
				int currScore = in.nextInt(); //read the score of the shirt
				//in.nextLine(); //consumes the new-line escape character #### VERY IMPORTANT ####
				if(currScore > maxScore){
					maxScore = currScore; //we store the highest score shirt
					bestScoreStack.push(maxScore); //keeps track of the next highest score in the stack
				}
				myStack.push(currScore); //add the shirt into the pile
				//System.out.println("Pushing Score " + currScore);
			}
			else{ //command is to party
				//in.nextLine();
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
								int wasteVal = (int)myStack.pop();
								//System.out.println("Popping Score" + wasteVal);
							} catch (EmptyStackException e) {
								System.out.println("Empty Stack - 1!");
							}
							toppled += 1;
							try {
								shirtScore = (int)myStack.top();
							} catch (EmptyStackException e) {
								System.out.println("Empty Stack - 1.5!");
							}
						}

						//You have the max score at the top!
						try {
							int wasteVal = (int)myStack.pop();
						} catch (EmptyStackException e) {
							System.out.println("Empty Stack - 2!");
						} //remove the shirt
	
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
						System.out.println("Empty Stack - 5");
					}	
				}
 	
				//Now you have the highest scored shirt at the top of the pile
					
				// }
			} //repeats for the number of operations to be executed
		}
	}
}
