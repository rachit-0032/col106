public class program
{
	public boolean test(int a, int b)
	{
		/*
		Exercise 9: Same last digit- Given two non-negative integers, return true if they
		have the same last digit, such as with 27 and 57. Note that the % "mod" operator
		computes remainder, so 17%10 is 7.
		*/
		return (a % 10 == b % 10);
	}

	// Had there been a main() function | To test the program
	// public static void main(String[] args) {
	// 	System.out.println(test(56,66));
	// }
}
