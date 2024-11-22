package appDomain;

import implementations.MyQueue;
import implementations.Parser;

public class Driver
{
	public static void main(String[] args)
	{
		// call parser
		Parser MyParser = new Parser();
		// parse files
		for (String arg : args)
		{
			MyQueue<String> ErrorsFound = MyParser.ParseXML(arg);
			if (ErrorsFound.isEmpty())
			{
				System.out.println("No error found.");
			} else
			{
				System.out.println(ErrorsFound.toString());
			}
		}
	}

}
