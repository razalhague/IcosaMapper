package org.penny_craal.icosamapper;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import org.penny_craal.icosamapper.map.Path;
import org.penny_craal.icosamapper.map.InvalidPathException;
import org.penny_craal.icosamapper.map.GreyscaleLR;
import org.penny_craal.icosamapper.map.Layer;
import org.penny_craal.icosamapper.map.Map;

/**
 *
 * @author Ville Jokela
 */
public class MapFileSerializerTest {
    private Map map;
    
    public MapFileSerializerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws InvalidPathException {
        byte[] ap = {1};
        map = new Map();
        map.addLayer(new Layer("test-layer", new GreyscaleLR(), (byte) 0));
        map.divide("test-layer", new Path(ap));
    }
    
    @After
    public void tearDown() {
        map = null;
    }

    /**
     * Test of save method, of class MapSerializer.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        File f = File.createTempFile("MapSerializerTest.save", ".imm");
        f.deleteOnExit();
        MapFileSerializer ms = new MapFileSerializer(f);
        ms.save(map);
    }

    /**
     * Test of load method, of class MapSerializer.
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("load");
        File f = File.createTempFile("MapSerializerTest.load", ".imm");
        f.deleteOnExit();
        MapFileSerializer ms = new MapFileSerializer(f);
        ms.save(map);
        Map map2 = ms.load();
        assertEquals(map, map2);
    }
}