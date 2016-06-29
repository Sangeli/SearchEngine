/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;
import java.util.*;

/**
 *
 * @author steph_000
 */
public class TrieRoot extends TrieNode {
    public TrieRoot(){
        super(0);
        
    }
    
    public Map<String, String[]> GetWordArrayByNextChar (String WordStart) {
        TrieNode End = GetEndNode(WordStart);
        return End.GetWordArrayByNextChar();
    }
    
    
    
}
