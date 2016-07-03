/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;
import java.io.*;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author scefali
 */
public class SearchEngine {

    public static void main(String[] args) {
        
        TrieRoot Root = new TrieRoot();
        String FileName = "wordsEn.txt";
        
        if ( Root.ReadFile(FileName) == false) {
            return;
        }
        
        Console c = System.console();
        if (c == null) {
            System.out.println("Must run from command line");
            return;
        }
        String WordStart = c.readLine("Enter your search sub string: ");
        
        String[] WordArray = Root.GetWordArray(WordStart);
        for (String FoundWord: WordArray) {
            System.out.println(FoundWord);
        }
        
    }
    
    
    
}
