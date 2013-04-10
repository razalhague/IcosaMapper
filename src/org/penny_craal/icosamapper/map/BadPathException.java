package org.penny_craal.icosamapper.map;

/**
 *
 * @author Ville Jokela
 */
public class BadPathException extends Exception {
    private AccessPath ap;
    
    public BadPathException(AccessPath ap) {
        super("The given path is invalid: " + ap.toString());
        this.ap = ap;
    }
    
    public AccessPath getAP() {
        return ap;
    }
}