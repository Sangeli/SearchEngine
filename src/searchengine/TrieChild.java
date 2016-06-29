/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

/**
 *
 * @author steph_000
 */
public class TrieChild extends TrieNode {
    private String SubStr;
    private int Weight;
    
    
    public TrieChild(String SubStr, int Depth) {
        super(Depth);
        this.SubStr = SubStr;
        this.Weight = 0;
    }
    
    public TrieChild UpdateTrie(String Word) {
        //at the end node
        if (Word.length() == this.Depth) {
            this.Weight += 1;
            UpdateWordList(Word, this);
            return this;
        }
        return super.UpdateTrie(Word);
    }
    
    public int GetDepth() {
        return this.Depth;
    }
    
    public int GetWeight() {
        return this.Weight;
    }
}
