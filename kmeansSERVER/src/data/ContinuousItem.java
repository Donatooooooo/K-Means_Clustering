package data;

/**
 * Classe concreta che estende la classe Item e 
 * rappresenta una coppia <Attributo continuo - valore continuo>.
 */
class ContinuousItem extends Item{

	/**
	 * Costruttore di classe che inizializza gli attributi della classe.
	 * Invoca il costruttore della superclasse.
	 * @param attribute Elemento di tipo attributo.
	 * @param value Valore dell'attributo.
	 */
	ContinuousItem(Attribute attribute, double value) {
		super(attribute, value);
	}

	/**
	 * Metodo che restituisce 0 se i due item sono uguali, 1 altrimenti.
	 * @param a Oggetto di tipo Object.
	 * @return Distanza tra due ContinuousItem.
	 */
	double distance(Object a) {
		return Math.abs(((ContinuousAttribute) getAttribute()).getScaledValue((Double) getValue())
						- ((ContinuousAttribute) getAttribute()).getScaledValue((Double) a));
	}
}
