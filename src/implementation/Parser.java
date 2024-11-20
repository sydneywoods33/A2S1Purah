package implementation;

import java.io.File;
import java.util.Scanner;

public class Parser
{
	public void ParseXML(String fileName)
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

		// start parser
		try
		{
			// scan file
			File inputFile = new File(fileName);
			Scanner myReader = new Scanner(inputFile);

			// read line
			while (myReader.hasNextLine())
			{
				String data = myReader.nextLine();
				// is line self closing
				if (data.startsWith(Start_Tag) && data.endsWith(Self_Closing))
				{
					break;
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
						if (myParser.contains(data))
						{
							while (myParser.contains(data))
							{
								errorQ.enqueue(myParser.pop());

							}
							throw new Exception("Improper tag contrustion.");
						} else
						{
							extrasQ.enqueue(data);
						}
					}
				}
			}
			// check queues
			while (!(errorQ.isEmpty() && extrasQ.isEmpty()))
			{
				// check stack
				if (!myParser.isEmpty())
				{
					while (!myParser.isEmpty())
					{
						errorQ.enqueue(myParser.pop());
					}
				}
				// if one, but not both, queues are empty
				else if (errorQ.isEmpty() ^ extrasQ.isEmpty())
				{
					throw new Exception("Queue length is uneven.");
				}
				// both queues are not empty
				else if (!(errorQ.isEmpty() && extrasQ.isEmpty()))
				{
					if (errorQ.peek() == extrasQ.peek())
					{
						errorQ.dequeue();
						extrasQ.dequeue();
					} else
					{
						errorQ.dequeue();
						throw new Exception("Queues do not match.");
					}
				}
			}
		} catch (Exception e)
		{
			System.out.println("Something went wrong with the file.");
		}
	}
}