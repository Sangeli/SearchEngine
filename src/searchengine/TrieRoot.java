/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

/**
 *
 * @author steph_000
 */
public class TrieRoot extends TrieNode {
    public TrieRoot(){
        super(0);
        
    }
    
    public TrieChild UpdateTrie(String Word) {
        return super.UpdateTrie(Word.toLowerCase());
    }
    
    public Map<String, String[]> GetWordArrayByNextChar (String WordStart) {
        //always lower case
        TrieNode End = GetEndNode(WordStart.toLowerCase());
        return End.GetWordArrayByNextChar();
    }
    
    
    public void ReadFile(String FileName){
        Pattern word_pattern = Pattern.compile("(\\w+)");
        Matcher word_matcher;
        String word;
        try {
            FileReader file_reader = new FileReader(FileName);
            BufferedReader buffered_reader = new BufferedReader(file_reader);
            
            String line = null;
            while((line = buffered_reader.readLine()) != null) {
                word_matcher = word_pattern.matcher(line);
                while(word_matcher.find()) {
                    word = word_matcher.group(1).toLowerCase();
                    UpdateTrie(word);
                }
                
            }
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + FileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + FileName + "'");            
        }
    }
    
    
    
}
