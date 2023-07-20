package com.navigatedb.ws.shared.dto;

import java.io.Serializable;

public class TupleDto implements Serializable {

    public static final long serialVersionUID = 3339413346166786206L;
    private long id;
    private String tupleId;
    private String constraintType;
    private String columnName;
    private String dataType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTupleId() {
        return tupleId;
    }

    public void setTupleId(String tupleId) {
        this.tupleId = tupleId;
    }

    public String getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
