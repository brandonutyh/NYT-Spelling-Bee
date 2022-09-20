import java.util.*;
import java.io.*;
public class SpellingBee{

  private int points=0;
  private String coreLetter;
  public String guess;
  public String currentRank = "";
  private ArrayList<String> groupAnswers = new ArrayList<String>();
  private String[] sevenLetters = new String[7];
  private Scanner input = new Scanner(System.in);
  private String gameType;
  private int totalPoints = 0;
  private ArrayList<String> wordList;
  private ArrayList<String> currWordList = new ArrayList<String>();
  private ArrayList<String> pangramList;
  private ArrayList<String> currPangramList = new ArrayList<String>();
  private String [] customSeven ={"tghlmoy","mceilop","achnovy","kacdelo","ebgiknp","radhnty","bceilot", "magijnu", "gadnruv", "nacelow", "ochikpt"};
  
  
  public SpellingBee(HashSet<String> s, HashSet<String> p){
    wordList = new ArrayList<String>(s);
    pangramList = new ArrayList<String>(p);
  }

  public void begin(){
    System.out.println("*** WELCOME TO THE NICK AND BRANDON'S SPELLING BEE GAME.");
    getInfo();
    System.out.println("Would you like to select the seven letters for the game or play a random spelling game? Type 1 for custom type 2 for random.");
    gameType = validateInput(input.next(), false);
    if(gameType.equals("1")){
      playCustom();
    }
    else{
      playRandom();
    } 
  }

  //plays with 7 letters of your choosing
  public void playCustom(){
    System.out.println("Please enter 7 letters for your custom Spelling Bee game. Your first letter will be the center letter");
    sevenLetters = validateLetters(input.next());
    play();
  }

   public void playRandom(){
   //idk
    //wat
     //obh true lmao XDD forgot abt
    //you need a *10 bc amth.random is 0.0-1 ohhhh i got it.
     //oh yeah i see got it
     //wait why is it sevenLetters[0] im setting the core letter to be the first element of core letter
     //you too clean nick ye
     //too ez all we have to do now is quit and replay
     //does our quit work not yet
     //oh the game didnt end
    String str = customSeven[(int)(Math.random()*customSeven.length)];
    for(int i = 0; i < str.length(); i++){
      sevenLetters[i] = str.substring(i,i+1);
    }
    coreLetter = sevenLetters[0];
    makeCurrPangrams(str);
    makeCurrWordList(str);
    calcTotalPoints();
    play();
   }

  public void play(){
    while(!checkWin()){
      updateRank();
      System.out.println("Rank: " + currentRank + "; Points: " + points);
      printGrid();
      System.out.println("Type out any word using the letters above. You must use the center letter.");
      System.out.println("Type !info for detailed rules \nType !list for for the list of words you've found already. \nType !shuffle to shuffle the letters displayed. \nType !rank to display all the ranks.\nType !quit to quit the game.\n" );
      guess = validateInput(input.next(), true);
      if(!guess.equals("")){
        groupAnswers.add(guess);
        getPoints(guess);
      }
    }
    System.out.println("YOU ARE A QUEEN BEE!");
  }

  // validates the 7 letters for the custon game
  public String[] validateLetters(String str){
    boolean doLoop = true;
    boolean wrong = false;
    while(doLoop){
      str = str.toLowerCase();
      if(str.length() != 7){
      System.out.println("You did not choose 7 letters. Please select 7 letters.\n");
        str = input.next();
        wrong = true;
      }
      else{
        for(int i = 0; i < str.length()-1; i++){
          String currLetter = str.substring(i,i+1);
          if(str.substring(i+1).contains(currLetter)){
            System.out.println("Please no duplicates. Type in another 7 letters.\n");
            str = input.next();
            wrong = true;
            break;
          }
          else{
            wrong = false;
          }
        }
      }
      if(!wrong){
        coreLetter = str.substring(0,1);        
        makeCurrPangrams(str);
        if(currPangramList.size()>0){
          doLoop = false;
        }
        else{
          System.out.println("These 7 letters that you chose did not have a pangram. Please put another 7 letters.\n");
          str = input.next();
        }
      }
    }
    makeCurrWordList(str);
    calcTotalPoints();
    sevenLetters = str.split("");
    return sevenLetters;
  }

