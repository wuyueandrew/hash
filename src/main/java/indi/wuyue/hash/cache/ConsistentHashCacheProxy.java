package indi.wuyue.hash.cache;

import indi.wuyue.hash.hashcircle.HashCircle;

import java.util.HashMap;
import java.util.Map;

public class ConsistentHashCacheProxy implements Cacheable {

    private Map<String, Cacheable> cacheNodesMap;

    private HashCircle hashCircle;

    public ConsistentHashCacheProxy(Map<String, Cacheable> cacheNodesMap, HashCircle hashCircle) {
        this.hashCircle = hashCircle;
        this.cacheNodesMap = cacheNodesMap;
        hashCircle.setNodes(cacheNodesMap.keySet());
    }

    @Override
    public void set(String key, String value) {
        getCacheByKey(key).set(key, value);
    }

    @Override
    public String get(String key) {
        return getCacheByKey(key).get(key);
    }

    @Override
    public int size() {
        return cacheNodesMap.values().stream().mapToInt(Cacheable::size).sum();
    }

    private Cacheable getCacheByKey(String key) {
        String node = hashCircle.locateKeys(key);
        if (node == null) {
            throw new RuntimeException("缓存节点为空");
        }
        Cacheable cache = cacheNodesMap.get(node);
        if (cache == null) {
            throw new RuntimeException("节点映射缓存为空");
        }
        return cache;
    }

    public void stat(boolean displayDetail) {
        Map<String, Integer> cacheCount = new HashMap<>();
        cacheNodesMap.forEach((node, cache) -> cacheCount.put(node, cache.size()));
        int sum = size();
        int nodeCount = cacheNodesMap.size();
        int avg = sum / nodeCount;
        double standardDeviation = Math.sqrt(cacheCount.values()
                .stream()
                .mapToDouble(cnt -> Math.pow((double)(avg - cnt), 2D)).sum() / nodeCount);

        if (displayDetail) {
            System.out.println(String.format("缓存节点数：%d，缓存总数量：%d", cacheCount.size(), size()));
            cacheCount.forEach((node, count) -> System.out.println(String.format("缓存节点：%s，总数量：%d", node, count)));
        }
        System.out.println(String.format("缓存节点数：%d，缓存总数量：%d，标准差: %f", cacheCount.size(), size(), standardDeviation));
    }

}
