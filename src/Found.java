import java.util.ArrayList;

/**
 * Helper class, wrapped by the Futures class. Contains the name of the file that was read,
 * the results of the GREP in list form.
 */
public class Found
{
    //Name of the file searched.
    private String fileSearched;

    //An arraylist of matched strings with their line number in front.
    private ArrayList<String> results;

    //Constructor to create a found file, created for every txt file provided.
    public Found(String fileSearched)
    {
        //Initialize variables
        this.fileSearched = fileSearched;
        results = new ArrayList<>();
    }

    //Quick method to add a result to the arraylist.
    public void recordMatch(Integer lineNumber, String line)
    {
        results.add(lineNumber + " " + line);
    }

    //Method override to create a long string to print out all results consecutively
    //without having to worry about print statements being cut off.
    @Override
    public String toString()
    {
        String printOut = "This section GREPPED from: " + fileSearched + "\n";
        for (String line : results){
            printOut += line + "\n";
        }
        return printOut;
    }
}