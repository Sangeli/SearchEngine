
import java.util.ArrayList;
import java.util.Map;
import searchengine.*;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author scefali
 */
public class Testing {
    
    
    
    @Test
    public void TestNull(){
        TrieRoot Root = new TrieRoot();
        assertEquals(Root.GetWordArray().length, 0);
    }
    
    @Test
    public void Test1(){
        String Word = "a";
        TrieRoot Root = new TrieRoot();
        TrieChild end = Root.UpdateTrie(Word);
        
        //check root and end for the length and content
        assertEquals(Root.GetWordArray().length, 1);
        assertEquals(end.GetWordArray().length, 1);
        
        assertEquals(Root.GetWordArray()[0], "a");
        assertEquals(end.GetWordArray()[0], "a");
        
    }
    
    @Test
    public void Test2Diff(){
        String Word = "a";
        TrieRoot Root = new TrieRoot();
        TrieChild end1 = Root.UpdateTrie(Word);
        
        Word = "b";
        TrieChild end2 = Root.UpdateTrie(Word);
        
        assertEquals(Root.GetWordArray().length, 2);
        assertEquals(end1.GetWordArray().length, 1);
        assertEquals(end2.GetWordArray().length, 1);
        
        assertEquals(Root.GetWordArray()[0], "a");
        assertEquals(end1.GetWordArray()[0], "a");
        
        assertEquals(Root.GetWordArray()[1], "b");
        assertEquals(end2.GetWordArray()[0], "b");
        
    }
    
    
    @Test
    public void TestInsertIntoCache(){
        TrieRoot Root = new TrieRoot();
        String Prefix = "prefix";
        String Word;
        int a_offset = 97;
        
        //fill to capacity
        for (int i = 0; i < TrieNode.MaxWords; i ++){
            Word = Prefix + Character.toChars(i+a_offset)[0];
            //do it twice
            Root.UpdateTrie(Word);
        }
        
        
        String next_char;
        //check map
        Map<String, String[]> WordArrayByChar = Root.GetWordArrayByNextChar(Prefix);
        for (int i = 0; i < TrieNode.MaxWords; i ++){
            next_char = String.valueOf(Character.toChars(i+a_offset)[0]);
            Word = Prefix + Character.toChars(i+a_offset)[0];
            assertEquals(Word, WordArrayByChar.get(next_char)[0]);
        }
        
        
        //Add a new word that shouldn't be seen from adding once
        Word = "prefixk";
        Root.UpdateTrie(Word);
        for(String FoundWord: Root.GetWordArray()) {
            assertFalse(FoundWord == Word);
        }
        
        //add it again to see if its in the front
        Root.UpdateTrie(Word);
        assertEquals(Word, Root.GetWordArray()[0]);
        
        
    }
    
    @Test
    public void TestRearrangeWordList(){
        TrieRoot Root = new TrieRoot();
        String Prefix = "prefix";
        String Word;
        int a_offset = 97;
        
        //fill the list
        for (int i = 0; i < TrieNode.MaxWords; i ++){
            Word = Prefix + Character.toChars(i+a_offset)[0];
            //as many as i
            for (int j = 0; j <= i; j ++){
                Root.UpdateTrie(Word);
                Root.PrintWordList();
            }
        }
        
        //should be found in reverse order
        for (int i = 0; i < TrieNode.MaxWords; i ++){
            Word = Prefix + Character.toChars(i+a_offset)[0];
            assertEquals(Word, Root.GetWordArray()[TrieNode.MaxWords - i - 1]);
        }
        
    }
    
    @Test
    public void TestReadFile() {
        String FileName = "AClashOfKingsPrologue.txt";
        TrieRoot Root = new TrieRoot();
        Root.ReadFile(FileName);
        Map<String, String[]> WordArrayByChar = Root.GetWordArrayByNextChar("dragon");
        
        ArrayList<String> ExpectedWords = new ArrayList<String>();
        
        ExpectedWords.add("dragonstone");
        ExpectedWords.add("dragons");
        ExpectedWords.add("dragonshreath");
        ExpectedWords.add("dragonmont");
        
        for (String next_char: WordArrayByChar.keySet()){
            for (String word : WordArrayByChar.get(next_char)) {
                assertTrue(ExpectedWords.contains(word));
                ExpectedWords.remove(word);
            }
        }
        assertTrue(ExpectedWords.isEmpty());
    }
}
