import java.io.BufferedReader;
import java.io.IOException;

public class HumanPlayer extends GoPlayer {
   BufferedReader in;

   public HumanPlayer(Color color, GoBoard board, BufferedReader in) {
      super(color, board);
      this.in = in;
   }

   @Override
   public void takeTurn() throws IOException {
      String line = in.readLine();
      String[] args = line.split(" ");
      try {
         int x = Integer.parseInt(args[0]);
         int y = Integer.parseInt(args[1]);
         if (!super.board.placeStone(x - 1, y - 1, super.color)){
            throw new IllegalArgumentException();
         }
      } catch (NumberFormatException e) {
         System.out.println("Niepoprawne współrzędne");
         takeTurn();
      } catch (IllegalArgumentException e) {
         System.out.println("Nie można ustawić kamienia na tym polu");
         takeTurn();
      }
   }

   @Override
   String getName() {
      if (super.color == Color.BLACK) {
         return "grający czarnymi";
      } else {
         return "grający białymi";
      }
   }


}


