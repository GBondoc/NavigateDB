package com.navigatedb.ws.ui.model.response;

public class TupleRest {
    private String tupleId;
    private String constraintType;
    private String columnName;
    private String dataType;

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
