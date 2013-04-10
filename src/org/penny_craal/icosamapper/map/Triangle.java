package org.penny_craal.icosamapper.map;

/**
 *
 * @author Ville Jokela & James Pearce
 */
public interface Triangle {
    public byte access(AccessPath ap) throws BadPathException;
    public void subdivide(AccessPath ap) throws BadPathException;
    public void setAtPath(AccessPath ap, byte val) throws BadPathException;
    public byte getMeanValue();
}