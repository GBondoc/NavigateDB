package com.navigatedb.ws.shared.dto;

import java.io.Serializable;
import java.util.List;

public class EntityDto implements Serializable {

    public static final long serialVersionUID = -4509749847612323418L;
    private long id;
    private String entityId;
    private String name;
    private long rowCount;
    private ErdDto erdDetails;
    private List<TupleDto> tuples;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

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

    public ErdDto getErdDetails() {
        return erdDetails;
    }

    public void setErdDetails(ErdDto erdDetails) {
        this.erdDetails = erdDetails;
    }

    public List<TupleDto> getTuples() {
        return tuples;
    }

    public void setTuples(List<TupleDto> tuples) {
        this.tuples = tuples;
    }
}
