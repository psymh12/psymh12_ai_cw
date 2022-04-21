import java.util.Random;

public class HillClimbingHeuristic {
    private Random rand = new Random();
    private double dos;

    public void setDos(double dos) {
        this.dos = dos;
    }

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

    public void bitFlip(int[] solution, int index) {
        if (solution[index] == 0) {
            solution[index] = 1;
            return;
        }
        solution[index] = 0;
    }

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

    public boolean in_arr_check(int n, int[] arr) {
        for (int element : arr) if (element == n) return true;
        return false;
    }
}
