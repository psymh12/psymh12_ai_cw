import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Object used for storing the population of solutions, as well as providing functions like objective value calculation.
 */
public class SolutionPop {
    /**
     * Nested ArrayList containing profit-weight paired values from test instance.
     */
    private ArrayList<ArrayList<Double>> instances;
    /**
     * Array containing the population of solutions.
     */
    private int[][] solutions;
    /**
     * Array containing memeplexes.
     */
    private double[][] meme_store;
    /**
     * Contains number of solutions within the population.
     */
    private int pop_size;
    /**
     * Number of profit-weight pairs in test instance.
     */
    private double n;
    /**
     * Maximum allowable weight.
     */
    private double W;
    /**
     * Object of Random class.
     */
    private Random rand = new Random();

    /**
     * <p>Constructor of SolutionPop. Extracts values of n and W from instances. Initialises pop_size, solutions, and
     * meme_store.</p>
     *
     * @param instances Nested ArrayList containing profit-weight paired values from test instance.
     */
    public SolutionPop(ArrayList<ArrayList<Double>> instances) {
        this.instances = instances;

        ArrayList<Double> nW_details = instances.get(0);
        n = nW_details.get(0);
        W = nW_details.get(1);
        instances.remove(0);

        pop_size = (int) n;
        if (pop_size > 100) pop_size = 100;

        solutions = new int[pop_size][(int) n];
        for (int i = 0; i < pop_size; i++) solutions[i] = generateSolution();

        meme_store = new double[pop_size][4];
        for (int x = 0; x < pop_size; x++) {
            double[] meme_element = {(double) rand.nextInt(1, 4), rand.nextDouble(), rand.nextDouble(),
                    (double) rand.nextInt(1, 4)};

            meme_store[x] = meme_element;
        }
    }

    /**
     * <p>Returns instances</p>
     *
     * @return instances.
     */
    public ArrayList<ArrayList<Double>> getInstances() {
        return instances;
    }

    /**
     * <p>Returns the solution at a particular index in the population.</p>
     *
     * @param pop_index Index of wanted solution.
     * @return wanted solution.
     */
    public int[] getSolution(int pop_index) {
        return solutions[pop_index];
    }

    /**
     * <p>Returns the full array of memeplexes.</p>
     *
     * @return array of memeplexes.
     */
    public double[][] getMemeStore() {
        return meme_store;
    }

    /**
     * <p>Returns memeplex at a particular index in the meme store.</p>
     *
     * @param meme_index Index of wanted memeplex.
     * @return wanted memeplex.
     */
    public double[] getMemeplex(int meme_index) {
        return meme_store[meme_index];
    }

    /**
     * <p>Returns an array containing all the solutions in the population.</p>
     *
     * @return array of all solutions in the population.
     */
    public int[][] getSolutions() {
        return solutions;
    }

    /**
     * <p>Replaces the old population entirely with a new population.</p>
     *
     * @param new_solutions new population to replace old population with.
     */
    public void setSolutions(int[][] new_solutions) {
        solutions = new_solutions;
    }

    /**
     * <p>Replaces memeplex at an index of the meme_store with another memeplex.</p>
     *
     * @param new_memeplex new memeplex to replace old memeplex with.
     * @param meme_index   index of memeplex to be replaced.
     */
    public void setMemeplex(double[] new_memeplex, int meme_index) {
        meme_store[meme_index] = new_memeplex;
    }

    /**
     * <p>Returns the number of solutions in the population.</p>
     *
     * @return pop_size
     */
    public int getPopSize() {
        return pop_size;
    }

    /**
     * <p>Returns a randomly generated solution which has a length determined by the variable n.</p>
     *
     * @return randomly generated solution.
     */
    public int[] generateSolution() {
        int[] rand_solution = new int[(int) n];
        for (int i = 0; i < n; i++) {
            double rand_double = rand.nextDouble();
            if (rand_double < 0.5) {
                rand_solution[i] = 0;
            } else {
                rand_solution[i] = 1;
            }
        }
        return rand_solution;
    }

    /**
     * <p>Returns highest objective value in the population.</p>
     *
     * @return highest objective value in the population.
     */
    public double getHighestObjectiveValue() {
        ArrayList<Double> fitness_vals = new ArrayList<>();
        for (int i = 0; i < solutions.length; i++) {
            fitness_vals.add(getObjectiveValue(i));
        }
        return Collections.max(fitness_vals);
    }

    /**
     * <p>Returns lowest objective value in the population.</p>
     *
     * @return lowest objective value in the population.
     */
    public double getLowestObjectiveValue() {
        ArrayList<Double> fitness_vals = new ArrayList<>();
        for (int i = 0; i < solutions.length; i++) {
            fitness_vals.add(getObjectiveValue(i));
        }
        return Collections.min(fitness_vals);
    }

    /**
     * <p>Returns the objective value of a solution at a specified index of the population.</p>
     *
     * @param pop_index Index of solution to be analysed.
     * @return returns objective value of solution at given index of the population.
     */
    public double getObjectiveValue(int pop_index) {
        double score = 0.0;
        double weight = 0.0;
        int i = 0;
        for (int bit_num : solutions[pop_index]) {
            if (bit_num == 1) {
                score += instances.get(i).get(0);

                weight += instances.get(i).get(1);
                if (weight > W) return 0.0;
            }
            i++;
        }
        return score;
    }

    /**
     * <p>Returns objective value of a given solution.</p>
     *
     * @param input_solution solution to be analysed.
     * @return objective value of given solution.
     */
    public double getObjectiveValue(int[] input_solution) {
        double score = 0.0;
        double weight = 0.0;
        int i = 0;
        for (int bit_num : input_solution) {
            if (bit_num == 1) {
                score += instances.get(i).get(0);

                weight += instances.get(i).get(1);
                if (weight > W) return 0.0;
            }
            i++;
        }
        return score;
    }
}
