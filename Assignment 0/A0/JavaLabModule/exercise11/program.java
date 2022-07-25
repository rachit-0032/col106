public class program
{
	public float[] test(int b, int c)
	{
		/*
		Exercise 11: Roots of polynomial- Write a Java program that given b and c,
		computes the roots of the polynomial x*x+b*x+c. You can assume that the
		roots are real valued and need to be return in an array.
		Return the result in an array [p,q] where p<=q meaning the smaller 
		element should be the first element of the array
		*/
		double x_first = (-b - Math.sqrt(b*b - 4*c))/2.0;
		double x_second = (-b + Math.sqrt(b*b - 4*c))/2.0;
		float ret[] = {(float) x_first, (float) x_second};
		//System.out.println(ret[0]);
		return ret;
	}
}
