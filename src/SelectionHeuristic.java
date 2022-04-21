import java.util.Random;

public class SelectionHeuristic {
    private Random rand = new Random();

    public int tournamentSelection(SolutionPop problem) {
        int[] participants = new int[2];
        participants[0] = rand.nextInt(problem.getPopSize() - 1); //index number of 1st participant
        participants[1] = rand.nextInt(problem.getPopSize() - 1);
        while (participants[0] == participants[1]) participants[1] = rand.nextInt(problem.getPopSize());

        System.out.println("Participant 0: " + participants[0] + ", Value: " + problem.getObjectiveValue(participants[0]));
        System.out.println("Participant 1: " + participants[1] + ", Value: " + problem.getObjectiveValue(participants[1]));

        if (problem.getObjectiveValue(participants[0]) < problem.getObjectiveValue(participants[1])) {
            System.out.println("Winner: " + participants[1]);
            return participants[1];
        } else if (problem.getObjectiveValue(participants[0]) > problem.getObjectiveValue(participants[1])) {
            System.out.println("Winner: " + participants[0]);
            return participants[0];
        } else {
            double rand_double = rand.nextDouble();
            if (rand_double < 0.5) {
                System.out.println("Winner: " + participants[1]);
                return participants[1];
            } else {
                System.out.println("Winner: " + participants[0]);
                return participants[0];
            }
        }
    }
}
