package dev.mathias.exercice_3_2;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * After Having Issues with one test case, I realized my solution with a weight map or a heat map. This solution could not be solved with my initial code:
 * <pre>
 *        {{0, 0, 0, 0, 0, 0},
 *        {1, 1, 1, 1, 1, 0},
 *        {0, 0, 0, 0, 0, 0},
 *        {0, 1, 1, 1, 1, 1},
 *        {0, 1, 1, 1, 1, 1},
 *        {0, 0, 0, 0, 1, 0}}
 * </pre>
 * I decided to make a heatmap that will increase by a big arbitrary number when it crosses a wall and a small number if it does not.
 * In this way:
 * If a path with breaking a wall is faster, it will return early with a faster solution.
 * If breaking a wall does not return early, paths that did not break a wall will be allowed to revisit each node because its weight will always be inferior.
 * It poses an issues for matrix filled with 0, cause the stack is constantly filled with the same value.
 * To address this issue, we just check If a node is already in the stack to avoid duplication.
 */
public class Solution {

  public static int solution(int[][] map) {
    int                    xSize  = map.length - 1;
    int                    ySize  = map[0].length - 1;
    int[][]                weight = new int[map.length][map[0].length];
    ArrayDeque<Coordinate> stack  = new ArrayDeque<>();
    stack.addLast(new Coordinate(0, 0, 1, false));
    Arrays.stream(weight).forEach(ints -> Arrays.fill(ints, Integer.MAX_VALUE));
    weight[0][0] = 0;
    while (!stack.isEmpty()) {
      Coordinate currentCoordinate = stack.pollFirst();
      if (currentCoordinate.x == xSize && currentCoordinate.y == ySize) {
        return currentCoordinate.numberOfMove;
      }
      if (currentCoordinate.canBunnyGoDown(map, xSize, weight)) {
        Coordinate next = currentCoordinate.down(map, weight);
        if (!stack.contains(next)) {
          stack.addLast(next);
        }
      }
      if (currentCoordinate.canBunnyGoUp(map, weight)) {
        Coordinate next = currentCoordinate.up(map, weight);
        if (!stack.contains(next)) {
          stack.addLast(next);
        }
      }
      if (currentCoordinate.canBunnyGoLeft(map, weight)) {
        Coordinate next = currentCoordinate.left(map, weight);
        if (!stack.contains(next)) {
          stack.addLast(next);
        }
      }
      if (currentCoordinate.canBunnyGoRight(map, ySize, weight)) {
        Coordinate next = currentCoordinate.right(map, weight);
        if (!stack.contains(next)) {
          stack.addLast(next);
        }
      }
    }
    return 1;
  }

  static class Coordinate {

    public int     numberOfMove;
    public boolean isWallDestroyed;
    public int     x;
    public int     y;

    public Coordinate(int x, int y, int numberOfMove, boolean isWallDestroyed) {
      this.x               = x;
      this.y               = y;
      this.numberOfMove    = numberOfMove;
      this.isWallDestroyed = isWallDestroyed;
    }

    public Coordinate down(int[][] map, int[][] weight) {
      weight[this.x + 1][this.y] = map[this.x + 1][this.y] == 1 ? weight[this.x][this.y] + 100000 : weight[this.x][this.y] + 1;
      return new Coordinate(this.x + 1, this.y, this.numberOfMove + 1, map[this.x + 1][this.y] == 1 || this.isWallDestroyed);
    }

    public Coordinate up(int[][] map, int[][] weight) {
      weight[this.x - 1][this.y] = map[this.x - 1][this.y] == 1 ? weight[this.x][this.y] + 100000 : weight[this.x][this.y] + 1;
      return new Coordinate(this.x - 1, this.y, this.numberOfMove + 1, map[this.x - 1][this.y] == 1 || this.isWallDestroyed);
    }

    public Coordinate left(int[][] map, int[][] weight) {
      weight[this.x][this.y - 1] = map[this.x][this.y - 1] == 1 ? weight[this.x][this.y] + 100000 : weight[this.x][this.y] + 1;
      return new Coordinate(this.x, this.y - 1, this.numberOfMove + 1, map[this.x][this.y - 1] == 1 || this.isWallDestroyed);
    }

    public Coordinate right(int[][] map, int[][] weight) {
      weight[this.x][this.y + 1] = map[this.x][this.y + 1] == 1 ? weight[this.x][this.y] + 100000 : weight[this.x][this.y] + 1;
      return new Coordinate(this.x, this.y + 1, this.numberOfMove + 1, map[this.x][this.y + 1] == 1 || this.isWallDestroyed);
    }

    public boolean canBunnyGoDown(int[][] map, int xSize, int[][] weight) {
      if (this.x + 1 <= xSize && weight[this.x + 1][this.y] > weight[this.x][this.y]) {
        return (map[this.x + 1][this.y] != 1 || !isWallDestroyed);
      }
      return false;
    }

    public boolean canBunnyGoUp(int[][] map, int[][] weight) {
      if (this.x - 1 >= 0 && weight[this.x - 1][this.y] > weight[this.x][this.y]) {
        return map[this.x - 1][this.y] != 1 || !isWallDestroyed;
      }
      return false;
    }

    public boolean canBunnyGoLeft(int[][] map, int[][] weight) {
      if (this.y - 1 >= 0 && weight[this.x][this.y - 1] > weight[this.x][this.y]) {
        return map[this.x][this.y - 1] != 1 || !isWallDestroyed;
      }
      return false;
    }

    public boolean canBunnyGoRight(int[][] map, int ySize, int[][] weight) {
      if (this.y + 1 <= ySize && weight[this.x][this.y + 1] > weight[this.x][this.y]) {
        return map[this.x][this.y + 1] != 1 || !isWallDestroyed;
      }
      return false;
    }

    public boolean equals(final Object o) {
      Coordinate c = (Coordinate) o;
      return this.numberOfMove == c.numberOfMove &&
             this.isWallDestroyed == c.isWallDestroyed &&
             this.x == c.x &&
             this.y == c.y;
    }

  }
}
