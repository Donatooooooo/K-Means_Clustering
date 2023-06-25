package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Iterator;
import java.sql.SQLException;
import database.*;

public class Data {

    private List < Example > data;
    private int numberOfExamples;
    private List < Attribute > attributeSet;

    public Data(String server, String database, String table, String userID, String psw)
    	throws DatabaseConnectionException, SQLException, NoValueException, EmptySetException {

        DbAccess db = new DbAccess(server, database, userID, psw);
        db.initConnection();
        TableData td = new TableData(db);
        TableSchema ts = new TableSchema(db, table);

        data = td.getDistinctTransazioni(table);
        numberOfExamples = data.size();
        attributeSet = new ArrayList < > ();

        double qMIN;
        double qMAX;

        for (int i = 0; i < ts.getNumberOfAttributes(); i++) {
            if (ts.getColumn(i).isNumber()) {
                qMIN = (double) td.getAggregateColumnValue(table, ts.getColumn(i), QUERY_TYPE.MIN);
                qMAX = (double) td.getAggregateColumnValue(table, ts.getColumn(i), QUERY_TYPE.MAX);
                attributeSet.add(new ContinuousAttribute(ts.getColumn(i).getColumnName(), i, qMIN, qMAX));
            } else {
                HashSet < Object > distValues = (HashSet < Object > ) td.getDistinctColumnValues(table, ts.getColumn(i));
                HashSet < String > values = new HashSet < > ();

                for (Object o: distValues)
                    values.add((String) o);

                attributeSet.add(new DiscreteAttribute(ts.getColumn(i).getColumnName(), i, values));
            }

        }
    }

    public int getNumberOfExamples() {
        return numberOfExamples;
    }

    public int getNumberOfExplanatoryAttributes() {
        return attributeSet.size();
    }

    public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return data.get(exampleIndex).get(attributeIndex);
    }

    Attribute getAttribute(int index) {
        return attributeSet.get(index);
    }

    public String toString() {

        String s = new String(" ");
        for (Attribute i: attributeSet)
            s += i.getName() + " ";

        s += "\n";

        int i = 1;
        for (Example ex: data) {
            s += i + ": " + ex.toString() + "\n";
            i++;
        }
        return s;
    }

    public Tuple getItemSet(int index) {

        Tuple tuple = new Tuple(attributeSet.size());

        for (Attribute a: attributeSet) {
            if (a instanceof DiscreteAttribute)
                tuple.add(new DiscreteItem((DiscreteAttribute) a, (String) data.get(index).get(a.getIndex())), a.getIndex());
            else
                tuple.add(new ContinuousItem((ContinuousAttribute) a, (Double) data.get(index).get(a.getIndex())), a.getIndex());
        }

        return tuple;
    }

    public int[] sampling(int k) throws OutOfRangeSampleSize {

        if (k < 1 || k > data.size()) throw new OutOfRangeSampleSize
        	("\n--- Valore inserito non valido, deve essere compreso tra 1 e " + data.size() + " ---\n");

        int[] centroidIndexes = new int[k];
        Random rand = new Random();

        rand.setSeed(System.currentTimeMillis());

        for (int i = 0; i < k; i++) {
            int c;
            boolean found = false;

            do {
                found = false;
                c = rand.nextInt(getNumberOfExamples());
                for (int j = 0; j < i; j++)
                    if (compare(centroidIndexes[j], c)) {
                        found = true;
                        break;
                    }
            } while (found);

            centroidIndexes[i] = c;
        }
        return centroidIndexes;
    }

    private boolean compare(int i, int j) {

        for (Attribute a: attributeSet)
            if (!data.get(i).get(a.getIndex()).equals(data.get(j).get(a.getIndex())))
                return false;

        return true;
    }

    Object computePrototype(Set < Integer > idList, Attribute attribute) {

        if (attribute instanceof DiscreteAttribute)
            return computePrototype(idList, (DiscreteAttribute) attribute);
        else
            return computePrototype(idList, (ContinuousAttribute) attribute);
    }

    String computePrototype(Set < Integer > idList, DiscreteAttribute attribute) {

        Iterator < String > i = attribute.iterator();
        String prototype = i.next();
        int max = attribute.frequency(this, idList, prototype);
        int tmp;

        String temp;
        while (i.hasNext()) {
            temp = i.next();
            tmp = attribute.frequency(this, idList, temp);
            if (tmp > max) {
                max = tmp;
                prototype = temp;
            }
        }

        return prototype;
    }

    double computePrototype(Set < Integer > idList, ContinuousAttribute attribute) {
        double sum = 0;
        for (int i: idList)
            sum += (Double) data.get(i).get(attribute.getIndex());
        return sum / idList.size();
    }
}