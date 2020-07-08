package indi.wuyue.hash.cache;


import java.util.HashMap;
import java.util.Map;

public class SimpleCache implements Cacheable {

    private Map<String, String> cacheMap;

    public SimpleCache() {
        this.cacheMap = new HashMap<>();
    }

    @Override
    public void set(String key, String value) {
        cacheMap.put(key, value);
    }

    @Override
    public String get(String key) {
        return cacheMap.get(key);
    }

    @Override
    public int size() {
        return cacheMap.size();
    }

}
