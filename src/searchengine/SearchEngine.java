/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author scefali
 */
public class SearchEngine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestNull();
        Test1();
        Test2Diff();
    }
    
    public static void TestNull(){
        TrieRoot Root = new TrieRoot();
        assertEquals(Root.GetWordArray().length, 0);
    }
    
    public static void Test1(){
        String Word = "a";
        TrieRoot Root = new TrieRoot();
        TrieChild end = Root.UpdateTrie(Word);
        
        //check root and end for the length and content
        assertEquals(Root.GetWordArray().length, 1);
        assertEquals(end.GetWordArray().length, 1);
        
        assertEquals(Root.GetWordArray()[0], "a");
        assertEquals(end.GetWordArray()[0], "a");
        
        //Map<String, String[]> WordArrayByChar = Root.GetWordArrayByNextChar("a");
        //System.out.println(WordArrayByChar);
    }
    
    public static void Test2Diff(){
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
    
}
