package data;
import utility.ArraySet;

public class DiscreteAttribute extends Attribute {
	
	private String values[];
	
	DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);
		this.values=values;
	}
	
	int getNumberOfDistinctValues() {
		return values.length;
	}
	
	String getValue(int i) {
		return values[i];
	}
	
		 
	int frequency(Data data, ArraySet idList, String v) {
        	int count = 0;
        	int[] a = idList.toArray();
        	for (int i = 0; i < a.length; i++) {
            	if (data.getAttributeValue(a[i], this.getIndex()).equals(v))
                	count++;
        	}
        	return count;
    	}
		 
		 
		
	
	
	
	
	

}
