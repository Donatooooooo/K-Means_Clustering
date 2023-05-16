package data;

import java.util.*;

public class Data {
	List<Example> data;
	private int numberOfExamples;
	List<Attribute> attributeSet = new LinkedList<Attribute>();
	
	public Data(){
		
		TreeSet<Example> tempData=new TreeSet<Example>();
        Example ex0 = new Example();
        Example ex1 = new Example();
        Example ex2 = new Example();
        Example ex3 = new Example();
        Example ex4 = new Example();
        Example ex5 = new Example();
        Example ex6 = new Example();
        Example ex7 = new Example();
        Example ex8 = new Example();
        Example ex9 = new Example();
        Example ex10 = new Example();
        Example ex11 = new Example();
        Example ex12 = new Example();
        Example ex13 = new Example();

        ex0.add("sunny");
        ex1.add("sunny");
        ex2.add("overcast");
        ex3.add("rain");
        ex4.add("rain");
        ex5.add("rain");
        ex6.add("overcast");
        ex7.add("sunny");
        ex8.add("sunny");
        ex9.add("rain");
        ex10.add("sunny");
        ex11.add("overcast");
        ex12.add("overcast");
        ex13.add("rain");
        
        ex0.add("hot");
        ex1.add("hot");
        ex2.add("hot");
        ex3.add("mild");
        ex4.add("cool");
        ex5.add("cool");
        ex6.add("cool");
        ex7.add("mild");
        ex8.add("cool");
        ex9.add("mild");
        ex10.add("mild");
        ex11.add("mild");
        ex12.add("hot");
        ex13.add("mild");

        ex0.add("high");
        ex1.add("high");
        ex2.add("high");
        ex3.add("high");
        ex4.add("normal");
        ex5.add("normal");
        ex6.add("normal");
        ex7.add("high");
        ex8.add("normal");
        ex9.add("normal");
        ex10.add("normal");
        ex11.add("high");
        ex12.add("normal");
        ex13.add("high");

        ex0.add("weak");
        ex1.add("strong");
        ex2.add("weak");
        ex3.add("weak");
        ex4.add("weak");
        ex5.add("strong");
        ex6.add("strong");
        ex7.add("weak");
        ex8.add("weak");
        ex9.add("weak");
        ex10.add("strong");
        ex11.add("strong");
        ex12.add("weak");
        ex13.add("strong");

        ex0.add("no");
        ex1.add("no");
        ex2.add("yes");
        ex3.add("yes");
        ex4.add("yes");
        ex5.add("no");
        ex6.add("yes");
        ex7.add("no");
        ex8.add("yes");
        ex9.add("yes");
        ex10.add("yes");
        ex11.add("yes");
        ex12.add("yes");
        ex13.add("no");

        tempData.add(ex0);
        tempData.add(ex1);
        tempData.add(ex2);
        tempData.add(ex3);
        tempData.add(ex4);
        tempData.add(ex5);
        tempData.add(ex6);
        tempData.add(ex7);
        tempData.add(ex8);
        tempData.add(ex9);
        tempData.add(ex10);
        tempData.add(ex11);
        tempData.add(ex12);
        tempData.add(ex13);

        data=new ArrayList<Example>(tempData);
        numberOfExamples = data.size();

		// TO DO : avvalorare ciascune elemento di attributeSet con un oggetto della classe DiscreteAttribute
        //che modella il corrispondente attributo (e.g. outlook, temperature,etc)
		String outLookValues[]=new String[3];
		outLookValues[0]="overcast";
		outLookValues[1]="rain";
		outLookValues[2]="sunny";
		attributeSet.add(new DiscreteAttribute("Outlook",0, outLookValues));
		
		String[] temperatureValues = new String[3];
		temperatureValues[0] = "cool";
		temperatureValues[1] = "mild";
		temperatureValues[2] = "hot";
        attributeSet.add(new DiscreteAttribute("Temperature", 1, temperatureValues));

        String[] humidityValues = new String[2];
        humidityValues[0] = "high";
        humidityValues[1] = "normal";
        attributeSet.add(new DiscreteAttribute("Humidity", 2, humidityValues));

        String[] windValues = new String[2];
        windValues[0] = "weak";
        windValues[1] = "strong";
        attributeSet.add(new DiscreteAttribute("Wind", 3, windValues));

        String[] playTennisValues = new String[2];
        playTennisValues[0] = "yes";
        playTennisValues[1] = "no";
        attributeSet.add(new DiscreteAttribute("PlayTennis", 4, playTennisValues));
	}
	
