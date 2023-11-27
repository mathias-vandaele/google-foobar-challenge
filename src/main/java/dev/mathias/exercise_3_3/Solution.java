package dev.mathias.exercise_3_3;


public class Solution {

  public static int solution(int[] l) {
    int counter = 0;
    int size    = l.length;
    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) {
        for (int k = j + 1; k < size; k++) {
          if (l[j] % l[i] == 0 && l[k] % l[j] == 0) {
            counter++;
          }
        }
      }
    }
    return counter;
  }

}
