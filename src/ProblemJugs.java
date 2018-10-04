/*
 Dana Huget, V00860786
 CSC 421 Assignment 1
 Oct 4, 2018
 */
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/*
Consider the following problem (Water Jugs Problem).
You have three jugs, measuring 12 gallons, 8 gallons,
and 3 gallons, and a water faucet. You can fill the
jugs up, or empty them out from one another or onto
the ground. You need to measure out exactly one gallon.
 */
public class ProblemJugs extends Problem {
    static final int jug12 = 0;
    static final int jug8 = 1;
    static final int jug3 = 2;
    static final int ground = 3;

    boolean goal_test(Object state) {
        StateJugs jug_state = (StateJugs) state;

        if (jug_state.jugArray[ground]==1)
            return true;
        else return false;
    }

    Set<Object> getSuccessors(Object state) {

        Set<Object> set = new HashSet<Object>();
        StateJugs jug_state = (StateJugs) state;

        //Let's create without any constraint, then remove the illegal ones
        StateJugs successor_state;
        int temp;

        // fill 12gallon jug from faucet
        successor_state = new StateJugs(jug_state);
        temp = 12 - successor_state.jugArray[jug12];
        successor_state.jugArray[jug12] += temp;
        if (isValid(successor_state)) {
            set.add(successor_state);
        }

        // fill 8gallon jug from faucet
        successor_state = new StateJugs(jug_state);
        temp = 8 - successor_state.jugArray[jug8];
        successor_state.jugArray[jug8] += temp;
        if (isValid(successor_state)) set.add(successor_state);

        // fill 3gallon jug from faucet
        successor_state = new StateJugs(jug_state);
        temp = 3 - successor_state.jugArray[jug3];
        successor_state.jugArray[jug3] += temp;
        if (isValid(successor_state)) set.add(successor_state);

        // pour from 12gallon jug to 8gallon jug
        successor_state = new StateJugs(jug_state);
        temp = successor_state.jugArray[jug12];
        if (temp + successor_state.jugArray[jug8] > 8) {
            temp = 8 - successor_state.jugArray[jug8];
        }
        successor_state.jugArray[jug12] -= temp;
        successor_state.jugArray[jug8] += temp;
        if (isValid(successor_state)) set.add(successor_state);

        // pour from 12gallon jug to 3gallon jug
        successor_state = new StateJugs(jug_state);
        temp = successor_state.jugArray[jug12];
        if (temp + successor_state.jugArray[jug3] > 3) {
            temp = 3 - successor_state.jugArray[jug3];
        }
        successor_state.jugArray[jug12] -= temp;
        successor_state.jugArray[jug3] += temp;
        if (isValid(successor_state)) set.add(successor_state);

        // pour from 8gallon jug to 12gallon jug
        successor_state = new StateJugs(jug_state);
        temp = successor_state.jugArray[jug8];
        if (temp + successor_state.jugArray[jug12] > 12) {
            temp = 12 - successor_state.jugArray[jug12];
        }
        successor_state.jugArray[jug8] -= temp;
        successor_state.jugArray[jug12] += temp;
        if (isValid(successor_state)) set.add(successor_state);

        // pour from 8gallon jug to 3gallon jug
        successor_state = new StateJugs(jug_state);
        temp = successor_state.jugArray[jug8];
        if (temp + successor_state.jugArray[jug3] > 3) {
            temp = 3 - successor_state.jugArray[jug3];
        }
        successor_state.jugArray[jug8] -= temp;
        successor_state.jugArray[jug3] += temp;
        if (isValid(successor_state)) set.add(successor_state);

        // pour from 3gallon jug to 8gallon jug
        successor_state = new StateJugs(jug_state);
        temp = successor_state.jugArray[jug3];
        if (temp + successor_state.jugArray[jug8] > 8) {
            temp = 8 - successor_state.jugArray[jug8];
        }
        successor_state.jugArray[jug3] -= temp;
        successor_state.jugArray[jug8] += temp;
        if (isValid(successor_state)) set.add(successor_state);

        // pour from 3gallon jug to 12gallon jug
        successor_state = new StateJugs(jug_state);
        temp = successor_state.jugArray[jug3];
        if (temp + successor_state.jugArray[jug12] > 12) {
            temp = 12 - successor_state.jugArray[jug12];
        }
        successor_state.jugArray[jug3] -= temp;
        successor_state.jugArray[jug12] += temp;
        if (isValid(successor_state)) set.add(successor_state);

        // empty out 12gallon jug onto ground
        successor_state = new StateJugs(jug_state);
        temp = successor_state.jugArray[jug12];
        successor_state.jugArray[jug12] = 0;
        successor_state.jugArray[ground] = temp;
        if (isValid(successor_state)) set.add(successor_state);

        // empty out 8gallon jug onto ground
        successor_state = new StateJugs(jug_state);
        temp = successor_state.jugArray[jug8];
        successor_state.jugArray[jug8] = 0;
        successor_state.jugArray[ground] = temp;
        if (isValid(successor_state)) set.add(successor_state);

        // empty out 3gallon jug onto ground
        successor_state = new StateJugs(jug_state);
        temp = successor_state.jugArray[jug3];
        successor_state.jugArray[jug3] = 0;
        successor_state.jugArray[ground] = temp;
        if (isValid(successor_state)) set.add(successor_state);

        return set;
    }

