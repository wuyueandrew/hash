package indi.wuyue.hash.hashcircle;

import java.util.Set;

public abstract class HashCircle {

    private Set<String> nodes;

    public Set<String> getNodes() {
        return nodes;
    }

    public void setNodes(Set<String> nodes) {
        this.nodes = nodes;
        locateNodes();
    }

    abstract void locateNodes();

    public abstract String locateKeys(String key);

}
