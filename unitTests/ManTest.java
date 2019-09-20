package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import commands.Man;
import fileSystem.FileTree;
import fileSystem.Node;

public class ManTest {

  FileTree tree;
  String[] inputArgs;

  @Before
  public void setUp() {
    tree = new FileTree(new Node("", "", false));
  }

  @Test
  public void testExecute() {
    // single argument
    inputArgs = "man".split("\\s+");
    assertEquals(Man.execute(inputArgs, tree), "Must specify command\n");
    // man command for man with extra whitespace
    inputArgs = "   man        man    ".split("\\s+");
    assertEquals(Man.execute(inputArgs, tree), "Print documentation for CMD\n");
    // man command for pwd
    inputArgs = "man         pwd       ".split("\\s+");
    assertEquals(Man.execute(inputArgs, tree),
        "Print the current working directory " + "(including the whole path).\n");

  }

}
