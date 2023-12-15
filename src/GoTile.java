public class GoTile {
   /**
    * Kolor kamienia na polu, jeśli null to pole jest puste
    */
   private Color tileColor = null;

   /**
    * Tablica sąsiadów pola
    * 0 - Góra
    * 1 - Dół
    * 2 - Lewo
    * 3 - Prawo
    */
   private GoTile[] neighbors = new GoTile[4];

   /**
    * Sąsiedzi dający oddech polu
    * 0 - Góra
    * 1 - Dół
    * 2 - Lewo
    * 3 - Prawo
    */
   private boolean[] breath = {false, false, false, false};

   /**
    * Sąsiedzi, którzy otrzymują oddech od pola
    * 0 - Góra
    * 1 - Dół
    * 2 - Lewo
    * 3 - Prawo
    */
   private boolean[] dependent = {false, false, false, false};

   /**
    * Licznik zbitych kamieni
    */
   private GameCounter counter;

   public GoTile(GameCounter counter) {
      this.counter = counter;
   }

   public void setNeighbors(GoTile[] neighbors) {
      this.neighbors = neighbors;
   }

   public void giveBreath() {

   }

   public void takeBreath() {

   }


}
