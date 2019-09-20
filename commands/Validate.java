package commands;

import fileSystem.Node;
import fileSystem.FileTree;

public class Validate {
  private History history;
  private FileTree tree;

  public Validate() {
    tree = new FileTree(new Node("", "", false));
    history = new History();
  }

  public String getTreeCwd() {
    return tree.getCwd().getPath();
  }

  public History getHistory() {
    return this.history;
  }

  public String validCommand(String[] inputArgs, String userInput) {

    try {
      String command = inputArgs[0];
      if (!command.equals("save"))
        history.addToHistory(userInput);
      // Handles each command, if the input given is correct for the
      // command, the related command return tree.getNodeFromPath(path,
      // tree.getCwd());class is executed
      switch (command) {
        case "history":
          return history.execute(inputArgs, tree);
        case "pwd":
          return Pwd.execute(inputArgs, tree);
        case "cd":
          return Cd.execute(inputArgs, tree);
        case "cat":
          return Cat.execute(inputArgs, tree);
        case "echo":
          return Echo.execute(userInput, tree);
        case "ls":
          return Ls.execute(inputArgs, tree);
        case "man":
          return Man.execute(inputArgs, tree);
        case "mkdir":
          return Mkdir.execute(inputArgs, tree);
        case "exit":
          return Exit.execute(inputArgs);
        case "pushd":
          return Pushd.execute(inputArgs, tree);
        case "popd":
          return Popd.execute(tree);
        case "get":
          return Get.execute(inputArgs, tree);
        case "mv":
          return Mv.execute(inputArgs, tree);
        case "cp":
          return Cp.execute(inputArgs, tree);
        case "tree":
          return Tree.execute(inputArgs, tree);
        case "save":
          return SaveFile.execute(inputArgs, getHistory());
        case "find":
          return Find.execute(inputArgs, tree);
        default:
          return "Invalid command\n";
      }
    } catch (Exception NullPointerException) {
      return "";
    }
  }
}
