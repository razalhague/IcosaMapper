package org.penny_craal.icosamapper;

import org.penny_craal.icosamapper.map.*;
import org.penny_craal.icosamapper.map.layerrenderers.Greyscale;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.testng.Assert.*;

/**
 * @author Ville Jokela
 */
public class JavaMapSerializerTest {
    private Map map = null;
    JavaMapSerializer serializer = null;

    @BeforeMethod
    public void setUp() throws InvalidPathException {
        byte[] ap = {1};
        map = new Map();
        map.addLayer(new Layer("test-layer", new Greyscale(), (byte) 0));
        map.divide("test-layer", new Path(ap));
        serializer = new JavaMapSerializer();
    }

    @AfterMethod
    public void tearDown() {
        map = null;
        serializer = null;
    }

    @Test
    public void testSerialize() throws Exception {
        MapDAO mapDAO = new MemoryMapDAO();
        mapDAO.save(map, serializer);
    }

    @Test
    public void testDeserialize() throws Exception {
        MapDAO mapDAO = new MemoryMapDAO();
        mapDAO.save(map, serializer);
        Map newMap = mapDAO.load(serializer);
        assertEquals(map, newMap);
    }

    private class MemoryMapDAO implements MapDAO {
        private byte[] serializedData = null;

        @Override
        public void save(Map map, MapSerializer serializer) throws DAException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            serializer.serialize(map, baos);
            serializedData = baos.toByteArray();
        }

        @Override
        public Map load(MapSerializer serializer) throws DAException {
            assertNotNull(serializedData);
            ByteArrayInputStream bais = new ByteArrayInputStream(serializedData);
            return serializer.deserialize(bais);
        }
    }
}