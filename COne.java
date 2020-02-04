/*
 * @author: Jamie WOn
 * Date: Jan. 30 2020
 * Technical take home assessment
 */

// import statements
import java.lang.String;
import java.util.Scanner;
import java.io.*;

// start of class
public class COne {

	private static int totalLines;
	private static int comments;
	private static int sComments;
	private static int mBComments;
	private static int bComments;
	private static int todos;

	public static void main (String [] args)  throws IOException{

		// creates scanner
		Scanner s = new Scanner(System.in);

		System.out.println("Enter the file name.");

		// gets file name
		String fName = s.nextLine();

		// checks if valid file name (not empty, doesn't start with '.', contains a '.', '.' isn't the last character)
		if (fName.equals("") || fName.charAt(0) == '.' || !fName.contains(".") || fName.indexOf(".") == fName.length()-1) {
			System.out.println("Invalid file name. Exiting");
			System.exit(0);
		}

		// finds type of file
		String type = fName.substring(fName.indexOf(".")+1);
		System.out.println(type);
		
		try {
			File file = new File(fName);
			// creates scanner
	  	Scanner fileReader = new Scanner (file);

			if (type.equals("java")||type.equals("c")) analyzeJava(fileReader);
			if (type.equals("py")) anyalyzePy(fileReader);
			
			throw new FileNotFoundException();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found. Exiting.");
			System.exit(0);
			
		}

	}


	/**
		* analyzePy Analyzes a python file for comments
		*@param fileReader scanner linked to file to be read
		*/
	private static void anyalyzePy (Scanner fileReader) {

		boolean block = false;

		while (fileReader.hasNext()) {
			String s = fileReader.nextLine();

			// if a comment line
			if (s.contains ("#")) {
				// if previous line wasn't a comment
				if (!block) {
					block = true; // it's now a block
					sComments ++; // increase single line
					mBComments ++; // increase singles in block
					bComments ++; // increases blocks
				} else { // if already a block
					mBComments ++; // increases single lines in blocks
				}
				// increases comment counter
				comments ++;

			} else {
				block = false;
			}


			// if todo
			if (s.contains("TODO")) {
				todos ++;
				comments ++;
			}

			totalLines ++;
		}

		// subtracts #blocks from #single lines
		sComments -= bComments;

	}

	/**
		* analyzeJava Analyzes a java file for comments
		*@param fileReader scanner linked to file to be read
		*/
	private static void analyzeJava (Scanner fileReader) {
    	
	  // for tracking block comments
	  boolean block = false;
	  // loops through file, adding to count
	  while (fileReader.hasNext()) {
	  	String s = fileReader.nextLine();

	  	// if a single comment
	  	if (s.contains("//")) {
	      comments ++;
	      sComments ++;
	  	}

	  	// if start of block
	  	if (s.contains("/*")) {
	      comments ++;
	      bComments ++;
	      mBComments ++;
	      block = true;
	  	// if end of block
	  	} else if (s.contains("*/")) {
	      comments ++;
	      mBComments ++;
	      block = false;
	    // if in blovk
	  	} else if (block) {
	      comments ++;
	      mBComments++;
	  	}

	  	// todos
	  	if (s.contains("TODO")) {
	  		todos ++;
	  		comments ++;
	  	}
	        
	  	// counts the line
	  	totalLines ++;
	        
	   }
	   fileReader.close(); // closes scanner
	   
  }

  // accessorss
  public int getTotalLines() {
  	return totalLines;
  }

  public int getComments() {
  	return comments;
  }
  
  public int getSingleComments() {
  	return sComments;
  }
  
  public int getCommentsInBlocks() {
  	return mBComments;
  }
  
  public int getBlockComments() {
  	return bComments;
  }
  
  public int getToDos() {
  	return todos;
  }
  
  /**
  	* printData prints information after abalyzing file
  	*/
	private void printData() {
		System.out.println("Total # of lines: " + totalLines);
		System.out.println("Total # of comment lines: " + comments);
		System.out.println("Total # of single line comments: " + sComments);
		System.out.println("Total # of comment lines within block comments:: " + mBComments);
		System.out.println("Total # of block line comments: " + bComments);
		System.out.println("Total # of TODO's: " + todos);
	}

}
