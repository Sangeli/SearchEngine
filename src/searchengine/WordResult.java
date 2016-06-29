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
public class WordResult {
    private String Word;
    private int Weight;
    private WordResult Prev;
    private WordResult Next;

    public WordResult(String Word, int Weight) {
        this.Word = Word;
        this.Weight = Weight;
    }
    public int GetWeight() {
        return this.Weight;
    }
    public String GetWord() {
        return this.Word;
    }
    public void IncrWeight() {
        this.Weight ++;
    }
    public WordResult GetNext(){
        return this.Next;
    }
    public void SetNext(WordResult Next){
        this.Next = Next;
    }
    public WordResult GetPrev(){
        return this.Prev;
    }
    public void SetPrev(WordResult Prev){
        this.Prev = Prev;
    }
    
}
