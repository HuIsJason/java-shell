package commands;

import fileSystem.FileTree;

/**
 * The Cat class implements functionality to display the content of specified files.
 */
public class Cat extends Command {
  /**
   * This function uses functions from fileTree to display the contents of inputArgs, which is the
   * file specified by the user.
   * 
   * @param inputArgs The input from the user, and optional parameters to write or append the output
   *        message to a file
   * @param tree The file-system from fileTree
   * @return A string representation of the file contents, or nothing if to be written to a file
   */
  public static String execute(String[] inputArgs, FileTree tree) {
    if (inputArgs.length >= 2) {
      String content = new String();
      String[] splitPath = inputArgs[inputArgs.length - 1].split("/");
      String outFile = splitPath[splitPath.length - 1];
      int i;
      // Checks each argument after the "cat" command argument
      // If file exists, it concatenates it to a String to be returned
      for (i = 1; i < inputArgs.length; i++) {
        if (inputArgs[i] == ">" || inputArgs[i] == ">>")
          break;
        String[] list = inputArgs[i].split("/");
        if (!list[list.length - 1].matches("^[a-zA-Z0-9]*$")) {
          System.out
              .println("Invalid File: " + list[list.length - 1] + " contains illegal characters.");
          continue;
        }
        // If file exists in cwd, then it is printed
        if (list.length > 1) {
          if (tree.getNodeFromPath(inputArgs[i], tree.getCwd()) != null) {
            if (tree.getNodeFromPath(inputArgs[i], tree.getCwd()).isFile())
              content = content.concat(tree.printContents(list[list.length - 1],
                  tree.getSecondLastNodeFromPath(inputArgs[i], tree.getCwd())) + "\n\n\n");
          } else {
            content = content.concat(list[list.length - 1] + " does not exist in specified path\n");
          }
        } else {
          if (!inputArgs[i].matches("^[a-zA-Z0-9]*$")) {
            System.out
                .println("Invalid Directory: " + inputArgs[i] + " contains illegal characters.");
            continue;
          }
          content =
              content.concat(tree.printContents(list[list.length - 1], tree.getCwd()) + "\n\n\n");
        }
      }
      if (inputArgs[i] == ">") {
        // now we must overwrite OUTFILE with message
        if (splitPath.length == 1 && splitPath[0].isEmpty()) {
          // this means the outFile is to be in root
          if (tree.containsFile(outFile, tree.getRoot()))
            tree.getRoot().hasFile(outFile).setContent(content);
          else
            tree.getRoot().addFile(outFile, content);
        } else {
          if (tree.containsFile(outFile,
              tree.getSecondLastNodeFromPath(inputArgs[3], tree.getCwd())))
            tree.getNodeFromPath(inputArgs[3], tree.getCwd()).setContent(content);
          else
            tree.getSecondLastNodeFromPath(inputArgs[3], tree.getCwd()).addFile(outFile, content);
        }
      }
      if (inputArgs[i] == ">>") {
        if (splitPath.length == 1 && splitPath[0].isEmpty()) {
          // this means the outFile is to be in root
          if (tree.containsFile(outFile, tree.getRoot()))
            tree.getRoot().hasFile(outFile).appendContent(content);
          else
            tree.getRoot().addFile(outFile, content);
        } else {
          if (tree.containsFile(outFile,
              tree.getSecondLastNodeFromPath(inputArgs[3], tree.getCwd())))
            tree.getNodeFromPath(inputArgs[3], tree.getCwd()).appendContent(content);
          else
            tree.getSecondLastNodeFromPath(inputArgs[3], tree.getCwd()).addFile(outFile, content);
        }
      }
      return content + "\n";
    } else
      return "Must specify files\n";
  }
}
