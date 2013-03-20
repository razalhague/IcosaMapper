package org.pennycraal.icosamapper.map;

/**
 *
 * @author Ville Jokela & James Pearce
 */
public interface Triangle {
    public byte access(AccessPath ap);
    public void subdivide(AccessPath ap);
    public void setAtPath(AccessPath ap, byte val);
    public byte getMeanValue();
}