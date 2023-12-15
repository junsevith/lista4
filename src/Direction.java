public enum Direction {
   UP, DOWN, LEFT, RIGHT;

   public static Direction getOpposite(Direction direction){
      if(direction == Direction.UP){
         return Direction.DOWN;
      } else if(direction == Direction.DOWN){
         return Direction.UP;
      } else if(direction == Direction.LEFT){
         return Direction.RIGHT;
      } else {
         return Direction.LEFT;
      }
   }
}
