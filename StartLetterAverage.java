package w1d3_q2;

import java.util.ArrayList;
import java.util.List;

public class StartLetterAverage {
	private List<Mapper> mappers;
	private List<GroupByPair<Reducer, Pair<String, Pair<Integer, Integer>>>> reducers;
	private int m_nums;
	private int r_nums;
	public StartLetterAverage(int m_nums, int r_nums) {
		this.mappers = new ArrayList<>();
		this.reducers = new ArrayList<>();
		this.m_nums = m_nums;
		this.r_nums = r_nums;
		
		for(int i = 0; i < m_nums; i++) {
			mappers.add(new Mapper());
		}
		
		for(int i = 0; i < r_nums; i++) {
			reducers.add(new GroupByPair<Reducer, Pair<String, Pair<Integer, Integer>>>(new Reducer()));
		}
	}
	
	public void execute() {
		//get mapper outputs
		List<Pair<String, Pair<Integer, Integer>>> m_out = new ArrayList<>();
		for (int i = 0; i < mappers.size(); i++) {
			m_out = mappers.get(i).map("./input/a"+i+".txt");	
			System.out.println("\nMapper " + i + " Output");
			System.out.println("===============");
			m_out.forEach(p -> System.out.println("<" + p.getKey() + ", " + p.getValue() + ">"));
			
			//Channel the mapper outputs to the right reducers
			for (Pair<String, Pair<Integer, Integer>> pair : m_out) {
				int r = getPartition(pair.getKey());
				reducers.get(r).getValue().add(pair);
			}
		}
		
		//output the reducer outputs
		Reducer reducer;
		for (int k = 0; k < reducers.size(); k++) {
			GroupByPair<Reducer, Pair<String, Pair<Integer, Integer>>> p = reducers.get(k);
			reducer = reducers.get(k).getKey();
			System.out.println("\nReducer " + k + " Input");
			System.out.println("===============");
			List<Pair<String, Pair<Integer, Integer>>> r_in = p.getValue();
			List<Pair<String, Double>> r_out = reducer.reduce(r_in);
			System.out.println("\nReducer " + k + " Output");
			System.out.println("===============");
			r_out.forEach(pr -> System.out.println("<" + pr.getKey() + ", " + pr.getValue() + ">"));
		}
		
	}
	
	public int getPartition(String key){
		return (int) key.hashCode() % r_nums;
	}

}
