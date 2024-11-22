package appDomain;

import implementations.MyQueue;
import implementations.Parser;

public class Driver
{
	public static void main(String[] args)
	{
		// setup
		String fileName = null;
		 // Parse command line arguments
        for (String arg : args) {
            if (arg.startsWith("-f")) {
                fileName = arg.substring(2);}
                
		// call parser
		Parser MyParser = new Parser();

			try
			{
				// create header
				System.out.println("\nParsing " + fileName + " for errors.\n");

				System.out.println(" ================ ERROR LOG ================ \n");

				// run the parser on the supplied file
				MyQueue<String> ErrorsFound = MyParser.ParseXML(fileName);
				if (ErrorsFound.isEmpty())
				{
					System.out.println("No errors found.\n");
				} else
				{
					while (!ErrorsFound.isEmpty())
					{
						System.out.println(ErrorsFound.peek());
						ErrorsFound.dequeue();
					}
				}
			} catch (Exception e)
			{
				System.out.println("Something went wrong with the file.");
			}
		}
	}
}
