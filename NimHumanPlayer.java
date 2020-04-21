import java.util.Scanner;

/**
 * Class that inherit from the "NimPlayer" Class
 * NimHumanPlayer, is a player which can remove stones
 * of a NimGame thought input
 *
 * @author  Ernesto Alejandro Calderon Caparo
 * @version 3.0
 * @date    05/2019
 */
public class NimHumanPlayer extends NimPlayer {

    //Constructor
    public NimHumanPlayer(String userName, String familyName, String givenName) {

        super(userName,familyName,givenName);
    }

    @Override
    public int removeStones(Scanner keyboardObj, int u, int cs){
        return keyboardObj.nextInt();
    }

}
