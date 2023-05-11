package data;

/**
 * Classe che modella l'eccezione OutOfRangeSampleSize, estende la superclasse Exception
 */
public class OutOfRangeSampleSize extends Exception{
	
	/**
	 * Costruttore della classe, richiama il costruttore della superclasse
	 */
	public OutOfRangeSampleSize() {
		super();
	}
	
	/**
	 * Costruttore della classe, richiama il costruttore della superclasse passando il paramentro 
	 * @param stringa txt raffigurante un messaggio
	 */
	 public OutOfRangeSampleSize(String txt) {
		super(txt);
	 }
	
}
