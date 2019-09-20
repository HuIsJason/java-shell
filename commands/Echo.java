package commands;

import fileSystem.FileTree;
import fileSystem.Node;

/**
 * This Echo class implements a way to overwrite or append to a existing or non existing file in the
 * file system.
 *
 */
public class Echo extends Command {

  /**
   * This function checks the inputArgs to check whether to call the overwrite or append function in
   * the Echo class
   * 
   * @param inputArgs Contains the string to be added to file, and the path to the file
   * @param tree The file system from file tree
   * @return Feedback to the user if echo was successful or missing arguments
   */
  public static String execute(String userInput, FileTree tree) {
    userInput = userInput.trim();
    if (userInput.indexOf('"') == -1) {
      return "String must be in double quotes\n";
    }
    String command = userInput.substring(0, userInput.indexOf(" "));
    String text = userInput.substring(userInput.indexOf('"'),
        userInput.indexOf('"', userInput.indexOf('"') + 1) + 1);

    if (userInput.charAt(userInput.length() - 1) == '"') {
      return text.substring(1, text.length() - 1) + "\n";
    }

    String arrow = userInput.substring(userInput.lastIndexOf('"') + 2, userInput.lastIndexOf(" "));
    String path = userInput.substring(userInput.lastIndexOf(" ") + 1, userInput.length());
    String[] allArgs = new String[4];
    allArgs[0] = command;
    allArgs[1] = text;
    allArgs[2] = arrow;
    allArgs[3] = path;

    if (allArgs[2].equals("") && allArgs[3].equals("")) {
      return allArgs[1];
    }

    if (!allArgs[0].equals("") && !allArgs[1].equals("") && !allArgs[2].equals("")
        && !allArgs[3].equals("")) {
      if (allArgs[2].equals(">")) {
        fileOverwrite(allArgs[1].substring(1, allArgs[1].length() - 1), allArgs[3], tree);
        return "File successfully overwritten\n";
      } else if (allArgs[2].equals(">>")) {
        fileAppend(allArgs[1].substring(1, allArgs[1].length() - 1), allArgs[3], tree);
        return "File successfully appended\n";
      }
    } else {
      return "Missing/Invalid arguments\n";
    }
    return "";

  }

  /**
   * This helper function is called to overwrite the contents of a file, with the String string.
   * 
   * @param string The string to overwrite the contents of a file
   * @param path The string path to the file
   * @param cwd The current working directory
   * @param tree The file system from file tree
   */
  private static void fileOverwrite(String string, String path, FileTree tree) {
    Node file = null;
    String[] filesInPath = path.split("/");
    String fileName = filesInPath[filesInPath.length - 1];

    if (filesInPath.length == 1) {
      file = tree.getCwd();
    } else if (filesInPath.length >= 2) {
      file = tree.getSecondLastNodeFromPath(path, tree.getCwd());
    } else {
      System.out.println("Require a path");
    }

    Node fileOW = tree.getCwd().hasFile(fileName);

    if (fileOW != null) {
      fileOW.setContent(string);
    } else if (fileOW == null) {
      file.addFile(fileName, string);
    }
  }

  /**
   * This help function is called to append a string to the contents of a file, with the parameter
   * string.
   * 
   * @param string The string to be appended to the contents of a file
   * @param path The path to the file
   * @param cwd The current working directory
   * @param root
   */
  private static void fileAppend(String string, String path, FileTree tree) {
    Node file = null;
    String[] filesInPath = path.split("/");
    String fileName = filesInPath[filesInPath.length - 1];

    if (filesInPath.length == 1) {
      file = tree.getCwd();
    } else if (filesInPath.length >= 2) {
      file = tree.getCwd();
    } else {
      System.out.println("Require a path");
    }

    Node fileAppend = tree.getCwd().hasFile(fileName);

    if (fileAppend != null) {
      fileAppend.appendContent(string);
    } else if (fileAppend == null) {
      file.addFile(fileName, string);
    }
  }
}
