package com.navigatedb.ws.ui.model.request;

import java.util.List;

public class RelationDetailsRequestModel {
    private String relationType;
    private String nullable;
    private List<EntityRelationDetailsRequestModel> entityRelations;

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

    public List<EntityRelationDetailsRequestModel> getEntityRelations() {
        return entityRelations;
    }

    public void setEntityRelations(List<EntityRelationDetailsRequestModel> entityRelations) {
        this.entityRelations = entityRelations;
    }
}
