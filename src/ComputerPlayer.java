public class ComputerPlayer extends GoPlayer {
   ComputerPlayer(Color color, GoBoard board) {
      super(color, board);
   }

   @Override
   public String takeTurn() {
      return "";
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
