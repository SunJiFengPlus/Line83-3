package game;

import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

class PokerTest {
    @Test
    void test() {
        String[] inputList = new String[]{
                // StraightFlush
                "AH KH QH JH TH",
                "2H 3H 4H 5H 6H",
                "3H 4H 5H 6H 7H",
                // FourOfAKind
                "AS AD AH AC KH",
                "2S 2D 2H 2C 3H",
                // FullHouse
                "AS AD AH KC KH",
                "2S 2D 2H 3C 3H",
                // Flush
                "AH KH QH JH 9H",
                "2H 3H 4H 5H 7H",
                // Straight
                "AS KH QC JH TS",
                "2S 3H 4C 5H 6S",
                "3S 4H 5C 6H 7S",
                // ThreeOfAKind
                "AS AH AC KD QH",
                "2S 2H 2C 3D 4H",
                // TwoPair
                "AS AH KS KH QS",
                "2S 2H 3S 3H 4S",
                "2S 2H 3S 3H 5S",
                // OnePair
                "AS AH KS QH JS",
                "AS AH KS QH TS",
                "2S 2H 3S 4H 5S",
                // HighCard
                "AS KH QS JH 9S",
                "2S 3H 4S 5H 7S"
        };

        CombinationApprovals.verifyAllCombinations(this::compare, inputList, inputList);
    }

    String compare(String player1, String player2) {
        Poker poker = new Poker();
        return poker.compairResult(player1, player2);
    }
}