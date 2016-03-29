import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * ScrawQ, which is the working name of ScrawDB
 * is under development. It is a phyloinformatics DB 
 * creation and management system.
 * 
 * ADD LOG GLOBAL!!!!
 */
public class ScrawQ {
	public static ArrayList<Character> bases=new ArrayList<Character>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//a simple bool to decide whether to display the "ready for next command" prompt
		populateBases();
		
		boolean amTesting=false;
		if(amTesting){
			validateDB();
			return;
		}
		
		
		
		boolean firstRun=true; 
		//print welcome message and user prompt
		System.out.println("Welcome to EMU-Phy!");
		System.out.println("Please enter a command. Type 'help' for help and 'quit' to quit");
		//connect to standard in
		Scanner scanIn=new Scanner(System.in);
		//will continue to read in input until user quits 
		//or something terrible happens (execption thrown)
		while(true){
			if(firstRun==true){
				firstRun=false;
			}
			else{
				System.out.println("Ready for next command      (enter 'quit' to exit)");
			}
			//get the next input, save as string
			String thisCommand=scanIn.next();
			//A very large switch command. More efficient and readable
			//than if-else ifs-elses
			switch(thisCommand){
				//if we are done and want to exit peacefully
				case "quit":
					System.out.println("Thank you for using ScrawQ!");
					System.exit(0);
					break;
				//help message to show all commands
				case "help":
					printHelp();
					break;
				//installs the system
				case "install":
					installFileSys();
					break;
				//To do: give functionality
				//will update everything
				case "update":
					updateAll();
					break;
				//forcibly redo all the analysis
				case "redo":
					redoAll();
					break;
				case "validate":
					validateDB();
					break;
					
				//add subprompt
				case "add":
					boolean keepChecking=true;
					while(keepChecking==true){
						String addCommand;
						System.out.println("Add what?");
						System.out.println("Options are: group, taxa, gene, primer, taxonomy. Enter 'cancel' to cancel or 'done' to finish");
						addCommand=scanIn.next();
						switch(addCommand){
							case "group":
								mkGroup();
								break;
							case "taxa":
								addNewTaxa();
								break;
							case "gene":
								//gene add
								addGene();
								break;
							case "primer":
								//add primer
								break;
							case "taxonomy":
								//add taxonomy
								break;
							case "cancel":
								keepChecking=false;
								break;
							case "done":
								keepChecking=false;
								break;
							default:
								System.out.println("Invalid selection. Please try again...");
								break;
						}
					}
					break;
				case "show":
					keepChecking=true;
					while(keepChecking==true){
						String showCommand;
						System.out.println("Show what?");
						System.out.println("Options are: groups, taxa, genes, primers, taxonomy. Enter 'cancel' to cancel or 'done' to finish");
						showCommand=scanIn.next();
						switch(showCommand){
						case "groups":
							try {
								System.out.println("Groups are...");
								System.out.println();
								showFile("ScrawQ_Phyloinformatics/Groups/List_of_All_Groups.txt");
								System.out.println();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case "taxa":
							try {
								System.out.println("Taxon are...");
								System.out.println();
								showFile("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt");
								System.out.println();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case "genes":
							keepChecking=true;
							while(keepChecking==true){
								Scanner temp=new Scanner(System.in);
								String taxa=new String();
								System.out.println("Name of taxa to display genes for? Type 'display' to show all available taxa (type 'cancel' to cancel");
								taxa=temp.next();
								if(taxa.equals("cancel")){
									keepChecking=false;
								}
								else if(taxa.equals("display")){
									System.out.println("Displaying available taxa\n");
									try {
										showFile("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt");
										System.out.println();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								else{
									System.out.println("Displaying Genes for Taxa: "+taxa+"...");
									try {
										showFile("ScrawQ_Phyloinformatics/All_Taxa/"+taxa+"/Genes/GeneList.txt");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										System.out.println("That taxa does not exist. Try adding the taxa and trying again.");
									}
								
								}
							}
							break;
						case "primer":
							//add primer
							break;
						case "taxonomy":
							//add taxonomy
							break;
						case "cancel":
							keepChecking=false;
							break;
						case "done":
							keepChecking=false;
							break;
						default:
							System.out.println("Invalid selection. Please try again...");
							break;
						}
					}
					break;
				case "mkGroup":
					mkGroup();
					break;
				//help message for group only commands
				case "groupHelp":
					printGroupHelpMessage();
					break;
				case "newTaxa":
					addNewTaxa();
					break;
				default: 
					System.out.println("Invalid command: help message will be displayed");
					printHelp();
					break;
			}
		}
	}
	//////////////////////////////INSTALLATION FUNCTION/////////////////////////////////////////////////////

	/*
	 * Initializes filesystem that will be used as DB
	 * uses user input to decide to install examples or not
	 */
	public static void installFileSys(){
		//connec
		Scanner temp=new Scanner(System.in);
		String examplesDesired=new String();
		System.out.println("Installing EMU-Phy in working directory...!");
		System.out.println(System.getProperty("user.dir"));
		boolean keepchecking=true;
		boolean doExamples=false;
		while(keepchecking==true){
			System.out.println("Examples desired? (Will insert example data in datasytem)");
			System.out.println("Valid options are 'y' or 'n' or 'cancel')");
			examplesDesired=temp.next();
			switch(examplesDesired){
				case "y":
					doExamples=true;
					keepchecking=false;
					print("Installing EMU-Phy with examples...");
					break;
				case "n":
					doExamples=false;
					keepchecking=false;
					break;
				case "cancel":
					System.out.println("Cancelling installation...");
					return;
			}
		}
		//make all the dirs and files basally necessary for system.
		new File("ScrawQ_Phyloinformatics").mkdir();
		new File("ScrawQ_Phyloinformatics/Groups").mkdir();
		new File("ScrawQ_Phyloinformatics/All_Taxa").mkdir();
		new File("ScrawQ_Phyloinformatics/ScrawkovPhy").mkdir();
		new File("ScrawQ_Phyloinformatics/pipelineModules").mkdir();
		File ListOfAllTaxa=new File("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt");
		File ListOfAllGroups=new File("ScrawQ_Phyloinformatics/Groups/List_of_All_Groups.txt");
		File ListOfAllModules=new File("ScrawQ_Phyloinformatics/pipelineModules/List_of_All_Modules.txt");
		mkFile(ListOfAllTaxa);
		mkFile(ListOfAllGroups);
		mkFile(ListOfAllModules);
		//File dir=new File(".");
		//File files[]=dir.listFiles();
		//for(File f: files){
			//System.out.println(f);
		//}
		//System.out.println(System.getProperty("user.home"));
		
	}
	
	
	
	
	//////////////////////////////ALL INTERACTIVE ADD FUNCTIONS/////////////////////////////////////////////
	
	/**
	 * addGene adds gene info to the database. Requires user to specify a taxa to add the gene for
	 */
	public static void addGene(){
		Scanner temp=new Scanner(System.in);
		String thisTaxa=new String();
		System.out.println("Name of taxa to add gene for? Type 'display' to see available taxa (type 'cancel' to cancel");
		thisTaxa=temp.next();
		if(thisTaxa.equals("cancel")){
			return;
		}
		else if(thisTaxa.equals("display")){
			System.out.println("Displaying available taxa\n");
			try {
				showFile("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt");
				addGene();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			File dir= new File("ScrawQ_Phyloinformatics/All_Taxa/"+thisTaxa);
			if(!dir.exists()){
				System.out.println("That taxa does not exist. Try adding it and trying again");
				addGene();
			}
			else{
				String geneName;
				System.out.println("What is the gene name?");
				geneName=temp.next();
				geneName=sanitizeInput(geneName);
				print("Using gene name: "+geneName);
				String method;
				System.out.println("DNA/RNA sequence required...");
				System.out.println("To supply a path to a file containing only this gene in FASTA format, enter 'path'...");
				System.out.println("To supply the sequence directly, enter 'direct'...");
				System.out.println("To cancel addition of gene, enter 'cancel'...");
				method=temp.next();
				String sequence="";
				if(method.equals("cancel")){
					print("Canceling addition of gene...");
					return;
				}
				else if(method.equals("path")){
					class pathDoer{
						public String doPath(){
							String seq="";
							String pathToSeq;
							System.out.println("What is the path to the sequence file (in FASTA Format)?");
							pathToSeq=temp.next();
							//DO NOT SANITIZE!!!!! Is supposed to have /s or \s
							File seqFile=new File(pathToSeq);
							if(!seqFile.exists()){
								System.out.println("Invalid path to file...");
								doPath();
							}
							else{
								//read in seq function, but for now print something
								System.out.println("Got to a valid file!");
								seq=readSeqFromFasta(seqFile);
								seq=validateSeq(seq);
							}
							return(seq);
						}
					}
					sequence=new pathDoer().doPath();
					//String pathToSeq;
					//System.out.println("What is the path to the sequence file (in FASTA Format)?");
					//pathToSeq=temp.next();
					//DO NOT SANITIZE!!!!! Is supposed to have /s or \s
					//File seqFile=new File(pathToSeq);
					//if(!seqFile.exists()){
						
					//}
					//}
				}
				else if(method.equals("direct")){
					class directDoer{
						public String doDirect(){
							System.out.println("What is the sequence?");
							String seq=temp.next();
							seq=validateSeq(seq);
							if(seq.equals("")){
								print("Invalid sequence! Please use IUPAC compliant sequences only!");
								doDirect();
							}
							return seq;
						}
					}
					sequence=new directDoer().doDirect();
				}
				else{
					System.out.println("Invalid method of supplying sequence. Addition of gene" + geneName +"aborting...\n");
					return;
				}
				new File("ScrawQ_Phyloinformatics/All_Taxa/"+thisTaxa+"/Genes/"+geneName+"/").mkdir();
				BufferedWriter output;
				try {
					output= new BufferedWriter(new FileWriter("ScrawQ_Phyloinformatics/All_Taxa/"+thisTaxa+"/Genes/"+geneName+"/sequence.txt", true));
					output.append(sequence);
					output.newLine();
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		print("Gene addition sucessful!");
		
	}
	
	/*
	 * makes a new group based on user input
	 * sanitizes the input, just in case
	 */
	public static void mkGroup(){
		//connect to standard in
		Scanner temp=new Scanner(System.in);
		String thisGroup=new String();
		//get input
		System.out.println("Name of new group to add? (type 'cancel' to cancel");
		thisGroup=temp.next();
		System.out.println("adding group "+thisGroup+"...");
		//if this is not what they wanted to do, cancel
		if(thisGroup.equals("cancel")){
			return;
		}
		//sanitize the input, make a new dir for the group
		//make a file to keep track of the members of the group. 
		else{
			thisGroup=sanitizeInput(thisGroup);
			new File("ScrawQ_Phyloinformatics/Groups/"+thisGroup).mkdir();
			File groupFile=new File("ScrawQ_Phyloinformatics/Groups/"+thisGroup+"/members.txt");
			mkFile(groupFile);
			BufferedWriter output;
			try {
				output= new BufferedWriter(new FileWriter("ScrawQ_Phyloinformatics/Groups/List_of_All_Groups.txt", true));
				output.append(thisGroup);
				output.newLine();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(thisGroup+" was added sucessfully!");
		}
		
	}
	
	/*
	 * Adds a new taxa via user input
	 * sanitizes the user input, just in case
	 */
	public static void addNewTaxa(){
		Scanner temp=new Scanner(System.in);
		String thisTaxa=new String();
		System.out.println("Name of new taxa to add? (type 'cancel' to cancel");
		thisTaxa=temp.next();
		
		if(thisTaxa.equals("cancel")){
			return;
		}
		else{
			System.out.println("adding Taxa: "+thisTaxa+"...");
			thisTaxa=sanitizeInput(thisTaxa);
			new File("ScrawQ_Phyloinformatics/All_Taxa/"+thisTaxa).mkdir();
			new File("ScrawQ_Phyloinformatics/All_Taxa/"+thisTaxa+"/Genes/").mkdir();
			File taxaFile=new File("ScrawQ_Phyloinformatics/All_Taxa/"+thisTaxa+"/Genes/GeneList.txt");
			mkFile(taxaFile);
			File aliases=new File("ScrawQ_Phyloinformatics/All_Taxa/"+thisTaxa+"/nicknames.txt");
			mkFile(aliases);
			BufferedWriter output;
			try {
				output= new BufferedWriter(new FileWriter("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt", true));
				output.append(thisTaxa);
				output.newLine();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(thisTaxa+" was added sucessfully!");
		}
		
	}
//////////////////////////////////END INTERACTIVE ADDS///////////////////////////////////////////	
	
	//////////////////INTERNAL ADD FUNCTIONS//////////////////////////////////////////////////////
	/*
	 * Adds new taxa based on a string arg. Meant for internal use. 
	 */
	public static void addNewTaxaInternal(String thisTaxa){
		thisTaxa=sanitizeInput(thisTaxa);
		new File("ScrawQ_Phyloinformatics/All_Taxa/"+thisTaxa).mkdir();
		File taxaFile=new File("ScrawQ_Phyloinformatics/All_Taxa/"+thisTaxa+"/GeneList.txt");
		mkFile(taxaFile);
		File aliases=new File("ScrawQ_Phyloinformatics/All_Taxa/"+thisTaxa+"/nicknames.txt");
		mkFile(aliases);
	}
	
	/*
	 * Same as mkgroup, but meant for internal use based off a string arg
	 */
	public static void mkGroupInternal(String thisGroup){
		thisGroup=sanitizeInput(thisGroup);
		new File("ScrawQ_Phyloinformatics/Groups/"+thisGroup).mkdir();
		File groupFile=new File("ScrawQ_Phyloinformatics/Groups/"+thisGroup+"/members.txt");
		mkFile(groupFile);
		BufferedWriter output;
		try {
			output= new BufferedWriter(new FileWriter("ScrawQ_Phyloinformatics/Groups/List_of_All_Groups.txt", true));
			output.append(thisGroup);
			output.newLine();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(thisGroup+" was added sucessfully!");
	}
	
	
///////////////////////////////END INTERNAL ADDS////////////////////////////////////////////////////////////

///////////VALIDATE METHODs///////////////////////////////////////////////////////////////////////////////
	public static void validateDB(){
		validateTaxa();
		validateGroups();
	}
	public static void validateGroups(){
		
	}
	public static void validateTaxa(){
		ArrayList<String> recordedTaxa=getRecordedTaxa();
		print(recordedTaxa);
		ArrayList<String> observedTaxa=getObservedTaxa();
		print(observedTaxa);
		ArrayList<String> notInObs=new ArrayList<String>(recordedTaxa);
		notInObs.removeAll(observedTaxa);
		if(notInObs.size()>0){
			print("There is a difference between master list and observed taxa entries");
			print("Resolving difference by updating master list");
			File temp=new File("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt");
			///We are set to delete this without backing file up, since we have it in RAM already to correct if something bad happens.
			boolean completed=temp.delete();
			ArrayList<String> forAllTaxa=new ArrayList<String>(observedTaxa);
			if(completed==false){
				print("There was an error deleting abherent entries");
				print("Restoring master list to last working state...");
			}
			else{
				File ListOfAllTaxa=new File("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt");
				mkFile(ListOfAllTaxa);
				BufferedWriter output = null;
				try {
					output= new BufferedWriter(new FileWriter("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt", true));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(String i : forAllTaxa){
					try{
						output.append(i);
						output.newLine();
					}
					catch(IOException o){
						o.printStackTrace();
					}
				}
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/////End if(notInObs.size>0)
		ArrayList<String> notInRec=new ArrayList<String>(observedTaxa);
		notInRec.removeAll(recordedTaxa);
		if(notInRec.size()>0){
			File ListOfAllTaxa=new File("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt");
			BufferedWriter output = null;
			try{
				output= new BufferedWriter(new FileWriter("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt", true));
				for(String i : notInRec){
					boolean havePrinted=false;
					File temp=new File("ScrawQ_Phyloinformatics/All_Taxa/"+i+"/Genes");
					if(temp.exists()==false){
						print("Incomplete directory information found for taxa "+ i+"!");
						print("Updating directory to conform to minimal requirements.");
						havePrinted=true;
						temp.mkdir();
					}
					temp=new File("ScrawQ_Phyloinformatics/All_Taxa/"+i+"/Genes/GeneList.txt");
					if(temp.exists()==false){
						if(havePrinted==false){
							print("Incomplete directory information found for taxa "+ i+"!");
							print("Updating directory to conform to minimal requirements.");
						}
						mkFile(temp);
					}
					output.append(i);
					output.newLine();
					
				}
				output.close();
				}
			catch(IOException o){
				o.printStackTrace();
			}
		}
	}
	
	public static ArrayList<String> getRecordedTaxa(){
		ArrayList<String> taxaList=new ArrayList<String>();
		File temp=new File("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt");
		if(!temp.exists()){
			///generate a blank one on the fly!
			mkFile(temp);								
		}
		taxaList=returnFile("ScrawQ_Phyloinformatics/All_Taxa/List_of_All_Taxa.txt");
		return taxaList;
	}
	public static ArrayList<String>getObservedTaxa(){
		ArrayList<String>obsTaxaList=new ArrayList<String>();
		File temp=new File("ScrawQ_Phyloinformatics/All_Taxa/");
		File[] tempList=temp.listFiles();
		for(File i: tempList){
			String tempName=i.getName();
			if(tempName.equals("List_of_All_Taxa.txt")==false){
				obsTaxaList.add(tempName);
			}
		}
		return(obsTaxaList);
	}
	
///////////END VALIDATE METHODs//////////////////////////////////////////////////////////////////////
	
//////////INCOMPLETE METHODS////////////////////////////////////////////////////////////////////////
	
	/*
	 * INCOMPLETE METHOD
	 * will eventually force a complete re-do of all 
	 * analysis
	 */
	public static void redoAll(){
		System.out.println("Performing all analyses, regardless of state of database...");
	}
	
	
	/*
	 * 
	 * Will eventually update all the files and
	 * reperform the analysis as necessary.
	 */
	public static void updateAll(){
		System.out.println("Updating the phyloinformatics system to reflect current state of affairs...");
	}
	/*
	 * Prints just the messages relevant to group functions
	 */
	
	
//////END INCOMPLETE FUNCTIONS//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	////////Help Message Functions//////////////////////////////////////////////////////////////////////////////////////////
	public static void printGroupHelpMessage(){
		System.out.println("Here are the commands pertaining to working with groups");
		System.out.println("groupHelp: Displays this help message");
		System.out.println("mkGroup: Makes a new group");
	}
	/*
	 * Prints the purpose of all commands
	 */
	public static void printHelp(){
		System.out.println("Below are commands and their function");
		System.out.println("help : Displays this help message");
		System.out.println("quit : Exits ScrawQ. There is no prompt for confirmation.");
		System.out.println("install: Installs ScrawQ. Only Run this once unless you want a fresh install!");
		System.out.println("update: Updates the internal structures and trees if new data is present.");
		System.out.println("redo: Performs all analyses regardless of if the data has been updated or not.");
		System.out.println("mkGroup: Makes a new group");
	}
	
/////////////////////////END HELP MESSAGE FUNCTIONS////////////////////////////////////////////////////////////////////////////	
	

	///////////HELPER FUNCTIONS////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * Replaces all '/','\',' ', and '.' characters with underscores
	 * and alerts the user to this change
	 */
	public static String sanitizeInput(String token){
		String newToken;
		newToken=token.replace('\\', '_');
		newToken=newToken.replace('/', '_');
		newToken=newToken.replace(' ', '_');
		newToken=newToken.replace('.', '_');
		if(!token.equals(newToken)){
			System.out.println("Possible problem with user input...");
			System.out.println("User input "+ token+"  was changed to "+newToken);
		}
		return newToken;
	}

	public static void showFile(String fileWithPath) throws IOException{
		 BufferedReader br = new BufferedReader(new FileReader(fileWithPath));
		 String line = null;
		 while ((line = br.readLine()) != null) {
		   System.out.println(line);
		 }
		 br.close();
	}
	public static ArrayList<String> returnFile(String fileWithPath){
		ArrayList<String> contents=new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileWithPath));
			String line = null;
			while ((line = br.readLine()) != null) {
				   contents.add(line);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(contents);
		
	}
	
	/*
	 * a method that just try-catches the creation of a file.
	 * Just in case.
	 */
	public static void mkFile(File filenameANDpath){
		File newfile=filenameANDpath;
		try{
			newfile.createNewFile();
		}
		catch(IOException ioe){
			System.err.println("Error in making files\n "+ioe);
		}
	}
	public static void populateBases(){
		bases.add('A');
		bases.add('C');
		bases.add('G');
		bases.add('T');
		bases.add('a');
		bases.add('c');
		bases.add('g');
		bases.add('t');
		bases.add('U');
		bases.add('u');
		bases.add('R');
		bases.add('r');
		bases.add('Y');
		bases.add('y');
		bases.add('S');
		bases.add('s');
		bases.add('W');
		bases.add('w');
		bases.add('K');
		bases.add('k');
		bases.add('M');
		bases.add('m');
		bases.add('B');
		bases.add('b');
		bases.add('D');
		bases.add('d');
		bases.add('H');
		bases.add('h');
		bases.add('V');
		bases.add('v');
		bases.add('N');
		bases.add('n');
		bases.add('.');
		bases.add('-');
	}
	public static String readSeqFromFasta(File fileWithPath){
		String finalSeq="";
		boolean hitCarrot=false;
		try {
			BufferedReader read=new BufferedReader( new FileReader(fileWithPath));
			String line = null;
			while((line=read.readLine())!=null){
				if(line.charAt(0)=='>'){
					if(hitCarrot==false){
						hitCarrot=true;
						continue;
					}
					else{
						return(finalSeq);
					}
				}
				else{
					String seq=read.readLine();
					finalSeq+=seq;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalSeq;
	}
	public static String validateSeq(String seq){
		char[]seqChars=seq.toCharArray();
		for(char i:seqChars){
			if(!bases.contains(i)){
				return "";
			}
		}
		return seq.toUpperCase();
	}
	public static void print(Object o){
		System.out.println(o);
	}
}
