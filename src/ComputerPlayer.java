import java.io.IOException;

public class ComputerPlayer extends GoPlayer {
   ComputerPlayer(Color color) {
      super(color);
   }


   @Override
   String takeTurn(GoBoard board) throws IOException {
      return null;
   }

   @Override
   String getName() {
      if (super.color == Color.BLACK) {
         return "komputer grający czarnymi";
      } else {
         return "komputer grający białymi";
      }
   }

   @Override
   boolean askFinish() {
      return false;
   }
}
