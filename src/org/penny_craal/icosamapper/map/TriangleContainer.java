package org.penny_craal.icosamapper.map;

/**
 *
 * @author Ville Jokela
 */
public interface TriangleContainer {
    public abstract boolean isValidPath(AccessPath ap);
    public abstract byte access(AccessPath ap) throws BadPathException;
    public abstract void subdivide(AccessPath ap) throws BadPathException;
    public abstract void setAtPath(AccessPath ap, byte val) throws BadPathException;
    public abstract byte getMeanValue();
}