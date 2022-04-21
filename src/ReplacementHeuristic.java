import java.util.Arrays;
import java.util.Random;

public class ReplacementHeuristic {
    private Random rand = new Random();

    public int[][] SteadyStateGAWithStrongElitism(SolutionPop problem, int p1_index, int p2_index,
                                                  int[] c1_solution, int[] c2_solution) {
        System.out.println("(Inside rep) Parent 1 index: " + p1_index + " | " + Arrays.toString(problem.getSolution(p1_index)));
        System.out.println("(Inside rep) Parent 2 index: " + p2_index + " | " + Arrays.toString(problem.getSolution(p2_index)));
        System.out.println("(Inside rep) Child 1 sol: " + Arrays.toString(c1_solution));
        System.out.println("(Inside rep) Child 2 sol: " + Arrays.toString(c2_solution));

        int[][] p_and_c_fitnesses = {
                {(int) problem.getObjectiveValue(p1_index), p1_index},
                {(int) problem.getObjectiveValue(p2_index), p2_index},
                {(int) problem.getObjectiveValue(c1_solution), -1},
                {(int) problem.getObjectiveValue(c2_solution), -2}
        };

        System.out.println("Parent and children array: ");
        for (int[] p_and_c_ele : p_and_c_fitnesses) System.out.println(Arrays.toString(p_and_c_ele));
        System.out.println("End of p_and_c array");

        int[][] p_and_c_highest = returnDetailsOfTwoHighestObjectiveValues(p_and_c_fitnesses);
        System.out.println("Highest parent/child details: ");
        for (int[] pc_ele : p_and_c_highest) System.out.println(Arrays.toString(pc_ele));

        //transform problem.getSolutions()
        int[][] pop_fitnesses = new int[problem.getPopSize()][2];
        for (int a = 0; a < pop_fitnesses.length; a++) {
            int[] temp_arr = {(int) problem.getObjectiveValue(a), a};
            pop_fitnesses[a] = temp_arr;
        }

        int[][] pop_lowest = returnDetailsOfTwoLowestObjectiveValues(pop_fitnesses);
        System.out.println("Population lowest details: ");
        for (int[] low_ele : pop_lowest) System.out.println(Arrays.toString(low_ele));

        int[][] new_pop = Arrays.copyOf(problem.getSolutions(), problem.getSolutions().length);

        for (int i = 0; i < problem.getPopSize(); i++) {
            if (i == pop_lowest[0][1]) {
                if (p_and_c_highest[0][1] == -1) {
                    new_pop[i] = c1_solution;
                } else if (p_and_c_highest[0][1] == -2) {
                    new_pop[i] = c2_solution;
                } else {
                    new_pop[i] = new_pop[p_and_c_highest[0][1]];
                }
            } else if (i == pop_lowest[1][1]) {
                if (p_and_c_highest[1][1] == -1) {
                    new_pop[i] = c1_solution;
                } else if (p_and_c_highest[1][1] == -2) {
                    new_pop[i] = c2_solution;
                } else {
                    new_pop[i] = new_pop[p_and_c_highest[1][1]];
                }
            }
        }

        return new_pop;
    }

    public int[][] returnDetailsOfTwoHighestObjectiveValues(int[][] f_and_i) {
        int[][] temp = Arrays.copyOf(f_and_i, f_and_i.length);
        int[][] highest_details = new int[2][2];

        highest_details[0] = returnDetailsWithHighestObjectiveValues(temp);
        temp = deleteDetails(temp, highest_details[0][0]);
        highest_details[1] = returnDetailsWithHighestObjectiveValues(temp);

        return highest_details;
    }

    public int[][] returnDetailsOfTwoLowestObjectiveValues(int[][] f_and_i) {
        int[][] temp = Arrays.copyOf(f_and_i, f_and_i.length);
        int[][] lowest_details = new int[2][2];

        lowest_details[0] = returnDetailsWithLowestObjectiveValues(temp);
        temp = deleteDetails(temp, lowest_details[0][0]);
        lowest_details[1] = returnDetailsWithLowestObjectiveValues(temp);

        return lowest_details;
    }


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
