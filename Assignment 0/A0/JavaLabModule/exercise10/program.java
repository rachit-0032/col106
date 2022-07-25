import javax.lang.model.util.ElementScanner6;

public class program
{
	//Recursive GCD Function; could have written an iterative function using a-b approach
	public int gcd (int a, int b){
		if (b == 0)
			return a;
		else
			return gcd(b, a%b);
	}

	public int test(int n, int m)
	{
		/*
		Exercise 10: Least common multiple- Given two integers n and m, the objective
		is to compute the LCM (least common multiple) of n and m. LCM is the smallest
		number that is divisble by both n amd m. For e.g. is n is 12 and m is 14, the
		LCM is 84. If n is 32 and m is 16, the LCM is 32.
		*/

		/*
		We can find the GCD of the two numbers and use (n*m)/GCD to get the LCM.
		This would work since we divide their multiplication with the greatest common divisor
		and thus the extra multiples of divisors that come in n*m get removed by GCD.
		*/

		if (n>m)
			return (n*m)/gcd(n,m);
		else
			return (n*m)/gcd(m,n);

	}
}
