package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa l'interfaccia Comparable e che
 * modella una transizione (riga della tabella).
 */
public class Example implements Comparable < Example > {

    /**
     * Lista di oggetti che rappresentano i valori della transizione.
     */
    private List < Object > example = new ArrayList < > ();

    /** 
     * Metodo che aggiunge un oggetto alla lista.
     * @param o oggetto da aggiungere alla lista.
     */
    public void add(Object o) {
        example.add(o);
    }

    /**
     * Metodo che restituisce l'i-esimo elemento della lista.
     * @param i indice dell'elemento da restituire.
     * @return l'i-esimo elemento collezionato nella lista.
     */
    public Object get(int i) {
        return example.get(i);
    }

    /**
     * Implementazione del metodo compareTo dell'interfaccia Comparable.
     * @param ex oggetto da confrontare con l'oggetto corrente.
     */
    public int compareTo(Example ex) {
        int i = 0;
        for (Object o: ex.example) {
            if (!o.equals(this.example.get(i)))
                return ((Comparable) o).compareTo(example.get(i));
            i++;
        }
        return 0;
    }

    /**
     * Metodo che restituisce una stringa che rappresenta 
     * lo stato di un oggetto Example.
     * @return stringa che rappresenta lo stato di Example.
     */
    public String toString() {
        String str = "";
        for (Object o: example)
            str += " " + o.toString() + " ";
        return str;
    }
}
