package fileSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Node class defines the node which can represent either a file or a directory to be used in
 * the file system.
 */
public class Node {

  /**
   * Boolean value that is true if the node is a file node or false if it is a directory node.
   */
  private boolean isFile;

  /**
   * The list of nodes that represents the children nodes.
   */
  private List<Node> childs;

  /**
   * The content of a file node.
   */
  private String content;

  /**
   * The name of the file/directory.
   */
  private String name;

  /**
   * The full path of the node starting from the root directory.
   */
  private String incrementalPath;

  /**
   * The constructor for Node.
   * 
   * @param nodeName the name of the node
   * @param incrementalPath the full path of the node's location
   * @param file a boolean value, true if it is a file type or false if it is a directory type
   */
  public Node(String nodeName, String incrementalPath, boolean file) {
    childs = new ArrayList<Node>();
    isFile = file;
    name = nodeName;
    content = "";
    this.incrementalPath = incrementalPath;
  }

  /**
   * Sets the path of a node.
   * 
   * @param newPath the new path
   */
  public void setPath(String newPath) {
    if (newPath.equals("/"))
      this.incrementalPath = "/" + this.name;
    else
      this.incrementalPath = newPath + "/" + this.name;
  }

  /**
   * Returns list of child Nodes.
   */
  public List<Node> getChilds() {
    return this.childs;
  }

  /**
   * Adds an existing node to the child list.
   * 
   * @param child the node to be added as a child
   */
  public void addChilds(Node child) {
    this.childs.add(child);
  }

  /**
   * Removes an existing node from the child list.
   * 
   * @param child the node to be removed
   */
  public void removeChild(Node child) {
    this.childs.remove(child);
  }

  public boolean isFile() {
    return isFile;
  }

  /**
   * Creates and adds a new directory to an existing directory.
   * 
   * @param dirName the name of the new directory
   */
  public void addDirectory(String dirName) {
    Node newChild = new Node(dirName, incrementalPath + "/" + dirName, false);
    childs.add(newChild);
  }

  /**
   * Creates and adds a new file to an existing directory.
   * 
   * @param fileName the name of the new file
   * @param fileContent the content of the file
   */
  public void addFile(String fileName, String fileContent) {
    Node newChild = new Node(fileName, incrementalPath + "/" + fileName, true);
    newChild.setContent(fileContent);
    childs.add(newChild);
  }

  /**
   * Creates a new directory and adds it based on the path given from root.
   * 
   * @param dir the node corresponding to the directory
   * @param list list of strings in the path split by the character '/'
   */
  public void addDirectoryFromRoot(Node dir, String[] list) {
    list = Arrays.copyOfRange(list, 1, list.length);
    if (list.length == 0)
      return;
    if (list.length == 1)
      dir.addDirectory(list[0]);
    else {
      for (Node n : childs)
        if (n.name.equals(list[0]))
          dir.addDirectoryFromRoot(n, list);
    }
  }

  /**
   * Returns a boolean based on if the given path corresponds to an existing directory.
   * 
   * @param list a list of strings in the path split by the character '/'
   * @return returns true if the directory is valid or false if not
   */
  public boolean validDirectory(String[] list) {
    for (Node n : childs) {
      if (n.name.equals(list[0])) {
        if (list.length == 1)
          return true;
        else
          return n.validDirectory(Arrays.copyOfRange(list, 1, list.length));
      }
    }
    return false;
  }

  /**
   * Returns the full path of the file/directory.
   * 
   * @return the path of the file/directory
   */
  public String getPath() {
    if (incrementalPath.equals(""))
      return "/";
    else
      return incrementalPath;
  }

  /**
   * Returns the name of the file/directory.
   * 
   * @return the name of the file/directory
   */
  public String toString() {
    return name;
  }

  /**
   * Returns the contents of a file.
   * 
   * @return the file's content
   */
  public String content() {
    return content;
  }

  /**
   * Returns the names of the files and directories in the current directory.
   * 
   * @return the String containing the names of all files and directories in the given directory
   */
  public String printFiles() {
    String names = "";
    boolean space = true;
    for (Node n : childs) {
      if (space)
        space = false;
      else
        names = names + "\n";
      names = names + n.name;
    }
    return names;
  }

  /**
   * Returns the directory node, if it exists in the current directory.
   * 
   * @param givenName the name of the directory to be searched for
   * @return the node corresponding to the path or null if its invalid
   */
  public Node hasDir(String givenName) {
    if (childs.isEmpty())
      return null;
    for (Node n : childs)
      if (n.name.equals(givenName))
        return n;
    return null;
  }

  /**
   * Returns the desired file's node, if it exists in the current directory.
   * 
   * @param givenName the name of the file to be searched for
   * @return the node corresponding to the path or null if its invalid
   */
  public Node hasFile(String givenName) {
    if (isFile)
      return null;
    for (Node n : childs)
      if (n.isFile)
        if (n.name.equals(givenName))
          return n;
    return null;
  }

  /**
   * Returns the node of last file/directory from the list of paths.
   * 
   * @param list the name of the new directory
   * @return the node corresponding to the path or null if its invalid
   */
  public Node getNodeFromPath(String[] list) {
    if (list.length == 0)
      return this;
    if (list.length > 1)
      for (Node n : childs)
        if (n.name.equals(list[0]) || "".equals(list[0]))
          return n.getNodeFromPath(Arrays.copyOfRange(list, 1, list.length));
    if (list.length == 1 && list[0].equals(""))
      return null;
    for (Node n : childs)
      if (n.name.equals(list[0]))
        return n;
    return null;
  }

  /**
   * Sets the content of a file.
   * 
   * @param input the content to be set to
   */
  public void setContent(String input) {
    this.content = input;
  }

  /**
   * Appends to the content of a file.
   * 
   * @param input the content to be appended
   */
  public void appendContent(String input) {
    this.content = this.content + "\n" + input;
  }
}
