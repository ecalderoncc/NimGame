/**
 * Nimsys program to manage a collection of Nimplayers.
 * The options to manage the players are:
 * add, remove, edit, resetstats, display players, display rankings, startgame
 * A NimGame is possible with two chosen players
 *
 * @author  Ernesto Alejandro Calderon Caparo
 * @version 3.0
 * @date    05/2019
 */
import java.io.*;
import java.util.Scanner;

public class Nimsys {

    //Attributes section
    private NimPlayer[] playersCollection;
    private int player1Index, player2Index,playerCounter;

    private Nimsys(){
        this.playersCollection = new NimPlayer[100];
        this.playerCounter = 0;
        this.player1Index = 0;
        this.player2Index = 0;
    }

    public static void main(String[] args) {

        //Variables initialization Section
        Nimsys nimSystemObj = new Nimsys();

        System.out.println("Welcome to Nim\n");

        nimSystemObj.verifyFile();
        nimSystemObj.execution();
    }


    //Method in charge of run the Nimsys commands
    private void execution (){
        Scanner keyboardObj = new Scanner(System.in);
        String[] userInput;

        while(true){
            System.out.print("$");
            userInput = splitUserInput(keyboardObj.nextLine());
            try{
                switch (NimsysCommands.valueOf(userInput[0].toUpperCase())) {
                    case ADDPLAYER:
                        addPlayer(userInput);
                        System.out.println();
                        break;
                    case ADDAIPLAYER:
                        addAiPlayer(userInput);
                        System.out.println();
                        break;
                    case REMOVEPLAYER:
                        if(userInput.length>1){
                            removePlayer(userInput);
                            System.out.println();
                            break;
                        }
                        else {
                            System.out.println("Are you sure you want to remove all players? (y/n)");
                            if(keyboardObj.nextLine().equals("y")){
                                removePlayer();
                                System.out.println();
                                break;
                            }
                            else {
                                System.out.println();
                                break;
                            }
                        }
                    case EDITPLAYER:
                        editPlayer(userInput);
                        System.out.println();
                        break;
                    case RESETSTATS:
                        if(userInput.length>1){
                            resetStats(userInput);
                            System.out.println();
                            break;
                        }
                        else {
                            System.out.println("Are you sure you want to reset all player statistics? (y/n)");
                            if(keyboardObj.nextLine().equals("y")){
                                resetStats();
                                System.out.println();
                                break;
                            }
                            else {
                                System.out.println();
                                break;
                            }
                        }
                    case DISPLAYPLAYER:
                        if(userInput.length>1){
                            displayPlayer(userInput);
                            System.out.println();
                            break;
                        }
                        else {
                            displayPlayer();
                            System.out.println();
                            break;
                        }
                    case RANKINGS:
                        if(userInput.length>1){
                            if(userInput[1].equals("desc")){
                                calculateWinRatio();
                                rankingPlayerDesc();
                                System.out.println();
                                break;
                            }
                            else if(userInput[1].equals("asc")){
                                calculateWinRatio();
                                rankingPlayerAsc();
                                System.out.println();
                                break;
                            }
                            else{
                                throw new Exception();
                            }
                        }
                        else {
                            calculateWinRatio();
                            rankingPlayerDesc();
                            System.out.println();
                            break;
                        }
                    case STARTGAME:
                        if(userInput.length>1){
                            if(!startGameValidation(userInput)){
                                System.out.println("One of the players does not exist.");
                                System.out.println();
                                break;
                            }
                            else {
                                NimGame newNimGame = new NimGame(Integer.parseInt(userInput[1]),
                                        Integer.parseInt(userInput[2]),
                                        playersCollection[getPlayer1Index()],
                                        playersCollection[getPlayer2Index()]);
                                newNimGame.play(keyboardObj);
                                System.out.println();
                                keyboardObj.reset();
                                break;
                            }
                        }
                        else{
                            System.out.println();
                            break;
                        }
                    case EXIT:
                        System.out.println();
                        writeFile();
                        System.exit(0);
                    default:
                        break;
                }
            }catch (IllegalArgumentException iae){
                System.out.println("'"+userInput[0]+"' is not a valid command.");
                System.out.println();
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Incorrect number of arguments supplied to command.");
                System.out.println();
            }catch (Exception e){
                System.out.println("'"+userInput[1]+"' is not a valid command.");
                System.out.println();
            }
        }
    }

