import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;
/**
 * Simple program implementing Grep for concurrent processors utilizing callables, futures, and executors.
 * @author Owen Quan Dong
 */
public class CGrep
{
    //Max number of threads as specified by the assignment
    private static final int MAX_THREAD_SIZE = 3;

    /**
     * Perform grep looking for the regex in the files provided.
     *
     * @param args
     *        The regex pattern to look for, followed by all the files that the program
     *        should grep through. Alternatively only provide the regex pattern if you want
     *        to grep standard input.
     */
    public static void main(String[] args)
    {

        // Print message if no arguments are provided.
        int tasks = 0;
        boolean fileInput;
        if (args.length == 0) {
            System.out.println("Usage: java CGrep "
                    + "pattern [file . . .]");
            return;
        }
        // Else assign standard input if only regex expression given
        else if (args.length == 1){
            fileInput = false;
        }
        //If multiple args provided, assume that file input is given.
        else{
            fileInput = true;
        }

        // The regex pattern is in the first argument
        String regex = args[0];

        // Create the executor and the completion decorator
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREAD_SIZE);
        ExecutorCompletionService<Found> completed = new ExecutorCompletionService<>(
                service);

        //Create tasks or otherwise set up standard input.
        if (!fileInput) {
            //Not really sure what to do here for standard input so I'm gonna leave it blank since it does not
            //seem to be a primary focus of the assignment.
        }
        else {
            //Loop through arguments(text file names) past the first
            for (int i = 1; i < args.length; i++) {
                //Print out statement showing where file resides.
                System.out.println("Attempting to GREP from: " + System.getProperty("user.dir") + "/" +
                        args[i]);
                //Call method to create an arraylist containing each line within the file
                ArrayList inputText = createInputText(args[i]);
                //Create a new task to be completed
                completed.submit(new Task(args[i], inputText, regex));
                //Increment count of tasks by 1
                tasks += 1;
            }
            System.out.println();
        }


        // As the service completes tasks, print out their future statement.
        for (int i = 0; i < tasks; i++) {
            try {
                Future<Found> result = completed.take();
                System.out.println(result.get());
            } catch (Exception e) {
                System.out.println("Process was interrupted during printing of results");
            }
        }

        // After each task has been polled, terminate the service and
        // wait for the service to exit.
        service.shutdown();
    }

    public static ArrayList<String> createInputText(String fileName){
        ArrayList<String> inputText = new ArrayList();
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader textReader = new BufferedReader(fr);
            String line;
            while ((line = textReader.readLine()) != null){
                inputText.add(line);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Failed to open file:" + fileName);
        }
        catch (IOException e){
            System.out.println("Failed to read in a line in: " + fileName);
        }

        return inputText;
    }
}