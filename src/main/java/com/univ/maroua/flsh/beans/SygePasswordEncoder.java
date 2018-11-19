package com.univ.maroua.flsh.beans;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author mekomou
 */
public class SygePasswordEncoder {
    
    public String encode(String rawPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(rawPassword);
    }
    
    public boolean matches(String rawPassword, String encodePass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(rawPassword, encodePass);
    }
}
