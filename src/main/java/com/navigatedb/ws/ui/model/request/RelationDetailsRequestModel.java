package com.navigatedb.ws.ui.model.request;

public class RelationDetailsRequestModel {
    private String relationType;
    private String nullable;

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
