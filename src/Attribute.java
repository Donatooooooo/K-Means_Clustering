abstract public class Attribute{
	private String name;
	private int index;
	
	protected Attribute(String name, int index){
		this.name=name;
		this.index=index;
	}
	
	protected String getName() {
		return name;
	}
	
	protected int getIndex() {
		return index;
	}
	
	public String toString() {
		return name; 
	}
}
