package com.navigatedb.ws.io.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name="entities")
public class EntityEntity implements Serializable {
    public static final long serialVersionUID = 8749986052501608713L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String entityId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private long rowCount;

    @ManyToOne
    @JoinColumn(name = "erds_id")
    private ErdEntity erdDetails;

    @OneToMany(mappedBy = "entityDetails", cascade = CascadeType.ALL)
    List<TupleEntity> tuples;

    @OneToMany(mappedBy = "entityDetails", cascade = CascadeType.ALL)
    List<EntityRelationEntity> entityRelations;

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

    public ErdEntity getErdDetails() {
        return erdDetails;
    }

    public void setErdDetails(ErdEntity erdDetails) {
        this.erdDetails = erdDetails;
    }

    public List<TupleEntity> getTuples() {
        return tuples;
    }

    public void setTuples(List<TupleEntity> tuples) {
        this.tuples = tuples;
    }

    public List<EntityRelationEntity> getEntityRelations() {
        return entityRelations;
    }

    public void setEntityRelations(List<EntityRelationEntity> entityRelations) {
        this.entityRelations = entityRelations;
    }
}
