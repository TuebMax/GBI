/**
 * Assignment 01
 * Authors: Christopher Kolberg, Maximiliam Wilhelm
 */

public class EditDistance {

    /**
     *      Exercise 3 - compute the edit distance using Dynamic Programming
     *
     */

    public int computeEditDistance(Fasta fasta1, Fasta fasta2){
        String sequence1 = fasta1.getSequence();
        String sequence2 = fasta2.getSequence();
        int[][] table = new int[sequence1.length() + 1][sequence2.length() + 1];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (i == 0) {
                    table[i][j] = j;
                } else if (j == 0) {
                    table[i][j] = i;
                } else if (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) {
                    table[i][j] = table[i - 1][j - 1];
                } else {
                    table[i][j] = Math.min(Math.min(table[i - 1][j - 1] + 1, table[i][j - 1] + 1), table[i - 1][j] + 1);
                }
            }
        }
        return table[sequence1.length()][sequence2.length()];
    }

}
