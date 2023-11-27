package dev.mathias.exercise_3_1;

public class Solution {

  public static int[] solution(int[][] m) {
    if (m.length == 1) {
      return new int[]{1, 1};
    }
    int[][][] probabilityMatrix = createProbabilityMatrix(m);
    boolean[] indexRowsToKeep   = new boolean[probabilityMatrix.length];
    for (int i = 0; i < probabilityMatrix.length; i++) {
      int sumRow = sumRow(probabilityMatrix, i);
      if (sumRow != 0) {
        indexRowsToKeep[i] = true;
      } else {
        if (i == 0) {
          return new int[]{1, 1};
        }
      }
    }
    int lenght = 0;
    for (final boolean b : indexRowsToKeep) {
      if (b) {
        lenght++;
      }
    }
    int[][][] cleanMatrix = new int[lenght][probabilityMatrix.length][2];
    int       row         = 0;
    for (int i = 0; i < probabilityMatrix.length; i++) {
      if (indexRowsToKeep[i]) {
        cleanMatrix[row] = probabilityMatrix[i];
        row++;
      }
    }
    int[][][] qMatrix         = new int[lenght][lenght][2];
    int[][][] rMatrix         = new int[lenght][probabilityMatrix.length - lenght][2];
    int       qMatrixColIndex = 0;
    int       rMatrixColIndex = 0;
    for (int i = 0; i < cleanMatrix[0].length; i++) {
      if (indexRowsToKeep[i]) {
        for (int j = 0; j < cleanMatrix.length; j++) {
          qMatrix[j][qMatrixColIndex] = cleanMatrix[j][i];
        }
        qMatrixColIndex++;
      } else {
        for (int j = 0; j < cleanMatrix.length; j++) {
          rMatrix[j][rMatrixColIndex] = cleanMatrix[j][i];
        }
        rMatrixColIndex++;
      }
    }

    int[][][] identityMinusQMatrix = identityMatrixMinusGivenMatrix(qMatrix);
    int[][][] inverseMatrix       = inverseMatrix(identityMinusQMatrix);
    int[][]   result               = multiplyMatrix(inverseMatrix, rMatrix)[0];

    int[] finalResult = new int[result.length + 1];

    int notSimplifiedDenominator = result[0][1];
    for (int i = 1; i < result.length; i++) {
      notSimplifiedDenominator = lcd(notSimplifiedDenominator, result[i][1]);
    }
    for (int i = 0; i < result.length; i++) {
      finalResult[i] = result[i][0] * (notSimplifiedDenominator / result[i][1]);
    }
    finalResult[result.length] = notSimplifiedDenominator;
    return finalResult;
  }


