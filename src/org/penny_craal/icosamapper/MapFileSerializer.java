package org.penny_craal.icosamapper;

import java.io.File;
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
public class MapFileSerializer implements MapDAO {
    private File file;
    
    public MapFileSerializer(File file) {
        this.file = file;
    }

    @Override
    public void save(Map map) throws DAException {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
        } catch (IOException ex) {
            throw new DAException(ex);
        }
    }

    @Override
    public Map load() throws DAException {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Map) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new DAException(ex);
        }
    }
}