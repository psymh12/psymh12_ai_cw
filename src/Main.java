import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    private static ArrayList<ArrayList<Double>> instances;
    private static Random rand = new Random();
    private static SolutionPop solutionPop;
    private static HillClimbingHeuristic hillClimbingHeuristic;
    private static SelectionHeuristic selectionHeuristic;
    private static XOHeuristic xoHeuristic;
    private static MutationHeuristic mutationHeuristic;
    private static ReplacementHeuristic replacementHeuristic;

    /* data access examples:

        ArrayList<Double> bruh = instances.get(0);
        Double jit = bruh.get(0);

        System.out.println(jit);
        System.out.println(Arrays.toString(bruh.toArray()));
        System.out.println(Arrays.deepToString(instances.toArray()));
         */
    public static void main(String[] args) {
        try {
            instances = readInstance("testinstance.txt");
        } catch (IOException e) {
            System.out.println("Error: File read failed.");
        }

        solutionPop = new SolutionPop(instances);
        hillClimbingHeuristic = new HillClimbingHeuristic();
        selectionHeuristic = new SelectionHeuristic();
        xoHeuristic = new XOHeuristic(0.99);
        mutationHeuristic = new MutationHeuristic(1.0 / solutionPop.getSolution(0).length);
        replacementHeuristic = new ReplacementHeuristic();

        /*
        instances = solutionPop.getInstances();
        System.out.println(Arrays.deepToString(instances.toArray()));
        for(int anum : solutionPop.getSolution()) System.out.println(anum);
         */

        for (int i = 0; i < 150; i++) {
            System.out.println("GENERATION " + i);
            System.out.println("BEST OBJECTIVE VALUE: " + solutionPop.getHighestObjectiveValue());
            System.out.println("--------------------------------------------------------");

            int[][] solutions = solutionPop.getSolutions();
            for (int[] temp_sol : solutions) System.out.println(Arrays.toString(temp_sol));
            int p1_index = selectionHeuristic.tournamentSelection(solutionPop);
            int p2_index = selectionHeuristic.tournamentSelection(solutionPop);

            while (p1_index == p2_index) p2_index = selectionHeuristic.tournamentSelection(solutionPop);

            System.out.println("Parent 1 index = " + p1_index + " | " + Arrays.toString(solutionPop.getSolution(p1_index)));
            System.out.println("Parent 2 index = " + p2_index + " | " + Arrays.toString(solutionPop.getSolution(p2_index)));

            int[][] child_solutions = xoHeuristic.singlePointXO(solutionPop, p1_index, p2_index);
            int[] child1_mutation = mutationHeuristic.bitFlip(child_solutions[0]);
            int[] child2_mutation = mutationHeuristic.bitFlip(child_solutions[1]);

            System.out.println("Child 1 (mutated): " + Arrays.toString(child1_mutation));
            System.out.println("Child 2 (mutated): " + Arrays.toString(child2_mutation));

            hillClimbingHeuristic.applyDBHC(solutionPop, child1_mutation);
            hillClimbingHeuristic.applyDBHC(solutionPop, child2_mutation);
            System.out.println("Child 1 (HC applied): " + Arrays.toString(child1_mutation));
            System.out.println("Child 2 (HC applied): " + Arrays.toString(child2_mutation));

            System.out.println("Parent 1 index: " + p1_index + " | " + Arrays.toString(solutionPop.getSolution(p1_index)));
            System.out.println("Parent 2 index: " + p2_index + " | " + Arrays.toString(solutionPop.getSolution(p2_index)));
            System.out.println("Child 1 solution: " + Arrays.toString(child1_mutation));
            System.out.println("Child 2 solution: " + Arrays.toString(child2_mutation));

            int test[][] = replacementHeuristic.SteadyStateGAWithStrongElitism(solutionPop, p1_index, p2_index, child1_mutation, child2_mutation);

            System.out.println("Old solution population: " + solutionPop.getHighestObjectiveValue());
            for (int[] solution : solutionPop.getSolutions()) System.out.println(Arrays.toString(solution));

            solutionPop.setSolutions(test);

            System.out.println("Updated solution population: " + solutionPop.getHighestObjectiveValue());
            for (int[] solution : solutionPop.getSolutions()) System.out.println(Arrays.toString(solution));
        }

        // GA progress ends here

        /*
        System.out.println();

        int[] solution = solutionPop.getSolution(2);
        System.out.println(Arrays.toString(solution));
        System.out.println(solutionPop.getObjectiveValue(2));

        hillClimbingHeuristic.applyDHBC(solutionPop, 2);

        solution = solutionPop.getSolution(2);
        System.out.println(Arrays.toString(solution));
        System.out.println(solutionPop.getObjectiveValue(2));

        solutions = solutionPop.getSolutions();

        System.out.println();
        for(int[] temp_sol : solutions) System.out.println(Arrays.toString(temp_sol));
         */
    }

    public static ArrayList<ArrayList<Double>> readInstance(String pathExtension) throws IOException {
        String path = "src/" + pathExtension;
        ArrayList<ArrayList<Double>> instance = new ArrayList<>();
        String values;

        File file = new File(path);

        BufferedReader reader = new BufferedReader(new FileReader(file));

        while ((values = reader.readLine()) != null) {
            if (!values.isEmpty()) {
                List<Double> wp = new ArrayList<>();
                String[] splitted = values.split(" ");
                wp.add((double) Double.parseDouble(splitted[0]));
                wp.add((double) Double.parseDouble(splitted[1]));
                instance.add((ArrayList<Double>) wp);
            }
        }

        return instance;
    }
}