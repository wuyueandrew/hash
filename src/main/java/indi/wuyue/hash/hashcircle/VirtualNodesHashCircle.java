package indi.wuyue.hash.hashcircle;


import indi.wuyue.hash.util.HashUtils;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

public class VirtualNodesHashCircle extends HashCircle {

    private long interval = (1L<<32) - 1;

    private TreeMap<Long, VirtualNodeInfo> nodesMap;

    private final int virtualNodeCount;

    public VirtualNodesHashCircle(int virtualNodeCount) {
        this.virtualNodeCount = virtualNodeCount;
    }

    @Override
    void locateNodes() {
        nodesMap = new TreeMap<>(Comparator.naturalOrder());
        Set<String> nodes = getNodes();
        nodes.forEach(node -> {
            for (int i = 0; i < virtualNodeCount; i++) {
                VirtualNodeInfo virtualNodeInfo = new VirtualNodeInfo();
                virtualNodeInfo.node = node;
                virtualNodeInfo.virtualNo = i;
                long nodeHash = virtualNodeInfo.vNodeHashCode();
                long nodeLoc = nodeHash % interval;
                nodesMap.put(nodeLoc, virtualNodeInfo);
            }
        });
    }

    @Override
    public String locateKeys(String key) {
        long keyHash = HashUtils.md5HashingAlg(key);
        long keyLoc = keyHash % interval;
        Long nodeKey = nodesMap.ceilingKey(keyLoc);
        if (nodeKey == null) {
            nodeKey = nodesMap.firstKey();
        }
        return nodesMap.get(nodeKey).node;
    }

    class VirtualNodeInfo {

        int virtualNo;

        String node;

        long vNodeHashCode() {
            return HashUtils.md5HashingAlg(virtualNo + node);
        }
    }

}
