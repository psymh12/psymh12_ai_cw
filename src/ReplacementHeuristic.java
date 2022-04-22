import java.util.Arrays;
import java.util.Random;

/**
 * Used for applying replacement strategies to a population.
 */
public class ReplacementHeuristic {
    /**
     * Object of Random object.
     */
    private Random rand = new Random();

    /**
     * <p>Replaces worst 2 of a population with the best 2 out of parent and child solutions.</p>
     *
     * @param problem        Used to get information from the population, as well as to set the values of memeplexes.
     * @param p1_index       Index of parent 1 in the population.
     * @param p2_index       Index of parent 2 in the population.
     * @param c1_solution    Binary representation of child 1 solution.
     * @param c2_solution    Binary representation of child 2 solution.
     * @param child_memeplex Memeplex shared by child solutions.
     * @return new population after replacement has been applied.
     */
    public int[][] SteadyStateGAWithStrongElitism(SolutionPop problem, int p1_index, int p2_index,
                                                  int[] c1_solution, int[] c2_solution, double[] child_memeplex) {
        int[][] p_and_c_fitnesses = {
                {(int) problem.getObjectiveValue(p1_index), p1_index},
                {(int) problem.getObjectiveValue(p2_index), p2_index},
                {(int) problem.getObjectiveValue(c1_solution), -1},
                {(int) problem.getObjectiveValue(c2_solution), -2}
        };

        int[][] p_and_c_highest = returnDetailsOfTwoHighestObjectiveValues(p_and_c_fitnesses);

        //transform problem.getSolutions()
        int[][] pop_fitnesses = new int[problem.getPopSize()][2];
        for (int a = 0; a < pop_fitnesses.length; a++) {
            int[] temp_arr = {(int) problem.getObjectiveValue(a), a};
            pop_fitnesses[a] = temp_arr;
        }

        int[][] pop_lowest = returnDetailsOfTwoLowestObjectiveValues(pop_fitnesses);

        int[][] new_pop = Arrays.copyOf(problem.getSolutions(), problem.getSolutions().length);

        for (int i = 0; i < problem.getPopSize(); i++) {
            if (i == pop_lowest[0][1]) {
                if (p_and_c_highest[0][1] == -1) {
                    new_pop[i] = c1_solution;
                    problem.setMemeplex(child_memeplex, i);
                } else if (p_and_c_highest[0][1] == -2) {
                    new_pop[i] = c2_solution;
                    problem.setMemeplex(child_memeplex, i);
                } else {
                    new_pop[i] = new_pop[p_and_c_highest[0][1]];
                    problem.setMemeplex(problem.getMemeplex(p_and_c_highest[0][1]), i);
                }
            } else if (i == pop_lowest[1][1]) {
                if (p_and_c_highest[1][1] == -1) {
                    new_pop[i] = c1_solution;
                    problem.setMemeplex(child_memeplex, i);
                } else if (p_and_c_highest[1][1] == -2) {
                    new_pop[i] = c2_solution;
                    problem.setMemeplex(child_memeplex, i);
                } else {
                    new_pop[i] = new_pop[p_and_c_highest[1][1]];
                    problem.setMemeplex(problem.getMemeplex(p_and_c_highest[1][1]), i);
                }
            }
        }

        return new_pop;
    }

    /**
     * <p>Returns array of objective value and index (if a child solution is selected than index value is set to either -1
     * or -2) value pairs.</p>
     *
     * @param f_and_i Array containing fitness values and index values.
     * @return Objective value and index value details of the two element in the given list with highest objective value.
     */
    public int[][] returnDetailsOfTwoHighestObjectiveValues(int[][] f_and_i) {
        int[][] temp = Arrays.copyOf(f_and_i, f_and_i.length);
        int[][] highest_details = new int[2][2];

        highest_details[0] = returnDetailsWithHighestObjectiveValues(temp);
        temp = deleteDetails(temp, highest_details[0][0]);
        highest_details[1] = returnDetailsWithHighestObjectiveValues(temp);

        return highest_details;
    }

    /**
     * <p>Returns array of objective value and index value pairs.</p>
     *
     * @param f_and_i Array containing fitness values and index values.
     * @return array of objective value and index value details of two elements in population with the two lowest objective
     * values.
     */
    public int[][] returnDetailsOfTwoLowestObjectiveValues(int[][] f_and_i) {
        int[][] temp = Arrays.copyOf(f_and_i, f_and_i.length);
        int[][] lowest_details = new int[2][2];

        lowest_details[0] = returnDetailsWithLowestObjectiveValues(temp);
        temp = deleteDetails(temp, lowest_details[0][0]);
        lowest_details[1] = returnDetailsWithLowestObjectiveValues(temp);

        return lowest_details;
    }


    /**
     * <p>Gives the objective value and index value of the highest objective value element in a given array.</p>
     *
     * @param f_and_i Array containing fitness values and index values.
     * @return array containing details of highest objective value element in given array.
     */
    public int[] returnDetailsWithHighestObjectiveValues(int[][] f_and_i) {
        int[][] temp = Arrays.copyOf(f_and_i, f_and_i.length);
        int largest = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i][0] > largest) largest = temp[i][0];
        }
        for (int x = 0; x < temp.length; x++) {
            if (temp[x][0] == largest) return temp[x];
        }

        int[] failed_search = {-1};
        return failed_search;
    }

    /**
     * <p>Gives the objective value and index value of the lowest objective value element in a given array.</p>
     *
     * @param f_and_i Array containing fitness values and index values.
     * @return array containing details of lowest objective value element in given array.
     */
    public int[] returnDetailsWithLowestObjectiveValues(int[][] f_and_i) {
        int[][] temp = Arrays.copyOf(f_and_i, f_and_i.length);

        int smallest = Integer.MAX_VALUE;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i][0] < smallest) smallest = temp[i][0];
        }
        for (int x = 0; x < temp.length; x++) {
            if (temp[x][0] == smallest) return temp[x];
        }

        int[] failed_search = {-1};
        return failed_search;
    }

    /**
     * <p>Returns a copy of a given array which no longer contains a target value.</p>
     *
     * @param f_and_i array to be modified.
     * @param target  target value to remove.
     * @return version of input array that no longer contains target value.
     */
    public int[][] deleteDetails(int[][] f_and_i, int target) {
        int[][] temp = Arrays.copyOf(f_and_i, f_and_i.length);
        int[][] modified = new int[f_and_i.length - 1][2];
        for (int i = 0; i < temp.length; i++) {
            if (temp[i][0] == target) {
                System.arraycopy(temp, i + 1, temp, i, temp.length - i - 1);
                break;
            }
        }

        for (int x = 0; x < temp.length - 1; x++) modified[x] = temp[x];

        return modified;
    }
}
