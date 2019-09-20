package commands;

import fileSystem.FileTree;
import java.util.Queue;
import java.util.LinkedList;
import fileSystem.Node;

/**
 * This History class uses a queue to implement the history command.
 *
 */
public class History extends Command {
  // Using a queue to store each command
  private Queue<String> history;

  /**
   * Constructor for History class, which initializes the history queue.
   */
  public History() {
    history = new LinkedList<String>();
  }

  /**
   * Provides a way to add String commands to the history queue.
   * 
   * @param command the String to be added to the queue.
   */
  public void addToHistory(String command) {
    // Store a command using queue's add method
    history.add(command);
  }

  /**
   * Returns the full history of commands entered by the user.
   * 
   * @return String that represents the entire history of commands
   */
  public String historyFull() {
    // Similar to printHistory method, except for no truncateValue parameter
    int historyNumber = 1;
    String historyString = "";

    for (String command : history) {
      historyString += (historyNumber + ". " + command + "\n");
      historyNumber += 1;
    }
    return historyString;
  }

  /**
   * Returns the commands in the history queue, that follow after the specified integer,
   * truncateValue.
   * 
   * @param truncateValue the integer that controls how much the formatted output is truncated by.
   * @return String representation truncated by truncateValue
   */
  public String historyTruncate(int truncateValue) {
    int historyNumber = 1;
    int size = history.size();
    if (truncateValue > size)
      return historyFull();
    String historyString = "";
    // Iterate through each command in the queue
    for (String command : history) {
      // Only print the commands if historyNumber is greater than the
      // specified truncate value
      if (historyNumber > truncateValue)
        historyString += historyNumber + ". " + command + "\n";
      historyNumber += 1;
    }
    return historyString;
  }

  /**
   * This function calls the above functions based on the parameter inputArgs. And is used in
   * Validate class.
   * 
   * @param inputArgs array of string that is entered by user, and optional parameters to write or
   *        append the output message to a file
   * @return a string representation of history of commands, or nothing if to be written to a file
   */
  public String execute(String[] inputArgs, FileTree tree) {
    String message = "";
    int inputLength = inputArgs.length;
    if (inputLength == 2 || inputLength == 4)
      message = historyTruncate(Integer.parseInt(inputArgs[1]));
    else if (inputLength == 1 || inputLength == 3)
      message = historyFull();
    else
      return "Too many arguments\n";
    if (inputLength == 3 || inputLength == 4) {
      String[] splitPath = inputArgs[inputLength - 1].split("/");
      String outFile = splitPath[splitPath.length - 1];
      // check if OUTFILE is an illegal file name
      if (!outFile.matches("^[a-zA-Z0-9]*$"))
        return ("Invalid File: " + outFile + " contains illegal characters.\n");
      if (inputArgs[inputLength - 2].equals(">")) {
        // now we must overwrite OUTFILE with message
        if (splitPath.length == 1 && splitPath[0].isEmpty()) {
          // this means the outFile is to be in root
          if (tree.containsFile(outFile, tree.getRoot()))
            tree.getRoot().hasFile(outFile).setContent(message);
          else
            tree.getRoot().addFile(outFile, message);
        } else {
          if (tree.containsFile(outFile,
              tree.getSecondLastNodeFromPath(inputArgs[inputLength - 1], tree.getCwd())))
            tree.getNodeFromPath(inputArgs[inputLength - 1], tree.getCwd()).setContent(message);
          else
            tree.getSecondLastNodeFromPath(inputArgs[inputLength - 1], tree.getCwd())
                .addFile(outFile, message);
        }
      } else if (inputArgs[inputLength - 2].equals(">>")) {
        // now we must append OUTFILE with message
        if (splitPath.length == 1 && splitPath[0].isEmpty()) {
          // this means the outFile is to be in root
          if (tree.containsFile(outFile, tree.getRoot()))
            tree.getRoot().hasFile(outFile).appendContent(message);
          else
            tree.getRoot().addFile(outFile, message);
        } else {
          if (tree.containsFile(outFile,
              tree.getSecondLastNodeFromPath(inputArgs[inputLength - 1], tree.getCwd())))
            tree.getNodeFromPath(inputArgs[inputLength - 1], tree.getCwd()).appendContent(message);
          else
            tree.getSecondLastNodeFromPath(inputArgs[inputLength - 1], tree.getCwd())
                .addFile(outFile, message);
        }
      } else
        return "Invalid arguments, must specify output file writing type\n";
      message = "";
    }
    return message;
  }
}
