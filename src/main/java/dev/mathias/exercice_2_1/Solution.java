package dev.mathias.exercice_2_1;

import java.util.ArrayDeque;

public class Solution {

  public static int solution(int src, int dest) {
    int[][] floor = new int[8][8];
    for (int i = 0; i < 64; i++) {
      floor[i / 8][i % 8] = Integer.MAX_VALUE;
    }
    ArrayDeque<Integer> queue = new ArrayDeque<>();
    queue.addLast(src);
    floor[getX(src)][getY(src)] = 0;
    while (!queue.isEmpty()) {
      int pos = queue.removeFirst();
      int x   = getX(pos);
      int y   = getY(pos);
      if (pos == dest) {
        return floor[x][y];
      } else {
        if (isValidPos(x + 2, y + 1, floor)) {
          floor[x + 2][y + 1] = floor[x][y] + 1;
          queue.addLast(getPos(x + 2, y + 1));
        }
        if (isValidPos(x + 2, y - 1, floor)) {
          floor[x + 2][y - 1] = floor[x][y] + 1;
          queue.addLast(getPos(x + 2, y - 1));
        }
        if (isValidPos(x - 2, y - 1, floor)) {
          floor[x - 2][y - 1] = floor[x][y] + 1;
          queue.addLast(getPos(x - 2, y - 1));
        }
        if (isValidPos(x - 2, y + 1, floor)) {
          floor[x - 2][y + 1] = floor[x][y] + 1;
          queue.addLast(getPos(x - 2, y + 1));
        }
        if (isValidPos(x + 1, y + 2, floor)) {
          floor[x + 1][y + 2] = floor[x][y] + 1;
          queue.addLast(getPos(x + 1, y + 2));
        }
        if (isValidPos(x - 1, y + 2, floor)) {
          floor[x - 1][y + 2] = floor[x][y] + 1;
          queue.addLast(getPos(x - 1, y + 2));
        }
        if (isValidPos(x + 1, y - 2, floor)) {
          floor[x + 1][y - 2] = floor[x][y] + 1;
          queue.addLast(getPos(x + 1, y - 2));
        }
        if (isValidPos(x - 1, y - 2, floor)) {
          floor[x - 1][y - 2] = floor[x][y] + 1;
          queue.addLast(getPos(x - 1, y - 2));
        }
      }
    }
    return 0;
  }

  private static boolean isValidPos(int x, int y, final int[][] floor) {
    return x < 8 && x >= 0 && y < 8 && y >= 0 && Integer.MAX_VALUE == floor[x][y];
  }

  private static int getPos(int x, int y) {
    return y * 8 + x;
  }

  private static int getX(int pos) {
    return pos % 8;
  }

  private static int getY(int pos) {
    return pos / 8;
  }


}
