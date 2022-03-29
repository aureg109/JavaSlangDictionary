package javaslangdictionary;

import java.io.*;
import java.util.*;

public class Main {
    public static String RAW_DATA_FILE = "slang.txt";
    public static String ORIGINAL_DATA_FILE = "orginal_data.txt";
    public static String CURRENT_DATA_FILE = "current_data.txt";
    public static String HISTORY_FILE = "history.txt";
    
    public static boolean checkFile(String fileName) {
        return (new File(fileName)).isFile();
    }
    
    public static void importSlang(String sourceFile, String destinationFile) throws IOException {
        SlangDict D = new SlangDict();
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        reader.readLine();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            D.addWord(new SlangWord(line));
            //System.out.println(line);
        }
        reader.close();
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile));
        writer.write(D.toString());
        writer.close();
        //System.out.println(D.toString());
    }
    
    public static SlangDict loadSlang(String fileName) throws IOException {
        SlangDict res = new SlangDict();
        
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            String[] data = line.split("\\: ");
            String a = data[1];
            
            line = reader.readLine();
            data = line.split("\\: ");
            List<String> b = Arrays.asList(data[1].split("\\| "));
            
            res.addWord(new SlangWord(a, b));
            //System.out.println(a + "\n" + b + "\n");
        }
        reader.close();
        
        return res;
    }
    
    public static void saveSlang(String fileName, SlangDict D) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(D.toString());
        writer.close();
    }
    
    public static void addHistory(SlangWord word) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(HISTORY_FILE, true));

        out.writeUTF(word.word);
        out.writeInt(word.definitions.size());
        for (String def : word.definitions)
                out.writeUTF(def);

        out.close();
    }
    
    public static void showHistory() throws IOException {
        DataInputStream in;
        try {
            in = new DataInputStream(new FileInputStream(HISTORY_FILE));
            List<SlangWord> historyList = new ArrayList<>();

            while (true) {
                    try {
                        SlangWord word = new SlangWord();
                        word.word = in.readUTF();

                        int nDef = in.readInt();
                        word.definitions = new ArrayList<>();
                        for (int i = 0; i < nDef; ++i)
                            word.definitions.add(in.readUTF());

                        historyList.add(word);

                    } catch (EOFException eof) {
                        break;
                    }
            }

            System.out.println("\nSearch history:");
            for (SlangWord sw : historyList) {
                System.out.println(sw);
            }
            in.close();

        } catch (FileNotFoundException e) {
            System.out.println("History is empty!");
        }
    }
    
    public static void main(String[] args) throws IOException {
        SlangDict D = new SlangDict();
        
        if (!checkFile(ORIGINAL_DATA_FILE) || !checkFile(CURRENT_DATA_FILE)) {
            importSlang(RAW_DATA_FILE, ORIGINAL_DATA_FILE);
            importSlang(RAW_DATA_FILE, CURRENT_DATA_FILE);
            System.out.println("Data imported successfully!");
        }
        D = loadSlang(CURRENT_DATA_FILE);
        
        Scanner sc = new Scanner(System.in);
        
        int choice = -1;
        while (choice != 0) {
            /*try {
                
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input, please try again\n");
                sc.nextLine();
            }*/
            System.out.println("Welcome to Slang Dictionary! - Created by 20120076 - Mai Vinh Hien");
            System.out.println("1. Search for a slang word");
            System.out.println("2. Search for slang words by definition");
            System.out.println("3. Show search history");
            System.out.println("4. Add a slang word");
            System.out.println("5. Edit a slang word");
            System.out.println("6. Delete a slang word");
            System.out.println("7. Reset back to the original list (slang.txt)");
            System.out.println("8. On this day slang word");
            System.out.println("9. Game: Guess the definition from a slang");
            System.out.println("10. Game: Guess the slang from a definition");
            System.out.println("0. Exit");
            
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 0:
                    break;

                case 1: {
                    System.out.println("Enter slang word: ");

                    SlangWord res = D.findWord(sc.next());
                    sc.nextLine();
                    if (res != null) {
                        System.out.println("Slang word found!\n" + res.toString());
                        addHistory(res);
                    } else {
                        System.out.println("Slang word is not found!");
                    }

                    break;
                }

                case 2: {
                    System.out.println("Enter keyword: ");

                    ArrayList<SlangWord> res = D.findByKeyword(sc.next());
                    sc.nextLine();
                    if (!res.isEmpty()) {
                        System.out.println("Slang words found:");
                        for (SlangWord word : res) {
                            System.out.println(word.toString());
                            addHistory(word);
                        }
                    } else {
                        System.out.println("No slang word found with that definition!");
                    }

                    break;
                }

                case 3: {
                    showHistory();
                    break;
                }

                case 4: {
                    System.out.println("Enter new slang word (format: word`definition1| definition2| ...):");
                    SlangWord added = new SlangWord(sc.nextLine());
                    if (D.findWord(added.getWord()) != null) {
                        System.out.println("Slang word is already in database, try *Edit a slang word* option instead.");
                    } else {
                        D.addWord(added);
                        System.out.println("Add slang word successful!\n");
                    }
                    
                    saveSlang(CURRENT_DATA_FILE, D);
                    
                    break;
                }

                case 5: {
                    System.out.println("Enter slang word to edit (format: word`definition1| definition2| ...):");
                    SlangWord sw = new SlangWord(sc.nextLine());
                    if (D.findWord(sw.getWord()) == null) {
                        System.out.println("Slang word is not found, try *Add a slang word* option instead.");
                    } else {
                        D.updateWord(sw);
                        System.out.println("Edit slang word successful!\n");
                    }
                    
                    saveSlang(CURRENT_DATA_FILE, D);
                    
                    break;
                }

                case 6: {
                    System.out.println("Enter slang word:");
                    String word = sc.next();
                    sc.nextLine();
                    if (D.findWord(word) == null) {
                        System.out.println("Slang word is not found, nothing to delete.");
                    } else {
                        D.deleteWord(word);
                        System.out.println("Delete slang word successful!\n");
                    }
                    
                    saveSlang(CURRENT_DATA_FILE, D);
                    
                    break;
                }

                case 7: {
                    importSlang(RAW_DATA_FILE, CURRENT_DATA_FILE);
                    D = loadSlang(CURRENT_DATA_FILE);
                    System.out.println("Data reset complete!\n");
                    break;
                }

                case 8: {
                    System.out.println("On this day slang word:");
                    SlangWord res = D.getRandomWord();
                    System.out.println(res.toString());
                    break;
                }

                case 9: {
                    sc.nextLine();
                    System.out.println("Which is the right definitions for the following slang word?");
                    SlangWord sw = D.getRandomWord();
                    System.out.println("Slang word: " + sw.getWord());
                    
                    int i = new Random().nextInt(sw.getDefinitions().size());
                    int pos = new Random().nextInt(4);
                    String correctAns = sw.getDefinitions().get(i);
                    String[] option = new String[4];
                    for (int j = 0; j < 4; ++j) {
                        SlangWord r = D.getRandomWord();
                        if (j == pos) option[j] = correctAns;
                        else {
                            i = new Random().nextInt(sw.getDefinitions().size());
                            option[j] = r.getDefinitions().get(i);
                        }
                        System.out.println(Integer.toString(j + 1) + ". " + option[j]);
                    }
                    
                    System.out.println("Enter your answer:");
                    int c = sc.nextInt();
                    if (option[c - 1] == correctAns) System.out.println("You are correct! Congrats!");
                    else System.out.println("Sorry, wrong answer!");
                    
                    sc.nextLine();
                    break;
                }

                case 10: {
                    sc.nextLine();
                    System.out.println("Which is the right slang word for the following definitions?");
                    SlangWord sw = D.getRandomWord();
                    int i = new Random().nextInt(sw.getDefinitions().size());
                    System.out.println("Definition: " + sw.getDefinitions().get(i));
                    
                    int pos = new Random().nextInt(4);
                    String correctAns = sw.getWord();
                    String[] option = new String[4];
                    for (int j = 0; j < 4; ++j) {
                        SlangWord r = D.getRandomWord();
                        if (j == pos) option[j] = correctAns;
                        else option[j] = r.getWord();
                        System.out.println(Integer.toString(j + 1) + ". " + option[j]);
                    }
                    
                    System.out.println("Enter your answer:");
                    int c = sc.nextInt();
                    if (option[c - 1] == correctAns) System.out.println("You are correct! Congrats!");
                    else System.out.println("Sorry, wrong answer!");
                    
                    sc.nextLine();
                    break;
                }

                default:
                    System.out.println("Your choice is N/A, please try again.");
                    break;
            }
            System.out.println("Press Enter to continue...");
            sc.nextLine();
        }
        sc.close();
    }
}
