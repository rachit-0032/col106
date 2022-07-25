public class program
{
	public String[] test(String fileNames[])
	{
		/*
		Exercise 17: Java files- You have been given the list of the names
		of the files in a directory.
		You have to select Java files from them.
		A file is a Java file if it’s name ends with ".java".
		For e.g. "File-Names.java" is a Java file, "FileNames.java.pdf" is not.
		If the input is {"can.java","nca.doc","and.java","dan.txt","can.java","andjava.pdf"} 
		the expected output is {"can.java","and.java","can.java"}
		*/

		int count = 0;

		for (int i=0; i<fileNames.length; i++){
			if(fileNames[i].length() >= 5){
				if (fileNames[i].substring(fileNames[i].length() - 5, fileNames[i].length()).equals(".java")){
					count++; //counts the number of .java files
				}
			}
		}
		
		String javaFiles[] = new String[count]; //creating an array of the requisite size
		count = 0;
		
		//A 2-pass process needs to be followed since we are dealing with arrays which have a pre-determined size
		for (int i=0; i<fileNames.length; i++){
			if(fileNames[i].length() >= 5){
				if (fileNames[i].substring(fileNames[i].length() - 5, fileNames[i].length()).equals(".java")){
					//instead of string1.equals(string2), we could have compared each character from the end using .charAt function
					javaFiles[count] = fileNames[i];
					count++;
				}
			}
		}
		return javaFiles;
	}
}
