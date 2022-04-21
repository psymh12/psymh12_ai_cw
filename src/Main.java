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

    public static void main(String[] args) {
        String instanceName = "hidden4_15_375";
        for (int trial_num = 0; trial_num < 5; trial_num++) {
            try {
                instances = readInstance(instanceName + ".txt");
            } catch (IOException e) {
                System.out.println("Error: File read failed.");
            }

            solutionPop = new SolutionPop(instances);
            hillClimbingHeuristic = new HillClimbingHeuristic();
            selectionHeuristic = new SelectionHeuristic();
            xoHeuristic = new XOHeuristic(0.99);
            mutationHeuristic = new MutationHeuristic(1.0 / solutionPop.getSolution(0).length, 0.15);
            replacementHeuristic = new ReplacementHeuristic();

            for (int i = 0; i < 150; i++) {
                int p1_index = selectionHeuristic.tournamentSelection(solutionPop);
                int p2_index = selectionHeuristic.tournamentSelection(solutionPop);

                while (p1_index == p2_index) p2_index = selectionHeuristic.tournamentSelection(solutionPop);

                double[] child_memeplex = xoHeuristic.returnChildMemeplex(solutionPop, p1_index, p2_index); //

                int[][] child_solutions;
                if (child_memeplex[0] == 1.0) {
                    child_solutions = xoHeuristic.singlePointXO(solutionPop, p1_index, p2_index);
                } else if (child_memeplex[0] == 2.0) {
                    child_solutions = xoHeuristic.doublePointXO(solutionPop, p1_index, p2_index);
                } else if (child_memeplex[0] == 3.0) {
                    child_solutions = xoHeuristic.uniformXO(solutionPop, p1_index, p2_index);
                } else {
                    child_solutions = xoHeuristic.singlePointXO(solutionPop, p1_index, p2_index);
                }

                child_memeplex = mutationHeuristic.mutateMemeplex(child_memeplex); //

                mutationHeuristic.setIom(child_memeplex[1]);

                int[] child1_mutation = mutationHeuristic.bitFlipWithIoM(child_solutions[0]);
                int[] child2_mutation = mutationHeuristic.bitFlipWithIoM(child_solutions[1]);

                hillClimbingHeuristic.setDos(child_memeplex[2]);

                if (child_memeplex[3] == 1.0) {
                    hillClimbingHeuristic.applyDBHCWithDoS(solutionPop, child1_mutation);
                    hillClimbingHeuristic.applyDBHCWithDoS(solutionPop, child2_mutation);
                } else if (child_memeplex[3] == 2.0) {
                    hillClimbingHeuristic.applySAHCWithDoS(solutionPop, child1_mutation);
                    hillClimbingHeuristic.applySAHCWithDoS(solutionPop, child2_mutation);
                } else if (child_memeplex[3] == 3.0) {
                    hillClimbingHeuristic.applyFAHCWithDoS(solutionPop, child1_mutation);
                    hillClimbingHeuristic.applyFAHCWithDoS(solutionPop, child2_mutation);
                } else {
                    hillClimbingHeuristic.applyDBHCWithDoS(solutionPop, child1_mutation);
                    hillClimbingHeuristic.applyDBHCWithDoS(solutionPop, child2_mutation);
                }

                int test[][] = replacementHeuristic.SteadyStateGAWithStrongElitism(solutionPop, p1_index, p2_index,
                        child1_mutation, child2_mutation, child_memeplex);

                solutionPop.setSolutions(test);
            }
            System.out.println("Trial #" + (trial_num + 1));
            System.out.println(solutionPop.getHighestObjectiveValue());
            for (int[] solution : solutionPop.getSolutions()) {
                if (solutionPop.getObjectiveValue(solution) == solutionPop.getHighestObjectiveValue()) {
                    System.out.println(Arrays.toString(solution));
                    break;
                }
            }
        }
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