import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Assignment 04
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 * <p>
 * Class implementing the Needleman-Wunsch algorithm for global pairwise sequence alignment. A scoring matrix is used to
 * get the match/mismatch scores for each pair of characters. The algorithm is implemented using dynamic programming.
 */
public class NeedlemanWunsch {

    /**
     * {@link ScoringMatrix} object to be used for global pairwise sequence alignment.
     */
    private final ScoringMatrix scoringMatrix;

    private final int gapPenalty;

    private final char[] sequence1;

    private final char[] sequence2;

    /**
     * Alignment matrix storing the local alignment scores.
     */
    private final int[][] alignmentMatrix;
    /**
     * Traceback matrix storing the traceback information. 3-dimesional array to store the previous cell as a
     * 2-dimensional coordinate (row, column) for each cell in the alignment matrix.
     */
    private final int[][][] tracebackMatrix;


    public NeedlemanWunsch(ScoringMatrix scoringMatrix, int gapPenalty, String sequence1, String sequence2) {
        this.scoringMatrix = scoringMatrix;
        this.gapPenalty = gapPenalty;
        this.sequence1 = sequence1.toCharArray();
        this.sequence2 = sequence2.toCharArray();
        this.alignmentMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];
        this.tracebackMatrix = new int[sequence1.length() + 1][sequence2.length() + 1][2];
    }

    public void alignSequences() {
        // Fill Score- and Traceback matrix here.
        // Iterate over the alignment matrix and fill it in by computing the score for each cell.
        for (int i = 0; i < this.sequence1.length + 1; i++) {
            for (int j = 0; j < this.sequence2.length + 1; j++) {
                // For the first row and column, the score is 0.
                if (i == 0 && j == 0) {
                    this.alignmentMatrix[i][j] = 0;
                    // Convention for traceback matrix: -1 is the initial value.
                    this.tracebackMatrix[i][j][0] = -1;
                    this.tracebackMatrix[i][j][1] = -1;
                } else if (i == 0) {
                    this.alignmentMatrix[i][j] = this.alignmentMatrix[i][j - 1] - this.gapPenalty;
                    this.tracebackMatrix[i][j][0] = i;
                    this.tracebackMatrix[i][j][1] = j - 1;
                } else if (j == 0) {
                    this.alignmentMatrix[i][j] = this.alignmentMatrix[i - 1][j] - this.gapPenalty;
                    this.tracebackMatrix[i][j][0] = i - 1;
                    this.tracebackMatrix[i][j][1] = j;
                } else {
                    // Calculate the score for the current cell and fill in the traceback matrix.
                    calculateCell(i, j);
                }
            }
        }
    }

    /**
     * Helper function to calculate the value of a cell in the alignment matrix and fill in the traceback matrix at
     * that position.
     *
     * @param i row index of the cell
     * @param j column index of the cell
     */
    private void calculateCell(int i, int j) {
        int match;
        if ((this.sequence1[i - 1] == this.sequence2[j - 1]) && (this.sequence1[i - 1] == '-')) {
            // Special case for matching gaps.
            match = this.alignmentMatrix[i - 1][j - 1];
        } else {
            if(this.sequence1[i-1] == '-' || this.sequence2[j-1] == '-'){
                match = this.alignmentMatrix[i - 1][j - 1] - this.gapPenalty;
            }else{
                // Match case
                match = this.alignmentMatrix[i - 1][j - 1] + this.scoringMatrix.getScore(this.sequence1[i - 1], this.sequence2[j - 1]);
            }
        }
        int delete = this.alignmentMatrix[i - 1][j] - this.gapPenalty;
        int insert = this.alignmentMatrix[i][j - 1] - this.gapPenalty;

        if (match >= delete && match >= insert) {
            // Match/mismatch case
            this.tracebackMatrix[i][j][0] = i - 1;
            this.tracebackMatrix[i][j][1] = j - 1;
            this.alignmentMatrix[i][j] = match;
        } else if (delete >= insert) {
            // Deletion case
            this.tracebackMatrix[i][j][0] = i - 1;
            this.tracebackMatrix[i][j][1] = j;
            this.alignmentMatrix[i][j] = delete;
        } else {
            // Insertion case
            this.tracebackMatrix[i][j][0] = i;
            this.tracebackMatrix[i][j][1] = j - 1;
            this.alignmentMatrix[i][j] = insert;
        }

    }

    public List<String> getAlignedSequences() {
        // Run traceback to obtain aligned sequences.
        StringBuilder alignmentStringS1 = new StringBuilder();
        StringBuilder alignmentStringS2 = new StringBuilder();
        // Go through the traceback matrix to reconstruct one optimal global alignments.
        // Start at the lower right cell and stop when the traceback reaches a cell with value -1.
        int[] currentCell = new int[]{sequence1.length, sequence2.length};
        while (!Arrays.equals(tracebackMatrix[currentCell[0]][currentCell[1]], new int[]{-1, -1})) {
            int[] previousCell = tracebackMatrix[currentCell[0]][currentCell[1]];

            if (previousCell[0] == currentCell[0] - 1 && previousCell[1] == currentCell[1] - 1) {
                // Match/mismatch case.
                alignmentStringS1.append(this.sequence1[currentCell[0] - 1]);
                alignmentStringS2.append(this.sequence2[currentCell[1] - 1]);
            } else if (previousCell[0] == currentCell[0] - 1) {
                // Deletion case.
                alignmentStringS1.append(this.sequence1[currentCell[0] - 1]);
                alignmentStringS2.append("-");
            } else {
                // Insertion case.
                alignmentStringS1.append("-");
                alignmentStringS2.append(this.sequence2[currentCell[1] - 1]);
            }
            currentCell = previousCell;
        }
        // Reverse the alignment strings to get the correct order.
        ArrayList<String> alignedStrings = new ArrayList<>();
        alignedStrings.add(alignmentStringS1.reverse().toString());
        alignedStrings.add(alignmentStringS2.reverse().toString());
        return alignedStrings;
    }
}
