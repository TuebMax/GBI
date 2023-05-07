import java.util.Arrays;

/**
 * Assignment 02
 * Authors: Christopher Kolberg und Maximilian Wilhelm
 */

/**
 * Class implementing the Smith-Waterman algorithm for local sequence alignment.
 */
public class SmithWatermanAligner {

    /**
     * Integer value used to score matches during DP recurrence.
     */
    private final int matchScore;

    /**
     * Integer value used to score mismatches during DP recurrence.
     */
    private final int mismatchScore;

    /**
     * Integer value used to penalize gaps during DP recurrence.
     */
    private final int gapPenalty;

    /**
     * First sequence to align.
     */
    private final String s1;

    /**
     * Second sequence to align.
     */
    private final String s2;


    /**
     * Alignment matrix storing the local alignment scores.
     */
    private final int[][] alignmentMatrix;
    /**
     * Traceback matrix storing the traceback information. 3-dimesional array to store the previous cell as a
     * 2-dimensional coordinate (row, column) for each cell in the alignment matrix.
     */
    private final int[][][] tracebackMatrix;
    /**
     * Cell with the highest value in the alignment matrix.
     */
    private int[] bestCell = new int[2];
    /**
     * Cell where the local alignment starts.
     */
    private int[] startCell = new int[2];


    /**
     * Constructor for the SmithWatermanAligner class. Initializes the alignment and traceback matrices.
     *
     * @param matchScore    The score for a match.
     * @param mismatchScore The score for a mismatch.
     * @param gapPenalty    The penalty for a gap.
     * @param s1            The first sequence to align.
     * @param s2            The second sequence to align.
     */
    public SmithWatermanAligner(int matchScore, int mismatchScore, int gapPenalty, String s1, String s2) {
        this.matchScore = matchScore;
        this.mismatchScore = mismatchScore;
        this.gapPenalty = gapPenalty;
        this.s1 = s1;
        this.s2 = s2;
        this.alignmentMatrix = new int[s1.length() + 1][s2.length() + 1];
        this.tracebackMatrix = new int[s1.length() + 1][s2.length() + 1][2];
    }

    /**
     * Calculates the optimal local alignment score for the given sequences and parameters.
     *
     * @return The optimal local alignment score.
     */
    public Integer alignSequences() {
        // Optimal alignment score to be computed and returned.
        int optimalAlignmentScore = Integer.MIN_VALUE;

        // Iterate over the alignment matrix and fill it in by computing the score for each cell.
        for (int i = 0; i < s1.length() + 1; i++) {
            for (int j = 0; j < s2.length() + 1; j++) {
                // For the first row and column, the score is 0.
                if (i == 0 || j == 0) {
                    alignmentMatrix[i][j] = 0;
                    // Convention for traceback matrix: -1 means stop traceback.
                    tracebackMatrix[i][j][0] = -1;
                    tracebackMatrix[i][j][1] = -1;
                } else {
                    // Calculate the score for the current cell and fill in the traceback matrix.
                    calculateCell(i, j);
                    // Update the optimal alignment score if the current cell has a higher score.
                    if (alignmentMatrix[i][j] > optimalAlignmentScore) {
                        optimalAlignmentScore = alignmentMatrix[i][j];
                        bestCell = new int[]{i, j};
                    }
                }
            }
        }
        return optimalAlignmentScore;
    }


    /**
     * Helper function to calculate the value of a cell in the alignment matrix and fill in the traceback matrix at
     * that position.
     *
     * @param i row index of the cell
     * @param j column index of the cell
     */
    private void calculateCell(int i, int j) {
        int match = alignmentMatrix[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? matchScore : mismatchScore);
        int delete = alignmentMatrix[i - 1][j] - gapPenalty;
        int insert = alignmentMatrix[i][j - 1] - gapPenalty;

        if (match > 0 && match >= delete && match >= insert) {
            // Match/mismatch case
            tracebackMatrix[i][j][0] = i - 1;
            tracebackMatrix[i][j][1] = j - 1;
            alignmentMatrix[i][j] = match;
        } else if (delete > 0 && delete >= insert) {
            // Deletion case
            tracebackMatrix[i][j][0] = i - 1;
            tracebackMatrix[i][j][1] = j;
            alignmentMatrix[i][j] = delete;
        } else if (insert > 0) {
            // Insertion case
            tracebackMatrix[i][j][0] = i;
            tracebackMatrix[i][j][1] = j - 1;
            alignmentMatrix[i][j] = insert;
        } else {
            // Maximum score is 0; traceback stops here.
            tracebackMatrix[i][j][0] = -1;
            tracebackMatrix[i][j][1] = -1;
            alignmentMatrix[i][j] = 0;
        }

    }

    /**
     * returns the local alignment by tracing back through the traceback matrix.
     *
     * @return local alignment
     */
    public String traceback() {
        // Feel free to implement an own format/class to represent aligned sequences!
        StringBuilder alignmentStringS1 = new StringBuilder();
        StringBuilder alignmentStringS2 = new StringBuilder();
        // Go through the traceback matrix to reconstruct one optimal local alignments.
        // Start at the best cell and stop when the traceback reaches a cell with value -1.
        int[] currentCell = bestCell;
        while (!Arrays.equals(tracebackMatrix[currentCell[0]][currentCell[1]], new int[]{-1, -1})) {
            int[] previousCell = tracebackMatrix[currentCell[0]][currentCell[1]];

            if (previousCell[0] == currentCell[0] - 1 && previousCell[1] == currentCell[1] - 1) {
                // Match/mismatch case.
                alignmentStringS1.append(s1.charAt(currentCell[0] - 1));
                alignmentStringS2.append(s2.charAt(currentCell[1] - 1));
            } else if (previousCell[0] == currentCell[0] - 1) {
                // Deletion case.
                alignmentStringS1.append(s1.charAt(currentCell[0] - 1));
                alignmentStringS2.append("-");
            } else {
                // Insertion case.
                alignmentStringS1.append("-");
                alignmentStringS2.append(s2.charAt(currentCell[1] - 1));
            }
            currentCell = previousCell;
        }
        // Set the start cell of the alignment.
        this.startCell = currentCell;
        // Reverse the alignment strings to get the correct order.
        return alignmentStringS1.reverse() + "\n" + alignmentStringS2.reverse();
    }

    public int[] getStartCell() {
        return startCell;
    }

}
