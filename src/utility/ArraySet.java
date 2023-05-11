package utility;
import java.util.Arrays;

/**
 * Classe che rappresenta un array di booleani
 */
public class ArraySet {
	
	/**
	 * array di booleani
	 */
	private boolean set[];
	
	/**
	 * lunghezza dell'array
	 */
	private int size=0;
	
	
	/**
	 * Costruttore della classe ArraySet, imposta ogni posizione in false
	 */
	public ArraySet () {
		set=new boolean[50];
		for(int i = 0; i < set.length; i++)
			set[i] = false;
	}
	
	/**
	 * Ritorna vero se il metodo add cambia arraySet
	 * @param i indice di posizione di arraySet
	 */
	public boolean add(int i){
		if(i >= set.length)
		{
			//enlarge the set
			boolean temp[]=new boolean[set.length*2];
			Arrays.fill(temp,false);
			System.arraycopy(set, 0, temp, 0, set.length);
			set = temp;
		}	
		boolean added=set[i];
		set[i] = true;
		if(i >= size)
			size = i+1;
		return !added;
		
		
	}
	
	/**
	 * Ritorna vero se l'indice di posizione è minore di size
	 * @param i indice di posizione di arraySet
	 */
	public boolean delete(int i){
		if(i < size){
			boolean deleted = set[i];
			set[i]=false;
			if(i == size-1){
				//update size
				int j;
				for(j = size-1; j >= 0 && !set[j]; j--);
				size = j+1;
			}
			
			return deleted;
		}
		return false;
	}
	
	/**
	 * Ritorna il valore di set in posizione dell'indice i
	 * @param i indice di posizione di arraySet
	 */
	public boolean get(int i){
		return set[i];
	}
	
	/**
	 * Ritorna un array di interi raffiguranti la posizione delle tuple (true) accessibili ???
	 * @return a
	 */
	public int[] toArray(){
		int a[] = new int[0];
		for(int i = 0; i < size; i++){
			if(get(i)){
				int temp[] = new int[a.length+1];
				System.arraycopy(a, 0, temp, 0, a.length);
				a = temp;
				a[a.length-1] = i;
			}
		}
		return a;
	}
}
