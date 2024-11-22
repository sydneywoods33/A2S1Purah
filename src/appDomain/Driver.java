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
			try
			{
				System.out.println(" =========== ERROR LOG =========== ");
				MyQueue<String> ErrorsFound = MyParser.ParseXML(arg);
				if (ErrorsFound.isEmpty())
				{
					System.out.println("No error found.");
				} else
				{
					System.out.println(ErrorsFound.toString());
				}
			} catch (Exception e)
			{
				System.out.println("Something went wrong with the file.");
			}
		}
	}

}
