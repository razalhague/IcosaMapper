package org.penny_craal.icosamapper;

import org.penny_craal.icosamapper.map.Map;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Ville Jokela
 */
public interface MapSerializer {
    /**
     * Serializes a Map into an OutputStream
     * @param map Map to serialize
     * @param dst destination stream
     */
    void serialize(Map map, OutputStream dst) throws DAException;

    /**
     * Deserializes a Map from an InputStream
     * @param src stream to deserialize from
     * @return
     */
    Map deserialize(InputStream src) throws DAException;
}