	public int getNumberOfExamples() {
		return numberOfExamples;
	}
	
	public int getNumberOfExplanatoryAttributes() {
		return attributeSet.size();
	}
	
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data.get(exampleIndex).get(attributeIndex);
	}
	
	Attribute getAttribute(int index){
		return attributeSet.get(index);
	}
	
	public String toString(){
		
		String s = new String(" ");
		for(Attribute i: attributeSet) {
			s += i.getName() + " ";
		}
		
		s += "\n";
		
		int i = 0;
		for(Example ex: data) {
			s += i + ":" + ex.toString() + "\n";
			i++;
		}
		return s;
	}
	
	public Tuple getItemSet(int index) {
        Tuple tuple = new Tuple(attributeSet.size());
        
        for (Attribute a: attributeSet)
        {
            tuple.add(new DiscreteItem((DiscreteAttribute) a, (String) data.get(index).get(a.getIndex())), a.getIndex());
        }
        
        return tuple;
    }
	
	public int[] sampling(int k) throws OutOfRangeSampleSize {
        int[] centroidIndexes = new int[k]; //choose k random different centroids in data.
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < k; i++) {
            boolean found = false;
            int c;
            do {
                found = false;
                c = rand.nextInt(getNumberOfExamples()); // verify that centroid[c] is not equal to a centroide already stored in CentroidIndexes
                for (int j = 0; j < i; j++)
                    if (compare(centroidIndexes[j], c)) {
                        found = true;
                        break;
                    }
            }
            while (found);
            centroidIndexes[i] = c;
        }
        return centroidIndexes;
    }
	
	private boolean compare(int i, int j) {
		
		for(Attribute a: attributeSet) 
		{
			if (!data.get(i).get(a.getIndex()).equals(data.get(j).get(a.getIndex()))) 
			{
				return false;
			}
		}
		return true;
	}
	
	Object computePrototype(Set<Integer> idList, Attribute attribute) {
		return computePrototype(idList, (DiscreteAttribute)attribute);
	}
	
	 String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
		
		Iterator<String> i = attribute.iterator();
		String prototype = i.next();
		int max = attribute.frequency(this, idList, prototype);
		int tmp;
	     
	    String temp;
	    while (i.hasNext())
	    {
	    	temp = i.next();
	        tmp = attribute.frequency(this, idList, temp);
	        if (tmp > max) 
	        {
	        	max = tmp;
	        	prototype = temp;
	         }
	    }
	        
	    return prototype;
	}
	
	/**
	 * Inner class che serve a modellare ciascuna tansizione
	 */
	class Example implements Comparable<Example> {
		
		/**
		 * Array di object che rappresenta la singola transizione
		 */
		private List<Object> example = new ArrayList<Object>();
		
		/**
		 * Aggiunge in coda l'oggetto o
		 * @param o
		 */
		private void add(Object o) {
			example.add(o);
		}
		
		/**
		 * Ritorna l'oggetto della lista in posizione i
		 * @param i
		 * @return oggetto i-esimo
		 */
		private Object get(int i) {
			return example.get(i);
		}
		
		/**
		 * Implementazione del compareTo
		 * @param
		 */
		public int compareTo(Example ex) {
			
			Iterator<Object> ex1 = this.example.iterator();
			Iterator<Object> ex2 = ex.example.iterator();
			while(ex1.hasNext() & ex2.hasNext())
			{
				Object o1 = ex1.next();
				Object o2 = ex2.next();
				if (!o1.equals(o2))
	               {
	                   return ((Comparable) o1).compareTo(o2);
	               }
			}
			return 0;
		}
		
		public String toString() {
			String s = "";
			for(Object o: example)
			{
				s += (String)o + " ";
			}
			return s;
		}
	}
	
	
	public static void main(String[] args) {
        Data trainingSet = new Data();
        System.out.println(trainingSet);
        
    }

}
