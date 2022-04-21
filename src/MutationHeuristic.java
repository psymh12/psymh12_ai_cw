import java.util.Random;

public class MutationHeuristic {
    private Random rand = new Random();
    private double mutation_rate;

    public MutationHeuristic(double mutation_rate) {
        this.mutation_rate = mutation_rate;
    }

    public int[] bitFlip(int[] solution) {
        int[] temp_solution = solution;
        for (int i = 0; i < temp_solution.length; i++) {
            if (rand.nextDouble() < mutation_rate) {
                if (temp_solution[i] == 1) temp_solution[i] = 0;
                else temp_solution[i] = 1;
            }
        }
        return temp_solution;
    }
}
