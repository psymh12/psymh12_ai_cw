import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SolutionPop {
    private ArrayList<ArrayList<Double>> instances;
    private int[][] solutions;
    private int pop_size;
    private double n;
    private double W;
    private Random rand = new Random();

    public SolutionPop(ArrayList<ArrayList<Double>> instances) {
        this.instances = instances;

        ArrayList<Double> nW_details = instances.get(0);
        n = nW_details.get(0);
        W = nW_details.get(1);
        instances.remove(0);

        pop_size = (int) n;
        if (pop_size > 100) pop_size = 100;

        solutions = new int[pop_size][(int) n];
        for (int i = 0; i < pop_size; i++) solutions[i] = generateSolution();
    }

    public ArrayList<ArrayList<Double>> getInstances() {
        return instances;
    }

    public int[] getSolution(int pop_index) {
        return solutions[pop_index];
    }

    public int[][] getSolutions() {
        return solutions;
    }

    public void setSolutions(int[][] new_solutions) {
        solutions = new_solutions;
    }

    public double getN() {
        return n;
    }

    public double getW() {
        return W;
    }

    public int getPopSize() {
        return pop_size;
    }

    public void setSolutions(int pop_index, int locus, int num) {
        solutions[pop_index][locus] = num;
    }

    public void setSolutions(int index, int[] in_solution) {
        solutions[index] = in_solution;
    }

    public int[] generateSolution() {
        int[] rand_solution = new int[(int) n];
        for (int i = 0; i < n; i++) {
            double rand_double = rand.nextDouble();
            if (rand_double < 0.5) {
                rand_solution[i] = 0;
            } else {
                rand_solution[i] = 1;
            }
        }
        return rand_solution;
    }

    public double getHighestObjectiveValue() {
        ArrayList<Double> fitness_vals = new ArrayList<>();
        for (int i = 0; i < solutions.length; i++) {
            fitness_vals.add(getObjectiveValue(i));
        }
        return Collections.max(fitness_vals);
    }

    public double getObjectiveValue(int pop_index) {
        double score = 0.0;
        double weight = 0.0;
        int i = 0;
        for (int bit_num : solutions[pop_index]) {
            if (bit_num == 1) {
                score += instances.get(i).get(0);

                weight += instances.get(i).get(1);
                if (weight > W) return 0.0;
            }
            i++;
        }
        return score;
    }

    public double getObjectiveValue(int[] input_solution) {
        double score = 0.0;
        double weight = 0.0;
        int i = 0;
        for (int bit_num : input_solution) {
            if (bit_num == 1) {
                score += instances.get(i).get(0);

                weight += instances.get(i).get(1);
                if (weight > W) return 0.0;
            }
            i++;
        }
        return score;
    }
}
