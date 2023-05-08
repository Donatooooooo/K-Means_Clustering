package mining;
import data.*;

/**
 * Classe raffigurante un insieme di cluster e posizione all'interno data dall'indice i  
 *
 */

public class ClusterSet {
	
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
	 * @param k intero raffigurante la dimensione di C[]
	 */
	ClusterSet(int k) throws OutOfRangeSampleSize {
		if(k  <= 0 || k > 14)
			throw new OutOfRangeSampleSize();
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
	
	
	void initializeCentroids(Data data) throws OutOfRangeSampleSize {
		int[] centroidIndexes = data.sampling(C.length);
       
		for (int i = 0; i < centroidIndexes.length; i++) {
            Tuple centroidI = data.getItemSet(centroidIndexes[i]);
            add(new Cluster(centroidI));
        }
		
	}
    
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

     void updateCentroids(Data data) {
         for (int i = 0; i < C.length; i++) 
         {
             C[i].computeCentroid(data);
         }
     }

     public String toString() {
         String str = "";
         
         for (int i = 0; i < C.length; i++)
         {
             str += i + ": " + C[i] + "\n";
         }
         
         return str;
     }

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
