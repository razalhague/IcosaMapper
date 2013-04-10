package org.penny_craal.icosamapper.map;

/**
 *
 * @author Ville Jokela
 */
abstract public class ArrayTriangleContainer implements TriangleContainer {
    private byte[] vals;        // values
    private TriangleContainer[] tris;    // triangles
    private final int size;
    private final String name;
    
    public ArrayTriangleContainer(int size, String name, byte init) {
        this.size = size;
        this.name = name;
        
        vals = new byte[size];
        tris = null;
        
        for (int i = 0; i < vals.length; i++) {
            vals[i] = init;
        }
    }

    @Override
    public byte access(AccessPath ap) throws BadPathException {
        if (ap.length() == 1) {
            return vals[ap.head()];
        } else if (tris == null) {
            throw new BadPathException(ap);
        } else {
            return tris[ap.head()].access(ap.rest());
        }
    }
    
    @Override
    public void subdivide(AccessPath ap) throws BadPathException {
        if (ap.length() == 1) {
            if (tris == null) {
                tris = new TriangleContainer[size];
                for (int i = 0; i < tris.length; i++) {
                    tris[i] = null;
                }
                tris[ap.head()] = new ArrayTriangle(vals[ap.head()]);
            } else if (tris[ap.head()] != null) {
                for (int i = 0; i < tris.length; i++) {
                    byte[] path = { (byte)i };
                    tris[ap.head()].subdivide(new AccessPath(path));
                }
            } else {
                tris[ap.head()] = new ArrayTriangle(vals[ap.head()]);
            }
        } else if (tris != null){
            tris[ap.head()].subdivide(ap.rest());
        } else {
            throw new BadPathException(ap);
        }
    }

    @Override
    public void setAtPath(AccessPath ap, byte val) throws BadPathException {
        if (ap.length() == 1) {
            vals[ap.head()] = val;
        } else if (tris != null){
            tris[ap.head()].setAtPath(ap.rest(), val);
            vals[ap.head()] = tris[ap.head()].getMeanValue();
        } else {
            throw new BadPathException(ap);
        }
    }
    
    @Override
    public byte getMeanValue() {
        int n = 0;
        
        for (byte v: vals) {
            n += v;
        }
        
        return (byte) (n/size);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(name);
        sb.append(": { ");
        for (int i = 0; i < vals.length; i++) {
            sb.append(vals[i]);
            if (tris != null) {
                sb.append(": ");
                sb.append(tris[i].toString());
            }
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }}");
        return sb.toString();
    }
}