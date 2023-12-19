import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
   public static void main(String[] args) {
      String ring = """


            1 2
            2 2
            1 3
            2 3
            1 4
            2 4
            2 5
            3 4
            3 5
            4 3
            4 5
            4 2
            5 4
            3 2
            5 3
            4 4
            5 2
            9 8
            4 1
            9 7
            3 1
            9 6
            2 1
            9 5
            3 3
            """;
      String ko = """
            
            
            1 2
            2 2
            2 1
            3 1
            2 3
            3 3
            3 2
            4 2
            9 9
            2 2
            3 2
            9 8
            9 7
            3 2
            pas
            pas
            t
            t
            """;
      InputStream in = new ByteArrayInputStream(ko.getBytes());
      GoGame game = new GoGame(in, System.out);
      try {
         game.startGame();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
