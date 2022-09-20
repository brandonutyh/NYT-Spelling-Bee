import java.util.*;
import java.io.*;
class Main {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(new File("dictionary.txt"));
    HashSet<String> words = new HashSet<String>();
    HashSet<String> pangrams = new HashSet<String>();
    ArrayList<String> uniqueLet = new ArrayList<String>();
    while(sc.hasNext())
    {
      String str = sc.next();
      words.add(str);
      uniqueLet.add(str.substring(0,1));
      for(int i = 1; i < str.length(); i++){  
        if(!uniqueLet.contains(str.substring(i,i+1))){
          uniqueLet.add(str.substring(i,i+1));
        }
      }
      if(uniqueLet.size() >= 7){
        pangrams.add(str);
      }
      uniqueLet.clear();
    }
    SpellingBee s = new SpellingBee(words, pangrams);
    s.begin();
  }
}