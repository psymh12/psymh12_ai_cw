import java.util.Random;

public class HillClimbingHeuristic {
    private Random rand = new Random();

    public void applyDBHC(SolutionPop problem, int pop_index) {
        double bestEval = problem.getObjectiveValue(pop_index);

        int[] perm = generatePerm(problem.getSolution(pop_index).length);

        for (int j = 0; j < problem.getSolution(pop_index).length; j++) {
            bitFlip(problem, perm[j], pop_index);
            double tmpEval = problem.getObjectiveValue(pop_index);
            if (tmpEval < bestEval) {
                bitFlip(problem, perm[j], pop_index);
            } else {
                bestEval = tmpEval;
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

    public void bitFlip(SolutionPop problem, int index, int pop_index) {
        if (problem.getSolution(pop_index)[index] == 0) {
            problem.setSolutions(pop_index, index, 1);
            return;
        }
        problem.setSolutions(pop_index, index, 0);
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
