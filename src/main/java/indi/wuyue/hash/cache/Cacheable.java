package indi.wuyue.hash.cache;


public interface Cacheable {

    void set(String key, String value);

    String get(String key);

    int size();

}
