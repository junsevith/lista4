import java.util.*;

public class GoTile {
   /**
    * Kolor kamienia na polu, jeśli null to pole jest puste
    */
   private Color stoneColor = null;

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
   private int breathCount = 0;

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

   /**
    * Mapa zwracająca przeciwny kierunek
    */
   private final Map<Integer, Integer> revDir = Map.of(
         0, 1,
         1, 0,
         2, 3,
         3, 2
   );

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

   public boolean setTile(Color color) {
      if (stoneColor != null) {
         return false;
      } else if (getNeighbors(Color.opposite(color)).size() == 4) {
         return false;
      } else {
         stoneColor = color;

         return true;
      }
   }

   public Color getStoneColor() {
      return stoneColor;
   }

   /**
    * Zwraca sąsiadów o danym kolorze
    *
    * @param color Kolor do dopasowania
    * @return Lista sąsiadów o danym kolorze
    */
   public List<Integer> getNeighbors(Color color) {
      List<Integer> matchingNeighbors = new ArrayList<>();
      for (int i = 0; i < 4; i++) {
         if (neighbors[i] != null && neighbors[i].getStoneColor() == color) {
            matchingNeighbors.add(i);
         }
      }
      return matchingNeighbors;
   }

   private void checkNeighbors() {
      for (Integer neighbor : getNeighbors(null)) {
         breath[neighbor] = true;
         breathCount += 1;
      }

      for (Integer neighbor : getNeighbors(stoneColor)) {
         if (neighbors[neighbor].thereAreOtherBreaths(revDir.get(neighbor))) {
            breath[neighbor] = true;
            breathCount += 1;
         } else {
            dependent[neighbor] = true;
         }


         for (int i = 0; i < 4; i++) {
            if (breath[i] && thereAreOtherBreaths(i)) {
               dependent[i] = true;
            }
         }
      }
   }

   /**
    * Sprawdza, czy sąsiad w danym kierunku to jego jedyny oddech
    *
    * @param direction Kierunek sąsiada
    * @return True, jeśli są inni dostawcy oddechu poza sąsiadem w danym kierunku
    */
   private boolean thereAreOtherBreaths(int direction) {
      if (!breath[direction]) {
         throw new IllegalArgumentException("Nie można sprawdzić, czy sąsiad jest jedynym dostawcą oddechu, jeśli nie jest dostawcą oddechu");
      }
      return breathCount > 1;

   }
}
