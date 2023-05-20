package mining;

import data.*;
import java.io.*;

/*
 * classe che au un oggetto ClusterSet sfrutta l'algorito kmeans
 */
public class KMeansMiner {
	
	/**
	 *oggetto cluster su cui applicare kmeans
	 */
	 ClusterSet C;
	
	 /**
	 * Costruttore della classe KMeansMiner
	 * @param k intero raffigurante la dimensione del clusterSet
	 */
	public KMeansMiner(int k) throws OutOfRangeSampleSize {
		C = new ClusterSet(k);
	}
	
	public KMeansMiner(String fileName) throws FileNotFoundException, IOException,ClassNotFoundException
    {
        ObjectInputStream in=new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
        C=(ClusterSet) in.readObject();
        in.close();
    }
	
	public void SaveKMeansMiner(String fileName) throws FileNotFoundException,IOException
    {
        ObjectOutputStream out=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        out.writeObject(C);
        out.close();
    }

	
	/**
	 * Ritorna il ClusterSet dichiarato
	 * @return C;
	 */
	public ClusterSet getC(){
		return C;
	}
	
	/**
	 *  Implementa l'algoritmo K-means per la clustering dei dati
	 * @throws OutOfRangeSampleSize, eccezione scaturita qualora l'intero inserito dall'utente non rispetti il range dichiarato
	 * @param Data data, insieme di dati su cui applicare il k-means
	 * @return numberOfIteraion, numero di iterazioni effettuate durante l'esecuzione del k-means.
	 */
	public int kmeans(Data data) throws OutOfRangeSampleSize{
		 int numberOfIterations = 0;
	        //STEP 1
	        C.initializeCentroids(data);
	        boolean changedCluster = false;
	        do {
	            numberOfIterations++;
	            //STEP 2
	            changedCluster = false;
	            for (int i = 0; i < data.getNumberOfExamples(); i++) {
	                Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
	                Cluster oldCluster = C.currentCluster(i);
	                boolean currentChange = nearestCluster.addData(i);
	                if (currentChange)
	                    changedCluster = true;
	                //rimuovo la tupla dal vecchio cluster
	                if (currentChange && oldCluster != null)
	                    //il nodo va rimosso dal suo vecchio cluster
	                    oldCluster.removeTuple(i);
	            }
	            //STEP 3
	            C.updateCentroids(data);
	        } while (changedCluster);
	        return numberOfIterations;
		
	}
	
	
	
	
}
