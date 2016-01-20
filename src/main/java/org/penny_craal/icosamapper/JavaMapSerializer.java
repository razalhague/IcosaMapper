package org.penny_craal.icosamapper;

import org.penny_craal.icosamapper.map.Map;

import java.io.*;

/**
 * Serializes using Java's own serialization mechanism.
 * @author Ville Jokela
 */
public class JavaMapSerializer implements MapSerializer {
    @Override
    public void serialize(Map map, OutputStream dst) throws DAException {
        try (ObjectOutputStream oos = new ObjectOutputStream(dst)) {
            oos.writeObject(map);
        } catch (IOException e) {
            throw new DAException("Serialization failed", e);
        }
    }

    @Override
    public Map deserialize(InputStream src) throws DAException {
        try (ObjectInputStream ois = new ObjectInputStream(src)) {
            return (Map) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DAException("Deserialization failed", e);
        }
    }
}
