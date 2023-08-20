package data;

/**
 * Classe concreta che estende la superclasse Exception, 
 * modella l'eccezione OutOfRangeSampleSize.
 */

public class OutOfRangeSampleSize extends Exception{

	/**
	 * Costruttore della classe, richiama il costruttore della superclasses Exception. 
	 * @param txt Messaggio di errore.
	 */
	 public OutOfRangeSampleSize(String txt) {
		super(txt);
	 }
	
}
