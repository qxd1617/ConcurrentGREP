import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;

/**
 *  Thread class that will handle GREP for a specific text file for the given
 *  REGEX value.
 */
public class Task implements Callable<Found>
{
    //Name of the text file being GREPPED by this thread
    private String name;

    //Text within the .txt file meant to be GREPPED
    private List<String> inputText;

    //REGEX to GREP for.
    private Pattern regex;

    //Create a task given these parameters.
    public Task(String name, List<String> text, String regex)
    {
        this.name = name;
        this.inputText = text;
        this.regex = Pattern.compile(regex);
    }

    @Override
    public Found call() throws Exception
    {
        // Create a found(result) object for this specific thread/task/text file.
        Found foundResults = new Found(name);

        // Use the regex class to match lines with the regex provided.
        for (int i = 0; i < inputText.size(); i++) {
            Matcher match = regex.matcher(inputText.get(i));
            if (match.find()) {
                //If matched, add this to the foundResults found object to be printed later.
                foundResults.recordMatch(i, inputText.get(i));
            }

        }

        return foundResults;
    }
}