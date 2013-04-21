/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.penny_craal.icosamapper;

import org.penny_craal.icosamapper.map.Map;

/**
 *
 * @author Ville Jokela
 */
public interface MapDAO {
    public void save(Map map) throws DAException;
    public Map load() throws DAException;
}