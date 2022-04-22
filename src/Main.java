import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Executes multi-memetic algorithm on test instance, handles output to terminal, and also handles output to text files.
 */
public class Main {
    /**
     * Contains details of profit-weight pairs given in the test instances.
     */
    private static ArrayList<ArrayList<Double>> instances;
    /**
     * Object of Random class.
     */
    private static Random rand = new Random();
    /**
     * Object of SolutionPop class.
     */
    private static SolutionPop solutionPop;
    /**
     * Object of HillClimbingHeuristic class.
     */
    private static HillClimbingHeuristic hillClimbingHeuristic;
    /**
     * Object of SelectionHeuristic class.
     */
    private static SelectionHeuristic selectionHeuristic;
    /**
     * Object of XOHeuristic class.
     */
    private static XOHeuristic xoHeuristic;
    /**
     * Object of MutationHeuristic class.
     */
    private static MutationHeuristic mutationHeuristic;
    /**
     * Object of ReplacementHeuristic class.
     */
    private static ReplacementHeuristic replacementHeuristic;

    /**
     * <p>Runs a multi-memetic algorithm on a particular test instance. Details are output to both the terminal and
     * text files.</p>
     *
     * @param args Command line input.
     */
    public static void main(String[] args) {
        String instanceName = "hidden4_15_375";
        int numberOf = 100;
        double[][] best_current_values = new double[150][2];

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

                best_current_values[i][0] = solutionPop.getHighestObjectiveValue();
                best_current_values[i][1] = solutionPop.getLowestObjectiveValue();
            }
            System.out.println("Trial #" + (trial_num + 1));
            System.out.println(solutionPop.getHighestObjectiveValue());
            for (int[] solution : solutionPop.getSolutions()) {
                if (solutionPop.getObjectiveValue(solution) == solutionPop.getHighestObjectiveValue()) {
                    System.out.println(Arrays.toString(solution));
                    break;
                }
            }
            writeBestCurrent(instanceName, trial_num + 1, best_current_values, numberOf);
        }
    }

    /**
     * <p>Writes the best and current values of each generation (up until a certain generation) into a text file.</p>
     *
     * @param instanceName Instance name that algorithm is being applied to.
     * @param trialID      Trial number.
     * @param bc           Array of best and current values.
     * @param numberOf     Controls which generation to stop writing to the file at.
     */
    public static void writeBestCurrent(String instanceName, int trialID, double[][] bc, int numberOf) {
        try {
            FileWriter myWriter = new FileWriter(instanceName + "_" + trialID + "_output.txt");
            for (int i = 0; i < numberOf; i++) {
                myWriter.write(bc[i][0] + " " + bc[i][1] + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("File write failed.");
        }
    }

    /**
     * <p>Processes text file of a test instance and returns it as a nested ArrayList of profit-weight paired values.</p>
     *
     * @param pathExtension Name of test instance to take values from.
     * @return Nested ArrayList of Doubles that contains profit-weight paired values.
     * @throws IOException if unable to successfully read values from instance.
     */
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