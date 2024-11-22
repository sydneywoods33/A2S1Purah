package implementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import exceptions.EmptyQueueException;

public class Parser
{

	public Parser()
	{
		super();
	}

	public MyQueue<String> ParseXML(String fileName) throws EmptyQueueException, FileNotFoundException
	{
		// parts of an XML tag
		String Self_Closing = "/>"; // < tag />
		String Start_Tag = "<"; // < tag >
		String Close_Tag = ">";
		String End_Tag = "</"; // < /tag >

		// parts of the parser
		MyStack<String> myParser = new MyStack<>();
		MyQueue<String> errorQ = new MyQueue<>();
		MyQueue<String> extrasQ = new MyQueue<>();
		int counter = 0;

		// start parser
		// scan file
		File inputFile = new File(fileName);
		Scanner myReader = new Scanner(inputFile);

		// read line
		while (myReader.hasNextLine())
		{
			counter++;
			String data = myReader.nextLine();
			// is line self closing
			if (data.startsWith(Start_Tag) && data.endsWith(Self_Closing))
			{

				// is line a start tag
			} else if (data.startsWith(Start_Tag) && data.endsWith(Close_Tag))
			{
				myParser.push(data);
				// is line an end tag
			} else if (data.startsWith(End_Tag))
			{
				// if the line matches the top of the stack
				if (data == myParser.peek())
				{
					myParser.pop();
					// if line matches error queue
				} else if (data == errorQ.peek())
				{
					errorQ.dequeue();
					// if stack is empty
				} else if (myParser.peek() == null)
				{
					errorQ.enqueue(data);

				} else
				{
					// check if matching tag in stack
					if (myParser.contains(data.replace("/", "")))
					{
						while (myParser.contains(data))
						{
							errorQ.enqueue(myParser.pop());
						}
						errorQ.enqueue("Error: line " + counter + " - Improper tag construction.");
					} else
					{
						extrasQ.enqueue(data);
					}
				}
			}
		}
		
		myReader.close();
		
		// check stack
		while (!myParser.isEmpty())
		{
			errorQ.enqueue(myParser.pop());
		}
		// if one, but not both, queues are empty
		while (errorQ.isEmpty() ^ extrasQ.isEmpty())
		{
			errorQ.enqueue("Error: line " + counter + " - Queue length is uneven.");
		}
		// both queues are not empty
		while (!(errorQ.isEmpty() && extrasQ.isEmpty()))
		{
			if (errorQ.peek() == extrasQ.peek())
			{
				errorQ.dequeue();
				extrasQ.dequeue();
			} else
			{
				errorQ.dequeue();
				errorQ.enqueue("Error: line " + counter + " - Queues do not match.");
			}
		}

		return errorQ;
	}

}
