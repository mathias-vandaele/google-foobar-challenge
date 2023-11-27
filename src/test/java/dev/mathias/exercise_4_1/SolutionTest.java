package dev.mathias.exercise_4_1;

import static org.junit.Assert.assertArrayEquals;

import junit.framework.TestCase;

public class SolutionTest extends TestCase {

  public void testSolution1() {
    assertArrayEquals(new int[][]{{0}, {0}},
                      Solution.solution(2, 1));

  }

  public void testSolution2(){
    assertArrayEquals(new int[][]{{0}, {1}, {2}, {3}},
                      Solution.solution(4, 4));

  }

  public void testSolution3(){
    assertArrayEquals(new int[][]{{0, 1, 2, 3, 4, 5},
                                  {0, 1, 2, 6, 7, 8},
                                  {0, 3, 4, 6, 7, 9},
                                  {1, 3, 5, 6, 8, 9},
                                  {2, 4, 5, 7, 8, 9}},
                      Solution.solution(5, 3));
  }
}