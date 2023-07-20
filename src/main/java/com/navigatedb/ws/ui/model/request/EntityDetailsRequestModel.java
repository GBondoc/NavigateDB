package com.navigatedb.ws.ui.model.request;

import java.util.List;

public class EntityDetailsRequestModel {

    private String name;
    private long rowCount;
    private List<TupleDetailsRequestModel> tuples;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    public List<TupleDetailsRequestModel> getTuples() {
        return tuples;
    }

    public void setTuples(List<TupleDetailsRequestModel> tuples) {
        this.tuples = tuples;
    }
}
