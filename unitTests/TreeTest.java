package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Tree;
import commands.Mkdir;
import fileSystem.*;


public class TreeTest {

  FileTree tree;
  String[] inputArgs;

  @Before
  public void setUp() {
    tree = new FileTree(new Node("", "", false));
  }

  @Test
  public void testExecute() {
    // single directory, only root "\"
    inputArgs = "tree".split("\\s+");
    assertTrue(Tree.execute(inputArgs, tree).charAt(0) == '\\');
    // sub-directories
    inputArgs = "mkdir A B".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "tree".split("\\s+");
    assertEquals(Tree.execute(inputArgs, tree), "\\\n  A\n  B\n");
    // sub-directories with sub-directories
    inputArgs = "mkdir A B".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "mkdir /A/C /A/D".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "mkdir /A/C/E".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "tree".split("\\s+");
    assertEquals(Tree.execute(inputArgs, tree),
        "\\\n" + "  A\n" + "    C\n" + "      E\n" + "    D\n" + "  B\n");
  }

}
