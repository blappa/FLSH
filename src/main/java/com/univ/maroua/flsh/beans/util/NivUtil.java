package com.univ.maroua.flsh.beans.util;

import com.univ.maroua.flsh.entities.Niveau;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NivUtil {

    private static Map<String, Long> result;

    public static Map<String, Long> getLevel(List<Niveau> niveaux) {
        result = new TreeMap<>();
        //Collections.sort(niveaux, Niveau.niveauComparator);
        for (Niveau niveau : niveaux) {
            switch (niveau.getLevel()) {
                case 1:
                    result.put("Licence 1", niveau.getId());
                    break;
                case 2:
                    result.put("Licence 2", niveau.getId());
                    break;
                case 3:
                    result.put("Licence 3", niveau.getId());
                    break;
                case 4:
                    result.put("Master 1", niveau.getId());
                    break;
                case 5:
                    result.put("Master 2", niveau.getId());
                    break;
            }
        }
        return result;
    }
}