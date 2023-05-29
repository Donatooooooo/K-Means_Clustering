package data;

public class ContinuousItem extends Item{

	ContinuousItem(Attribute attribute, double value) {
		super(attribute, value);
	}

	double distance(Object a) {
		return Math.abs(((ContinuousAttribute) getAttribute()).getScaledValue((Double) getValue())
						- ((ContinuousAttribute) getAttribute()).getScaledValue((Double) a));
	}
}
