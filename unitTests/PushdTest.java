package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.*;
import fileSystem.FileTree;
import fileSystem.Node;

public class PushdTest {

  FileTree tree;
  String[] inputArgs;

  @Before
  public void setUp() throws Exception {
    tree = new FileTree(new Node("", "", false));

  }

  @Test
  public void testExecute() {
    // test pushing invalid directory
    inputArgs = "pushd nonexsistingdirectory".split("\\s+");
    Pushd.execute(inputArgs, tree);
    assertTrue(Pwd.execute(inputArgs, tree).equals("/\n"));
    // make a directory and test with directory "a"
    inputArgs = "mkdir a".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    // push single directory a onto stack
    inputArgs = "pushd a".split("\\s+");
    Pushd.execute(inputArgs, tree);
    // make sure that you have entered directory
    assertTrue(Pwd.execute(inputArgs, tree).equals("/a\n"));
    // make sure correct directory is on top of stack
    assertEquals(Pushd.dir.peek(), tree.getRoot());
    // test pwd after attempting to make a bad directory change
    inputArgs = "cd fakeDirectoryName".split("\\s+");
    Cd.execute(inputArgs, tree);
    assertTrue(Pwd.execute(inputArgs, tree).equals("/a\n"));
  }

}
