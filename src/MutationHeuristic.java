import java.util.Arrays;
import java.util.Random;

/**
 * Used for applying mutation heuristics to solutions.
 */
public class MutationHeuristic {
    /**
     * Object of Random object.
     */
    private Random rand = new Random();
    /**
     * Controls success rate of mutating genetic material.
     */
    private double mutation_rate;
    /**
     * Controls the number of times a mutation operator is applied to a solution.
     */
    private double iom;
    /**
     * Controls success rate of mutating memetic material.
     */
    private double innovation_rate;

    /**
     * <p>Constructor of MutationHeuristic. Initialises values of mutation and innovation rate.</p>
     *
     * @param mutation_rate   Value to set mutation rate to.
     * @param innovation_rate Value to set innovation rate to.
     */
    public MutationHeuristic(double mutation_rate, double innovation_rate) {
        this.mutation_rate = mutation_rate;
        this.innovation_rate = innovation_rate;
    }

    /**
     * <p>Setter function used for changing value of iom (Intensity of Mutation) variable.</p>
     *
     * @param iom Value to set Intensity of Mutation to.
     */
    public void setIom(double iom) {
        this.iom = iom;
    }

    /**
     * <p>Mutates a given memeplex. The success rate of mutating each element of the memeplex is controlled
     * by the innovation rate.</p>
     *
     * @param memeplex Memeplex to be mutated.
     * @return state of memeplex after undergoing mutation.
     */
    public double[] mutateMemeplex(double[] memeplex) {
        double[] copy = Arrays.copyOf(memeplex, memeplex.length);
        double index0 = rand.nextInt(1, 4);
        double index3 = rand.nextInt(1, 4);

        if (rand.nextDouble() < innovation_rate) {
            while (index0 == copy[0]) index0 = rand.nextInt(1, 4);
            copy[0] = index0;
        }

        if (rand.nextDouble() < innovation_rate) copy[1] = rand.nextDouble();

        if (rand.nextDouble() < innovation_rate) copy[2] = rand.nextDouble();

        if (rand.nextDouble() < innovation_rate) {
            while (index3 == copy[3]) index3 = rand.nextInt(1, 4);
            copy[3] = index3;
        }

        return copy;
    }

    /**
     * <p>Applies bit flip mutation to a given solution. Depending on the Intensity of Mutation, the operator can be
     * applied multiple times.</p>
     *
     * @param solution Solution that bit flip mutation operator is being applied to.
     * @return state of solution after undergoing mutation.
     */
    public int[] bitFlipWithIoM(int[] solution) {
        int iterations;
        int[] temp_solution = Arrays.copyOf(solution, solution.length);

        if (0.0 <= iom && iom < 0.2) {
            iterations = 1;
        } else if (0.2 <= iom && iom < 0.4) {
            iterations = 2;
        } else if (0.4 <= iom && iom < 0.6) {
            iterations = 3;
        } else if (0.6 <= iom && iom < 0.8) {
            iterations = 4;
        } else if (0.8 <= iom && iom < 1) {
            iterations = 5;
        } else if (iom == 1) {
            iterations = 6;
        } else {
            iterations = 1;
        }

        for (int i = 0; i < iterations; i++) {
            bitFlip(temp_solution);
        }
        return temp_solution;
    }

    /**
     * <p>Applies a single pass of the bit flip mutation operator to the solution.</p>
     *
     * @param solution Solution that bit flip mutation operator is being applied to.
     * @return state of solution after undergoing mutation.
     */
    public int[] bitFlip(int[] solution) {
        int[] temp_solution = Arrays.copyOf(solution, solution.length);

        for (int i = 0; i < temp_solution.length; i++) {
            if (rand.nextDouble() < mutation_rate) {
                if (temp_solution[i] == 1) temp_solution[i] = 0;
                else temp_solution[i] = 1;
            }
        }
        return temp_solution;
    }
}
