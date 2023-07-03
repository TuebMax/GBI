import java.io.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;

/**
 * Assignment 09
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 * <p>
 * Simple implementation of hidden markov models.
 */
@SuppressWarnings("unused")
public class HMM {

    /**
     * The number of states stored in this HMM.
     */
    private int noStates = 0;

    /**
     * The actual states stored in this HMM.
     */
    private ArrayList<Character> states = new ArrayList<>();

    /**
     * The number of symbols stored in this HMM.
     */
    private int noSymbols = 0;

    /**
     * The actual emission symbols stored in this HMM.
     */
    private ArrayList<Character> symbols = new ArrayList<>();

    /**
     * The transition probabilities of states stored in this model (row index; from state, column index; to state).
     */
    private double[][] transitionMatrix;

    /**
     * The emission probabilities of symbols stored in this model (row index; state, column index; symbol).
     */
    private double[][] emissionProbabilities;

    /**
     * Constructor of an empty HMM.
     */
    public HMM() {
    }

    /**
     * Constructor of an HMM from preset property instances.
     *
     * @param states                {@link ArrayList} of states (each as one {@link Character}) to hold.
     * @param symbols               {@link ArrayList} of symbols (each as one {@link Character}) to hold.
     * @param transitionMatrix      2D array of doubles; Specify transition probabilities of states.
     * @param emissionProbabilities 2D array of doubles; Specify symbol emission probabilities per state.
     * @throws InvalidParameterSpecException When the passed matrix dimensions do not match the specified states and symbols.
     */
    public HMM(ArrayList<Character> states, ArrayList<Character> symbols, double[][] transitionMatrix, double[][] emissionProbabilities) throws InvalidParameterSpecException {
        this.noStates = states.size();
        this.states = states;
        this.noSymbols = symbols.size();
        this.symbols = symbols;
        this.transitionMatrix = transitionMatrix;
        this.emissionProbabilities = emissionProbabilities;
        if (this.transitionMatrix.length != this.noStates || this.transitionMatrix[0].length != this.noStates)
            throw new InvalidParameterSpecException("The dimensions of the transition matrix have to match the no. states.");
        if (this.emissionProbabilities.length != this.noStates || this.emissionProbabilities[0].length != this.noSymbols)
            throw new InvalidParameterSpecException("The dimensions of the emission probabilities have to match the no. symbols (columns) and no. states (rows).");
    }

    /**
     * Reads a {@link HMM} from a {@link String}.
     *
     * @param str The {@link String} containing a description of the HMM.
     * @return {@link HMM} read from passed {@link String}.
     */
    public static HMM valueOf(String str) {
        return read(new StringReader(str));
    }

