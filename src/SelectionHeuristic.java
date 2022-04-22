import java.util.Random;

/**
 * Used for applying selection heuristics to choose parent solutions.
 */
public class SelectionHeuristic {
    /**
     * Object of Random class.
     */
    private Random rand = new Random();

    /**
     * <p>Randomly chooses two, unique participants from the population and then returns the participant with
     * higher objective value, or randomly chooses a participant if the objective values are equal.</p>
     *
     * @param problem Population to choose parents from.
     * @return index of chosen parent.
     */
    public int tournamentSelection(SolutionPop problem) {
        int[] participants = new int[2];
        participants[0] = rand.nextInt(problem.getPopSize() - 1); //index number of 1st participant
        participants[1] = rand.nextInt(problem.getPopSize() - 1);
        while (participants[0] == participants[1]) participants[1] = rand.nextInt(problem.getPopSize());

        if (problem.getObjectiveValue(participants[0]) < problem.getObjectiveValue(participants[1])) {
            return participants[1];
        } else if (problem.getObjectiveValue(participants[0]) > problem.getObjectiveValue(participants[1])) {
            return participants[0];
        } else {
            double rand_double = rand.nextDouble();
            if (rand_double < 0.5) {
                return participants[1];
            } else {
                return participants[0];
            }
        }
    }
}
