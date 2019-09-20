package commands;

import fileSystem.FileTree;

/**
 * This Mkdir class allows the user to create unique directories in the file system.
 */
public class Mkdir extends Command {

  /**
   * This function processes inputArgs to obtain the path and directory name, which the user wants
   * to create, and uses methods from file tree to do so.
   * 
   * @param inputArgs The path specified by the user which contains the name of the directory that
   *        they want to create, and optional parameters to write or append the output message to a
   *        file
   * @param tree The file system from file tree
   * @return feedback to tell the user if the command is successful
   */
  public static String execute(String[] inputArgs, FileTree tree) {
    int index = 0;
    if (inputArgs.length >= 2) {
      for (int i = 1; i < inputArgs.length; i++) {
        String[] list = inputArgs[i].split("/");
        if (!list[list.length - 1].matches("^[a-zA-Z0-9]*$")) {
          System.out.println(
              "Invalid Directory: " + list[list.length - 1] + " contains illegal characters.");
          continue;
        }
        // If directory does not already exist in cwd, then it is added
        if (list.length > 1) {
          for (int x = 0; x < inputArgs[i].length(); x++)
            if (inputArgs[i].substring(x, x + 1).equals("/"))
              index = x;
          if (!tree.containsDir(list[list.length - 1],
              tree.getNodeFromPath(inputArgs[1].substring(0, index), tree.getRoot()))) {
            tree.addDirectory(tree.getNodeFromPath(inputArgs[1].substring(0, index), tree.getCwd()),
                list[list.length - 1]);
            System.out.println("Added Directory: " + inputArgs[i]);
          }
        } else if (!tree.containsDir(inputArgs[i], tree.getCwd())) {
          if (!inputArgs[i].matches("^[a-zA-Z0-9]*$")) {
            System.out
                .println("Invalid Directory: " + inputArgs[i] + " contains illegal characters.");
            continue;
          }
          tree.addDirectory(tree.getCwd(), inputArgs[i]);
          System.out.println("Added Directory: " + inputArgs[i]);
        }
      }
      return "";
    } else
      return "Not enough arguments\n";
  }
}
