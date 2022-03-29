package javaslangdictionary;

import java.util.*;

public class SlangDict {
    private final TreeMap<String, List<String>> dict;
    
    public SlangDict() {
        dict = new TreeMap<>();
    }
    
    public Set<Map.Entry<String, List<String>>> getEntrySet() {
        return dict.entrySet();
    }
    
    public SlangWord toSlangWord(Map.Entry<String, List<String>> entry) {
        return new SlangWord(entry.getKey(), entry.getValue());
    }
    
    @Override
    public String toString() {
        String res = "";
        
        ArrayList<SlangWord> list = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : dict.entrySet()) {
            list.add(toSlangWord(entry));
        }
        
        for (SlangWord word : list)
            res += word.toString() + "\n";
        
        return res;
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
