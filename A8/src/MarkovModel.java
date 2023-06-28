import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Assignment 08
 * Authors:Maximilian Wilhelm and Christopher Kolberg
 * <p>
 * Simple implementation of a Markov model. You can implement all tasks asked for in the assignment in this class skeleton.
 */
public class MarkovModel {

    /*
    The number of states of the model.
     */
    private int noStates;

    /**
     * {@link ArrayList} representation of the states of this model. The single letter {@link String} at the first position (index 0) represents the first state, etc.
     */
    private ArrayList<String> states;

    /**
     * The transition matrix of this model.
     */
    private double[][] transitionMatrix;

    /**
     * Constructor for instances of this class.
     */
    public MarkovModel() {
        this.noStates = 0;
        this.states = new ArrayList<>();
        this.transitionMatrix = null;
    }

    /**
     * Read in a Markov model
     *
     * @param r the Reader
     * @throws Exception
     */
    public void readModel(Reader r) throws Exception {
        StreamTokenizer st = new StreamTokenizer(r);
        st.parseNumbers();
        st.commentChar('#');
        if (st.nextToken() != StreamTokenizer.TT_NUMBER)
            throw new IOException("Input line " + st.lineno() + ": Number of states expected.");
        this.noStates = (int) st.nval;
        // Read States
        st.wordChars('*', 'z');
        for (int i = 1; i <= this.noStates; i++) {
            st.nextToken();
            this.states.add(st.sval);
        }
        st.wordChars('A', 'z');
        this.transitionMatrix = new double[this.noStates][this.noStates];
        // Read Transition Matrix
        for (int i = 1; i <= this.noStates; i++) {
            for (int j = 1; j <= this.noStates; j++) {
                st.nextToken();
                double x = st.nval;
                this.transitionMatrix[i - 1][j - 1] = x;
            }
        }
    }

    /**
     * Returns the transition probability of fromState to toState stored in this model.
     *
     * @param fromState {@link String} representation of the from state.
     * @param toState   {@link String} representation of the to state.
     * @return The {@link Double} value representing the transition probability of fromState to toState.
     * @throws Exception When either of the states is unknown to this model.
     */
    public double getTransitionProbability(String fromState, String toState) throws Exception {
        if (!this.states.contains(fromState)) {
            throw new Exception("State " + fromState + " not stored in this model.");
        }
        if (!this.states.contains(toState)) {
            throw new Exception("State " + toState + " not stored in this model.");
        }
        return this.transitionMatrix[this.states.indexOf(fromState)][this.states.indexOf(toState)];
    }

    /**
     * Write the current model.
     *
     * @param w The writer.
     * @throws IOException
     */
    public void write(Writer w) throws IOException {
        w.write("# Number of states:\n");
        w.write("" + getNumberOfStates());
        w.write("\n#  State labels:\n");
        w.write(String.join(" ", states));
        w.write("\n# Transition matrix :\n");

        for (int i = 1; i <= this.noStates; i++) {
            for (int j = 1; j <= this.noStates; j++) {
                w.write(" " + this.transitionMatrix[i - 1][j - 1]);
            }
            w.write("\n");
        }
    }

    /**
     * Get the number of states of this model.
     *
     * @return The number of states of this model.
     */
    public int getNumberOfStates() {
        return this.noStates;
    }

    /**
     * Gets the string of known states (one char per state).
     *
     * @return The states as {@link String}.
     */
    public ArrayList<String> getStates() {
        return this.states;
    }

    /**
     * Gets the current transition matrix of this model.
     *
     * @return The transition matrix of this model.
     */
    public double[][] getTransitionMatrix() {
        return this.transitionMatrix;
    }

    /**
     * Uses the current markov chain to simulate data.
     *
     * @return String of simulated states.
     */
    public String simulate() {
        StringBuilder markovChain = new StringBuilder();
        // Set start and end state position
        int beginStatePos = this.states.indexOf("*");
        int endStatePos = this.states.indexOf("+");
        // set the current state to the begin state
        int currentStatePos = beginStatePos;
        // Append begin state to chain
        markovChain.append(this.states.get(currentStatePos));
        Random rnd = new Random();


        while (currentStatePos != endStatePos) {
            // Get a random real number between 0 and 1
            double randomChoice = rnd.nextDouble(0f, 1f);
            double sum = 0;
            // Check which state will be the next by summing up the probabilities for the current line
            // until the sum is greater than the chosen random number
            for (int i = 0; i < this.noStates; i++) {
                sum += this.transitionMatrix[currentStatePos][i];
                if (sum > randomChoice) {
                    // Set the next state to the chosen one
                    currentStatePos = i;
                    // Append the chosen one to the chain
                    markovChain.append(this.states.get(currentStatePos));
                    break;
                }
            }
        }
        return markovChain.toString();
    }


    /**
     * Given a string of states, returns the log-probability with which
     * this Markov chain generates the given string.
     *
     * @param markovChain The string of states
     * @return logP the log-probability for markovChain given the current model.
     */
    public double getLogProbability(String markovChain) {
        double logP = 0.0;
        for(int i = 0; i < markovChain.length()-1; i++){
            try {
                // Score each pair of subsequent characters in the markov chain wrt the transition probability
                logP += Math.log10(getTransitionProbability(markovChain.substring(i, i+1), markovChain.substring(i+1, i+2)));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return logP;
    }

    @SuppressWarnings({"finally", "ReturnInsideFinallyBlock"})
    public String toString() {
        StringWriter st = new StringWriter();
        try {
            write(st);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return st.toString();
        }
    }
}
