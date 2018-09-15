package aiPackage;

/**
 * 
 * @author Riley McCuen
 *
 *         This class is an object that does minimax search with alpha pruning
 *         using traditional Connect4 rules. The board is represented by a 2D
 *         array of integers.
 */

public class MiniMaxAlphaBeta {

	private int turn, move, maxDepth;

	final private int compNumber;

	private int[][] board;

	/**
	 * 
	 * @param turn       - the current turn count.
	 * 
	 * @param move       - the move that the computer wants to make.
	 * 
	 * @param maxDepth   - the maxDepth to search before termination.
	 * 
	 * @param compNumber - the number that the computer is this turn.
	 * 
	 * @param board      - the current board-state of the game.
	 * 
	 */

	public MiniMaxAlphaBeta(int compNumber) {
		this.turn = 1;
		this.move = 3;
		this.maxDepth = 0;
		this.compNumber = compNumber;
		this.board = new int[6][7];
	}

	/**
	 * Gets the move choice for the computer player.
	 * 
	 * @return an integer representing a win.
	 */
	public int getMove() {
		return this.move;
	}

	/**
	 * Gets the value of the computer. 1 is equivalent to player 1 and -1 is
	 * equivalent to player 2.
	 * 
	 * @return an integer representing the computer.
	 */
	public int getComputerNumber() {
		return this.compNumber;
	}

	/**
	 * A method to update the information that the AI has with the most current
	 * information from the actual game.
	 * 
	 * @param location - the location that the move provided was made at. Index 0 is
	 *                 the row, index 1 is the column.
	 * 
	 * @param playere  - the player number that made the move.
	 * 
	 * @param turn     - the turn that it is at the time of update, this can be
	 *                 useful for the AI to set a depth for the minimax method.
	 */
	public void updateAiInformation(int[] location, int player, int turn) {
		this.board[location[0]][location[1]] = player;
		this.turn = turn;
		return;
	}

	/**
	 * Finds the lowest row of the of the given column on the given board.
	 * 
	 * @param column - the column that the disc is trying to be put in.
	 * 
	 * @param board  - the board that the disc is being located on.
	 * 
	 * @return the row of the lowest move in the column, -10 if the column is full.
	 */
	public int lowestRow(int[][] board, int column) {
		for (int i = 5; i >= 0; --i) {
			if (board[i][column] == 0) {
				return i;
			}
		}
		return -10;
	}

	/**
	 * If there is no sufficiently good move this method will return the a legal
	 * move that is closest to the center of the board, which are generally better
	 * moves than moves near the edges.
	 * 
	 * @param values - an integer array representing the values of each move.
	 * 
	 * @return the move closest to the middle column.
	 */
	public int closestMiddlemove() {
		if (this.board[0][3] == 0) {
			this.move = 3;
			return 3;
		}
		if (this.board[0][2] == 0) {
			this.move = 2;
			return 2;
		}
		if (this.board[0][4] == 0) {
			this.move = 4;
			return 4;
		}
		if (this.board[0][1] == 0) {
			this.move = 1;
			return 1;
		}
		if (this.board[0][5] == 0) {
			this.move = 5;
			return 5;
		}
		if (this.board[0][0] == 0) {
			this.move = 0;
			return 0;
		} else {
			this.move = 6;
			return 6;
		}
	}

	/**
	 * This function runs the minimax algorithm. The best move found is put in
	 * this.move global variable.
	 */
	public void thinkAboutMove(int maxDepth) {
		if (this.turn == 1 || this.turn == 2) {
			return;
		}
		this.maxDepth = maxDepth;
		if (this.compNumber == -1) {
			this.move = this.findMin(0, this.board, -100000, 100000);
		} else {
			this.move = this.findMax(0, this.board, -100000, 100000);
		}
	}

	/**
	 * This is the min method in "minimax". It is used to recursively find the best
	 * move in the game.
	 * 
	 * @param depth - the depth that this method is being called at. When this
	 *              method is first called it should be called at depth 0, then it
	 *              will increment until it equals this.maxDepth.
	 * @param board - the board that the algorithm will be called on. At depth 0
	 *              this is the real game board, but at depths greater than 0 this
	 *              is the board created at the previous depth.
	 * @param alpha - the alpha value is the alpha cut off value used to evaluate
	 *              whether or not the next options in the tree are worthwhile.
	 * @param beta  - the beta value is the beta cut off value used to evaluate
	 *              whether or not the next options in the tree are worthwhile.
	 * @return - the minimum value found from all leaf nodes below this one.
	 */
	public int findMin(int depth, int[][] board, int alpha, int beta) {
		int evaluation = AiWinChecker.computerheuristic(board);
		if (depth == this.maxDepth || evaluation == 100 || evaluation == -100) {
			return evaluation;
		}
		int bestMove = 0;
		int[] values = new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
				Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE };
		for (int move = 3; move < 10; move++) {
			int newMove = move;
			if (newMove > 6) {
				newMove = newMove - 7;
			}
			if (this.board[0][newMove] == 0) {
				int row = this.lowestRow(board, newMove);
				board[row][newMove] = -1;
				values[newMove] = this.findMax((depth + 1), board, alpha, beta);
				board[row][newMove] = 0;
				if (values[newMove] < 100000 && values[newMove] > -100000) {
					beta = Integer.min(values[newMove], beta);
					if (values[newMove] == beta && depth == 0) {
						bestMove = newMove;
					}
					if (alpha >= beta) {
						return -1000;
					}
				}
			}
		}
		if (depth == 0) {
			if (AiWinChecker.checkArrayEquality(values)) {
				return this.closestMiddlemove();
			} else {
				return bestMove;
			}
		}
		return beta;
	}

	/**
	 * This is the max method in "minimax". It is used to recursively find the best
	 * move in the game.
	 * 
	 * @param depth - the depth that this method is being called at. When this
	 *              method is first called it should be called at depth 0, then it
	 *              will increment till it equals this.maxDepth.
	 * @param board - the board that the algorithm will be called on. At depth 0
	 *              this is the real game board, but at depths greater than 0 this
	 *              is the board created at the previous depth.
	 * @param alpha - the alpha value is the alpha cut off value used to evaluate
	 *              whether or not the next options in the tree are worthwhile.
	 * @param beta  - the beta value is the beta cut off value used to evaluate
	 *              whether or not the next options in the tree are worthwhile.
	 * @return - the maximum value found from all leaf nodes below this one.
	 */
	public int findMax(int depth, int[][] board, int alpha, int beta) {
		int evaluation = AiWinChecker.computerheuristic(board);
		if (depth == this.maxDepth || evaluation == 100 || evaluation == -100) {
			return evaluation;
		}
		int bestMove = 0;
		int[] values = new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE,
				Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE };
		for (int move = 3; move < 10; move++) {
			int newMove = move;
			if (newMove > 6) {
				newMove = newMove - 7;
			}
			if (this.board[0][newMove] == 0) {
				int row = this.lowestRow(board, newMove);
				board[row][newMove] = 1;
				values[newMove] = this.findMin((depth + 1), board, alpha, beta);
				board[row][newMove] = 0;
				if (values[newMove] < 100000 && values[newMove] > -100000) {
					alpha = Integer.max(values[newMove], alpha);
					if (values[newMove] == alpha && depth == 0) {
						bestMove = newMove;
					}
					if (alpha >= beta) {
						return 1000;
					}
				}
			}
		}
		if (depth == 0) {
			if (AiWinChecker.checkArrayEquality(values)) {
				return this.closestMiddlemove();
			} else {
				return bestMove;
			}
		}
		return alpha;
	}
}
