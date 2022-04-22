import java.util.Random;

/**
 * Used for applying hill climbing heuristics to solutions.
 */
public class HillClimbingHeuristic {
    /**
     * Object of Random class.
     */
    private Random rand = new Random();
    /**
     * Variable controlling Depth of Search.
     */
    private double dos;

    /**
     * <p>Setter function used for changing value of dos (Depth of Search) variable.</p>
     *
     * @param dos Value to set Depth of Search to.
     */
    public void setDos(double dos) {
        this.dos = dos;
    }

    /**
     * <p>Used for applying Davis Bit Hill Climbing (DBHC) to a solution with potentially more than 1 passes
     * depending on the value of dos (Depth of Search).</p>
     *
     * @param problem  Used for calculating objective values to decide whether to terminate further application of DBHC.
     * @param solution Solution that DBHC is being applied to.
     */
    public void applyDBHCWithDoS(SolutionPop problem, int[] solution) {
        int iterations;

        if (0.0 <= dos && dos < 0.2) {
            iterations = 1;
        } else if (0.2 <= dos && dos < 0.4) {
            iterations = 2;
        } else if (0.4 <= dos && dos < 0.6) {
            iterations = 3;
        } else if (0.6 <= dos && dos < 0.8) {
            iterations = 4;
        } else if (0.8 <= dos && dos < 1) {
            iterations = 5;
        } else if (dos == 1) {
            iterations = 6;
        } else {
            iterations = 1;
        }

        for (int i = 0; i < iterations; i++) {
            int before = (int) problem.getObjectiveValue(solution);
            applyDBHC(problem, solution);
            int after = (int) problem.getObjectiveValue(solution);
            if (before >= after) {
                break;
            }
        }
    }

    /**
     * <p>Used for applying Steepest Ascent Hill Climbing (SAHC) to a solution with potentially more than 1 passes
     * depending on the value of dos (Depth of Search).</p>
     *
     * @param problem  Used for calculating objective values to decide whether to terminate further application of SAHC.
     * @param solution Solution that SAHC is being applied to.
     */
    public void applySAHCWithDoS(SolutionPop problem, int[] solution) {
        int iterations;

        if (0.0 <= dos && dos < 0.2) {
            iterations = 1;
        } else if (0.2 <= dos && dos < 0.4) {
            iterations = 2;
        } else if (0.4 <= dos && dos < 0.6) {
            iterations = 3;
        } else if (0.6 <= dos && dos < 0.8) {
            iterations = 4;
        } else if (0.8 <= dos && dos < 1) {
            iterations = 5;
        } else if (dos == 1) {
            iterations = 6;
        } else {
            iterations = 1;
        }

        for (int i = 0; i < iterations; i++) {
            int before = (int) problem.getObjectiveValue(solution);
            applySAHC(problem, solution);
            int after = (int) problem.getObjectiveValue(solution);
            if (before >= after) {
                break;
            }
        }
    }

    /**
     * <p>Used for applying First Ascent Hill Climbing (FAHC) to a solution with potentially more than 1 passes
     * depending on the value of dos (Depth of Search).</p>
     *
     * @param problem  Used for calculating objective values to decide whether to terminate further application of FAHC.
     * @param solution Solution that FAHC is being applied to.
     */
    public void applyFAHCWithDoS(SolutionPop problem, int[] solution) {
        int iterations;

        if (0.0 <= dos && dos < 0.2) {
            iterations = 1;
        } else if (0.2 <= dos && dos < 0.4) {
            iterations = 2;
        } else if (0.4 <= dos && dos < 0.6) {
            iterations = 3;
        } else if (0.6 <= dos && dos < 0.8) {
            iterations = 4;
        } else if (0.8 <= dos && dos < 1) {
            iterations = 5;
        } else if (dos == 1) {
            iterations = 6;
        } else {
            iterations = 1;
        }

        for (int i = 0; i < iterations; i++) {
            int before = (int) problem.getObjectiveValue(solution);
            applyFAHC(problem, solution);
            int after = (int) problem.getObjectiveValue(solution);
            if (before >= after) {
                break;
            }
        }
    }