    /**
     * Reads a {@link HMM} from a {@link Reader} instance.
     *
     * @param r {@link Reader} instance to read HMM model from.
     */
    public static HMM read(Reader r) {

        // Read file into string.
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(r)) {
            while((line = br.readLine()) != null){
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Parse string into HMM.

        // Parse number of states and states.
        String[] lineSplit = sb.toString().split("\n");
        int noStates = Integer.parseInt(lineSplit[1]);
        ArrayList<Character> states = new ArrayList<>();
        String statesString = lineSplit[3].trim().replaceAll("\\s+", "");
        for (int i = 0; i < statesString.length(); i++) {
            states.add(statesString.charAt(i));
        }

        // Parse number of symbols and symbols.
        int noSymbols = Integer.parseInt(lineSplit[5]);
        ArrayList<Character> symbols = new ArrayList<>();
        String symbolsString = lineSplit[7].trim().replaceAll("\\s+", "");
        for (int i = 0; i < symbolsString.length(); i++) {
            symbols.add(symbolsString.charAt(i));
        }

        // Parse transition matrix and emission probabilities.
        double[][] transitionMatrix = new double[noStates][noStates];
        for(int i = 9; i < 9 + noStates; i++){
            String[] transitionLine = lineSplit[i].split(" ");
            for (int j = 0; j < transitionLine.length; j++) {
                transitionMatrix[i - 9][j] = Double.parseDouble(transitionLine[j]);
            }
        }

        double[][] emissionProbabilities = new double[noStates][noSymbols];
        for (int i = 10 + noStates; i < 10 + noStates + noStates; i++) {
            String[] emissionLine = lineSplit[i].split(" ");
            for (int j = 0; j < emissionLine.length; j++) {
                emissionProbabilities[i - 10 - noStates][j] = Double.parseDouble(emissionLine[j]);
            }
        }

        try{
            return new HMM(states, symbols, transitionMatrix, emissionProbabilities);
        } catch (InvalidParameterSpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes the HMM model.
     *
     * @param w {@link Writer} to use.
     * @throws IOException If an I/O exception to the writer occurs.
     */
    public void write(Writer w) throws IOException {
        w.write("#####\n");
        w.write("# Number of states:\n" + this.noStates + "\n");
        w.write("# States:\n");
        for (Character state : this.states) {
            w.write(state + " ");
        }
        w.write("\n");
        w.write("# Number of symbols:\n" + this.noSymbols + "\n");
        w.write("# Symbols:\n");
        for (Character symbol : this.symbols) {
            w.write(symbol + " ");
        }
        w.write("\n");
        w.write("# Transition matrix:\n");
        for (int i = 0; i < this.noStates; i++) {
            for (int j = 0; j < this.noStates; j++) {
                w.write(this.transitionMatrix[i][j] + " ");
            }
            w.write("\n");
        }
        w.write("# Emission probabilities:\n");
        for (int i = 0; i < this.noStates; i++) {
            for (int j = 0; j < this.noSymbols; j++) {
                w.write(this.emissionProbabilities[i][j] + " ");
            }
            w.write("\n");
        }
        w.write("#####\n");
        w.flush();
    }

    /**
     * Print a description of the model to a {@link String}.
     *
     * @return {@link String} description of this {@link HMM}.
     */
    public String toString() {
        StringWriter w = new StringWriter();
        try {
            write(w);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return w.toString();
    }

    /**
     * Get the number of states.
     *
     * @return Number of states.
     */
    public int getNoStates() {
        return this.noStates;
    }

    /**
     * Get the name of the i-th state.
     *
     * @param i The rank of the state to return.
     * @return The name of the i-th state.
     */
    public char getStateName(int i) {
        return this.states.get(i);
    }

    /**
     * Get the index of the state with name n or -1 if not stored.
     *
     * @param n The name of the state for which its index shall be retrieved.
     * @return The index of the state with name n or -1 if not stored.
     */
    public int getStateIndex(char n) {
        return this.states.indexOf(n);
    }

    /**
     * Get the number of symbols.
     *
     * @return Number of symbols.
     */
    public int getNoSymbols() {
        return this.noSymbols;
    }

    /**
     * Get the name of the i-th symbol.
     *
     * @param i The rank of the symbol to return.
     * @return The name of the i-th symbol.
     */
    public char getSymbolName(int i) {
        return this.symbols.get(i);
    }

    /**
     * Get the index of the symbol with name n or -1 if not stored.
     *
     * @param n The name of the symbol for which its index shall be retrieved.
     * @return The index of the symbol with name n or -1 if not stored.
     */
    public int getSymbolIndex(char n) {
        return this.symbols.indexOf(n);
    }

    /**
     * Get the transition probability from state s to t.
     *
     * @param s The source state.
     * @param t The target state.
     * @return Transition probability from s to t.
     */
    public double getTransitionProbability(char s, char t) {
        return this.transitionMatrix[getStateIndex(s)][getStateIndex(t)];
    }

    /**
     * Get the transition probability from state s to t (based on state ranks).
     *
     * @param s The source state index.
     * @param t The target state index.
     * @return Transition probability from s to t.
     */
    public double getTransitionProbability(int s, int t) {
        return this.transitionMatrix[s][t];
    }

    /**
     * Get the emission probability of symbol k from state s.
     *
     * @param s The state.
     * @param k The symbol.
     * @return Emission probability of symbol k from state s.
     */
    public double getEmissionProbability(char s, char k) {
        return this.emissionProbabilities[getStateIndex(s)][getSymbolIndex(k)];
    }

    /**
     * Get the emission probability of symbol k from state s (based on ranks).
     *
     * @param s The state index.
     * @param k The symbol index.
     * @return Emission probability of symbol k from state s.
     */
    public double getEmissionProbability(int s, int k) {
        return this.emissionProbabilities[s][k];
    }
}
