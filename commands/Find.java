package commands;

import java.util.List;
import fileSystem.FileTree;
import fileSystem.Node;

/**
 * This class is responsible for finding select items within select directories.
 */
public class Find extends Command {

  /**
   * Finds files/directories with a given name within a given directory, and recursively finds more
   * in sub-directories.
   * 
   * @param filePath the path of the directory to search in
   * @param type the type of the file (f for file, d for directory)
   * @param name the name of the file/directory
   * @param tree the file system
   * @return the String of a list of paths of files/directories with the matching name
   */
  private static String find(String filePath, String type, String name, FileTree tree) {
    String message = "";
    List<Node> childrenList;
    if (filePath.equals("/")) childrenList = tree.getRoot().getChilds();
    else childrenList = tree.getNodeFromPath(filePath, tree.getCwd()).getChilds();
    for (Node n : childrenList) {
      if (n.toString().equals(name)
          && ((n.isFile() && type.equals("f")) || (!n.isFile() && type.equals("d"))))
        message += n.getPath() + "\n";
      if (!n.isFile())
        message += find(n.getPath(), type, name, tree);
    }
    return message;
  }

  /**
   * Finds items with a given name, and within given directory/directories recursively.
   * 
   * @param inputArgs the paths of directories to be searched, the type of item, and the name of the
   *        item
   * @param tree the file system
   * @return the String of a list of paths of files/directories with the matching name, or an error
   *         message
   */
  public static String execute(String[] inputArgs, FileTree tree) {
    String message = "";
    int inputLength = inputArgs.length;
    if (inputLength < 6)
      return "Not enough arguments\n";
    else if (!inputArgs[inputLength - 4].equals("-type")
        || !inputArgs[inputLength - 2].equals("-name"))
      return "Invalid arguments";
    else if (inputLength >= 6) {
      String type = inputArgs[inputLength - 3];
      String name = inputArgs[inputLength - 1];
      int nameLength = name.length();
      if (name.charAt(0) != '"' && name.charAt(nameLength - 1) != '"')
        return "Name of file must be in quotes\n";
      for (int i = 1; i < inputLength - 4; i++)
        message += find(inputArgs[i], type, name.substring(1, nameLength - 1), tree);
    }
    return message + "\n";
  }
}
