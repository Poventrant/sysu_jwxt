package com.pwq.httpclient.ui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
* Created by 枫叶 on 2016/1/20.
*/
public class MyTableModel extends AbstractTableModel {

    private List<String[]> rows;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String columnNames[] = null;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int len = 3;

    public MyTableModel() {
        rows = new ArrayList<>(25);
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public String getColumnName(int column) {
        if(columnNames!=null) {
            return columnNames[column];
        }
        return null;
    }

    @Override
    public int getColumnCount() {
        return this.len;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = rows.get(rowIndex);
        return row[columnIndex];
    }

    public void addRow() {
        int rowCount = getRowCount();
        String[] row = new String[getColumnCount()];
        for (int index = 0; index < getColumnCount(); index++) {
            row[index] = rowCount + "x" + index;
        }
        rows.add(row);
        fireTableRowsInserted(rowCount, rowCount);
    }
    public void addRow(String[] objs) {
        int rowCount = getRowCount();
        rows.add(objs);
        fireTableRowsInserted(rowCount, rowCount);
    }

    public void insertRow(String[] objs, int pos) {
        rows.add(objs);
        fireTableRowsInserted(pos, pos);
    }

    public void clearTable() {
        int rowCount = getRowCount();
        rows.clear();
        fireTableRowsDeleted(0, rowCount);
    }
}
