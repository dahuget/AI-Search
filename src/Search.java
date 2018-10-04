import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Search {
	Problem problem;
	
	public Search(Problem problem) { this.problem = problem; }
	
	//Tree-search methods
	public String BreadthFirstTreeSearch() {
		return TreeSearch(new FrontierFIFO());
	}
	
	public String DepthFirstTreeSearch() {
		return TreeSearch(new FrontierLIFO());
	}
	
	public String UniformCostTreeSearch() {
		return TreeSearch(new FrontierPriorityQueue(new ComparatorG()));
	}	

	public String GreedyBestFirstTreeSearch() {
		return TreeSearch(new FrontierPriorityQueue(new ComparatorH(problem)));
	}
	
	public String AstarTreeSearch() {
		return TreeSearch(new FrontierPriorityQueue(new ComparatorF(problem)));
	}

	public String TreeSearchDepthLimited(int limit) {
		cnt = 0;
		return TreeSearchDepthLimited(new FrontierLIFO(), limit);
	}
	
	//Graph-search methods
	public String BreadthFirstGraphSearch() {
		return GraphSearch(new FrontierFIFO());
	}
	
	public String DepthFirstGraphSearch() {
		return GraphSearch(new FrontierLIFO());
	}
	
	public String UniformCostGraphSearch() {
		return GraphSearch(new FrontierPriorityQueue(new ComparatorG()));
	}	

	public String GreedyBestFirstGraphSearch() {
		return GraphSearch(new FrontierPriorityQueue(new ComparatorH(problem)));
	}
	
	public String AstarGraphSearch() {
		return GraphSearch(new FrontierPriorityQueue(new ComparatorF(problem)));
	}

	public String GraphSearchDepthLimited(int limit) {
		cnt = 0;
		return GraphSearchDepthLimited(new FrontierLIFO(), limit);
	}

	/*
	QUESTION 1
	• Breadth-first search (BFS) done
	• Uniform-cost search (UCS) done
	• Depth-first search (DFS) done
	• Depth-limited search (DLS) impl
	• Iterative deepening search (IDS) impl
	 */
	
	//Iterative deepening, tree-search and graph-search QUESTION 1
	// Depth-limited search (DFS, LIFO Frontier)
	/*
	function IterativeDeepening-TREE-SEARCH(problem) returns a solution, or failure
		for limit=0 to infinity
			result = DepthLimited-TREE-SEARCH(problem, limit)
			if result is a solution return result
	 */
	public String IterativeDeepeningTreeSearch() {
		//TODO
		cnt = 0;
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			String result = TreeSearchDepthLimited(new FrontierLIFO(), i);
			if (result != null)
				return result;
		}
		// not reached because above will loop until solution is found
		return null;
	}

	/*
	function IterativeDeepening-GRAPH-SEARCH(problem) returns a solution, or failure
	for limit=0 to infinity
		result = DepthLimited-GRAPH-SEARCH(problem, limit)
		if result is a solution return result
	 */
	public String IterativeDeepeningGraphSearch() {
		//TODO
		cnt = 0;
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			String result = GraphSearchDepthLimited(new FrontierLIFO(), i);
			if (result != null)
				return result;
		}
		// not reached because above will loop until solution is found
		return null;	
	}
	
	//For statistics purposes
	int cnt; //count expansions
	List<Node> node_list; //store all nodes ever generated
	Node initialNode; //initial node based on initial state
	//
	
	private String TreeSearch(Frontier frontier) {
		cnt = 0; 
		node_list = new ArrayList<Node>();
		
		initialNode = MakeNode(problem.initialState); 
		node_list.add( initialNode );
		
		frontier.insert( initialNode );
		while(true) {
			
			if(frontier.isEmpty())
				return null;
			
			Node node = frontier.remove();
			
			if( problem.goal_test(node.state) )
				return Solution(node);
			
			frontier.insertAll(Expand(node,problem));
			cnt++;
		}
	}

	private String GraphSearch(Frontier frontier) {
		cnt = 0;
		node_list = new ArrayList<Node>();

		initialNode = MakeNode(problem.initialState);
		node_list.add( initialNode );

		Set<Object> explored = new HashSet<Object>(); //empty set
		frontier.insert( initialNode );
		while(true) {
			
			if(frontier.isEmpty())
				return null;
			
			Node node = frontier.remove();
			
			if( problem.goal_test(node.state) )
				return Solution(node);
			
			if( !explored.contains(node.state) ) {
				explored.add(node.state);
				frontier.insertAll(Expand(node,problem));
				cnt++;
			}
		}
	}

	/*
	function DepthLimited-TREE-SEARCH(problem, limit) returns a solution, or failure
		initialize the frontier using the initial state of problem
		loop do
			if the frontier is empty then return failure
			remove n from the frontier
			if n contains a goal state then return the corresponding solution
			if the depth of n is less than limit then
				expand n adding the resulting nodes to the frontier
	 */
	// QUESTION 1
	private String TreeSearchDepthLimited(Frontier frontier, int limit) {
		//TODO
		node_list = new ArrayList<Node>();

		initialNode = MakeNode(problem.initialState);
		node_list.add( initialNode );

		frontier.insert( initialNode );
		while(true) {
			if (frontier.isEmpty())
				return null;

			Node node = frontier.remove();
			if( problem.goal_test(node.state) )
				return Solution(node);

			if (node.depth < limit) {
				frontier.insertAll(Expand(node, problem));
				cnt++;
			}

		}
	}

	/*
	function DepthLimited-GRAPH-SEARCH(problem, limit) returns a solution, or failure
		initialize the frontier using the initial state of problem
		initialize the explored set to be empty
		loop do
			if the frontier is empty then return failure
			remove n from the frontier
			if n contains a goal state then return the corresponding solution
			if the state of n is not in explored and the depth of n is less than limit
				add the state of n to explored
				expand n adding the resulting nodes to the frontier
	 */

	private String GraphSearchDepthLimited(Frontier frontier, int limit) {
		//TODO
		node_list = new ArrayList<Node>();

		initialNode = MakeNode(problem.initialState);
		node_list.add( initialNode );

		Set<Object> explored = new HashSet<Object>(); //empty set
		frontier.insert( initialNode );
		while(true) {
			if (frontier.isEmpty())
				return null;

			Node node = frontier.remove();
			if( problem.goal_test(node.state) )
				return Solution(node);

			if( !explored.contains(node.state) && node.depth < limit) {
				explored.add(node.state);
				frontier.insertAll(Expand(node, problem));
				cnt++;
			}
		}
	}

	private Node MakeNode(Object state) {
		Node node = new Node();
		node.state = state;
		node.parent_node = null;
		node.path_cost = 0;
		node.depth = 0;
		return node;
	}
	
	private Set<Node> Expand(Node node, Problem problem) {
		node.order = cnt;
		
		Set<Node> successors = new HashSet<Node>(); //empty set
		Set<Object> successor_states = problem.getSuccessors(node.state);
		
		for(Object result : successor_states) {
			Node s = new Node();
			s.state = result;
			s.parent_node = node;
			s.path_cost = node.path_cost + problem.step_cost(node.state, result); 
			s.depth = node.depth + 1; 
			successors.add(s);
			
			node_list.add( s );
		}
		
		return successors;
	}
	
	//Create a string to print solution. 
	private String Solution(Node node) {
		
		String solution_str = "(cost=" + node.path_cost + ", expansions=" + cnt + ")\t";
		
		Deque<Object> solution = new ArrayDeque<Object>();
		do {
			solution.push(node.state);
			node = node.parent_node;
		} while(node != null);
		
		while(!solution.isEmpty())
			solution_str += solution.pop() + " ";
		
		return solution_str;
	}

	/* QUESTION 2
	print out the final search tree, and add at each node its path cost value (g), the heuristic
	function value (h), their sum (f=g+h), and the order of expansion, e.g. the root will be 0,
	then the next node expanded will be 1, etc.
	Timisoara(g=0.0, h=329.0, f=329.0) order=0
		Arad(g=118.0, h=366.0, f=484.0) order=3
			...
		Lugoj(g=111.0, h=244.0, f=355.0) order=1
			...
	 */

	List<String> tree = new ArrayList<>();

	public void PrintTree(String fileName) {
		PrintTree(initialNode);
		Path file = Paths.get(fileName);
		try {
			Files.write(file, tree, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void PrintTree(Node n) {
		tree.add(getNodeString(n));
		for (Node m: node_list) {
			if (m.parent_node == n)
				PrintTree(m);
		}
	}

	private String getNodeString(Node n) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < n.depth; i++) {
			s.append("  ");
		}
		s.append(n.state+"(g="+n.path_cost+", h="+problem.h(n.state)+", f="+(n.path_cost+problem.h(n.state))+") order="+n.order);
		return s.toString();
	}
}
