public class program
{
	public int[] test(int input[])
	{
		/*
		Exercise 15: Remove zeros- Given an array of integers return an array in the
		same order with all 0’s removed. For e.g. is the input is {1,2,3,4,5,0,1,2,0,0,2}
		the expected output is {1,2,3,4,5,1,2,2}. If the input is {0,0,1,2} the output is
		{1,2}. If the input is {0,0,0,0} the expected output is {}.
		*/

		int temp[] = new int [input.length];
		int count = 0;

		for (int i=0; i<input.length; i++){
			if(input[i]!=0){
				temp[count] = input[i];
				count++; //tells the number of elements inside the new array
			}
		}

		int ret[] = new int [count];

		for (int i=0; i<count; i++){
			ret[i] = temp[i]; //reduces the array size and thus removes 0 elements from the array
		}

		return ret;
	}
}
