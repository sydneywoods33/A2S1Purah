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

	/**
	 * This is class that can be used to parse a XML file for errors. It works by
	 * going through and check opening tags with their associated closing tags to
	 * see if they are appropriately constructed or if a element is missing.
	 * 
	 * @param fileName an appropriate file with the .xml extension
	 * @return errorQ A queue of each error and their associated description
	 * @throws EmptyQueueException   throws an error if trying to access an empty
	 *                               queue
	 * @throws FileNotFoundException throws an error if the file cannot be found
	 */
	public MyQueue<String> ParseXML(String fileName) throws EmptyQueueException, FileNotFoundException
	{
		// attributes
		MyStack<String> myParser = new MyStack<>();
		MyQueue<String> errorQ = new MyQueue<>();
		int counter = 0;

		// call reader
		File inputFile = new File(fileName);
		Scanner myReader = new Scanner(inputFile);

		// start reading file
		while (myReader.hasNextLine())
		{
			// update to keep track of line
			counter++;
			String data = myReader.nextLine().trim();

			// Detect malformed tags
			if (data.contains(">>") || data.contains("<>") || !data.contains(">"))
			{
				errorQ.enqueue("Invalid tag at line " + counter + ": " + data);
				continue;
			}

			// Handle header tag
			if (data.startsWith("<?") && data.endsWith("?>"))
			{
				continue; // Valid header
			}

			// Handle self-closing tags
			if (data.startsWith("<") && data.endsWith("/>"))
			{
				continue; // Valid self-closing tag
			}

			// Handle start tags
			else if (data.startsWith("<") && !data.startsWith("</") && data.endsWith(">"))
			{
				int spaceIndex = data.indexOf(" ");
				int endIndex = data.indexOf(">");
				String tagName = data.substring(1, spaceIndex == -1 ? endIndex : spaceIndex).trim();
				myParser.push(tagName); // Push start tag onto stack
			}

			// Handle end tags
			else if (data.startsWith("</") && data.endsWith(">"))
			{
				String tagName = data.substring(2, data.indexOf(">")).trim();

				// Check if matches with the top of the stack
				if (!myParser.isEmpty() && myParser.contains(tagName))
				{
					myParser.pop(); // Valid match
				} else if (!myParser.isEmpty() && !tagName.equals(myParser.peek()))
				{
					errorQ.enqueue("Error at line " + counter + ": Mismatched end tag: </" + tagName + ">");
				} else
				{
					errorQ.enqueue("Error at line " + counter + ": Unmatched end tag: </" + tagName + ">");
				}
			}

			// Handle improperly nested tags
			else if (data.contains("<") && data.contains("</"))
			{
				String openTag = data.substring(data.indexOf("<") + 1, data.indexOf(">")).trim();
				String closeTag = data.substring(data.lastIndexOf("</") + 2, data.lastIndexOf(">")).trim();

				if (!openTag.equals(closeTag))
				{
					errorQ.enqueue("Error at line " + counter + ": Improperly nested tags: <" + openTag + "> and </"
							+ closeTag + ">");
				}
			}
		}

		myReader.close();

		// Report unclosed start tags
		while (!myParser.isEmpty())
		{
			errorQ.enqueue("Error: Unclosed start tag: <" + myParser.pop() + ">");
		}

		return errorQ;
	}

}