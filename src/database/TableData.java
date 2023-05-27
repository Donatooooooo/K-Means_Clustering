package database;

import java.sql.ResultSet;
import java.util.HashSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import database.TableSchema.Column;

public class TableData {

    private DbAccess db;

    public TableData(DbAccess db) {
        this.db = db;
    }

    public List < Example > getDistinctTransazioni(String table)
    		throws SQLException, EmptySetException {
    	
    	List < Example > list = new ArrayList < > ();
        TableSchema ts = new TableSchema(db, table);
        Statement s = db.getConnection().createStatement();
        ResultSet rs = s.executeQuery("SELECT DISTINCT * " 
        											+ "FROM " + table + ";");
        
        while (rs.next()) {
            Example ex = new Example();
            for (int i = 0; i < ts.getNumberOfAttributes(); i++) {
                if (ts.getColumn(i).isNumber()) 
                	ex.add(rs.getDouble(ts.getColumn(i).getColumnName()));
                else 
                	ex.add(rs.getString(ts.getColumn(i).getColumnName()));
            }
            list.add(ex);
        }
        
        s.close();
        rs.close();
        
        if (list.isEmpty()) 
        	throw new EmptySetException("--- Tabella " + table + " vuota ---");

        return list;
    }

    public Set < Object > getDistinctColumnValues(String table, Column column) throws SQLException {
    	
    	HashSet < Object > set = new HashSet < > ();
        Statement s = db.getConnection().createStatement();
        ResultSet rs = s.executeQuery("SELECT DISTINCT " + column.getColumnName() + " " 
        											+ "FROM " + table + " " 
        											+ "ORDER BY " + column.getColumnName() + ";");
        
        while (rs.next()) {
            if (column.isNumber()) 
            	set.add(rs.getDouble(column.getColumnName()));
            else 
            	set.add(rs.getString(column.getColumnName()));
        }
        
        s.close();
        rs.close();
        
        return set;

    }

    public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) 
    		throws SQLException, NoValueException {
    	
        Object ret;
        Statement s = db.getConnection().createStatement();
        ResultSet rs = s.executeQuery("SELECT " + aggregate + "(" + column.getColumnName() + ") AS aggregata " 
        											+ "FROM " + table + ";");
        
        try {
            if (rs.next()) {
                if (column.isNumber()) 
                	ret = rs.getDouble("aggregata");
                else 
                	ret = rs.getString("aggregata");
            } else {
            	throw new NoValueException("--- Nessun valore per la colonna " + column.getColumnName() + " ---");
            }
        } finally {
            s.close();
            rs.close();
        }
        return ret;
    }
}
