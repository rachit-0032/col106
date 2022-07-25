import javax.lang.model.util.ElementScanner6;

public class program
{
	public int test(char key[], char answer[])
	{
		/*
		Exercise 20: Score Marks- The "key" array is an array containing the correct
		answers to an exam, like {'a','a','b','c'}. The "answers" array contains a student's
		answers, with '?' representing a question left blank. The two arrays are not
		empty and are the same length. Return the score for the provided array of
		answers, giving a +4 for each correct answer, -1 for each incorrect answer and 0
		for each blank answer. For e.g.
		key = {'a','c','d','b'}
		answers = {'c','c','?’,'b'}
		then score is -1 + 4 + 0 + 4 = 7
		*/
		
		int ret = 0;
		
		for (int i=0; i<answer.length; i++){
			if(answer[i] == '?') //no need to check for the key element here
				ret += 0;
			else if (answer[i] == key[i])
				ret += 4;
			else
				ret += -1;
		}
		return ret;
	}
}
