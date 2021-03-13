package game;

import java.util.*;

// Please don't modify the class name.
public class Poker {

    // Please don't modify the signature of this method.
    // Please keep the result output format.
    public String compairResult(String player1, String player2) {
        AbstractCard player1Card = AbstractCard.of(player1, "player1");
        AbstractCard player2Card = AbstractCard.of(player2, "player2");

        return player1Card.compareTo(player2Card);
    }
}
