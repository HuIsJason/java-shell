package commands;

import java.util.Stack;
import fileSystem.FileTree;
import fileSystem.Node;

/**
 * This Pushd class uses a stack data structure to save the current working directory. And also
 * provides functionality to change the current working directory to a user specified directory
 *
 */
public class Pushd extends Command {

  /**
   * The stack of nodes.
   */
  public static Stack<Node> dir = new Stack<Node>();

  /**
   * The node that represents the previous node in the stack.
   */
  private static Node previous;

  /**
   * This method which is called from Validate, uses file tree methods to obtain the node of the
   * current working directory and pushes it onto the stack
   * 
   * @param tree The file system from file tree
   * @param path User specified path
   * @return The Node of the new current working directory
   */
  public static String execute(String[] inputArgs, FileTree tree) {
    if (inputArgs.length <= 1)
      return "Must specify arguments\n";
    else {
      String result = "";
      result = Cd.execute(inputArgs, tree);
      if (result.equals(""))
        dir.push(previous);
      return result;
    }
  }

  /**
   * Sets the previous node in stack.
   * 
   * @param prev the node to be set to previous
   */
  public static void setPrev(Node prev) {
    previous = prev;
  }
}
