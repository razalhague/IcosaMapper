package org.penny_craal.icosamapper.map;

/**
 *
 * @author Ville Jokela
 */
public interface TriangleContainer {
    public byte access(AccessPath ap) throws BadPathException;
    public void subdivide(AccessPath ap) throws BadPathException;
    public void setAtPath(AccessPath ap, byte val) throws BadPathException;
    public byte getMeanValue();
}