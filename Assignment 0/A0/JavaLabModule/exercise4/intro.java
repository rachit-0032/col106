public class intro
{
	public void printinfo ()
	{
		System.out.println(" Hello world " );
	}

	public static void main ( String args [])
	{
		intro var = new intro();
		var.printinfo();
		//printinfo(); //if the method had been defined as a 'static' member
	}
}