  //validates the guess or 1 or 2
  public String validateInput(String str, boolean type){
    boolean doLoop = true;
    while(doLoop){
      if(type == false){
        if(!str.equals("1") && !str.equals("2")){
          System.out.println("Please enter either 1 or 2.\n");
          str = input.next();
        }
        else{
          doLoop=false;
        }
      }
      else if(str.substring(0,1).equals("!")){
        str = str.toLowerCase();
        if(str.equals("!info")){
          getInfo();
          return "";
        }
        else if(str.equals("!list")){
          getList();
          return "";
        }
        else if(str.equals("!shuffle")){
          shuffle();
          return "";
        }
        else if(str.equals("!rank")){
          getRank();
          return "";
        }
        else if(str.equals("!quit")){
          quit();
          return "";
        }
        else{
          System.out.println("This is not a valid command. Please type in a valid command.");
          str = input.next();
        }
      }
      else{
        str.toLowerCase();
        if(str.length() < 4){
          System.out.println("Please enter in a valid word with 4 or more letters.\n");
          str = input.next();
        }
        else if(!str.contains(coreLetter)){
          System.out.println("Please enter a valid guess with the center letter.\n");
          str = input.next();
        }
        else if(!currWordList.contains(str)){
          System.out.println("Please enter a valid guess in the word list.\n");
          str = input.next();
        }
        
        else if(groupAnswers.contains(str)){
          System.out.println("Word already found! Please enter another word that you haven't put yet.\n");
          str = input.next();
        }
        else{
          doLoop=false;
        }
      }     
    }
    return str + "";
  }
  
  // makes a pangramList of the 7 letters
  public void makeCurrPangrams(String str){
    String let1 = str.substring(0,1);
    String let2 = str.substring(1,2);
    String let3 = str.substring(2,3);
    String let4 = str.substring(3,4);
    String let5 = str.substring(4,5);
    String let6 = str.substring(5,6);
    String let7 = str.substring(6,7);
    for(int i = 0; i < pangramList.size(); i++){
      if(pangramList.get(i).contains(coreLetter)){
        currPangramList.add(pangramList.get(i));
      }
    }
    //goes through the curr pangramList and removes the words that dont have the other needed letters in it
    for(int i = 0; i < currPangramList.size(); i++){
      String tempWord = currPangramList.get(i);
      for(int j = 0; j < tempWord.length(); j++){
        String tempLet = tempWord.substring(j,j+1);
        if(!tempLet.equals(let1) && !tempLet.equals(let2) && !tempLet.equals(let3) && !tempLet.equals(let4) && !tempLet.equals(let5) && !tempLet.equals(let6) && !tempLet.equals(let7)){
          currPangramList.remove(i);
          i--;
          break;
        }
      }
    }
  }
   // makes a word list of words that only have the 7 letters
  public void makeCurrWordList(String str){
    String let1 = str.substring(0,1);
    String let2 = str.substring(1,2);
    String let3 = str.substring(2,3);
    String let4 = str.substring(3,4);
    String let5 = str.substring(4,5);
    String let6 = str.substring(5,6);
    String let7 = str.substring(6,7);
    //first adds words that have the core letter
    for(int i = 0; i < wordList.size(); i++){
      if(wordList.get(i).contains(coreLetter)){
        currWordList.add(wordList.get(i));
      }
    }
    // then filters the words that do not have the other 6 letters
    for(int i = 0; i < currWordList.size(); i++){
      String tempWord = currWordList.get(i);
      for(int j = 0; j < tempWord.length(); j++){
        String tempLet = tempWord.substring(j,j+1);
        if(!tempLet.equals(let1) && !tempLet.equals(let2) && !tempLet.equals(let3) && !tempLet.equals(let4) && !tempLet.equals(let5) && !tempLet.equals(let6) && !tempLet.equals(let7)){
          currWordList.remove(i);
          i--;
          break;
        }
      }
    }
 
  }
  
  // calculates total points of the current word list
  public void calcTotalPoints(){
    for(int i = 0; i<currWordList.size(); i++){
      String current = currWordList.get(i);
      if(current.length() == 4){
        totalPoints++;
      }
      else if(currPangramList.contains(current)){
        totalPoints += current.length() + 7;
      }
      else{
        totalPoints += current.length();
      }
    }
  }
  

  //Prints the grid of letters and core letters
  public void printGrid(){
    System.out.print("***********");
    System.out.println();
    System.out.println("     " + sevenLetters[1]);
    System.out.println("   " + sevenLetters[2] + "   " + sevenLetters[3]);
    System.out.println("     " + sevenLetters[0]);
    System.out.println("   " + sevenLetters[4] + "   " + sevenLetters[5]);
    System.out.println("     " + sevenLetters[6]);
    System.out.println("***********");
  }

