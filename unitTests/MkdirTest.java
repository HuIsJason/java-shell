package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fileSystem.FileTree;
import fileSystem.Node;
import commands.Mkdir;
import commands.Cd;
import commands.Ls;

public class MkdirTest {

  FileTree tree;
  String[] inputArgs;

  @Before
  public void setUp() throws Exception {
    tree = new FileTree(new Node("", "", false));
  }

  @Test
  public void testExecute() {
    // no second arugment of mkdir
    inputArgs = "mkdir".split("\\s+");
    assertTrue(Mkdir.execute(inputArgs, tree).equals("Not enough arguments\n"));
    // mkdir a new file with extra whitespace

    inputArgs = "mkdir newDirectory".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "ls".split("\\s+");
    assertEquals(Ls.execute(inputArgs, tree), "newDirectory\n");
    //absolute path
    inputArgs = "mkdir dir".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "mkdir /dir/dir2".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "cd dir".split("\\s+");
    Cd.execute(inputArgs, tree);
    inputArgs = "ls".split("\\s+");
    assertEquals(Ls.execute(inputArgs, tree), "dir2\n");
  }

}
