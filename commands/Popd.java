package commands;

import fileSystem.FileTree;
import fileSystem.Node;

/**
 * This Popd class does some processes to the stack in Pushd, and retains the LIFO behavior of the
 * stack.
 */
public class Popd extends Command {

  /**
   * 
   */
  private static String m = "";

  /**
   * The node that represents the top-most node in the stack.
   */
  private static Node top;

  /**
   * This function pops the top element in the stack of Pushd.
   * 
   * @param tree The file system
   * @return The top element of the stack in Pushd.
   */
  public static String execute(FileTree tree) {
    if (Pushd.dir.empty()) {
      m = "Stack is empty\n";
      return m;
    }
    top = Pushd.dir.pop();
    m = "Directory changed to: " + top.getPath() + "\n";
    tree.setCwd(top);
    return m;
  }
}
