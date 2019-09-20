package commands;

import fileSystem.FileTree;
import fileSystem.Node;

/**
 * This class carries the responsibility of printing out all the contents of a file system in a
 * tree-like structure.
 */
public class Tree extends Command {

  /**
   * Returns a String, s, multiplied by itself n number of times.
   * 
   * @param s the String to be multiplied
   * @param n the number of time s is multiplied
   * @return the multiplied String
   */
  private static String stringMultiply(String s, int n) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++)
      sb.append(s);
    return sb.toString();
  }

  /**
   * Returns the file tree's contents in a tree structure.
   * 
   * @param target the node to be who's contents will be printed out
   * @param depth the depth of the recursion calls
   * @return a String of the tree structure
   */
  private static String printTree(Node target, int depth) {
    String message = "";

    // if current node is a directory
    if (!target.isFile()) {
      if (depth == 1)
        message += "\\" + "\n";
      else
        message += target.toString() + "\n";
      for (Node n : target.getChilds())
        message += stringMultiply("  ", depth) + printTree(n, depth + 1);
    }
    // if current node is a file
    else
      message += target.toString() + "\n";
    return message;
  }

  /**
   * Returns the root directory's contents in a tree structure.
   * 
   * @param inputArgs Optional parameters to write or append the output message to a file
   * @param tree the file system
   * @return a String of the tree structure, or nothing if to be written to a file
   */
  public static String execute(String[] inputArgs, FileTree tree) {
    String message = "";
    int inputLength = inputArgs.length;
    if (inputLength >= 1)
      message = printTree(tree.getRoot(), 1);
    if (inputLength > 3)
      return "Too many arguments\n";
    else if (inputLength == 2)
      return "Invalid arguments\n";
    else if (inputLength == 3) {
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
              tree.getSecondLastNodeFromPath(inputArgs[2], tree.getCwd())))
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
              tree.getSecondLastNodeFromPath(inputArgs[2], tree.getCwd())))
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
