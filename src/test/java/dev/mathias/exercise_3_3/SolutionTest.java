package dev.mathias.exercise_3_3;

import junit.framework.TestCase;

public class SolutionTest extends TestCase {

  public void testSolution() {
    assertEquals(1, Solution.solution(new int[]{1, 1, 1}));
    assertEquals(3, Solution.solution(new int[]{1, 2, 3, 4, 5, 6}));
  }
}