package commands;

import fileSystem.Node;
import fileSystem.FileTree;

/**
 * This class is responsible for copying items from one directory to another.
 */
public class Cp extends Command {

  /**
   * Makes a copy of an item in a target destination. Recursively copies contents as well if item is
   * a directory itself.
   * 
   * @param target the item to be copied
   * @param destination the destination directory to be copied to
   */
  private static void copyContents(Node target, Node destination) {

    target.setPath(destination.getPath());
    // if target is a file, just copy
    if (target.isFile())
      destination.addFile(target.toString(), target.content());
    // if it's a directory, recurse thru
    else {
      destination.addDirectory(target.toString());
      for (Node n : target.getChilds()) copyContents(n, destination.hasDir(target.toString()));
    }
  }

  /**
   * Copies an item to a target destination, including sub-items if target is a directory.
   * 
   * @param inputArgs Name of item to be copied, name of target destination,
   * @param tree the file system
   * @return output message indicating successful execution or errors
   */
  public static String execute(String[] inputArgs, FileTree tree) {

    if (inputArgs.length == 3) {
      Node target = tree.getNodeFromPath(inputArgs[1], tree.getCwd());
      Node destination = tree.getNodeFromPath(inputArgs[2], tree.getCwd());
      // check validity of target and destination
      if (target == tree.getCwd() || destination == tree.getCwd())
        return "Item to be copied/destination directory does not exist, or you cannot be in"
            + "target item/destination directory";
      else {
        copyContents(target, destination);
        return "Item successfully copied\n";
      }
    } else if (inputArgs.length < 3)
      return "Not enough arguments\n";
    else
      return "Too many arguments\n";
  }
}
