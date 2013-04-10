package org.penny_craal.icosamapper.map;

/**
 *
 * @author Ville Jokela
 */
public class ArrayTriangle implements Triangle {
    private byte[] vals;        // values
    private Triangle[] tris;    // triangles
    
    public ArrayTriangle(byte init) {
        vals = new byte[9];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = init;
        }
        tris = null;
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
                tris = new Triangle[9];
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
        
        return (byte) (n/9);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{AT: { ");
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