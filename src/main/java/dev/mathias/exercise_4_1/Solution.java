package dev.mathias.exercise_4_1;
import java.util.stream.IntStream;

public class Solution {

  public static int[][] solution(int numBuns, int numRequired) {
    //The minimal number of key.
    //I use the minus one trick (seen on math.stackexchange) because I am the one that have the missing key
    //for each other group of 2.
    int        minimalNumberOfKeyPerBunny = KCombinations(numBuns - 1, numRequired - 1);
    //This information makes me build the matrix that will hold the solution
    int[][]    solution                   = new int[numBuns][minimalNumberOfKeyPerBunny];
    //Custom combinator, (numBuns - numRequired + 1) because that the number of copy of each distinct key I will need
    //ex: for (4,1), I obviously need 4 same key and for (4, 4), I need 4 distinct key
    Combinator combinator                 = new Combinator(numBuns, numBuns - numRequired + 1);
    //To keep track of which column I am in
    int[]      indexes                    = new int[numBuns];
    while (combinator.hasNext()) {
      int[] combination = combinator.next();
      for (int elm : combination) {
        //Filling the solution
        solution[elm][indexes[elm]] = combinator.getIndex();
        indexes[elm]++;
      }
    }
    return solution;
  }

  // function to find the number of subset that can be made from a bigger set
  private static int KCombinations(int n, int k) {
    return getFactorial(n) / (getFactorial(k) * getFactorial(n - k));
  }

  // factorial calculation
  private static int getFactorial(int f) {
    if (f <= 1) {
      return 1;
    } else {
      return f * getFactorial(f - 1);
    }
  }

  static class Combinator {

    private int[][] listSubset;
    int index;

    public Combinator(int set, int subset) {
      //setting the index of iterator
      this.index = -1;
      int currentSubsetIndex = 0;
      //Get number of possible combinations to declare static array
      this.listSubset = new int[KCombinations(set, subset)][subset];
      int[] input = IntStream.range(0, set).toArray();
      int[] s     = new int[subset];

      if (subset <= input.length) {
        for (int i = 0; i < subset; i++) {
          s[i] = i;
        }
        System.arraycopy(s, 0, listSubset[currentSubsetIndex], 0, subset);
        currentSubsetIndex++;
        while (true) {
          int i = subset - 1;
          //Check which position can be incremented, if none, we have found all combinations.
          while (i >= 0 && s[i] == input.length - subset + i) {
            i--;
          }
          if (i < 0) {
            break;
          }
          //increase the current position and fill the right part of the subset.
          s[i]++;
          for (int j = i + 1; j < subset; j++) {
            s[j] = s[j - 1] + 1;
          }
          System.arraycopy(s, 0, listSubset[currentSubsetIndex], 0, subset);
          currentSubsetIndex++;
        }
      }
    }

    boolean hasNext() {
      return this.listSubset.length - 1 != index;
    }

    int[] next() {
      this.index++;
      return this.listSubset[this.index];
    }

    int getIndex() {
      return this.index;
    }
  }

}
