package data;

/**
 * Classe che modella Attributi continui
 */
public class ContinuousAttribute extends Attribute{

	/**
	 * Valore massimo
	 */
	private double max;
	/**
	 * Valore minimi
	 */
	private double min;
	
	/**
	 * Costruttore della classe ContinuousAttribute
	 * @param name
	 * @param index
	 * @param min
	 * @param max
	 */
	ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.max = max;
		this.min = min;
	
	}
	
	/**
	 * Restituisce il valore normalizzato di v
	 * @param v
	 * @return v normalizzato
	 */
	 double getScaledValue(double v) {
		return (v - min) / (max - min);
	}

}
