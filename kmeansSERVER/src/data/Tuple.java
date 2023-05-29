package data;

import java.util.Set;
import java.io.Serializable;

public class Tuple implements Serializable {

    private Item[] tuple;

    Tuple(int size) {
        tuple = new Item[size];
    }

    public int getLength() {
        return tuple.length;
    }

    public Item get(int i) {
        return tuple[i];
    }

    void add(Item c, int i) {
        tuple[i] = c;
    }

    public double getDistance(Tuple obj) {
        double distance = 0;

        for (int i = 0; i < getLength(); i++) {
            distance += tuple[i].distance(obj.get(i).getValue());
        }

        return distance;
    }

    public double avgDistance(Data data, Set < Integer > clusteredData) {
        double p = 0.0, sumD = 0.0;

        for (int i: clusteredData) {
            double d = getDistance(data.getItemSet(i));
            sumD += d;
        }
        p = sumD / clusteredData.size();

        return p;
    }
}