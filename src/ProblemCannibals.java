import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProblemCannibals extends Problem {
	
    static final int cannL = 0;
    static final int missL = 1;
    static final int boatL = 2;
    static final int cannR = 3;
    static final int missR = 4;
    static final int boatR = 5;
    
	boolean goal_test(Object state) {
        StateCannibals can_state = (StateCannibals) state;
        
        if (can_state.canArray[cannR]==3 && can_state.canArray[missR]==3 && can_state.canArray[boatR]==1)
            return true;
        else return false;
	}
  
    Set<Object> getSuccessors(Object state) {
    	
        Set<Object> set = new HashSet<Object>();
        StateCannibals can_state = (StateCannibals) state;
        
        //Let's create without any constraint, then remove the illegal ones
        StateCannibals successor_state;
        
        //one cannibal only from left to right
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[cannL] -= 1;
        successor_state.canArray[cannR] += 1;
        successor_state.canArray[boatL] -= 1;
        successor_state.canArray[boatR] += 1;
        if (isValid(successor_state)) set.add(successor_state);

        //one cannibal only from right to left
        //TODO
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[cannR] -= 1;
        successor_state.canArray[cannL] += 1;
        successor_state.canArray[boatR] -= 1;
        successor_state.canArray[boatL] += 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //two cannibals from left to right
        //TODO
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[cannL] -= 2;
        successor_state.canArray[cannR] += 2;
        successor_state.canArray[boatL] -= 1;
        successor_state.canArray[boatR] += 1;
        if (isValid(successor_state)) set.add(successor_state);

        //two cannibals from right to left 
        //TODO
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[cannR] -= 2;
        successor_state.canArray[cannL] += 2;
        successor_state.canArray[boatR] -= 1;
        successor_state.canArray[boatL] += 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //one missionary only from left to right 
        //TODO
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[missL] -= 1;
        successor_state.canArray[missR] += 1;
        successor_state.canArray[boatL] -= 1;
        successor_state.canArray[boatR] += 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //one missionary only from right to left 
        //TODO
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[missR] -= 1;
        successor_state.canArray[missL] += 1;
        successor_state.canArray[boatR] -= 1;
        successor_state.canArray[boatL] += 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //two missionaries from left to right 
        //TODO
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[missL] -= 2;
        successor_state.canArray[missR] += 2;
        successor_state.canArray[boatL] -= 1;
        successor_state.canArray[boatR] += 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //two missionaries from right to left 
        //TODO
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[missR] -= 2;
        successor_state.canArray[missL] += 2;
        successor_state.canArray[boatR] -= 1;
        successor_state.canArray[boatL] += 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //one cannibal and one missionary from left to right 
        //TODO
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[cannL] -= 1;
        successor_state.canArray[cannR] += 1;
        successor_state.canArray[missL] -= 1;
        successor_state.canArray[missR] += 1;
        successor_state.canArray[boatL] -= 1;
        successor_state.canArray[boatR] += 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //one cannibal and one missionary from right to left 
        //TODO
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[cannR] -= 1;
        successor_state.canArray[cannL] += 1;
        successor_state.canArray[missR] -= 1;
        successor_state.canArray[missL] += 1;
        successor_state.canArray[boatR] -= 1;
        successor_state.canArray[boatL] += 1;
        if (isValid(successor_state)) set.add(successor_state);

        return set;
    }
    
    private boolean isValid(StateCannibals state)
    {   
        //Checking to see if any element of the array is negative 
        for (int i = 0; i < 6; i++)
            if (state.canArray[i] < 0) return false;

        int missCount = state.canArray[missL] + state.canArray[missR];
        int cannCount = state.canArray[cannL] + state.canArray[cannR];
        int boatCount = state.canArray[boatL] + state.canArray[boatR];

        //Checking to see if the numbers of cannibals, missionaries, and boat 
        //are more then 3,3,1 respectively
        //TODO
        if(missCount > 3 || cannCount > 3 || boatCount > 1) return false;
        
        //Now, checking if cannibals out number missionaries
        //TODO
        // when checking if # cannibals > # missionaries, also check that # missionaries > 0,
        // otherwise, there wouldn't be any missionaries to devour
        if ((state.canArray[missL] > 0 && state.canArray[cannL] > state.canArray[missL]) ||
                (state.canArray[missR] > 0 && state.canArray[cannR] > state.canArray[missR])) return false;
        return true;
    }
	
	double step_cost(Object fromState, Object toState) { return 1; }

	/*
	QUESTION 5
	h(n) = Total People on Initial Side/Boat Capacity
	 */
	public double h(Object state) {
        StateCannibals can_state = (StateCannibals) state;
	    return (double) (can_state.canArray[cannL] + can_state.canArray[missL]) / 2;
	}


	public static void main(String[] args) throws Exception {
		ProblemCannibals problem = new ProblemCannibals();
		int[] canArray = {3,3,1,0,0,0};
		problem.initialState = new StateCannibals(canArray); 

		Search search  = new Search(problem);

        Path file = Paths.get("ProblemCannibalsStatisticsReport.txt");
        List<String> stats = new ArrayList<>();
        stats.add("Q3 Statistics:");
        stats.add("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
        stats.add("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
        stats.add("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());
        stats.add("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
        stats.add("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
        stats.add("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
        stats.add("TreeSearchDepthLimited(15):\t" + search.TreeSearchDepthLimited(15));
        stats.add("GraphSearchDepthLimited(15):\t" + search.GraphSearchDepthLimited(15));
        stats.add("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
        stats.add("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());

        stats.add("\nQ5 Statistics with h(n) = Total People on Initial Side/Boat Capacity");
        stats.add("AstarTreeSearch:\t\t" + search.AstarTreeSearch());
        stats.add("AstarGraphSearch:\t\t" + search.AstarGraphSearch());

        try {
            Files.write(file, stats, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
//
//        System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
//
//        System.out.println("UniformCostTreeSearch:\t\t\t" + search.UniformCostTreeSearch());
//
//        System.out.println("UniformCostGraphSearch:\t\t\t" + search.UniformCostGraphSearch());
//
//        System.out.println("DepthFirstTreeSearch:\t\t\t" + search.DepthFirstTreeSearch());
//
//        System.out.println("DepthFirstGraphSearch:\t\t\t" + search.DepthFirstGraphSearch());
//
//        System.out.println("TreeSearchDepthLimited(15):\t\t" + search.TreeSearchDepthLimited(15));
//
//        System.out.println("GraphSearchDepthLimited(15):\t\t" + search.GraphSearchDepthLimited(15));
//
//        System.out.println("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
//
//        System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());
//
//        System.out.println("AstarTreeSearch:\t\t\t\t" + search.AstarTreeSearch());
//
//        System.out.println("AstarGraphSearch:\t\t\t\t" + search.AstarGraphSearch());
	}
}
