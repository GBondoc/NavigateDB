package com.navigatedb.ws.ui.model.response;

import java.util.List;

public class EntityRest {

    private String entityId;
    private String name;
    private long rowCount;
    private List<TupleRest> tuples;
    private List<EntityRelationRest> entityRelations;

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

    public List<TupleRest> getTuples() {
        return tuples;
    }

    public void setTuples(List<TupleRest> tuples) {
        this.tuples = tuples;
    }

    public List<EntityRelationRest> getEntityRelations() {
        return entityRelations;
    }

    public void setEntityRelations(List<EntityRelationRest> entityRelations) {
        this.entityRelations = entityRelations;
    }
}