    /**
     * <p>Applies a single pass of Davis Bit Hill Climbing (DBHC) to a solution.</p>
     *
     * @param sol_pop  Used for calculating objective values to decide whether to terminate further application of DBHC.
     * @param solution Solution that DBHC is being applied to.
     */
    public void applyDBHC(SolutionPop sol_pop, int[] solution) {
        double bestEval = sol_pop.getObjectiveValue(solution);

        int[] perm = generatePerm(solution.length);

        for (int j = 0; j < solution.length; j++) {
            bitFlip(solution, perm[j]);
            double tmpEval = sol_pop.getObjectiveValue(solution);
            if (tmpEval < bestEval) {
                bitFlip(solution, perm[j]);
            } else {
                bestEval = tmpEval;
            }
        }
    }

    /**
     * <p>Applies a single pass of Steepest Ascent Hill Climbing (SAHC) to a solution.</p>
     *
     * @param sol_pop  Used for calculating objective values to decide whether to terminate further application of SAHC.
     * @param solution Solution that SAHC is being applied to.
     */
    public void applySAHC(SolutionPop sol_pop, int[] solution) {
        double bestEval = sol_pop.getObjectiveValue(solution);
        int bestIndex = -1;
        boolean improved = false;

        for (int j = 0; j < solution.length; j++) {
            bitFlip(solution, j);
            double tmpEval = sol_pop.getObjectiveValue(solution);
            if (tmpEval > bestEval) {
                bestIndex = j;
                bestEval = tmpEval;
                improved = true;
            }
            bitFlip(solution, j);
        }
        if (improved) bitFlip(solution, bestIndex);
    }

    /**
     * <p>Applies a single pass of First Ascent Hill Climbing (FAHC) to a solution.</p>
     *
     * @param sol_pop  Used for calculating objective values to decide whether to terminate further application of FAHC.
     * @param solution Solution that FAHC is being applied to.
     */
    public void applyFAHC(SolutionPop sol_pop, int[] solution) {
        double bestEval = sol_pop.getObjectiveValue(solution);

        for (int j = 0; j < solution.length; j++) {
            bitFlip(solution, j);
            double tmpEval = sol_pop.getObjectiveValue(solution);
            if (tmpEval > bestEval) {
                bestEval = tmpEval;
            } else {
                bitFlip(solution, j);
            }
        }
    }

    /**
     * <p>Flips a bit in an index of a solution to either 0 or 1 depending on it's current value.</p>
     *
     * @param solution Solution that is having one of it's bits flipped to 0/1.
     * @param index    Index of bit in solution that will be flipped to 0/1.
     */
    public void bitFlip(int[] solution, int index) {
        if (solution[index] == 0) {
            solution[index] = 1;
            return;
        }
        solution[index] = 0;
    }

    /**
     * <p>Generates an array of numbers where each element is in range [0..array_length-1] and every element is unique.</p>
     *
     * @param length Length of array to be generated.
     * @return a permutation of numbers.
     */
    public int[] generatePerm(int length) {
        int[] used_nums = new int[length];
        int rand_num = -1;

        for (int i = 0; i < length; i++) {
            while (in_arr_check(rand_num, used_nums)) rand_num = rand.nextInt(length);
            used_nums[i] = rand_num;
        }

        used_nums[0] = 0;

        rand_num = rand.nextInt(length);
        int temp = used_nums[rand_num];
        used_nums[rand_num] = 0;
        used_nums[0] = temp;

        return used_nums;
    }

    /**
     * <p>Returns true or false depending on whether a target number is within a given array.</p>
     *
     * @param n   Target number.
     * @param arr Array to be searched.
     * @return a boolean showing whether the target number is within the given array.
     */
    public boolean in_arr_check(int n, int[] arr) {
        for (int element : arr) if (element == n) return true;
        return false;
    }
}
