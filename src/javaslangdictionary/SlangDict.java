package javaslangdictionary;

import java.util.*;

public class SlangDict {
    private HashMap<String, List<String>> dict;
    
    public SlangDict() {
        dict = new HashMap<>();
    }
    
    public Slang toSlang(Map.Entry<String, List<String>> entry) {
        return new Slang(entry.getKey(), entry.getValue());
    }
    
    public Slang findSlang(String slang) {
        if (dict.get(slang) == null)
            return null;
        return new Slang(slang, dict.get(slang));
    }
    
    public void addSlang(Slang slang) {
        dict.put(slang.slang, slang.meanings);
    }
    
    public void updateSlang(Slang slang) {
        dict.put(slang.slang, slang.meanings);
    }

    public void deleteSlang(String slang) {
        dict.remove(slang);
    }
    
}
