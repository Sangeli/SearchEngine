/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;
import java.util.*;

/**
 *
 * @author StephenCefali
 */
public class TrieNode {
    public static int MaxWords = 6;
    protected int Depth;
    private Map<String, TrieChild> Children;
    private int CurrWordCount;
    private WordResult MPopWord;
    private WordResult LPopWord;
    private Map<String, WordResult> Words;
    
    public TrieNode(int Depth) {
        this.Depth = Depth;
        this.Children = new HashMap<String, TrieChild>();
        this.CurrWordCount = 0;
        this.MPopWord = null;
        this.LPopWord = null;
        this.Words = new HashMap<String, WordResult>();
    }
    
    
    public void SwapResults(WordResult HigherResult, WordResult LowerResult) throws IllegalArgumentException {
        
        //Check HigherResult actually being higher
        if (HigherResult.GetWeight() <= LowerResult.GetWeight()) {
            throw new IllegalArgumentException();
        }
        
        
        //if LowerResult not at the end, check to see if linked adjacently
        if (LowerResult != this.LPopWord) {
            if (HigherResult.GetPrev() != LowerResult) {
                throw new IllegalArgumentException();
            }
            if (LowerResult.GetNext() != HigherResult) {
                throw new IllegalArgumentException();
            }
        }
        
        
        //Keep a temp
        WordResult temp = LowerResult.GetPrev();
        
        //Swap prev and next for these two
        LowerResult.SetPrev(HigherResult);
        LowerResult.SetNext(HigherResult.GetNext());
        
        HigherResult.SetPrev(temp);
        HigherResult.SetNext(LowerResult);
        
        //Point the bookends to the new structure if existing
        //Otherise must be a head/tail
        if (HigherResult.GetPrev() == null) {
            this.MPopWord = HigherResult;
        } else {
            HigherResult.GetPrev().SetNext(HigherResult);
        }
        
        if (LowerResult.GetNext() == null) {
            this.LPopWord = LowerResult;
        } else {
            LowerResult.GetNext().SetPrev(LowerResult);
        }
    }
    
    //Look at the list we have stored at this location
    public Map<String, String[]> GetWordArrayByNextChar () {
        Map<String, String[]> WordArrayByChar = new HashMap<String, String[]>();
        for (String NChar : this.Children.keySet()) {
            TrieChild child = this.Children.get(NChar);
            WordArrayByChar.put(NChar, child.GetWordArray());
        }
        return WordArrayByChar;
    }
    
    public String[] GetWordArray() {
        String[] WordArray = new String[this.CurrWordCount];
        WordResult CurrWord = this.MPopWord;
        for (int i = 0; i < this.CurrWordCount; i ++) {
            WordArray[i] = CurrWord.GetWord();
            CurrWord = CurrWord.GetNext();
        }
        return WordArray;
    }
    
    public void PrintWordArray() {
        WordResult curr = this.MPopWord;
        for (int i = 0; i < this.CurrWordCount; i ++) {
            System.out.println(curr);
            curr = curr.GetNext();
        }
        
    }
    
    public TrieNode GetEndNode(String WordStart) {
        
        //if length is this depth we are done
        if (WordStart.length() == this.Depth) {
            return this;
        }
        
        //check to see if we even have children with the next char
        String next_char = String.valueOf(WordStart.charAt(this.Depth));
        if (!this.Children.containsKey(next_char)) {
            return null;
        }
        
        //call the child's method
        return Children.get(next_char).GetEndNode(WordStart);
        
    }
    
    public TrieChild UpdateTrie(String Word) {
        String next_char = String.valueOf(Word.charAt(this.Depth));
        //make it if it doesn't exist
        TrieChild child;
        if (!this.Children.containsKey(next_char)) {
            child = new TrieChild(next_char, this.Depth+1);
            this.Children.put(next_char, child);
        }
        child = this.Children.get(next_char);
        
        TrieChild End = child.UpdateTrie(Word);
        
        
        UpdateWordList(Word, End);
        
        return End;
        
    }
    
    public void UpdateWordList(String Word, TrieChild End) {
        
        
        WordResult curr;
        WordResult prev;
        //Check to see if it exits in our set
        if (this.Words.containsKey(Word)) {
            curr = this.Words.get(Word);
            curr.IncrWeight();
            
            prev = curr.GetPrev();
            
            //keep pushing the word back until its in order
            while (prev != null){
                if(curr.GetWeight() > prev.GetWeight()){
                    SwapResults(curr, prev);
                    prev = curr.GetPrev();
                }
                else {
                    break;
                }
            }
        }
        else {
            //not in set, check from end
            curr = new WordResult(Word, End.GetWeight());
            prev = this.LPopWord;
            //If smaller than max size, just add to the end
            if (this.CurrWordCount < this.MaxWords) {
                //First word found
                if (this.MPopWord == null) {
                    this.MPopWord = curr;
                }
                //Add to the end
                else {
                    prev.SetNext(curr);
                    curr.SetPrev(prev);
                }
                //Always put the new word on the tail
                this.LPopWord = curr;
                this.Words.put(Word, curr);
                this.CurrWordCount ++;
            }
            //Otherwise check to see if it would fit
            else {
                while (prev != null){
                    if(curr.GetWeight() > prev.GetWeight()){
                        //if prev was at the end, remove it
                        //and replace it with curr
                        if (prev == this.LPopWord) {
                            this.Words.remove(prev.GetWord());
                            this.Words.put(curr.GetWord(), curr);
                            this.LPopWord = curr;
                            curr.SetPrev(prev.GetPrev());
                            prev.GetPrev().SetNext(curr);
                        }
                        //otherwise swap
                        else {
                            SwapResults(curr, prev);
                        }
                        
                        
                        prev = curr.GetPrev();
                    }
                    else {
                        break;
                    }
                }
            }
        }
    }
    
    public WordResult GetLPopWord(){
        return this.LPopWord;
    }
    
    public WordResult GetMPopWord(){
        return this.MPopWord;
    }
    
    public void PrintWordList(){
        WordResult curr = this.MPopWord;
        while (curr != null) {
            curr = curr.GetNext();
        }
    }
}
