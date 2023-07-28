package com.navigatedb.ws.shared.dto;

import java.io.Serializable;

public class RelationDto implements Serializable {

    public static final long serialVersionUID = -9028953315225669327L;
    private long id;
    private String relationId;
    private String relationType;
    private String nullable;

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
}
