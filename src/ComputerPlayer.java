public class ComputerPlayer extends GoPlayer {
   ComputerPlayer(Color color, GoBoard board) {
      super(color, board);
   }

   @Override
   public void takeTurn() {

   }

   @Override
   String getName() {
      if (super.color == Color.BLACK) {
         return "komputer grający czarnymi";
      } else {
         return "komputer grający białymi";
      }
   }
}
