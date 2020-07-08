package indi.wuyue.hash.hashcircle;

import indi.wuyue.hash.hashcircle.HashCircle;

import java.util.*;

public class NormalHashCircle extends HashCircle {

    private long interval = (1L<<32) - 1;

    private TreeMap<Long, String> nodesMap;

    @Override
    void locateNodes() {
        nodesMap = new TreeMap<>(Comparator.naturalOrder());
        Set<String> nodes = getNodes();
        nodes.forEach(node -> {
            long nodeHash = node.hashCode();
            long nodeLoc = nodeHash % interval;
            nodesMap.put(nodeLoc, node);
        });
    }

    @Override
    public String locateKeys(String key) {
        long keyHash = key.hashCode();
        long keyLoc = keyHash % interval;
        String node = nodesMap.ceilingEntry(keyLoc).getValue();
        if (node == null) {
            node = nodesMap.get(nodesMap.firstKey());
        }
        return node;
    }

}
