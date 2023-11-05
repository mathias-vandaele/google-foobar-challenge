package dev.mathias.exercice_1;

public class Solution {

  public static int solution(int src) {
    StringBuilder stringBuilder = new StringBuilder();
    int           nb            = 0;
    while (stringBuilder.length() < 10005) {
      if (isPrime(nb)) {
        stringBuilder.append(nb);
      }
      nb++;
    }
    return Integer.parseInt(stringBuilder.substring(src, src + 5));
  }

  public static boolean isPrime(int n) {
    if (n <= 1) {
      return false;
    }
    for (int i = 2; i < Math.sqrt(n); i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }

}
