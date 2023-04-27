public class ContinuousAttribute extends Attribute{

	private double max;
	private double min;
	
	ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.max = max;
		this.min = min;
	
	}
	
	 double getScaledValue(double v) {
		return (v - min) / (max - min);
	}

}
