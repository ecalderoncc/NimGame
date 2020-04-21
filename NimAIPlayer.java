import java.util.Random;
import java.util.Scanner;
/**
 * Class that inherit from the "NimPlayer" Class
 * NimAIPlayer, is a player which can remove stones
 * of a NimGame thought certain specific rules
 * to guarantee its win
 *
 * @author  Ernesto Alejandro Calderon Caparo
 * @version 3.0
 * @date    05/2019
 */
public class NimAIPlayer extends NimPlayer{

    //Constructor
    public NimAIPlayer(String userName, String familyName, String givenName) {
        super(userName,familyName,givenName);
    }

    public String advancedMove(boolean[] available, String lastMove) {
        // the implementation of the victory
        // guaranteed strategy designed by you
        String move = "";

        return move;
    }

    @Override
    public int removeStones(Scanner key, int upperBound, int currStones){
        int k = 0;
        int i = 0;
        Random r = new Random();
        while(true){
            if((k*(upperBound + 1) + 1)>currStones)
                break;
            else {
                i = k*(upperBound + 1) + 1;
                k++;
            }
        }
        if(currStones == i)
            return r.nextInt((upperBound+1) - 1) + 1;
        else
            return currStones - i;
    }
}
