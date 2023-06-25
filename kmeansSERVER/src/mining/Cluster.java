package mining;

import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;
import data.*;

/**
 *  Classe che modella il cluster
 */
public class Cluster implements Serializable {

    /**
     * Oggetto di Tupla
     */
    private Tuple centroid;

    /**
     * Oggetto di Data
     */
    private Set < Integer > clusteredData;

    /**
     * Oggetto di Tupla
     */
    Cluster(Tuple centroid) {
        this.centroid = centroid;
        clusteredData = new HashSet < Integer > ();

    }

    Tuple getCentroid() {
        return centroid;
    }

    void computeCentroid(Data data) {
        for (int i = 0; i < centroid.getLength(); i++)
            centroid.get(i).update(data, clusteredData);
    }
    
    //return true if the tuple is changing cluster
    boolean addData(int id) {
        return clusteredData.add(id);
    }

    //verifica se una transazione � clusterizzata nell'array corrente
    boolean contain(int id) {
        return clusteredData.contains(id);
    }

    //remove the tuplethat has changed the cluster
    void removeTuple(int id) {
        clusteredData.remove(id);
    }

    public String toString() {
        String str = "Centroid=(";
        
        for (int i = 0; i < centroid.getLength(); i++) {
            str += centroid.get(i);
            if (i < centroid.getLength() - 1) {
                str += " ";
            }       	
        }

        str += ")";
        
        return str;
    }

    public String toString(Data data) {
        String str = "Centroid = (";

        for (int i = 0; i < centroid.getLength(); i++) {
            str += centroid.get(i);
            if (i < centroid.getLength() - 1) {
                str += " ";
            }
        }

        str += ")\nExamples:\n";

        for (int i : clusteredData) {
            str += "[";
            for (int j = 0; j < data.getNumberOfExplanatoryAttributes(); j++) {
                str += data.getAttributeValue(i, j);
                if (j < data.getNumberOfExplanatoryAttributes() - 1) {
                    str += " ";
                }
            }
            str += "] dist=" + getCentroid().getDistance(data.getItemSet(i)) + "\n";
        }

        str += "AvgDistance=" + getCentroid().avgDistance(data, clusteredData) + "\n";

        return str;
    }
}
