package org.lftechnology.outlier.instantreloader.data;

import java.util.HashMap;
import java.util.Map;

public class FieldsHolder {

    private Map<String, Object> fields = new HashMap<String, Object>();

    public Object getField(String fieldKey) {
        return fields.get(fieldKey);
    }

    public void addField(String fieldKey, Object value) {
        fields.put(fieldKey, value);
    }
}
