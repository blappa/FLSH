/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.projection;

/**
 *
 * @author william mekomou
 * <williammekomou@yahoo.com>
 */
public class DelStat {

    public DelStat() {
        session = 0;
        rat = 0;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public int getRat() {
        return rat;
    }

    public void setRat(int rat) {
        this.rat = rat;
    }
    private int session;
    private int rat;
}
