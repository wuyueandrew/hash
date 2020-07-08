package indi.wuyue.hash;

import indi.wuyue.hash.cache.Cacheable;
import indi.wuyue.hash.cache.ConsistentHashCacheProxy;
import indi.wuyue.hash.cache.SimpleCache;
import indi.wuyue.hash.hashcircle.HashCircle;
import indi.wuyue.hash.hashcircle.VirtualNodesHashCircle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {

    private final static int NODES_COUNT = 10;

    private final static int DATA_COUNT = 1000000;

    public void testonsistentHash(int virtualNodeCount, boolean displayDetail) {
        String fmt = "server-%d";
        HashCircle hashCircle = new VirtualNodesHashCircle(virtualNodeCount);
        Map<String, Cacheable> cacheNodesMap = new HashMap<>();
        for (int i = 0; i < NODES_COUNT; i++) {
            cacheNodesMap.put(String.format(fmt, i), new SimpleCache());
        }
        ConsistentHashCacheProxy proxy = new ConsistentHashCacheProxy(cacheNodesMap, hashCircle);
        for (int i = 0; i < DATA_COUNT; i++) {
            String data = String.valueOf(i);
            proxy.set(data, data);
        }
        System.out.print(String.format("虚拟节点: %d, ", virtualNodeCount));
        proxy.stat(displayDetail);
    }

    public static void main(String[] args) {
        Test test = new Test();
        Arrays.asList(10, 50, 100, 200, 500, 1000, 5000, 10000)
                .forEach(vNodeCnt -> test.testonsistentHash(vNodeCnt, false));
    }
}
