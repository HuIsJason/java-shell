package commands;

import fileSystem.FileTree;
import fileSystem.Node;

/**
 * This Ls class provides functionality to display files in a given directory
 *
 */
public class Ls extends Command {

  /**
   * This function uses methods in FileTree to display all the files in a give directory, which is
   * specified by the user in inputArgs.
   * 
   * @param inputArgs Contains the path to the directory which will be used to display its contents,
   *        and optional parameters to write or append the output message to a file
   * @param tree The file system from FileTree
   * @return The contents of the directory
   */

  private static String recursiveLs(Node target, FileTree tree) {
    String message = "";

    // if current node is a directory
    if (!target.isFile()) {
      for (Node n : target.getChilds())
        message = message.concat(target + "/:\n" + recursiveLs(n, tree) + "\n\n");
    }
    // if current node is a file
    else {
      message += target.toString() + "\n";
    }
    return message;

  }

  public static String execute(String[] inputArgs, FileTree tree) {
    // If only "ls" or "ls -R" as input, prints everything in cwd
    int loopIndex = 1;
    if (inputArgs[1].equals("-R"))
      loopIndex = 2;
    if (inputArgs.length == 1)
      return tree.printFiles(tree.getCwd()) + "\n";
    else if (inputArgs.length == 2 && inputArgs[1].equals("-R"))
      return recursiveLs(tree.getCwd(), tree) + "\n";
    // If a path is given, prints everything from that path (inputArgs[1])
    else {
      String content = "";
      for (int i = loopIndex; i < inputArgs.length; i++) {
        if (inputArgs[i].equals("/")) {
          if (inputArgs[1].equals("-R")) {
            // Recursive case
            content = content.concat(recursiveLs(tree.getRoot(), tree));
          } else {
            content = content.concat("/:\n" + tree.printFiles(tree.getRoot()) + "\n\n");
          }
          continue;
        }
        String[] list = inputArgs[i].split("/");
        if (!(list[list.length - 1].matches("^[a-zA-Z0-9]*$") || !inputArgs[i].equals("-R"))) {
          System.out.println(
              "Invalid Directory: " + list[list.length - 1] + " contains illegal characters.");
          continue;
        }
        // If directory exists in cwd, then it is printed
        if (list.length > 1) {
          if (tree.getNodeFromPath(inputArgs[i], tree.getCwd()) != null) {
            if (tree.getNodeFromPath(inputArgs[i], tree.getRoot()).isFile())
              content = content.concat(list[list.length - 1] + "\n");
            else {
              // Directory
              if (inputArgs[1].equals("-R")) {
                // If recursive
                content = content.concat(list[list.length - 1] + ":\n"
                    + recursiveLs(tree.getNodeFromPath(inputArgs[i], tree.getRoot()), tree)
                    + "\n\n");
              } else {
                content = content.concat(list[list.length - 1] + ":\n"
                    + tree.printFiles(tree.getNodeFromPath(inputArgs[i], tree.getRoot())) + "\n\n");
              }
            }
          }
        } else {
          if (!inputArgs[i].matches("^[a-zA-Z0-9]*$")) {
            System.out
                .println("Invalid Directory: " + inputArgs[i] + " contains illegal characters.");
            continue;
          }
        }
      }
      return content;
    }
  }

}
