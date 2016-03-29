import java.util.ArrayList;


public interface geneTreeMethod {
	//format seqs should put the seqs in the database into 
	// the format needed to run the genetree method and write 
	// them to some location tbd. It should return the full filenames
	// of every entry. If a program requires an alignment, then
	// an alignmentMethod interface should be instanciated locally
	public ArrayList<String> formatSeqs(ArrayList<String> seqNames, ArrayList<String> seqs);
	//should be last method called which will clear the 
	//files from memory
	public void removeFormattedSeqs();
	
	//should get the command line parameters in a way that allows
	//it to be tacked on to the end of the program call
	public String getParams(ArrayList<String> params);
	
	//should return the invokation of the program as a string
	//to be called from the system. Will not be OS independent
	//and will likely include calls to get params and format Seqs
	public String invokation();
	
	//returns a string designating the path to write files out, if
	//necessary. may be used in invokation.
	public String outDir();
	
	
}
