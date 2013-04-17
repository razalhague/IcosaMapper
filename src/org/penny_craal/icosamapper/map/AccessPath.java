package org.penny_craal.icosamapper.map;

import java.util.Arrays;

/**
 *
 * @author Ville Jokela
 */
public class AccessPath {
    private byte [] path;
    private int index;
    
    public AccessPath(byte[] path) {
        this.path = path;
        index = 0;
    }
    
    private AccessPath(byte[] path, int index) {
        this.path = path;
        this.index = index;
    }
    
    public byte head() {
        return path[index];
    }
    
    public int length() {
        return path.length - index;
    }
    
    /**
     * 
     * @return an AccessPath with the first element of the path removed. 
     */
    public AccessPath rest() {
        return new AccessPath(path, index + 1);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        
        for (int i = 0; i < path.length; i++) {
            if (i == index) {
                sb.append("CUR: ");
            }
            sb.append(path[i]);
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }");
        
        return sb.toString();
    }
}