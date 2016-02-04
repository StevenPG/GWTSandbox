// File: FileOpener.java
// Methods for getting a source file name 
//	and for assigning an output file's destination
// Also a method to list a directory and return the list

package stevengantz.memory.spiegel.Input;

import java.io.*;

public class FileOpener extends InputReader {

	// Read the input file name from the user
	public static String GetInputFile() throws java.io.IOException
	{DataInputStream FileNameInput=new DataInputStream(System.in);
	 System.out.print("File to Open >");
	 System.out.flush();
	 return(FileNameInput.readLine());
	}

	// Read the output file name from the user
	public static String getOutputFile() throws java.io.IOException
	{BufferedReader FileNameInput=new BufferedReader
	                                (new InputStreamReader(System.in));
	 System.out.println("Destination:");
	 System.out.println("S)creen	P)rinter	D)isk");
	 char reply=getChoice("SPD");
	 switch (reply) {
		 case 'S':return("con");
		 case 'P':return("prn");
		 case 'D':
			System.out.println("Enter file to open >");
			System.out.flush();
			return(FileNameInput.readLine());
	 }
	 return(FileNameInput.readLine());
	}

	public static void showLoggedDir()
	{File ThisDir=new File(".");
	 String [] dirList=ThisDir.list();
	 for (int i=0;i<dirList.length;i++)
		System.out.println(dirList[i]);
	}

	public static String[] getLoggedDirList()
	{File ThisDir=new File(".");
	 return(ThisDir.list());
	}

}

