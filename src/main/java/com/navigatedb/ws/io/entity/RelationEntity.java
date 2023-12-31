package com.navigatedb.ws.io.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name="relations")
public class RelationEntity implements Serializable {
    public static final long serialVersionUID = 2406632541409390343L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String relationId;

    @Column(nullable = false)
    private String relationType;

    @Column(nullable = false)
    private String nullable;

    @OneToMany(mappedBy = "relationDetails", cascade = CascadeType.ALL)
    List<EntityRelationEntity> entityRelations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public List<EntityRelationEntity> getEntityRelations() {
        return entityRelations;
    }

    public void setEntityRelations(List<EntityRelationEntity> entityRelations) {
        this.entityRelations = entityRelations;
    }
}
