package fileSystem;

import java.util.Arrays;

/**
 * The FileTree class defines the structure of the file system
 */
public class FileTree {

  /**
   * The node of the root directory.
   */
  private Node root;

  /**
   * The node of the current working directory.
   */
  private Node cwd;

  /**
   * The constructor for FileTree.
   * 
   * @param root the root node of the FileTree
   */
  public FileTree(Node root) {
    this.root = root;
    this.cwd = root;
  }

  /**
   * Returns the root.
   * 
   * @return the node of the root
   */
  public Node getRoot() {
    return root;
  }

  /**
   * Returns the current working directory.
   * 
   * @return the node of the current working directory
   */
  public Node getCwd() {
    return cwd;
  }

  /**
   * Sets the current working directory.
   * 
   * @param cwd the node of the current working directory to be set to
   */
  public void setCwd(Node cwd) {
    this.cwd = cwd;
  }

  /**
   * Adds a new directory to an existing directory.
   * 
   * @param cwd the node of the current working directory
   * @param path the full path of the new directory
   */
  public void addDirectory(Node cwd, String path) {
    String[] list = path.split("/");
    // latest element of the list is the filename
    if (list[0].equals(""))
      root.addDirectoryFromRoot(root, list);
    else if (list.length == 1) {
      cwd.addDirectory(path);
    } else {
      Node newDir = this.getSecondLastNodeFromPath(path, root);
      newDir.addDirectory(path);
    }
  }

  /**
   * Adds a new file to an existing directory.
   * 
   * @param cwd the node of the current working directory
   * @param name the name of the file to be added
   * @param fileContent the content of the file to be added
   */
  public void addFile(Node cwd, String name, String fileContent) {
    cwd.addFile(name, fileContent);
  }

  /**
   * Returns a boolean for the validity of a directory.
   * 
   * @param cwd the node of the current working directory
   * @param path the full path of the of directory to be evaluated
   * @return a boolean, true if the directory is valid, false if not
   */
  public boolean checkValidDirectory(Node cwd, String path) {
    String[] list = path.split("/");
    if (list[0].equals(""))
      return root.validDirectory(Arrays.copyOfRange(list, 1, list.length));
    else
      return cwd.validDirectory(list);
  }

  /**
   * Returns a string of the name(s) of all the files and sub-directories in a directory.
   * 
   * @param dir name of the directory whose files and sub-directories are to be printed
   * @return the string that contains the names of the files and sub-directories
   */
  public String printFiles(Node dir) {
    return dir.printFiles();
  }

  /**
   * Returns whether or not a sub-directory exists in a directory.
   * 
   * @param name the name of the sub-directory
   * @param cwd the node to the current working directory
   * @return a boolean, true if the sub-directory exists, false if not
   */
  public boolean containsDir(String name, Node cwd) {
    if (cwd.hasDir(name) != null)
      return true;
    else
      return false;
  }

  /**
   * Returns whether or not a file exists in a directory.
   * 
   * @param name the name of the file
   * @param cwd the node to the current working directory
   * @return a boolean, true if the file exists, false if not
   */
  public boolean containsFile(String name, Node cwd) {
    Node file = cwd.hasFile(name);
    if (file != null)
      return true;
    else
      return false;
  }

  /**
   * Prints the contents of a file.
   * 
   * @param name the name of the file
   * @param cwd the node to the current working directory
   * @return the content of the file
   */
  public String printContents(String name, Node cwd) {
    Node file = cwd.hasFile(name);
    return file.content();
  }

  /**
   * Returns the node of the last file/directory in a path.
   * 
   * @param path the full path whose last file/directory to be returned
   * @param cwd the node to the current working directory
   * @return the node to the last file/directory of path, or null if non-existent
   */
  public Node getNodeFromPath(String path, Node cwd) {
    Node target = null;
    if (checkValidDirectory(cwd, path) || path.equals("..") || path.equals(".")) {
      String[] list = path.split("/");
      if (list.length == 1 && list[0].equals("."))
        target = cwd;
      else if (list.length == 1 && list[0].equals(".."))
        target = getSecondLastNodeFromPath(cwd.getPath(), cwd);
      else if (list[0].equals(""))
        target = root.getNodeFromPath(Arrays.copyOfRange(list, 1, list.length));
      else
        target = cwd.getNodeFromPath(list);
    }
    if (target == null)
      return this.getCwd();
    // return null;
    else
      return target;
  }

  /**
   * Returns the node of the second last file/directory in a path.
   * 
   * @param path the full path whose second last file/directory to be returned
   * @param cwd the node to the current working directory
   * @return the node to the second last file/directory of path, or null if non-existent
   */
  public Node getSecondLastNodeFromPath(String path, Node cwd) {
    if (path.contentEquals(""))
      return null;
    String[] list = path.split("/");
    if (list[0].equals(""))
      return root.getNodeFromPath(Arrays.copyOfRange(list, 1, list.length - 1));
    else
      return cwd.getNodeFromPath(Arrays.copyOfRange(list, 0, list.length - 1));

  }
}
