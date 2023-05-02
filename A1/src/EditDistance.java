/**
 * Assignment 01
 * Authors: Maximilian Wilhelm, Christopher Kolberg
 */

public class EditDistance {

    /**
     *      Exercise 3 - compute the edit distance using Dynamic Programming
     *
     */

    public int computeEditDistance(Fasta fasta1, Fasta fasta2){

        /**
         * Declare 2 sequence-variables
         * and initialize with the sequences of the parameters by using the Get-Method from the Fasta-Class
         */
        String sequence1 = fasta1.getSequence();
        String sequence2 = fasta2.getSequence();

        /**
         * Declare a 2-dimensional array to mimic a table
         * Initialize table with an empty integer array and set the length based on the parameter-sequence length
         * Extend both dimensions by one to guarantee one column and row for the Initialization of the Algorithm
         */
        int[][] table = new int[sequence1.length() + 1][sequence2.length() + 1];

        /**
         * Iterate through every cell of the table
         * For that iterate through every column while iterating through every row
         */
        // row iteration
        for (int i = 0; i < table.length; i++) {
            // column iteration
            for (int j = 0; j < table[i].length; j++) {

                /**
                 * Implement the Algorithm
                 * Initialization for row and column 0
                 * cover all 3 different cases
                 */
                // Initialization step for row 0
                if (i == 0) {
                    table[i][j] = j;
                }
                // Initialization step for column 0
                else if (j == 0) {
                    table[i][j] = i;
                }
                // If the Characters are identical set the value to the value at (i-1, j-1)
                // Because the diagonal option does not add a 1 to the value in that case
                else if (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) {
                    table[i][j] = table[i - 1][j - 1];
                }
                // case 1, 2 and 3 combined
                // if no conditions are fulfilled, the characters in comparison are different
                // In this case it does not matter from which direction the previous value is taken, +1 is always added
                // therefore we search for the smallest previous value by using Math.min() and just add 1
                // Math.min() returns the smallest value of the given parameters.
                else {
                    table[i][j] = Math.min(Math.min(table[i - 1][j - 1] + 1, table[i][j - 1] + 1), table[i - 1][j] + 1);
                }
            }
        }

        // The whole table is returned even though we added one row and one column
        // Because arrays start with the index 0
        return table[sequence1.length()][sequence2.length()];
    }

}
