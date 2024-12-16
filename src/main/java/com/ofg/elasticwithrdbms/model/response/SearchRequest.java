package com.ofg.elasticwithrdbms.model.response;

import java.util.List;

public class SearchRequest {
    private List<String> fieldName;
    private List<String> searchValue;

    public List<String> getFieldName() {
        return fieldName;
    }

    public void setFieldName(List<String> fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(List<String> searchValue) {
        this.searchValue = searchValue;
    }
}