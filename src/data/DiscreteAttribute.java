package data;

import java.util.TreeSet;
import java.util.Set;
import  java.util.Iterator;

/**
 * Classe che modella attributi discreti.
 */
public class DiscreteAttribute extends Attribute implements Iterable<String> {
	
	/**
	 * TreeSet di stringhe
	 */
	private TreeSet<String> values;
	
	/**
	 * Costruttore della classe DescreteAttribute
	 * @param name
	 * @param index
	 * @param values
	 */
	DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);
		this.values = new TreeSet<String>();
		for (String s: values)
			this.values.add(s);
	}
	
	/**
	 * Restitusce un iteratore
	 */
	public Iterator<String> iterator() {
		return this.values.iterator();
	}
	
	/**
	 * Calcola la frequenza della syringa v nel dataset.
	 * @param data
	 * @param idList
	 * @param v
	 * @return count
	 */
	int frequency(Data data, Set<Integer> idList, String v) {
        	int count = 0;
 
        	for (int i: idList) {
            	if (data.getAttributeValue(i, this.getIndex()).equals(v))
                	count++;
        	}
        	return count;
    	}

		
	
	
	
	
	

}
