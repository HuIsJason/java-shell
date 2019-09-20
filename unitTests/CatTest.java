package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import commands.Cat;
import commands.Echo;
import fileSystem.FileTree;
import fileSystem.Node;

public class CatTest {

  FileTree tree;
  String userInput;
  String[] inputArgs;

  @Before
  public void setUp() {
    tree = new FileTree(new Node("", "", false));
  }

  @Test
  public void test() {
    // single argument
    inputArgs = "cat".split("\\s+");
    assertTrue(Cat.execute(inputArgs, tree).equals("Must specify files\n"));
    // cat on a single file with contents
    userInput = "echo \"someContent\" > newFile";
    Echo.execute(userInput, tree);
    inputArgs = "cat newFile".split("\\s+");
    assertTrue(Cat.execute(inputArgs, tree).equals("someContent\n\n\n\n"));
    // cat on multiple files that contain content
    userInput = "echo \"contentInFile1\" > File1";
    Echo.execute(userInput, tree);
    userInput = "echo \"contentInFile2\" > File2";
    Echo.execute(userInput, tree);
    userInput = "echo \"contentInFile3\" > File3";
    Echo.execute(userInput, tree);
    inputArgs = "cat File1 File2 File3".split("\\s+");
    assertTrue(Cat.execute(inputArgs, tree)
        .equals("contentInFile1\n\n\ncontentInFile2\n\n\n" + "contentInFile3\n\n\n\n"));
  }

}
