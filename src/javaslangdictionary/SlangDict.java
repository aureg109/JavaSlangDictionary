package javaslangdictionary;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class SlangDict {
    private final HashMap<String, List<String>> dict;
    
    public SlangDict() {
        dict = new HashMap<>();
    }
    
    public Set<Map.Entry<String, List<String>>> getEntrySet() {
        return dict.entrySet();
    }
    public SlangWord toSlangWord(Map.Entry<String, List<String>> entry) {
        return new SlangWord(entry.getKey(), entry.getValue());
    }
    
    public SlangWord findWord(String slang) {
        if (dict.get(slang) == null)
            return null;
        return new SlangWord(slang, dict.get(slang));
    }
    public void addWord(SlangWord slang) {
        dict.put(slang.word, slang.definitions);
    }
    public void updateWord(SlangWord slang) {
        dict.put(slang.word, slang.definitions);
    }
    public void deleteWord(String slang) {
        dict.remove(slang);
    }
    
    public ArrayList<SlangWord> findByKeyword(String kw) {
        ArrayList<SlangWord> res = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : dict.entrySet()) {
            for (String def : entry.getValue()) {
                if (def.contains(kw)) {
                    res.add(toSlangWord(entry));
                    break;
                }
            }
        }
        return res;
    }
    
    public SlangWord getRandomWord() {
        SlangWord res = new SlangWord();
        int idx = new Random().nextInt(dict.entrySet().size());
        int i = 0;
        for (Map.Entry<String, List<String>> entry : dict.entrySet()) {
            if (i == idx) {
                res = toSlangWord(entry);
                break;
            }
            ++i;
        }
        return res;
    }
}
