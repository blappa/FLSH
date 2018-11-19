package com.univ.maroua.flsh.beans.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UserUtil {

    public static String getUsername() {
        try {
            return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        } catch (Exception e) {
            return null;
        }
    }
}







