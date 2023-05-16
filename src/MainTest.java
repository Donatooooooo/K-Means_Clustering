import data.*;
import mining.KMeansMiner;
import keyboardinput.Keyboard;


public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		char value='Y';
		do {
			Data data =new Data();
			System.out.println(data);
			KMeansMiner kmeans;
			int k = 0;
			int numIter = 0;
			
			System.out.println("Inserisci il numero di cluster (tra 1 e 14) che verranno generati: ");			
			k=Keyboard.readInt();																//numero di centroidi
			try {
				kmeans=new KMeansMiner(k);												//oggetto kmeans
				numIter=kmeans.kmeans(data);											//richiama il metodo kmeans
			} catch (OutOfRangeSampleSize e) {
				System.out.println(e.getMessage());
		        continue;
			}
			System.out.println("Numero di Iterazione:"+numIter);
			System.out.println(kmeans.getC().toString(data));
				
			System.out.println("Ripetere il processo? Y -yes | N -no");
			do {
				value=Keyboard.readChar();
			} while (value != 'Y' && value != 'N');
			
			if(value == 'N') System.out.println("Bye");
		} while (value == 'Y');
		
	}
}
