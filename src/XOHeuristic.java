import java.util.Arrays;
import java.util.Random;

public class XOHeuristic {
    private Random rand = new Random();
    private double xo_rate;

    public XOHeuristic(double xo_rate) {
        this.xo_rate = xo_rate;
    }

    public int[][] singlePointXO(SolutionPop problem, int p1_index, int p2_index) {
        int[] p1_solution = Arrays.copyOf(problem.getSolution(p1_index), problem.getSolution(p1_index).length);
        int[] p2_solution = Arrays.copyOf(problem.getSolution(p2_index), problem.getSolution(p2_index).length);
        int[][] child_solutions = new int[2][p1_solution.length];

        System.out.println("Parent 1: " + Arrays.toString(p1_solution));
        System.out.println("Parent 2: " + Arrays.toString(p2_solution));

        int xo_point = rand.nextInt(p1_solution.length);

        System.out.println("Crossover point: " + xo_point);

        if (rand.nextDouble() < xo_rate) {
            for (int i = 0; i <= xo_point; i++) {
                int temp = p1_solution[i];
                p1_solution[i] = p2_solution[i];
                p2_solution[i] = temp;
            }
            System.out.println("Crossover performed.");
        }

        child_solutions[0] = p1_solution;
        child_solutions[1] = p2_solution;

        System.out.println("Child 1: " + Arrays.toString(child_solutions[0]));
        System.out.println("Child 2: " + Arrays.toString(child_solutions[1]));

        return child_solutions;
    }
}
