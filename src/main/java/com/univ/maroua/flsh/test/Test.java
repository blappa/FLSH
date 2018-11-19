/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.test;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author koossery
 */
public class Test {

    public static void main(String[] args) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        System.out.println("cal1 " + cal1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal2.add(Calendar.MONTH, -2);
        System.out.println("cal2 " + cal2);

    }
}
