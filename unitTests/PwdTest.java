package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Cd;
import commands.Mkdir;
import commands.Pwd;
import fileSystem.FileTree;
import fileSystem.Node;

public class PwdTest {

  FileTree tree;
  String[] inputArgs;

  @Before
  public void setUp() throws Exception {
    tree = new FileTree(new Node("", "", false));

  }

  @Test
  public void testExecute() {
    // test if on root with no directories made yet
    inputArgs = "pwd".split("\\s+");
    assertTrue(Pwd.execute(inputArgs, tree).equals("/\n"));
    // make a directory and test with directory "a"
    inputArgs = "mkdir a".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "cd a".split("\\s+");
    Cd.execute(inputArgs, tree);
    assertTrue(Pwd.execute(inputArgs, tree).equals("/a\n"));
    // test pwd after attempting to make a bad directory change
    inputArgs = "cd fakeDirectoryName".split("\\s+");
    Cd.execute(inputArgs, tree);
    assertTrue(Pwd.execute(inputArgs, tree).equals("/a\n"));
  }

}
