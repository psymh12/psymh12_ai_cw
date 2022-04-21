import java.util.Arrays;
import java.util.Random;

public class MutationHeuristic {
    private Random rand = new Random();
    private double mutation_rate;
    private double iom;
    private double innovation_rate;

    public MutationHeuristic(double mutation_rate, double innovation_rate) {
        this.mutation_rate = mutation_rate;
        this.innovation_rate = innovation_rate;
    }

    public void setIom(double iom) {
        this.iom = iom;
    }

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
