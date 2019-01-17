package w1d3_q2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Reducer implements Comparable<Reducer>{

		public List<Pair<String, Double>> reduce(List<Pair<String, Pair<Integer, Integer>>> m_output) {
			List<GroupByPair<String, Pair<Integer, Integer>>> r_input = new LinkedList<>();
			
			Iterator<Pair<String, Pair<Integer, Integer>>> it = m_output.iterator();
			
			//Creating list of GroupByPairs
			while(it.hasNext()) {
				//Check if it is in the GroupByPair list
				Pair<String, Pair<Integer, Integer>> pair = it.next();
				GroupByPair<String, Pair<Integer, Integer>> groupByPair = new GroupByPair<>(pair.getKey(), pair.getValue());
				if(r_input.contains(groupByPair)) {
					//get the groupbypair and add its value to key list
					r_input.get(r_input.indexOf(groupByPair)).getValue().add(pair.getValue());
					
				}else {
					r_input.add(groupByPair);
				}
			}
			
			Iterator<GroupByPair<String, Pair<Integer, Integer>>> r = r_input.iterator();
			List<Pair<String, Double>> r_output = new LinkedList<>();
			
			//Creating list of Pairs with key-value(total count) pair
			while(r.hasNext()) {
				//Check if it is in the GroupByPair list
				GroupByPair<String, Pair<Integer, Integer>> pair = r.next();
				System.out.println("<" + pair.getKey() + ", " + pair.getValue().toString() + ">");	
				
				double sum = pair.getValue().stream().mapToInt( p -> p.getKey().intValue()).sum();
				double cnt = pair.getValue().stream().mapToInt( p -> p.getValue().intValue()).sum();
				r_output.add(new Pair<String, Double>(pair.getKey(), sum/cnt));
			}
//			System.out.println("===========================");
			return r_output.stream().sorted().collect(Collectors.toList());
		}

		@Override
		public int compareTo(Reducer o) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
