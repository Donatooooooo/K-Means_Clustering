package data;
import java.io.Serializable;

/**
 * Classe che modella un attributo
 */
public abstract class Attribute implements Serializable {
	
	/**
	 * Nome dell'attributo
	 */
	private String name;
	/**
	 * Indice dell'attributo
	 */
	private int index;
	
	/**
	 * Costruttore della classe Attribute
	 * @param name
	 * @param index
	 */
	Attribute(String name, int index) {
		this.name=name;
		this.index=index;
	}
	
	/**
	 * Restituisce il nome dell'attributo
	 * @return name
	 */
	String getName() {
		return name;
	}
	
	/**
	 * Restituiscel'indice dell'attributo
	 * @return Index
	 */
	int getIndex() {
		return index;
	}
	
	/**
	 * Restituisce la stringa name
	 * @return name
	 */
	public String toString() {
		return name; 
	}
}
