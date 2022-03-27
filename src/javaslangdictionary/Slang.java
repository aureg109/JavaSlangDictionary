package javaslangdictionary;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Slang {
    public String slang;
    public List<String> meanings;
    
    public Slang() {
        meanings = new ArrayList<String>();
    }
    public Slang(String _slang, List<String> _meanings) {
        slang = _slang;
        meanings = _meanings;
    }
    public Slang(String line) {
        String[] slangData = line.split("`");
        slang = slangData[0];
        meanings = Arrays.asList(slangData[1].split("\\| "));
    }

    public String toString() {
        String res = "Slang: " + slang + ", Meanings: ";
        for (int i = 0; i < meanings.size(); i++)
            res += meanings.get(i) + (i < meanings.size() - 1 ? "| " : "");
        return res;
    }
}
