package org.penny_craal.icosamapper.map;

import java.util.Arrays;

/**
 *
 * @author Ville Jokela
 */
public class AccessPath {
    private byte[] path;
    
    public AccessPath(byte[] path) {
        this.path = path;
    }
    
    public byte head() {
        return path[0];
    }
    
    public int length() {
        return path.length;
    }
    
    public AccessPath rest() {
        return new AccessPath(Arrays.copyOfRange(path, 1, path.length));
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("{ ");
        for (byte b: path) {
            sb.append(b);
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }");
        
        return sb.toString();
    }
}