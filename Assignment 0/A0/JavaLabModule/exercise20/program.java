public class program
{
	public String test(String s1, String s2, int m, int n)
	{
		/*
		Exercise 19: String concat- Given two strings s1,s2 and two indices m,n return a
		string having chars from index m to end of s1 and then 0 to n of s2 (both m and n
		are inclusive). For eg, if s1="hello", s2="world", m=3, n=0, then answer is "low"
		*/

		String ret = "";

		for (int i=m; i<s1.length(); i++){
			ret += s1.charAt(i); //because the characters can be added this way in a Java string
		}

		for (int i=0; i<=n; i++){
			ret += s2.charAt(i);
		}
		return ret;
	}
}
