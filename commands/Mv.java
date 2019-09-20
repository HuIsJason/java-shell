package commands;

import fileSystem.FileTree;
import fileSystem.Node;

/**
 * This Echo class implements a way to overwrite or append to a existing or non existing file in the
 * file system.
 */
public class Mv extends Command {

  /**
   * Moves an item from one directory to another.
   * 
   * @param inputArgs Contains the name of item to be moved, the target destination
   * @param tree The file system
   * @return Message that reports successful execution, or errors in input
   */
  public static String execute(String[] inputArgs, FileTree tree) {
    if (inputArgs.length < 3) return "Must specify paths\n";
    else if (inputArgs.length == 3) {
      Node oldItem = tree.getNodeFromPath(inputArgs[1], tree.getCwd());
      Node newDir;
      if (inputArgs[2].equals("/")) newDir = tree.getRoot();
      else newDir = tree.getNodeFromPath(inputArgs[2], tree.getCwd());

      // check if target does not exist; if not, return error message
      if (oldItem.equals(tree.getCwd()) || newDir.equals(tree.getCwd()))
        return "Target item/destination directory does not exist, or you are currently "
            + "in target/destination to be modified\n";
      // add target to destination, and remove target from old location
      else {
        newDir.addChilds(oldItem);
        oldItem.setPath(newDir.getPath());
        Node oldParent = tree.getSecondLastNodeFromPath(inputArgs[1], tree.getCwd());
        oldParent.removeChild(oldItem);
        return "Successfully moved item\n";
      }
    }
    else return "Too many arguments\n";
  }
}
