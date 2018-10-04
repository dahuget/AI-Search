import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ProblemMap extends Problem {
	Map<String, Map<String, Double>> map;
	Map<String, Double> sld;
	
	public Object goalState;
	
	public ProblemMap(String mapfilename) throws Exception {
		map = new HashMap<String, Map<String, Double>>();
		//read map from file of source-destination-cost triples (tab separated)
    	BufferedReader reader = new BufferedReader( new FileReader (mapfilename));
        String line;
        while( ( line = reader.readLine() ) != null ) {
        	String[] strA = line.split("\t");
        	
        	String 	from_city = strA[0], 
        			to_city   = strA[1];
        	Double 	cost 	  = Double.parseDouble(strA[2]);
        	
        	if(!map.containsKey(from_city)) 
        		map.put(from_city, new HashMap<String, Double>());
        	map.get(from_city).put(to_city,cost);
        	
        	//putting the reverse edge as well
        	if(!map.containsKey(to_city)) 
        		map.put(to_city, new HashMap<String, Double>());
        	map.get(to_city).put(from_city,cost);
        }
        reader.close();
	}
	
	public ProblemMap(String mapfilename, String heuristicfilename) throws Exception {
		this(mapfilename);
		
		sld = new HashMap<String, Double>();
    	BufferedReader reader = new BufferedReader( new FileReader (heuristicfilename));
        String line;
        while( ( line = reader.readLine() ) != null ) {
        	String[] strA = line.split("\t");
        
        	String 	city = strA[0]; 
        	double 	h 	 = Double.parseDouble(strA[1]);
        	
        	sld.put(city, h);
        }
        reader.close();
	}
	
	boolean goal_test(Object state) {
		return state.equals(goalState);
	}

	Set<Object> getSuccessors(Object state) {
		Set<Object> result = new HashSet<Object>();
		for(Object successor_state : map.get(state).keySet())
			result.add(successor_state);
		return result;
	}
	
	double step_cost(Object fromState, Object toState) {
		return map.get(fromState).get(toState);
	}

	public double h(Object state) {
		return sld.get(state);
	}

	public static void main(String[] args) throws Exception {
		ProblemMap problem = new ProblemMap("/Users/danahuget/Documents/CSC421/AI-Search/romania.txt","/Users/danahuget/Documents/CSC421/AI-Search/romaniaSLD.txt");
		problem.initialState = "Timisoara";
		problem.goalState = "Bucharest";
		
		Search search  = new Search(problem);
		/*
		  QUESTION 1:
		• Breadth-first search (BFS) provided
		• Uniform-cost search (UCS) provided
		• Depth-first search (DFS) provided
		• Depth-limited search (DLS) implemented
		• Iterative deepening search (IDS) implemented
		 */
		Path file = Paths.get("ProblemMapStatisticsReport.txt");
		List<String> stats = new ArrayList<>();
		stats.add("Q1 Statistics:");
//		System.out.println("BreadthFirstTreeSearch:\t\t\t" + search.BreadthFirstTreeSearch());
		stats.add("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
//		System.out.println("BreadthFirstGraphSearch:\t\t" + search.BreadthFirstGraphSearch());
		stats.add("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
//		System.out.println("UniformCostTreeSearch:\t\t\t" + search.UniformCostTreeSearch());
		stats.add("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());
//		System.out.println("UniformCostGraphSearch:\t\t\t" + search.UniformCostGraphSearch());
		stats.add("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
//		System.out.println("DepthFirstTreeSearch:\t\t\t" + search.DepthFirstTreeSearch());
		stats.add("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
//		System.out.println("DepthFirstGraphSearch:\t\t\t" + search.DepthFirstGraphSearch());
		stats.add("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
//		System.out.println("TreeSearchDepthLimited(4):\t\t" + search.TreeSearchDepthLimited(4));
		stats.add("TreeSearchDepthLimited(4):\t" + search.TreeSearchDepthLimited(4));
//		System.out.println("GraphSearchDepthLimited(4):\t\t" + search.GraphSearchDepthLimited(4));
		stats.add("GraphSearchDepthLimited(4):\t" + search.GraphSearchDepthLimited(4));
//		System.out.println("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
		stats.add("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
//		System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());
		stats.add("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());

		// QUESTION 2:
		stats.add("\nQ2 Statistics:");
//		System.out.println("AstarTreeSearch:\t\t\t\t" + search.AstarTreeSearch());
		stats.add("AstarTreeSearch:\t\t" + search.AstarTreeSearch());
		search.PrintTree("FinalAstarTreeSearchTree.txt");

//		System.out.println("AstarGraphSearch:\t\t\t\t" + search.AstarGraphSearch());
		stats.add("AstarGraphSearch:\t\t" + search.AstarGraphSearch());
		search.PrintTree("FinalAstarGraphSearchTree.txt");

		try {
			Files.write(file, stats, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
