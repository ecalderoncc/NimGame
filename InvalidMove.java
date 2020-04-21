/**
 * Custom Exception class to handle Invalid moves
 * in NimGame
 *
 * @author  Ernesto Alejandro Calderon Caparo
 * @version 3.0
 * @date    05/2019
 */
public class InvalidMove extends  Exception{

    public InvalidMove(String invalidMessage) {
        super(invalidMessage);
    }
}