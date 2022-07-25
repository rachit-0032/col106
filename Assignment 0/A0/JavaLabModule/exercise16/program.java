import javax.lang.model.util.ElementScanner6;

public class program
{
	public int helper (char unit){
		if (unit == 'A') //could have built a switch case too; or done using (unit - 'A') + 10, to make it working
			return 10;
		else if (unit == 'B')
			return 11;
		else if (unit == 'C')
			return 12;
		else if (unit == 'D')
			return 13;
		else if (unit == 'E')
			return 14;
		else if (unit == 'F')
			return 15;
		else
			return (int) unit - '0'; //to ensure that we do not get the ASCII value while doing type conversion
	}

	public String test(String hex)
	{
		/*
		Exercise 16: Hex to binary- Given a string representing a number in hexadecimal
		format, convert it into its equivalent binary string. For e.g. if the input if "1F1"
		then its binary equivalent is "111110001". If the input is "13AFFFF", the output
		should be "1001110101111111111111111".
		*/

		//We can first find the number that the hexademical represents and then convert that number into binary

		
		int num = 0;

		for (int i=0; i<hex.length(); i++){
			char c = hex.charAt(i);
			int curr = helper(c);
			num = num*16 + curr;
		}
		
		String binary="";
		
		//Since the binary numbers are remainder of the number with 2, but in reverse order, we need to add this remainder in front of the string
		while(num>0){
			binary = num%2 + binary; //so that the earlier digit goes to the right end
			num = num/2; //or could have used right shift operator
		}
		return binary;
	}
}
