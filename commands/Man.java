package commands;

import fileSystem.FileTree;
import fileSystem.Node;
import java.util.HashMap;
import java.util.Map;

/**
 * This Man class uses a map to store commands as keys, and their descriptions as values.
 */
public class Man extends Command {

  /**
   * The map to store the commands as keys, and their descriptions as values.
   */
  private static Map<String, String> commandDict = new HashMap<String, String>();

  /**
   * Initializes all the commands with their respective descriptions.
   */
  private static void setCommands() {
    commandDict.put("exit", "Quit the program.");
    commandDict.put("mkdir", "Create directories, each of which may be relative to the current "
        + "directory or may be a full path.");
    commandDict.put("cd",
        "Change directory to DIR, which may be relative to the current "
            + "directory or may be a full path. As with Unix, "
            + ".. means a parent directory and a .means the current directory. "
            + "The directory must be /, the forward slash. The foot of the file system is a "
            + "single slash: /.");
    commandDict.put("ls",
        "If no paths are given, print the contents (file or directory) of "
            + "the current directory, with a new line following each of the "
            + "content (file or directory).\n " + "\n" + "Otherwise, for each path p, "
            + "the order listed:\n" + "\n" + "\t - If p specifies a file, print p\n"
            + "\t - If p specifies a directory, print p, a colon, then the contents "
            + "of that directory, then an extra new line.\n\t - If p does not exist, "
            + "print a suitable message.");
    commandDict.put("pwd", "Print the current working directory (including the whole path).");
    commandDict.put("pushd", "Saves the current working directory by pushing onto directory "
        + "stack and then changes the new current working directory to DIR. " + "The push must be "
        + "consistent as per the LIFO behavior of a stack. The pushd command " + "saves the old "
        + "current working directory in directory stack so that it can be " + "returned to at any "
        + "time (via the popd command).  The size of the directory stack is " + "dynamic "
        + "and dependent on the pushd and the popd commands.");
    commandDict.put("popd",
        "Remove the top entry from the directory stack, and cd into it. "
            + "The removal must be consistent as per the LIFO behavior of a stack."
            + " The popd command" + " removes the top most directory from "
            + "the directory stack and makes it the " + "current working directory. If there is "
            + "no directory onto the stack, then " + "give appropriate error message.");
    commandDict.put("history",
        "This command will print out recent commands, " + "one command per line. i.e.\n\n"
            + "\t 1. cd ..\n" + "\t 2. mkdir textFolder\n" + "\t 3. echo 'Hello World'\n"
            + "\t 4. fsjhdfks\n" + "\t 5. history\n" + "\n"
            + "The above output from history has two columns. The first column is "
            + "numbered such that the line with the highest number is"
            + "the most recent command. The most recent " + "command is history.  The second "
            + "column contains the actual command. Note: Your output should also "
            + "contain as output any syntactical errors typed by the user as seen " + "on line 4.\n"
            + "\n" + "We can truncate the output by specifying a "
            + "number (>=0) after the command." + " For instance, if we want to only see the last 3"
            + "commands typed, we can type the following on the " + "command line:\n" + "\n"
            + "\t history 3\n" + "\n" + "And the output will be as follows:\n" + "\n"
            + "\t 4. fsjhdfks\n" + "\t 5. history\n" + "\t 6. history 3");
    commandDict.put("cat",
        "Display the contents of FILE1 and other files (i.e. File2 ....) "
            + "concatenated in the shell. You may want to use three "
            + "line breaks to separate the contents of one file from the other file.");
    commandDict.put("echo",
        "Writes out to a file in one of two ways:\n" + "\n"
            + "\t - If called with '>' separating a string and a file, put the string "
            + "into file. This creates a new file"
            + "if specified does not exist already. If the file is not specified, print "
            + "the string onto the shell. In any case," + "the only thing in the file "
            + "should just be the string.\n"
            + "\t - If called with '>>' separating a string and a file, echo behaves "
            + "the same way excepts appends the string into the file, not overwrite.");
    commandDict.put("man", "Print documentation for CMD");
  }

  /**
   * Returns the value of the specified key.
   * 
   * @param givenCommand The command specified by user, and optional parameters to write or append
   *        the output message to a file
   * @return The description of the command specified by user, or nothing if to be to be written to
   *         a file
   */
  public static String execute(String[] inputArgs, FileTree tree) {
    Man.setCommands();
    String message = "";
    if (inputArgs.length >= 2) {
      if (commandDict.containsKey(inputArgs[1]))
        message = commandDict.get(inputArgs[1]) + "\n";
      else
        return "Command not supported by man\n";
      if (inputArgs.length == 4) {
        String[] splitPath = inputArgs[3].split("/");
        String outFile = splitPath[splitPath.length - 1];
        // check if OUTFILE is an illegal file name
        if (!outFile.matches("^[a-zA-Z0-9]*$"))
          return ("Invalid File: " + outFile + " contains illegal characters.\n");
        if (inputArgs[2].equals(">")) {
          // now we must overwrite OUTFILE with message
          if (splitPath.length == 1 && splitPath[0].isEmpty()) {
            // this means the outFile is to be in root
            if (tree.containsFile(outFile, tree.getRoot()))
              tree.getRoot().hasFile(outFile).setContent(message);
            else
              tree.getRoot().addFile(outFile, message);
          } else {
            if (tree.containsFile(outFile,
                tree.getSecondLastNodeFromPath(inputArgs[3], tree.getCwd())))
              tree.getNodeFromPath(inputArgs[3], tree.getCwd()).setContent(message);
            else
              tree.getSecondLastNodeFromPath(inputArgs[3], tree.getCwd()).addFile(outFile, message);
          }
        } else if (inputArgs[2].equals(">>")) {
          // now we must append OUTFILE with message
          if (splitPath.length == 1 && splitPath[0].isEmpty()) {
            // this means the outFile is to be in root
            if (tree.containsFile(outFile, tree.getRoot()))
              tree.getRoot().hasFile(outFile).appendContent(message);
            else
              tree.getRoot().addFile(outFile, message);
          } else {
            if (tree.containsFile(outFile,
                tree.getSecondLastNodeFromPath(inputArgs[3], tree.getCwd())))
              tree.getNodeFromPath(inputArgs[3], tree.getCwd()).appendContent(message);
            else
              tree.getSecondLastNodeFromPath(inputArgs[3], tree.getCwd()).addFile(outFile, message);
          }
        } else
          return "Invalid arguments, must specify output file writing type\n";
        message = "";
      } else if (inputArgs.length > 4)
        return "Too many arguments\n";
    } else
      return "Must specify command\n";
    return message;
  }
}
