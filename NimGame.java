/**
 * NimGame class used by program Nymsys.
 * Here we can the game logic of a game match.
 *
 * @author  Ernesto Alejandro Calderon Caparo
 * @version 2.0
 * @date    03/2019
 */

import java.util.Scanner;

public class NimGame {

    //Attributes section
    private int currentStoneCount;
    private int upperBound;
    private NimPlayer player1;
    private NimPlayer player2;

    public NimGame(int currentStoneCount, int upperBound, NimPlayer player1, NimPlayer player2) {
        this.currentStoneCount = currentStoneCount;
        this.upperBound = upperBound;
        this.player1 = player1;
        this.player2 = player2;
    }
    //Game process logic
    public void play(Scanner keyboardObj)   {

        //Initial game section
        this.printGameStart();
        this.player1.increaseNumberOfGamesPlayed();
        this.player2.increaseNumberOfGamesPlayed();

        //This will make players take turns until there is no stones left
        this.startPlaying(keyboardObj);

        System.out.println("Game Over");

        this.showWinner();//This method verify the loser player, and add a win to winner player
        this.player1.resetPlayerLoserStatus();
        this.player2.resetPlayerLoserStatus();
        String junk = keyboardObj.nextLine();//Statement used to avoid pitfall
    }

    //Game methods section
    public void printGameStart(){
        System.out.println();
        System.out.println("Initial stone count: "+this.getCurrentStoneCount());
        System.out.println("Maximum stone removal: "+this.getUpperBound());
        System.out.println("Player 1: "+this.player1.getGivenName()+" "+this.player1.getFamilyName());
        System.out.println("Player 2: "+this.player2.getGivenName()+" "+this.player2.getFamilyName());
        System.out.println();
    }
    public void printStones() {
        System.out.print(getCurrentStoneCount() + " stones left:");
        for (int i = 1; i <= getCurrentStoneCount(); i++) {
            System.out.print(" *");
        }
        System.out.print("\n");
    }
    public void startPlaying(Scanner keyboardObj){
        int turnFlag  = 0; //This flag indicate a player's turn: turnFlag=0 for player1, turnFlag=1 for player2

        while (this.getCurrentStoneCount()>0){
            try {
                //player1 turn
                if (turnFlag == 0) {
                    int stonesToBeRemoved;
                    this.printStones();
                    System.out.printf("%s's turn - remove how many?\n", this.player1.getGivenName());
                    stonesToBeRemoved = this.player1.removeStones(keyboardObj, getUpperBound(), getCurrentStoneCount());
                    System.out.println();
                    this.validateInput(stonesToBeRemoved);
                    this.setCurrentStoneCount(this.getCurrentStoneCount()-stonesToBeRemoved);
                    if(this.getCurrentStoneCount() == 0){
                        this.player1.setPlayerToLoser();
                        this.player2.increaseNumberOfGamesWon();
                    }
                    turnFlag = 1;
                }
                //player2 turn
                else {
                    int stonesToBeRemoved;
                    this.printStones();
                    System.out.printf("%s's turn - remove how many?\n", this.player2.getGivenName());
                    stonesToBeRemoved = this.player2.removeStones(keyboardObj, getUpperBound(), getCurrentStoneCount());
                    System.out.println();
                    this.validateInput(stonesToBeRemoved);
                    this.setCurrentStoneCount(this.getCurrentStoneCount()-stonesToBeRemoved);
                    if(this.getCurrentStoneCount() == 0){
                        this.player2.setPlayerToLoser();
                        this.player1.increaseNumberOfGamesWon();
                    }
                    turnFlag = 0;
                }
            }catch (IllegalArgumentException e){
                System.out.println("Invalid move.");

            }catch (InvalidMove im) {
                System.out.println(im.getMessage() + this.getCurrentStoneCount()+" stones.");
                System.out.println();
            }
            catch (Exception e){
                System.out.println("Invalid move. You must remove between 1 and "+this.getUpperBound()+" stones.");
                System.out.println();
            }

        }
    }
    public void validateInput (int stonesToBeRemoved) throws Exception{
        if((getCurrentStoneCount()>getUpperBound())&&(stonesToBeRemoved>getUpperBound()||stonesToBeRemoved<=0)){
            throw new Exception();
        }
        if((getCurrentStoneCount()<=getUpperBound())&&(stonesToBeRemoved>getCurrentStoneCount()||stonesToBeRemoved>=getUpperBound()||stonesToBeRemoved<=0)){
            throw new InvalidMove("Invalid move. You must remove between 1 and ");
        }
    }
    public void showWinner(){
        if(!this.player1.getLoserStatus()) {
            System.out.println(this.player1.getGivenName()+" "+this.player1.getFamilyName()+" wins!");
        }
        else {
            System.out.println(this.player2.getGivenName()+" "+this.player2.getFamilyName()+" wins!");
        }
    }

    //Getters and Setters section
    public int getCurrentStoneCount() {
        return this.currentStoneCount;
    }
    public void setCurrentStoneCount(int currentStoneCount) {
        this.currentStoneCount = currentStoneCount;
    }
    public int getUpperBound() {
        return this.upperBound;
    }
    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
}