  //prints the appropriate response of a correct guess
  public void getPoints(String str){
    if(str.length() == 4){
      points++;
      System.out.println("Good!\n");
    }
    else if(str.length()==5 || str.length()==6){
      points = points+str.length();
      System.out.println("Nice!\n");
    }
    else if(currPangramList.contains(str)){
      points = points+(7+str.length());
      System.out.println("You found a pangram! Nice!\n");
    }
    else if(str.length()>7){
      points = points + str.length();
      System.out.println("Awesome!\n");
    }
  }
  //method for !info command.
  public void getInfo(){
    System.out.println("How to play:");
    System.out.println("Words must contain at least 4 letters, the center letter, no hyphenated, obscured, or propered nouns, and letters can be only used once. \n");
    System.out.println("Scoring Rules:");
    System.out.println("4 letter words are 1 point each, and longer words earn 1 point per letter. Each puzzle contains at least one pangram and are worth 7 extra points. \n");
  }
  
  //method for !list command
  public void getList(){
    System.out.println("Words found so far:\n");
    for(int i=0; i<groupAnswers.size(); i++){
      System.out.println(groupAnswers.get(i));
    }
  }
  
  //method for !shuffle command
  public void shuffle(){
    List<String> tempArr = Arrays.asList(sevenLetters);
    Collections.shuffle(tempArr);
    if(!tempArr.get(0).equals(coreLetter)){
      tempArr.set(tempArr.indexOf(coreLetter), tempArr.get(0));
      tempArr.set(0,coreLetter);
    }
  }
  
  //method for !rank command
  public void getRank(){
    System.out.println("Ranks are based on a percentage of possible points in the puzzle. The minimum scores to reach each point are: ");
    System.out.println("Beginner: 0");
    System.out.println("Good Start: " + (int)(totalPoints*0.02));
    System.out.println("Moving Up: " + (int)(totalPoints*0.05));
    System.out.println("Good: " + (int)(totalPoints*0.08));
    System.out.println("Solid: " + (int)(totalPoints*0.15));
    System.out.println("Nice: " + (int)(totalPoints*0.25));
    System.out.println("Great: "+ (int)(totalPoints*0.4));
    System.out.println("Amazing: "+ (int)(totalPoints*0.5));
    System.out.println("Genius: " + (int)(totalPoints*0.7));
    System.out.println("Queen Bee: " + totalPoints);
  }
  
  // command for !quit command
  public void quit(){
    System.out.println("Thanks for playing.");
    System.out.println("Rank: "+ currentRank + " ;Points: "+ points);
    System.out.println("Would you like to see all the words possible for these 7 letters. Type in 1 for yes or 2 for no.");
    if(validateInput(input.next(), false).equals("1")){
      for(int i = 0; i < currWordList.size(); i++){      
        if(!currPangramList.contains(currWordList.get(i))){
          System.out.println(currWordList.get(i));
        }
        else{
          System.out.println(currWordList.get(i) + "     PANGRAM");
        }
      }
    }
    System.out.println("Would you like to play again? Type in 1 to play again, 2 to quit for real.");
    if(validateInput(input.next(),(false)).equals("1")){
      begin();
    }
    else{
      System.out.println("Better luck next time!");
      System.exit(0);
    }
  }
  
  // checks for win
  public boolean checkWin(){
    if(currWordList.size() == groupAnswers.size()){
      return true;
    }
    return false;
  }

  //updates rank
  public void updateRank(){
    if(points == totalPoints){
      currentRank = "Queen Bee";
    }
    else if(points >= (int)(totalPoints*0.7)){
      currentRank = "Genius";
    }
    else if(points >= (int)(totalPoints*0.5)){
      currentRank = "Amazing";
    }
    else if(points >= (int)(totalPoints*0.4)){
      currentRank = "Great";
    }
    else if(points >= (int)(totalPoints*0.25)){
      currentRank = "Nice";
    }
    else if(points >= (int)(totalPoints*0.15)){
      currentRank = "Solid";
    }
    else if(points >= (int)(totalPoints*0.08)){
      currentRank = "Good";
    }
    else if(points >= (int)(totalPoints*0.05)){
      currentRank = "Moving Up";
    }
    else if(points >= (int)(totalPoints*0.02)){
      currentRank = "Good Rank";
    }
    else{
      currentRank = "Beginner";
    }   
  }
}