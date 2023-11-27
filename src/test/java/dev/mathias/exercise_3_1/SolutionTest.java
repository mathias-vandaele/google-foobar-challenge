package dev.mathias.exercise_3_1;

import static org.junit.Assert.assertArrayEquals;

import junit.framework.TestCase;

public class SolutionTest extends TestCase {

  public void testSolution() {
    assertArrayEquals(new int[]{1, 1, 1, 2, 5}, Solution.solution(new int[][]{{0, 0, 0, 0, 3, 5, 0, 0, 0, 2},
                                                                              {0, 0, 4, 0, 0, 0, 1, 0, 0, 0},
                                                                              {0, 0, 0, 4, 4, 0, 0, 0, 1, 1},
                                                                              {13, 0, 0, 0, 0, 0, 2, 0, 0, 0},
                                                                              {0, 1, 8, 7, 0, 0, 0, 1, 3, 0},
                                                                              {1, 7, 0, 0, 0, 0, 0, 2, 0, 0},
                                                                              {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                                              {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                                              {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                                              {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}));
  }
}