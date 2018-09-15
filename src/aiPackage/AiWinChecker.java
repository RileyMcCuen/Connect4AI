package aiPackage;

/**
 * 
 * @author Riley McCuen
 *
 *         Provides useful methods for the minimax algorithm in the form of the
 *         heuristic and getting the move closest to the middle of the board as
 *         possible.
 */

public class AiWinChecker {

	/**
	 * This checks to see if all of the values in an array are equal.
	 * 
	 * @param values - an array of values.
	 * 
	 * @return true if the values are all equal and false otherwise.
	 */
	public static boolean checkArrayEquality(int[] values) {
		for (int i = 0; i < values.length; ++i) {
			if (values[0] != values[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks the value of a given board in the vertical direction.
	 * 
	 * @param row    - the starting row of the 4 disc check.
	 * @param column - the starting column of the 4 disc check.
	 * 
	 * @return The value of the check with the given initial row and column.
	 */
	public static int checkVerticalHeuristic(int row, int column, int[][] board) {
		int value = board[row][column];
		value = value + board[row + 1][column];
		value = value + board[row + 2][column];
		value = value + board[row + 3][column];
		if (value == 4 || value == -4) {
			return (value * 25);
		}
		if (value == 3 || value == -3) {
			return Integer.signum(value);
		}
		return 0;
	}

	/**
	 * Checks the value of a given board in the horizontal direction.
	 * 
	 * @param row    - the starting row of the 4 disc check.
	 * @param column - the starting column of the 4 disc check.
	 * 
	 * @return The value of the check with the given initial row and column.
	 */
	public static int checkHorizontalHeuristic(int row, int column, int[][] board) {
		int value = board[row][column];
		value = value + board[row][column + 1];
		value = value + board[row][column + 2];
		value = value + board[row][column + 3];
		if (value == 4 || value == -4) {
			return (value * 25);
		}
		if (value == 3 || value == -3) {
			return Integer.signum(value);
		}
		return 0;
	}

	/**
	 * Checks the value of a given board in the diagonal up direction.
	 * 
	 * @param row    - the starting row of the 4 disc check.
	 * @param column - the starting column of the 4 disc check.
	 * 
	 * @return The value of the check with the given initial row and column.
	 */
	public static int checkDiagonalUpHeuristic(int row, int column, int[][] board) {
		int value = board[row][column];
		value = value + board[row + 1][column - 1];
		value = value + board[row + 2][column - 2];
		value = value + board[row + 3][column - 3];
		if (value == 4 || value == -4) {
			return (value * 25);
		}
		if (value == 3 || value == -3) {
			return Integer.signum(value);
		}
		return 0;
	}

	/**
	 * Checks the value of a given board in the diagonal down direction.
	 * 
	 * @param row    - the starting row of the 4 disc check.
	 * @param column - the starting column of the 4 disc check.
	 * 
	 * @return The value of the check with the given initial row and column.
	 */
	public static int checkDiagonalDownHeuristic(int row, int column, int[][] board) {
		int value = board[row][column];
		value = value + board[row + 1][column + 1];
		value = value + board[row + 2][column + 2];
		value = value + board[row + 3][column + 3];
		if (value == 4 || value == -4) {
			return (value * 25);
		}
		if (value == 3 || value == -3) {
			return Integer.signum(value);
		}
		return 0;
	}

	/**
	 * This is what the computer uses to evaluate different board states. A high
	 * score is good and a low score is bad for the computer. Each run through of
	 * the for loop uses a different initial row and initial column for each of the
	 * checking directions. By the end of the nested for loop a total score is
	 * calculated based on all possible 4 in a row positions. If an actual four in a
	 * row is found an impossibly large value is returned to indicate that this
	 * board state is terminal.
	 * 
	 * @return - the integer representing the value of a given board state.
	 */
	public static int computerheuristic(int[][] testBoard) {
		int compScore = 0;
		for (int row = 0; row < 6; ++row) {
			for (int column = 0; column < 7; ++column) {
				if (row < 3) {
					int vert = checkVerticalHeuristic(row, column, testBoard);
					if (vert == 100 || vert == -100) {
						return vert;
					}
					compScore = compScore + vert;
				}
				if (column < 4) {
					int horiz = checkHorizontalHeuristic(row, column, testBoard);
					if (horiz == 100 || horiz == -100) {
						return horiz;
					}
					compScore = compScore + horiz;
				}
				if (column > 2 && row < 3) {
					int up = checkDiagonalUpHeuristic(row, column, testBoard);
					if (up == 100 || up == -100) {
						return up;
					}
					compScore = compScore + up;
				}
				if (column < 4 && row < 3) {
					int down = checkDiagonalDownHeuristic(row, column, testBoard);
					if (down == 100 || down == -100) {
						return down;
					}
					compScore = compScore + down;
				}
			}
		}
		return compScore;
	}
}
