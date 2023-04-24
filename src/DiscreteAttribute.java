public class DiscreteAttribute extends Attribute {
	
	private String values[];
	
	protected DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);
		this.values=values;
	};
	
	protected int getNumberOfDistinctValues() {
		return values.length;
	};
	
	protected String getValue(int i) {
		return values[i];
	};
	
	
	
	

}
