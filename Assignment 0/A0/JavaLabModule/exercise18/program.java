public class program
{
	public int test(String number)
	{
		/*
		Exercise 18: Most frequent digit- Given a number, the objective is to find out
		the most frequently occuring digit in the number. If more than 2 digits have
		the same frequency, return the smallest digit. The number is input as a string
		and the output should be the digit as an integer. For e.g. if the number is
		12345121, the most frequently occuring digit is 1. If the number is 9988776655
		the output should be 5 as it is the smallest of the digits with the highest frequency.
		*/

		//We could also have used a hashmap too, but I wanted to use basic functionalities only
		int mostFreqNum = 0, maxFreq = 0;
		int freq[] = new int[10]; //creating an array of 10 elements, where the index denotes the number itself
		
		for (int i=0; i<number.length(); i++){
			int unit = number.charAt(i) - '0'; //to convert the char into int using the concept of ASCII
			freq[unit]++; //since the default value is 0 in Java
		}

		for (int i=0; i<freq.length; i++){
			if(freq[i]>maxFreq){
				maxFreq = freq[i];
				mostFreqNum = i;
			}
		}
		return mostFreqNum;
	}
}
