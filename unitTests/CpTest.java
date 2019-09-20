package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Mkdir;
import commands.Ls;
import commands.Cp;
import fileSystem.FileTree;
import fileSystem.Node;

public class CpTest {

  FileTree tree;
  String[] inputArgs;

  @Before
  public void setUp() {
    tree = new FileTree(new Node("", "", false));
  }

  @Test
  public void testExecute() {
    // single command arguments
    inputArgs = "cp".split("\\s+");
    assertTrue(Cp.execute(inputArgs, tree).equals("Not enough arguments\n"));
    // more than 3 arguments
    inputArgs = "cp somedir anotherdir extra arguments".split("\\s+");
    assertTrue(Cp.execute(inputArgs, tree).equals("Too many arguments\n"));
    // copy directories from relative path
    inputArgs = "mkdir dir dir2".split("\\s+");
    Mkdir.execute(inputArgs, tree);
    inputArgs = "cp dir dir2".split("\\s+");
    Cp.execute(inputArgs, tree);
    inputArgs = "ls".split("\\s+");
    assertTrue(Ls.execute(inputArgs, tree).equals("dir\ndir2\n"));
    inputArgs = "ls /dir2".split("\\s+");
    assertTrue(Ls.execute(inputArgs, tree).equals("dir2:\ndir\n\n"));

  }

}
