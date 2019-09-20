package commands;

import fileSystem.FileTree;
import fileSystem.Node;

/**
 * This Pwd class provides functionality to display path to the current working directory.
 *
 */
public class Pwd extends Command {

  /**
   * This method uses functions from Node class to turn the string representation of the path to the
   * current working directory
   * 
   * @param inputArgs The name of the command itself, pwd, and optional parameters to write or
   *        append the output message to a file
   * @return String representation of the path to current directory, or nothing if to be written to
   *         a file
   */
  public static String execute(String[] inputArgs, FileTree tree) {

    String message = "";
    if (inputArgs.length >= 1) {
      message = tree.getCwd().getPath() + "\n";
      if (inputArgs.length == 3) {
        String[] splitPath = inputArgs[2].split("/");
        String outFile = splitPath[splitPath.length - 1];
        // check if OUTFILE is an illegal file name
        if (!outFile.matches("^[a-zA-Z0-9]*$"))
          return ("Invalid File: " + outFile + " contains illegal characters.\n");
        if (inputArgs[1].equals(">")) {
          // now we must overwrite OUTFILE with message
          if (splitPath.length == 1 && splitPath[0].isEmpty()) {
            // this means the outFile is to be in root
            if (tree.containsFile(outFile, tree.getRoot()))
              tree.getRoot().hasFile(outFile).setContent(message);
            else
              tree.getRoot().addFile(outFile, message);
          } else {
            if (tree.containsFile(outFile,
                tree.getSecondLastNodeFromPath(inputArgs[2], tree.getCwd())))
              tree.getNodeFromPath(inputArgs[2], tree.getCwd()).setContent(message);
            else
              tree.getSecondLastNodeFromPath(inputArgs[2], tree.getCwd()).addFile(outFile, message);
          }
        } else if (inputArgs[1].equals(">>")) {
          // now we must append OUTFILE with message
          if (splitPath.length == 1 && splitPath[0].isEmpty()) {
            // this means the outFile is to be in root
            if (tree.containsFile(outFile, tree.getRoot()))
              tree.getRoot().hasFile(outFile).appendContent(message);
            else
              tree.getRoot().addFile(outFile, message);
          } else {
            if (tree.containsFile(outFile,
                tree.getSecondLastNodeFromPath(inputArgs[2], tree.getCwd())))
              tree.getNodeFromPath(inputArgs[2], tree.getCwd()).appendContent(message);
            else
              tree.getSecondLastNodeFromPath(inputArgs[2], tree.getCwd()).addFile(outFile, message);
          }
        } else
          return "Invalid arguments, must specify output file writing type\n";
        message = "";
      } else if (inputArgs.length > 3)
        return "Too many arguments\n";
    }
    return message;
  }
}
