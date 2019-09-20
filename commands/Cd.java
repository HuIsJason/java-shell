package commands;

import fileSystem.FileTree;

/**
 * The Cd class implements functionality to change directories.
 */
public class Cd extends Command {

  /**
   * This function uses fileTree function to change directory, using inputArgs as the file to be the
   * new current working directory.
   * 
   * @param inputArgs Full path, or relative path entered by the user, and optional parameters to
   *        write or append the output message to a file
   * @param tree The file-system from file tree
   * @return The node of the file that the user wants to go into
   */
  public static String execute(String[] inputArgs, FileTree tree) {
    // Returns new directory node if it exists, otherwise returns current
    // working directory
    if (inputArgs.length <= 1) {
      return "Must specify directory\n";
    } else if (tree.getNodeFromPath(inputArgs[1], tree.getCwd()) == tree.getCwd()
        && !inputArgs[1].equals(".")) {
      return "Invalid directory\n";
    }
    // else if (tree.getNodeFromPath(inputArgs[1], tree.getCwd()) != null) {
    else {
      Pushd.setPrev(tree.getCwd());
      tree.setCwd(tree.getNodeFromPath(inputArgs[1], tree.getCwd()));
      return "";
    }
  }
}