    private boolean isValid(StateJugs state) {
        //Checking to see if any element of the array is negative
        for (int i = 0; i < 4; i++) if (state.jugArray[i] < 0) return false;

        //Check to see if jugs are overfilled
        if (state.jugArray[jug12] > 12 || state.jugArray[jug8] > 8 || state.jugArray[jug3] > 3) return false;

        return true;
    }

    /*
    Consider a variant of the Water Jugs Problem in which we are interested in
    minimizing the amount of water that must be poured. Filling the large jug
    when it is empty requires pouring 12 gallons into it. Likewise, transferring
    water from a full large jug into an empty small jug requires pouring 3 gallons.
    A lazy person might not mind a longer solution to the problem if it in fact
    reduced the amount of water they had to heft and pour. Attempt to solve the
    problem using the various uninformed search control strategies provided and
    report statistics.
     */
    double step_cost(Object fromState, Object toState) {
        StateJugs jugs_FromState = (StateJugs) fromState;
        StateJugs jugs_ToState = (StateJugs) toState;

        int pourAmount = Math.abs(jugs_ToState.jugArray[jug12] - jugs_FromState.jugArray[jug12]);
        pourAmount += Math.abs(jugs_ToState.jugArray[jug8] - jugs_FromState.jugArray[jug8]);
        pourAmount += Math.abs(jugs_ToState.jugArray[jug3] - jugs_FromState.jugArray[jug3]);

        // poured from one jug to another jug
        if (jugs_FromState.jugArray[jug12]+jugs_FromState.jugArray[jug8]+jugs_FromState.jugArray[jug3] ==
                jugs_ToState.jugArray[jug12]+jugs_ToState.jugArray[jug8]+jugs_ToState.jugArray[jug3]) {
            return (double) pourAmount / 2;
        }

        // poured from faucet OR onto ground
        return (double) pourAmount;
    }

    public double h(Object state) { return 0; }

    public static void main(String[] args) throws Exception {
        ProblemJugs problem = new ProblemJugs();
        int[] jugArray = {0,0,0,0};
        problem.initialState = new StateJugs(jugArray);

        Search search  = new Search(problem);
        Path file = Paths.get("ProblemJugsStatisticsReport.txt");
        List<String> stats = new ArrayList<>();
        stats.add("Q4 Statistics:");
        stats.add("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
        stats.add("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
        stats.add("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
        stats.add("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
        stats.add("TreeSearchDepthLimited(4):\t" + search.TreeSearchDepthLimited(4));
        stats.add("GraphSearchDepthLimited(4):\t" + search.GraphSearchDepthLimited(4));
        stats.add("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
        stats.add("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());

        try {
            Files.write(file, stats, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
//
//        System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
//
//        System.out.println("DepthFirstTreeSearch:\t\t\t" + search.DepthFirstTreeSearch());
//
//        System.out.println("DepthFirstGraphSearch:\t\t\t" + search.DepthFirstGraphSearch());
//
//        System.out.println("TreeSearchDepthLimited(4):\t\t" + search.TreeSearchDepthLimited(4));
//
//        System.out.println("GraphSearchDepthLimited(4):\t\t" + search.GraphSearchDepthLimited(4));
//
//        System.out.println("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
//
//        System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());

    }
}
