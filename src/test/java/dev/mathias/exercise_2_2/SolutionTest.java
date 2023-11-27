package dev.mathias.exercise_2_2;

import java.util.Arrays;
import junit.framework.TestCase;

public class SolutionTest extends TestCase {

  public void testSolution() {
    assertTrue(Arrays.equals(new int[]{12, 1}, Solution.solution(new int[]{4, 30, 50})));
    assertTrue(Arrays.equals(new int[]{-1, -1}, Solution.solution(new int[]{4, 17, 50})));
  }
}