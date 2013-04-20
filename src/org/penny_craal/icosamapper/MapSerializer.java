package org.penny_craal.icosamapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.penny_craal.icosamapper.map.Map;

/**
 *
 * @author Ville Jokela
 */
public class MapSerializer implements MapDAO {
    String path;
    
    public MapSerializer(String path) {
        this.path = path;
    }

    @Override
    public void save(Map map) throws DAException {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
        } catch (IOException ex) {
            throw new DAException(ex);
        }
    }

    @Override
    public Map load() throws DAException {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Map) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new DAException(ex);
        }
    }
}