    //Nimsys commands logic methods section
    private void addPlayer(String []player){
        boolean playerExist = false;
        if(playerCounter == 0){
            NimPlayer newPlayerObj = new NimHumanPlayer(player[1], player[2], player[3]);
            playersCollection[playerCounter] = newPlayerObj;
            playerCounter++;
            playerExist = true;
        }
        else{
            for(int i = 0; i < playerCounter; i++){
                if(playersCollection[i].getUsername().equals(player[1])){
                    System.out.println("The player already exists.");
                    playerExist = true;
                    break;
                }
            }
        }
        if(!playerExist){
            NimPlayer newPlayerObj = new NimHumanPlayer(player[1], player[2], player[3]);
            playersCollection[playerCounter] = newPlayerObj;
            playerCounter++;
        }
    }
    private void addAiPlayer(String []player){
        boolean playerExist = false;
        if(playerCounter == 0){
            NimPlayer newPlayerObj = new NimAIPlayer(player[1], player[2], player[3]);
            playersCollection[playerCounter] = newPlayerObj;
            playerCounter++;
            playerExist = true;
        }
        else{
            for(int i = 0; i < playerCounter; i++){
                if(playersCollection[i].getUsername().equals(player[1])){
                    System.out.println("The player already exists.");
                    playerExist = true;
                    break;
                }
            }
        }
        if(!playerExist){
            NimPlayer newPlayerObj = new NimAIPlayer(player[1], player[2], player[3]);
            playersCollection[playerCounter] = newPlayerObj;
            playerCounter++;
        }
    }
    private void removePlayer(String []player){
        boolean playerNoExist = true;
        for(int i = 0; i < playerCounter; i++){
            if(playersCollection[i].getUsername().equals(player[1])){
                for(int j = i; j <= playerCounter; j++){
                    playersCollection[j] = playersCollection[j+1];
                }
                playerCounter--;
                playerNoExist = false;
                break;
            }
        }
        if(playerNoExist){
            System.out.println("The player does not exist.");
        }
    }
    private void removePlayer(){
        for (int i = 0; i < playerCounter; i++){
            playersCollection[i] = null;
        }
        playerCounter  = 0;
    }
    private void editPlayer(String []player){
        boolean playerNoExist = true;
        for(int i = 0; i < playerCounter; i++){
            if(playersCollection[i].getUsername().equals(player[1])){
                playersCollection[i].setFamilyName(player[2]);
                playersCollection[i].setGivenName(player[3]);
                playerNoExist = false;
                break;
            }
        }
        if(playerNoExist){
            System.out.println("The player does not exist.");
        }
    }
    private void resetStats(String []player){
        boolean playerReseted = false;
        for(int i = 0; i < playerCounter; i++){
            if(playersCollection[i].getUsername().equals(player[1])){
                playersCollection[i].setNumberOfGamesPlayed(0);
                playersCollection[i].setNumberOfGamesWon(0);
                playerReseted = true;
                break;
            }
        }
        if(!playerReseted){
            System.out.println("The player does not exist.");
        }
    }
    private void resetStats(){
        for(int i = 0; i < playerCounter; i++){
            playersCollection[i].setNumberOfGamesPlayed(0);
            playersCollection[i].setNumberOfGamesWon(0);
        }
    }
    private void displayPlayer(String []player){
        boolean playerDisplayed = false;
        for(int i = 0; i < playerCounter; i++){
            if(playersCollection[i].getUsername().equals(player[1])){
                System.out.println(""+playersCollection[i].getUsername()+
                                  ","+playersCollection[i].getGivenName()+
                                  ","+playersCollection[i].getFamilyName()+
                                  ","+playersCollection[i].getNumberOfGamesPlayed()+" games"+
                                  ","+playersCollection[i].getNumberOfGamesWon()+" wins"
                                      );
                playerDisplayed = true;
                break;
            }
        }
        if(!playerDisplayed){
            System.out.println("The player does not exist.");
        }
    }
    private void displayPlayer(){
        for(int i = 0; i < playerCounter; i++){
            System.out.println(""+playersCollection[i].getUsername()+
                    ","+playersCollection[i].getGivenName()+
                    ","+playersCollection[i].getFamilyName()+
                    ","+playersCollection[i].getNumberOfGamesPlayed()+" games"+
                    ","+playersCollection[i].getNumberOfGamesWon()+" wins"
            );
        }
    }
    private void rankingPlayerDesc(){
        int max;
        for (int i = 0; i < playerCounter; i++){
            max = i;
            for (int j = 1; j< playerCounter; j++){
                if(playersCollection[j].getWinningRatio()> playersCollection[max].getWinningRatio()){
                    NimPlayer tempPlayer = playersCollection[max];
                    playersCollection[max] = playersCollection[j];
                    playersCollection[j] = tempPlayer;
                }
                if(playersCollection[j].getWinningRatio() == playersCollection[max].getWinningRatio()){
                    if(playersCollection[j].getUsername().compareTo(playersCollection[max].getUsername())<0){
                        NimPlayer tempPlayer = playersCollection[max];
                        playersCollection[max] = playersCollection[j];
                        playersCollection[j] = tempPlayer;
                    }
                }
            }
        }
        for (int i = 0;i<playerCounter;i++){
            if(i<10){
                String winR = String.format("%.0f", playersCollection[i].getWinningRatio())+"%";
                String str = String.format("%02d", playersCollection[i].getNumberOfGamesPlayed());
                System.out.printf("%-5s| %s games | %s %s\n",
                        winR,
                        str,
                        playersCollection[i].getGivenName(),
                        playersCollection[i].getFamilyName());
            }
            else {
                break;
            }
        }
    }
    private void rankingPlayerAsc(){
        int min;
        for (int i = 0; i < playerCounter; i++){
            min = i;
            for (int j = 1; j< playerCounter; j++){
                if(playersCollection[j].getWinningRatio()< playersCollection[min].getWinningRatio()){
                    playersCollection[min] = playersCollection[j];
                }
                if(playersCollection[j].getWinningRatio() == playersCollection[min].getWinningRatio()){
                    if(playersCollection[j].getUsername().compareTo(playersCollection[min].getUsername())<0){
                        NimPlayer tempPlayer = playersCollection[min];
                        playersCollection[min] = playersCollection[j];
                        playersCollection[j] = tempPlayer;
                    }
                }
            }
        }
        for (int i = 0;i<playerCounter;i++){
            if(i<10){
                String winR = String.format("%.0f", playersCollection[i].getWinningRatio())+"%";
                String str = String.format("%02d", playersCollection[i].getNumberOfGamesPlayed());
                System.out.printf("%-5s| %s games | %s %s\n",
                        winR,
                        str,
                        playersCollection[i].getGivenName(),
                        playersCollection[i].getFamilyName());
            }
            else {
                break;
            }
        }

        /*int equalValidator= 0 ;
        NimPlayer[] tempArray = new NimPlayer[playerCounter];
        int helpCounter = 0;
        for (int i = 0;i<playerCounter;i++){
            tempArray[i] = playersCollection [i];
        }
        for(int i = 0; i<playerCounter;i++){
            equalValidator = (int)tempArray[i].getWinningRatio()+equalValidator;
        }
        if((equalValidator/playerCounter) == equalValidator){
            Arrays.sort(tempArray, NimPlayer.NymplayerUserNameComparator);
        }else {
            Arrays.sort(tempArray);
        }
        for (int i = playerCounter-1;i >= 0;i--){
            if(helpCounter<10){
                String winR = String.format("%.0f", tempArray[i].getWinningRatio())+"%";
                String str = String.format("%02d", tempArray[i].getNumberOfGamesPlayed());
                System.out.printf("%-5s| %s games | %s %s\n",
                        winR,
                        str,
                        tempArray[i].getGivenName(),
                        tempArray[i].getFamilyName());
                helpCounter++;
            }
            else {
                break;
            }
        }*/
    }
    private void calculateWinRatio(){
        double percentage;
        for (int i = 0; i < playerCounter; i++){
            percentage = (double)Math.round((((double)playersCollection[i].getNumberOfGamesWon()*100)/(double)playersCollection[i].getNumberOfGamesPlayed())*100)/100;
            playersCollection[i].setWinningRatio(percentage);
        }
    }
    private boolean startGameValidation(String[] userInput){
        boolean validUser1=false, validUser2=false;
        for(int i = 0; i < playerCounter; i++){
            if(playersCollection[i].getUsername().equals(userInput[3])){
                setPlayer1Index(i);
                validUser1 = true;
                break;
            }
        }
        for(int i = 0; i < playerCounter; i++){
            if(playersCollection[i].getUsername().equals(userInput[4])){
                setPlayer2Index(i);
                validUser2 = true;
                break;
            }
        }
        if(validUser1  && validUser2)
        {
            return true;
        }
        else {
            setPlayer1Index(0);
            setPlayer2Index(0);
            return false;
        }
    }
    private String[] splitUserInput(String userInput){
        return userInput.split("\\s+|,");
    }
    //This method read the array of Nimplayers of an existing file
    private void readFile(ObjectInputStream objectReader){
        try {
            playersCollection = (NimPlayer[]) objectReader.readObject();
            setplayerCounter(countPlayers());
        }catch (Exception ce){
            System.out.println("Error reading the file players.dat.");
        }
    }
    //This method write the actual array of Nimplayers into a file
    private void writeFile(){
        ObjectOutputStream objectWritter = null;
        try {
            objectWritter = new ObjectOutputStream(new FileOutputStream("players.dat"));
            objectWritter.writeObject(playersCollection);
            objectWritter.close();
        }catch(IOException io){
            System.out.println("Error writing the file players.dat.");
            System.exit(0);
        }
    }
    //This method verify if a file if exists, then read it
    private void verifyFile(){
        File playersFile = null;
        ObjectInputStream objectReader = null;
        try{
            playersFile = new File("players.dat");
            if(playersFile.exists() && playersFile.length()>0){
                objectReader = new ObjectInputStream(new FileInputStream("players.dat"));
                readFile(objectReader);
                objectReader.close();
            }
        }catch (IOException e){
            System.out.println("Error opening the file players.dat.");
            System.exit(0);
        }
    }
    //This method retrieves the actual numbers of players in the players collection
    private int countPlayers(){
        int count = 0;
        for (int i = 0; i < playersCollection.length; i ++){
            if (playersCollection[i] == null)
                break;
            else{
                count++;
            }
        }
        return count;
    }

    //Getters and Setters section
    private int getPlayer1Index() {
        return this.player1Index;
    }
    private void setPlayer1Index(int player1Index) {
        this.player1Index = player1Index;
    }
    private int getPlayer2Index() {
        return this.player2Index;
    }
    private void setPlayer2Index(int player2Index) {
        this.player2Index = player2Index;
    }
    private int getplayerCounter() {
        return this.playerCounter;
    }
    private void setplayerCounter(int playerCounter) {
        this.playerCounter = playerCounter;
    }
}
