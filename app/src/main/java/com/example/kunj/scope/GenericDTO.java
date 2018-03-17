package com.example.kunj.scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kunj on 2/9/2018.
 */

public class GenericDTO {
    private Map<String, List<Object>> resultSetMap = new HashMap<String, List<Object>>() ;

    public void addAttribute(String attributeName, Object attributeValue) {
        if(resultSetMap.containsKey(attributeName)) {
            resultSetMap.get(attributeName).add(attributeValue);
        } else {
            List<Object> list = new ArrayList<Object>();
            list.add(attributeValue);
            resultSetMap.put(attributeName, list);
        }
    }

    public Object getAttributeValue(String key) {
        return (resultSetMap.get(key) == null) ? null : resultSetMap.get(key).get(0);
    }

    public List<Object> getAttributeValues(String key) {
        return resultSetMap.get(key);
    }

}
