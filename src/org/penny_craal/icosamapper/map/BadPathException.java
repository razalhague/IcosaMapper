/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.penny_craal.icosamapper.map;

/**
 *
 * @author Ville Jokela
 */
public class BadPathException extends Exception {
    AccessPath ap;
    public BadPathException(AccessPath ap) {
        super("The given path is invalid: " + ap.toString());
        this.ap = ap;
    }
}
