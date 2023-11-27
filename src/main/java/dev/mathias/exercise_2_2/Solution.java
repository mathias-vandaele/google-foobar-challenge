package dev.mathias.exercise_2_2;
public class Solution {

  public static int[] solution(int[] pegs) {
    int[] gearValues = new int[pegs.length];
    // Rn is the gear radius.
    // if odd then: R0 = 2 * (Σ (-1)^k * dk)
    // if even then : R0 = (2 * (Σ (-1)^k * dk) / 3)
    int denominator  = pegs.length % 2 == 0 ? 3 : 1;
    int currentValue = pegs[pegs.length - 1] - pegs[pegs.length - 2];
    for (int i = pegs.length - 2; i > 0; i--) {
      currentValue = pegs[i] - pegs[i - 1] - currentValue;
    }
    currentValue  = currentValue * 2;
    gearValues[0] = currentValue / denominator;
    if (gearValues[0] > 0) {
      for (int i = 1; i < gearValues.length; i++) {
        gearValues[i] = (pegs[i] - pegs[i - 1]) - gearValues[i - 1];
        if (gearValues[i] <= 0) {
          return new int[]{-1, -1};
        }
      }
    } else {
      return new int[]{-1, -1};
    }
    return new int[]{currentValue / gcd(currentValue, denominator), denominator / gcd(currentValue, denominator)};
  }

  private static int gcd(int a, int b) {
    if (b == 0) {
      return a;
    }
    return gcd(b, a % b);
  }

}
