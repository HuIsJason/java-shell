package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Pwd;
import commands.Cd;
import commands.Mkdir;
import fileSystem.FileTree;
import fileSystem.Node;

public class CdTest {

  FileTree tree;
  String[] inputArgs;

  @Before
  public void setUp() throws Exception {
    tree = new FileTree(new Node("", "", false));
  }

  @Test
  public void testExecute() {
    // only one argument
    inputArgs = "cd".split("\\s+");
    assertTrue(Cd.execute(inputArgs, tree).equals("Must specify directory\n"));
    // cd into a relative directory
    inputArgs = "mkdir newDir".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "cd newDir".split("\\s+");
    Cd.execute(inputArgs, tree);
    assertTrue(Pwd.execute(inputArgs, tree).equals("/newDir\n"));


  }

}
