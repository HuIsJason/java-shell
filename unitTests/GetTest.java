package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import commands.Get;
import commands.Cat;
import commands.Ls;
import fileSystem.FileTree;
import fileSystem.Node;

public class GetTest {

  FileTree tree;
  String[] inputArgs;

  @Before
  public void setUp() {
    tree = new FileTree(new Node("", "", false));
  }

  @Test
  public void test() throws Exception {
    // single argument
    inputArgs = "get    ".split("\\s+");
    assertTrue(Get.execute(inputArgs, tree).equals("Must specify URL\n"));
    // more than two arguments
    inputArgs = "get someUrl extra arguments".split("\\s+");
    assertTrue(Get.execute(inputArgs, tree).equals("Too many arguments\n"));
    // two arguments, reading from web site
    inputArgs =
        "get https://www.cs.utoronto.ca/~trebla/CSCB09-2019-Summer/lab02/myfile.txt".split("\\s+");
    Get.execute(inputArgs, tree);
    inputArgs = "cat myfile".split("\\s+");
    assertEquals(Cat.execute(inputArgs, tree),
        "\n" + "dilettantism\n" + "postdoctoral\n" + "splatting\n" + "implied\n" + "heisted\n"
            + "guilling\n" + "anecdotal\n" + "transducer\n" + "toffy\n" + "throatiest\n"
            + "destinies\n" + "postdoctoral\n" + "prickle\n" + "anecdotal\n" + "tempests\n"
            + "customizes\n" + "ashram\n" + "postdoctoral\n" + "seperate\n" + "guilling\n"
            + "tiffy\n" + "destinies\n" + "besot\n" + "implied\n" + "dilettantism\n" + "pays\n"
            + "pays\n" + "mindfully\n" + "implied\n" + "recovering\n" + "rendezvousing\n"
            + "throatiest\n\n\n\n");
  }

}