  public static int[][][] inverseMatrix(int[][][] matrix) {
    int       n               = matrix.length;
    int[][][] augmentedMatrix = new int[n][2 * n][2];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        augmentedMatrix[i][j]     = matrix[i][j];
        augmentedMatrix[i][j + n] = (i == j) ? new int[]{1, 1} : new int[]{0, 1};
      }
    }
    for (int pivotRow = 0; pivotRow < n; pivotRow++) {
      for (int row = 0; row < n; row++) {
        if (row != pivotRow) {
          int[] factorSubtract = divideFraction(augmentedMatrix[row][pivotRow], augmentedMatrix[pivotRow][pivotRow]);
          for (int column = 0; column < augmentedMatrix[row].length; column++) {
            int[] toRemoveAtCell = multiplyFraction(factorSubtract, augmentedMatrix[pivotRow][column]);
            augmentedMatrix[row][column] = subtractFraction(augmentedMatrix[row][column], toRemoveAtCell);
          }
          int[] factorScale = inverseFraction(augmentedMatrix[row][row]);
          for (int column = 0; column < augmentedMatrix[row].length; column++) {
            augmentedMatrix[row][column] = multiplyFraction(factorScale, augmentedMatrix[row][column]);
          }
        }
      }
    }

    int[][][] result = new int[matrix.length][matrix.length][2];
    for (int i = 0; i < augmentedMatrix.length; i++) {
      int column = 0;
      for (int j = augmentedMatrix[i].length / 2; j < augmentedMatrix[i].length; j++) {
        result[i][column] = augmentedMatrix[i][j];
        column++;
      }
    }
    return result;
  }

  private static int[] inverseFraction(final int[] fraction) {
    return new int[]{fraction[1], fraction[0]};
  }

  public static int[][][] identityMatrixMinusGivenMatrix(int[][][] matrix) {
    int numRows = matrix.length;
    int numCols = matrix[0].length;

    int[][][] result = new int[numRows][numCols][2];

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        if (i == j) {
          result[i][j] = subtractFraction(new int[]{1, 1}, matrix[i][j]);
        } else {
          result[i][j] = subtractFraction(new int[]{0, 1}, matrix[i][j]);
        }
      }
    }

    return result;
  }


  private static int sumRow(int[][][] probabilityMatrix, int row) {
    int result = 0;
    for (int i = 0; i < probabilityMatrix[row].length; i++) {
      result += probabilityMatrix[row][i][0];
    }
    return result;
  }

  private static int[][][] createProbabilityMatrix(int[][] initial) {
    int[][][] probabilityMatrix = new int[initial.length][initial[0].length][2];
    for (int i = 0; i < initial.length; i++) {
      int denominator = 0;
      for (int j = 0; j < initial[0].length; j++) {
        denominator += initial[i][j];
      }
      if (denominator != 0) {
        for (int j = 0; j < initial[0].length; j++) {
          probabilityMatrix[i][j][0] = initial[i][j];
          probabilityMatrix[i][j][1] = denominator;
        }
      }
    }
    return probabilityMatrix;
  }


  public static int[][][] multiplyMatrix(int[][][] matrix1, int[][][] matrix2) {
    int[][][] result = new int[matrix1.length][matrix2[0].length][2];

    for (int row = 0; row < result.length; row++) {
      for (int col = 0; col < result[row].length; col++) {
        int[] cell = new int[]{0, 1};
        for (int i = 0; i < matrix2.length; i++) {
          int tempNumerator = matrix1[row][i][0] * matrix2[i][col][0];
          if ((long) matrix1[row][i][1] * (long) matrix2[i][col][1] > Integer.MAX_VALUE) {
            return matrix1;
          }
          int tempDenominator = matrix1[row][i][1] * matrix2[i][col][1];
          cell = addFraction(cell, new int[]{tempNumerator, tempDenominator});
        }
        result[row][col] = asSimpleFraction(cell[0], cell[1]);
      }
    }
    return result;
  }

  public static int[] addFraction(int[] first, int[] second) {
    if (first[0] == 0) {
      return second;
    }
    if (second[0] == 0) {
      return first;
    }
    long numerator   = (long) first[0] * second[1] + (long) second[0] * first[1];
    long denominator = (long) second[1] * first[1];

    return asSimpleFraction(numerator, denominator);
  }

  public static int[] subtractFraction(int[] first, int[] second) {
    if (first[0] == 0) {
      return new int[]{-second[0], second[1]};
    }
    if (second[0] == 0) {
      return first;
    }
    long numerator   = (long) first[0] * second[1] - (long) second[0] * first[1];
    long denominator = (long) second[1] * first[1];

    return asSimpleFraction(numerator, denominator);
  }

  public static int[] divideFraction(int[] first, int[] second) {
    if (first[0] == 0) {
      return new int[]{0, 1};
    }
    if (second[1] == 0) {
      throw new RuntimeException("division by 0");
    }
    long numerator   = (long) first[0] * second[1];
    long denominator = (long) first[1] * second[0];

    return asSimpleFraction(numerator, denominator);
  }

  public static int[] multiplyFraction(int[] first, int[] second) {
    if (first[0] == 0) {
      return new int[]{0, 1};
    }
    if (second[0] == 0) {
      return new int[]{0, 1};
    }
    long numerator   = (long) first[0] * second[0];
    long denominator = (long) second[1] * first[1];

    return asSimpleFraction(numerator, denominator);
  }

  public static long gcd(long a, long b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  public static int lcd(int a, int b) {
    return (int) (a * b / gcd(a, b));
  }

  public static int[] asSimpleFraction(long a, long b) {
    if (b == 0) {
      return new int[]{0, 1};
    }
    long gcd = gcd(a, b);
    return new int[]{(int) (a / gcd), (int) (b / gcd)};
  }

}
