import java.util.Arrays;
import java.util.Random;

public class XOHeuristic {
    private Random rand = new Random();
    private double xo_rate;

    public XOHeuristic(double xo_rate) {
        this.xo_rate = xo_rate;
    }

    public double[] returnChildMemeplex(SolutionPop problem, int p1_index, int p2_index) {
        double p1_fitness = problem.getObjectiveValue(p1_index);
        double p2_fitness = problem.getObjectiveValue(p2_index);
        double[] p1_memeplex = Arrays.copyOf(problem.getMemeplex(p1_index), problem.getMemeplex(p1_index).length);
        double[] p2_memeplex = Arrays.copyOf(problem.getMemeplex(p2_index), problem.getMemeplex(p2_index).length);

        if (Arrays.equals(p1_memeplex, p2_memeplex)) {
            return p1_memeplex;
        } else if (p1_fitness == p2_fitness) {
            if (rand.nextDouble() < 0.5) {
                return p1_memeplex;
            } else {
                return p2_memeplex;
            }
        } else {
            if (p1_fitness > p2_fitness) {
                return p1_memeplex;
            } else {
                return p2_memeplex;
            }
        }
    }

    public int[][] uniformXO(SolutionPop problem, int p1_index, int p2_index) {
        int[] p1_solution = Arrays.copyOf(problem.getSolution(p1_index), problem.getSolution(p1_index).length);
        int[] p2_solution = Arrays.copyOf(problem.getSolution(p2_index), problem.getSolution(p2_index).length);
        int[][] child_solutions = new int[2][p1_solution.length];

        for (int i = 0; i < p1_solution.length; i++) {
            if (rand.nextDouble() < xo_rate) {
                int temp = p1_solution[i];
                p1_solution[i] = p2_solution[i];
                p2_solution[i] = temp;
            }
        }

        child_solutions[0] = p1_solution;
        child_solutions[1] = p2_solution;

        return child_solutions;
    }

    public int[][] doublePointXO(SolutionPop problem, int p1_index, int p2_index) {
        int[] p1_solution = Arrays.copyOf(problem.getSolution(p1_index), problem.getSolution(p1_index).length);
        int[] p2_solution = Arrays.copyOf(problem.getSolution(p2_index), problem.getSolution(p2_index).length);
        int[][] child_solutions = new int[2][p1_solution.length];

        int xo_point1 = rand.nextInt(p1_solution.length);
        int xo_point2 = rand.nextInt(p1_solution.length);
        while (xo_point1 == xo_point2) xo_point2 = rand.nextInt(p1_solution.length);

        int[] indexes = new int[2];

        if (xo_point1 < xo_point2) {
            indexes[0] = xo_point1;
            indexes[1] = xo_point2;
        } else {
            indexes[0] = xo_point2;
            indexes[1] = xo_point1;
        }

        if (rand.nextDouble() < xo_rate) {
            for (int i = 0; i <= indexes[0]; i++) {
                int temp = p1_solution[i];
                p1_solution[i] = p2_solution[i];
                p2_solution[i] = temp;
            }

            for (int i = indexes[1]; i < p1_solution.length; i++) {
                int temp = p1_solution[i];
                p1_solution[i] = p2_solution[i];
                p2_solution[i] = temp;
            }
        }

        child_solutions[0] = p1_solution;
        child_solutions[1] = p2_solution;

        return child_solutions;
    }

    public int[][] singlePointXO(SolutionPop problem, int p1_index, int p2_index) {
        int[] p1_solution = Arrays.copyOf(problem.getSolution(p1_index), problem.getSolution(p1_index).length);
        int[] p2_solution = Arrays.copyOf(problem.getSolution(p2_index), problem.getSolution(p2_index).length);
        int[][] child_solutions = new int[2][p1_solution.length];

        int xo_point = rand.nextInt(p1_solution.length);

        if (rand.nextDouble() < xo_rate) {
            for (int i = 0; i <= xo_point; i++) {
                int temp = p1_solution[i];
                p1_solution[i] = p2_solution[i];
                p2_solution[i] = temp;
            }
        }

        child_solutions[0] = p1_solution;
        child_solutions[1] = p2_solution;

        return child_solutions;
    }
}
