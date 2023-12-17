import java.io.IOException;

public abstract class GoPlayer {
   final Color color;
   final GoBoard board;
   GoPlayer(Color color, GoBoard board){
      this.color = color;
      this.board = board;
   }
   abstract void takeTurn() throws IOException;

   abstract String getName();
}
