import java.util.ArrayList;


public interface alignmentMethod {
	//should return a list of any size of filenames to be used in the alignment
	//if it only needs one big file, for instance, the length will be
	//size 1.
	public ArrayList<String> getFilesToAlign();
	
	//returns params as they would be tacked onto the invokation
	// of the program as a single string
	public String getAlignParams(ArrayList<String>params);
	
	//invokes the alignment command and returns the location of
	//the resulting aligned file. 
	public String align(ArrayList<String>info);
	
}
