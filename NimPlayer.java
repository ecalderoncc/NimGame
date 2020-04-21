/**
 * NimPlayer Class.
 * Here we can find the attributes of a player.
 * *NimPlayer implements Serializable class in order to
 * save objects direct into a file
 *
 * @author  Ernesto Alejandro Calderon Caparo
 * @version 3.0
 * @date    05/2019
 */
import java.io.Serializable;
import java.util.Scanner;

public abstract class NimPlayer implements Serializable {

    //Attributes section
    private boolean isLoser;
    private String userName;
    private String givenName;
    private String familyName;
    private int numberOfGamesPlayed;
    private int numberOfGamesWon;
    private double winningRatio;

    public NimPlayer(String userName, String familyName, String givenName) {

        this.userName = userName;
        this.familyName = familyName;
        this.givenName = givenName;
        this.numberOfGamesPlayed = 0;
        this.numberOfGamesWon = 0;
        this.winningRatio = 0;
        this.isLoser = false;
    }

    //Getters and setters
    public String getUsername() {
        return this.userName;
    }
    public void setUsername(String newPlayerUsername){
        this.userName = newPlayerUsername;
    }
    public String getGivenName() {
        return this.givenName;
    }
    public void setGivenName(String newPlayerGivenName){
        this.givenName = newPlayerGivenName;
    }
    public String getFamilyName() {
        return this.familyName;
    }
    public void setFamilyName(String newPlayerFamilyName){
        this.familyName = newPlayerFamilyName;
    }
    public int getNumberOfGamesPlayed() {
        return this.numberOfGamesPlayed;
    }
    public void setNumberOfGamesPlayed(int newNumberOfGamesPlayed){
        this.numberOfGamesPlayed = newNumberOfGamesPlayed;
    }
    public int getNumberOfGamesWon() {
        return this.numberOfGamesWon;
    }
    public void setNumberOfGamesWon(int newNumberOfGamesWon){
        this.numberOfGamesWon = newNumberOfGamesWon;
    }
    public double getWinningRatio() {
        return this.winningRatio;
    }
    public void setWinningRatio(double newWinningRatio) {
        this.winningRatio = newWinningRatio;
    }
    public boolean getLoserStatus() {
        return isLoser;
    }
    public void setPlayerToLoser() {
        this.isLoser = true;
    }

    //Player methods
    public void increaseNumberOfGamesPlayed(){
        this.numberOfGamesPlayed += 1;
    }
    public void increaseNumberOfGamesWon(){
        this.numberOfGamesWon += 1;
    }
    public void resetPlayerLoserStatus() {
        this.isLoser = false;
    }
    public abstract int removeStones(Scanner keyboardObj, int uppBound, int currStones);
}
