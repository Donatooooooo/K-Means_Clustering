public class DiscreteItem extends Item{
	
	DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
	}
	
	double distance(Object a) {
		if(getValue().equals(a))
		{
			return 0;
		}
		
		return 1;
	}

}
