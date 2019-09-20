package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import commands.Echo;
import commands.Cat;
import commands.Ls;
import fileSystem.FileTree;
import fileSystem.Node;

public class EchoTest {

  FileTree tree;
  String userInput;
  String[] inputArgs;

  @Before
  public void setUp() {
    tree = new FileTree(new Node("", "", false));
  }

  @Test
  public void testExecute() {
    // single argument
    userInput = "echo";
    assertEquals(Echo.execute(userInput, tree), "String must be in double quotes\n");
    // two arguments without quotes
    userInput = "echo someContent";
    assertTrue(Echo.execute(userInput, tree).equals("String must be in double quotes\n"));
    // two arguments with quotes
    userInput = "echo \"someContent\"";
    assertEquals(Echo.execute(userInput, tree), "someContent\n");
    // Create a new file with content
    userInput = "echo \"someContent\" > newFile";
    Echo.execute(userInput, tree);
    inputArgs = "cat newFile".split("\\s+");
    assertEquals(Cat.execute(inputArgs, tree), "someContent\n\n\n\n");
    userInput = "echo \"existingContent\" > newFile";
    Echo.execute(userInput, tree);
    userInput = "echo \"appendContent\" >> newFile";
    Echo.execute(userInput, tree);
    inputArgs = "cat newFile".split("\\s+");
    assertEquals(Cat.execute(inputArgs, tree), "existingContent\nappendContent\n\n\n\n");
    inputArgs = "ls".split("\\s+");
    assertEquals(Ls.execute(inputArgs, tree), "newFile\n");
  }

}
