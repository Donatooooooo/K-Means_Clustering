package mining;
import data.*;
import java.io.Serializable;

/**
 * Classe raffigurante un insieme di cluster e posizione all'interno data dall'indice i  
 *
 */

public class ClusterSet implements Serializable{
	
	/**
	 * array di oggetti cluster
	 */
	private Cluster C[];
	
	/**
	 * indice di posizione all'interno dell'array C
	 */
	private int i = 0; //posizione valida per la memorizzazione di un nuovo cluster in C
	
	/**
	 * Costruttore della classe ClusterSet
	 * @throws OutOfRangeSampleSize, eccezione scaturita qualora l'intero inserito dall'utente non rispetti il range dichiarato
	 * @param k intero raffigurante la dimensione di C[]
	 */
	ClusterSet(int k) throws OutOfRangeSampleSize {
		C = new Cluster[k];
	}
	
	/**
	 * Aggiunge un elemento all'interno di C[] in posizione i incrementadola
	 * @param c cluster da inserire
	 */
	void add(Cluster c) {
		C[i]=c;
		i++;
	}
	
	/**
	 * Ritorna il cluster presente in posizione i
	 * @param i intero raffigurante la posizione 
	 */
	Cluster get(int i) {
		return C[i];
	}
	
	/**
	 * Inizializza i centroidi necessari al k-means
	 * @param data insieme di dati da clusterizzare
	 * @throws OutOfRangeSampleSize, eccezione scaturita qualora l'intero inserito dall'utente non rispetti il range dichiarato
	 */
	void initializeCentroids(Data data) throws OutOfRangeSampleSize {
		int[] centroidIndexes = data.sampling(C.length);
       
		for (int i = 0; i < centroidIndexes.length; i++) {
            Tuple centroidI = data.getItemSet(centroidIndexes[i]);
            add(new Cluster(centroidI));
        }
		
	}
    
	/**
	 * Trova il cluster più vicino
	 * @param tuple, tupla su cui effettuare il controllo
	 * @return nearest, cluster più vicino
	 */
     Cluster nearestCluster(Tuple tuple) {
    	double min = tuple.getDistance(C[0].getCentroid());
    	double tmp;
        Cluster nearest = C[0];
         
       for (int i = 1; i < C.length; i++) 
       {
             tmp = tuple.getDistance(C[i].getCentroid());
             if (tmp < min) 
             {
                 min = tmp;
                 nearest = C[i];
             }
        }
       
         return nearest;
     }
     
     /**
 	 * Restituisce il cluster corrente a cui appartiene un determinato punto di dati
 	 * @param id, intero raffigurante la posizione
 	 * @return C[i], elemento i-esimo di c
 	 */
     Cluster currentCluster(int id) {
         for (int i = 0; i < C.length; i++) 
         {
             if (C[i].contain(id))
             {
                 return C[i];
             }
         }
         return null;
     }

     /**
 	 * Aggiorna i centroidi 
 	 * @param data, dati su cui calcolare i centroidi
 	 */
     void updateCentroids(Data data) {
         for (int i = 0; i < C.length; i++) 
         {
             C[i].computeCentroid(data);
         }
     }

     /**
  	 * Override di toString di Object, rappresenta testualmente gli elementi di C 
  	 * @return str, stringa utilizzata
  	 */
     public String toString() {
         String str = "";
         
         for (int i = 0; i < C.length; i++)
         {
             str += i + ": " + C[i] + "\n";
         }
         
         return str;
     }

     /**
   	 * Override di toString di Object, rappresenta testualmente gli elementi di C 
   	 * @param data, insieme di dati
   	 * @return str, stringa utilizzata
   	 */
     public String toString(Data data) {
         String str = "";
         
         for (int i = 0; i < C.length; i++) 
         {
             if (C[i] != null) 
             {
                 str += i + ": " + C[i].toString(data) + "\n";
             }
         }
         
         return str;
     }
}
