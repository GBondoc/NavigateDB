package com.navigatedb.ws.io.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name="tuples")
public class TupleEntity implements Serializable {
    public static final long serialVersionUID = 9142703627790462199L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String tupleId;

    @Column(nullable = false)
    private String constraintType;

    @Column(nullable = false)
    private String columnName;

    @Column(nullable = false)
    private String dataType;

    @ManyToOne
    @JoinColumn(name = "entities_id")
    private EntityEntity entityDetails;

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

    public EntityEntity getEntityDetails() {
        return entityDetails;
    }

    public void setEntityDetails(EntityEntity entityDetails) {
        this.entityDetails = entityDetails;
    }
}
