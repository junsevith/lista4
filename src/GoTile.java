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
   private final boolean[] breath = {false, false, false, false};
   private int breathCount = 0;

   private final Set<GoTile> breathTiles = new HashSet<>();

   /**
    * Sąsiedzi, którzy otrzymują oddech od pola
    * 0 - Góra
    * 1 - Dół
    * 2 - Lewo
    * 3 - Prawo
    */
   private final boolean[] dependent = {false, false, false, false};

   /**
    * Licznik zbitych kamieni
    */
   private final GameCounter counter;

   /**
    * Mapa zwracająca przeciwny kierunek
    */
   private final Map<Integer, Integer> revDir = Map.of(
         0, 1,
         1, 0,
         2, 3,
         3, 2
   );

   /**
    * Zwraca przeciwny kierunek, używane do wysyłania informacji do sąsiadów, tak aby wiedzieli skąd przyszła informacja
    *
    * @param direction Kierunek
    * @return Przeciwny kierunek
    */
   private Integer reverseDirection(Integer direction) {
      return revDir.get(direction);
   }

   public GoTile(GameCounter counter) {
      this.counter = counter;
   }

   public void setNeighbors(GoTile[] neighbors) {
      this.neighbors = neighbors;
   }

   /**
    * Resetuje pole do stanu początkowego
    */
   private void resetTile() {
      stoneColor = null;
      breathCount = 0;
      Arrays.fill(breath, false);
      Arrays.fill(dependent, false);
   }

   /**
    * Zwraca kolor kamienia na polu
    * Null, jeśli pole jest puste
    *
    * @return Kolor kamienia na polu
    */
   public Color getStoneColor() {
      return stoneColor;
   }

   public int getBreathCount() {
      return breathCount;
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

   /**
    * Sprawdza, czy pole ma więcej niż jeden oddech
    *
    * @return True, jeśli pole ma więcej niż jeden oddech
    */
   private boolean thereAreOtherBreaths() {
      return breathCount > 1;
   }

   /**
    * Dodaje oddech w danym kierunku
    *
    * @param direction Kierunek z kąd przychodzi oddech
    */
   public void giveBreath(int direction) {
      breath[direction] = true;
      breathCount += 1;
   }

   public void inheritBreath(Set<GoTile> tiles) {
      breathTiles.addAll(tiles);
      breathCount += tiles.size();
   }

   public void inheritBreath(GoTile tile) {
      breathTiles.add(tile);
      breathCount += 1;
   }

   /**
    * Odbiera oddech z danego kierunku
    *
    * @param direction Kierunek z kąd przychodzi oddech
    */
   public void takeBreath(int direction) {
      breath[direction] = false;
      breathCount -= 1;

      if (breathCount == 0) {

      }
   }

   /**
    * Odbiera oddech z danego kierunku
    * @param tile odbiera oddech z tego pola
    * @param invoker pole, które wywołało metodę (żeby metoda nie wywołała się drugi raz na tym samym polu)
    * @param source pole, które zapoczątkowało całą akcję (żeby metoda nie wywołała się drugi raz na tym samym polu)
    */
   public void looseBreath(GoTile tile, GoTile invoker, GoTile source) {
      breathTiles.remove(tile);
      breathCount -= 1;

      for (Integer dir : getNeighbors(stoneColor)) {
         if (neighbors[dir] != tile && neighbors[dir] != source && neighbors[dir] != invoker) {
            neighbors[dir].looseBreath(tile, this, source);
         }
      }

      if (breathTiles.isEmpty()) {
         killStone();
      }

   }

   /**
    * Zabija kamień na polu
    */
   private void killStone() {
      counter.addCapturedStone(stoneColor);

      if (stoneColor != null) {
         for (Integer dir : getNeighbors(stoneColor.opposite())) {
            neighbors[dir].inheritBreath(this);
         }
      }


//      for (Integer dir : getNeighbors(stoneColor)) {
//         neighbors[dir].looseBreath(this,this,this);
//      }

      resetTile();
   }

   /**
    * Ustawia kamień na polu
    *
    * @param color Kolor kamienia
    * @return True, jeśli udało się ustawić kamień
    */
   public boolean placeStoneOld(Color color) {
      if (stoneColor != null) {
         return false;
      } else if (getNeighbors(color.opposite()).size() == 4) {
         //TODO: sprawdzić czy nie zabija się kamieni
         setupStone(color);
         return true;

      } else if (getNeighbors(null).isEmpty()) {

         for (Integer direction : getNeighbors(color)) {
            if (neighbors[direction].thereAreOtherBreaths()) {
               setupStone(color);
               return true;
            }
         }

         for (Integer dir : getNeighbors(color.opposite())) {
            if (!neighbors[dir].thereAreOtherBreaths()) {
               setupStone(color);
               return true;
            }
         }
         return false;
      } else {
         setupStone(color);
         return true;
      }
   }

   /**
    * Ustawia kamień na polu
    *
    * @param color Kolor kamienia
    * @return True, jeśli udało się ustawić kamień
    */
   public boolean placeStone(Color color) {
      if (stoneColor != null) {
         return false;
      } else if (getNeighbors(color.opposite()).size() == 4) {
         for (Integer direction : getNeighbors(color.opposite())) {
            if (neighbors[direction].onlyBreath(this)) {
               setupStone(color);
               return true;
            }
         }
         return false;

      } else if (getNeighbors(null).isEmpty()) {

         for (Integer direction : getNeighbors(color)) {
            if (neighbors[direction].onlyBreath(this)) {
               setupStone(color);
               return true;
            }
         }

         for (Integer dir : getNeighbors(color.opposite())) {
            if (!neighbors[dir].onlyBreath(this)) {
               setupStone(color);
               return true;
            }
         }
         return false;
      } else {
         setupStone(color);
         return true;
      }
   }

   /**
    * Ustawia na polu kamień danego koloru
    *
    * @param color Kolor kamienia
    */
   private void setupStone(Color color) {
      stoneColor = color;
      checkNeighborsV2();
   }

   /**
    * Sprawdza sąsiadów i aktualizuje oddechy oraz zależności
    */
   private void checkNeighbors() {
      //zabiera oddechy wszystkim sąsiadom przeciwnego koloru
      for (Integer direction : getNeighbors(stoneColor.opposite())) {
         neighbors[direction].takeBreath(reverseDirection(direction));
      }

      //ustawia sobie oddechy od pustych sąsiadów
      for (Integer direction : getNeighbors(null)) {
         this.giveBreath(direction);
//         System.out.println("daje oddech");
      }

      for (Integer direction : getNeighbors(stoneColor)) {
         if (neighbors[direction].thereAreOtherBreaths()) {
            this.giveBreath(direction);
         } else {
            dependent[direction] = true;
         }
      }

      if (thereAreOtherBreaths()) {
         for (Integer direction : getNeighbors(stoneColor)) {
            dependent[direction] = true;
         }
      }
//      System.out.println(breathCount);
   }

   /**
    * Sprawdza sąsiadów i aktualizuje oddechy oraz zależności
    */
   private void checkNeighborsV2() {
      //zabiera oddechy wszystkim sąsiadom przeciwnego koloru
      for (Integer direction : getNeighbors(stoneColor.opposite())) {
         neighbors[direction].looseBreath(this, this, neighbors[direction]);
      }

      //ustawia sobie oddechy od pustych sąsiadów
      for (Integer direction : getNeighbors(null)) {
         this.inheritBreath(neighbors[direction]);
      }

      List<Integer> sameColorNeighbors = getNeighbors(stoneColor);
      if (!sameColorNeighbors.isEmpty()) {

         for (Integer direction : sameColorNeighbors) {
            this.inheritBreath(neighbors[direction].breathTiles);
         }

         this.looseBreath(this, this, this);

         for (Integer direction : sameColorNeighbors) {
            neighbors[direction].inheritBreath(this.breathTiles);
            neighbors[direction].looseBreath(this, this, this);
         }
      }


   }

   private boolean onlyBreath(GoTile tile) {
      return breathTiles.contains(tile) && breathTiles.size() == 1;
   }
}
