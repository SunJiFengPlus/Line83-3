package game;

import java.util.*;

// Please don't modify the class name.
public class Poker {

    // Please don't modify the signature of this method.
    // Please keep the result output format.
    public String compairResult(String player1, String player2) {
        String winResult = "";

        AbstractCard player1Card = AbstractCard.of(player1, "player1");
        AbstractCard player2Card = AbstractCard.of(player2, "player2");

        int[] player1Number = player1Card.getCardNum();
        int[] player2Number = player2Card.getCardNum();
        int player1Index = player1Card.getTypeIndex();
        int player2Index = player2Card.getTypeIndex();
        int[] player1ArraySort = player1Card.sort();
        int[] player2ArraySort = player2Card.sort();
        int[] player1Repeat = player1Card.noOrRepeatNumber(0);
        int[] player2Repeat = player2Card.noOrRepeatNumber(0);
        int[] player1NoRepeat = player1Card.noOrRepeatNumber(1);
        int[] player2NoRepeat = player2Card.noOrRepeatNumber(1);

        if (player1Index < player2Index) {
            winResult = "player1 wins - " + player1Card.getType();
        } else if (player1Index > player2Index) {
            winResult = "player2 wins - " + player2Card.getType();
        } else {
            if (player1Index == 0) {//同花顺
                if (player1Number[0] < player2Number[0]) {
                    String sig = intNumber(player2Number[0]);
                    winResult = "player2 wins - high card:" + sig;
                } else if (player1Number[0] > player2Number[0]) {
                    String sig = intNumber(player1Number[0]);
                    winResult = "player1 wins - high card:" + sig;
                } else {
                    winResult = "tie";
                }
            } else if (player1Index == 1) {//铁支
                if (player1ArraySort[0] < player2ArraySort[0]) {
                    String sig = intNumber(player2ArraySort[0]);
                    winResult = "player2 wins - high card:" + sig;
                } else {
                    String sig = intNumber(player1ArraySort[0]);
                    winResult = "player1 wins - high card:" + sig;
                }
            } else if (player1Index == 2) {//葫芦
                if (player1ArraySort[0] < player2ArraySort[0]) {
                    String sig = intNumber(player2ArraySort[0]);
                    winResult = "player2 wins - high card:" + sig;
                } else {
                    String sig = intNumber(player1ArraySort[0]);
                    winResult = "player1 wins - high card:" + sig;
                }
            } else if (player1Index == 3) {//同花
                for (int i = 0; i < 5; i++) {
                    if (player1Number[i] < player2Number[i]) {
                        String sig = intNumber(player2Number[i]);
                        winResult = "player2 wins - high card:" + sig;
                        break;
                    } else if (player1Number[i] > player2Number[i]) {
                        String sig = intNumber(player1Number[i]);
                        winResult = "player1 wins - high card:" + sig;
                        break;
                    } else {
                        winResult = "tie";
                    }
                }
            } else if (player1Index == 4) {//顺子
                if (player1Number[0] < player2Number[0]) {
                    String sig = intNumber(player2Number[0]);
                    winResult = "player2 wins - high card:" + sig;
                } else if (player1Number[0] > player2Number[0]) {
                    String sig = intNumber(player1Number[0]);
                    winResult = "player1 wins - high card:" + sig;
                } else {
                    winResult = "tie";
                }
            } else if (player1Index == 5) {//三条
                if (player1Repeat[0] < player2Repeat[0]) {
                    String sig = intNumber(player2Repeat[0]);
                    winResult = "player2 wins - high card:" + sig;
                } else {
                    String sig = intNumber(player1Repeat[0]);
                    winResult = "player1 wins - high card:" + sig;
                }
            } else if (player1Index == 6) {//两对
                for (int i = 0; i < 2; i++) {
                    if (player1Repeat[i] < player2Repeat[i]) {
                        String sig = intNumber(player2Repeat[i]);
                        winResult = "player2 wins - high card:" + sig;
                        break;
                    } else if (player1Repeat[i] > player2Repeat[i]) {
                        String sig = intNumber(player1Repeat[i]);
                        winResult = "player1 wins - high card:" + sig;
                        break;
                    }
                }
                if (winResult.equals("")) {
                    if (player1NoRepeat[0] < player2NoRepeat[0]) {
                        String sig = intNumber(player2NoRepeat[0]);
                        winResult = "player2 wins - high card:" + sig;
                    } else if (player1NoRepeat[0] > player2NoRepeat[0]) {
                        String sig = intNumber(player1NoRepeat[0]);
                        winResult = "player1 wins - high card:" + sig;
                    } else {
                        winResult = "tie";
                    }
                }
            } else if (player1Index == 7) {//对子
                if (player1Repeat[0] < player2Repeat[0]) {
                    String sig = intNumber(player2Repeat[0]);
                    winResult = "player2 wins - high card:" + sig;
                } else if (player1Repeat[0] > player2Repeat[0]) {
                    String sig = intNumber(player1Repeat[0]);
                    winResult = "player1 wins - high card:" + sig;
                } else {
                    for (int i = 0; i < 3; i++) {
                        if (player1NoRepeat[i] < player2NoRepeat[i]) {
                            String sig = intNumber(player2NoRepeat[i]);
                            winResult = "player2 wins - high card:" + sig;
                            break;
                        } else if (player1NoRepeat[i] > player2NoRepeat[i]) {
                            String sig = intNumber(player1NoRepeat[i]);
                            winResult = "player1 wins - high card:" + sig;
                            break;
                        } else {
                            winResult = "tie";
                        }
                    }
                }
            } else {//散牌
                for (int i = 0; i < 5; i++) {
                    if (player1Number[i] < player2Number[i]) {
                        String sig = intNumber(player2Number[i]);
                        winResult = "player2 wins - high card:" + sig;
                        break;
                    } else if (player1Number[i] > player2Number[i]) {
                        String sig = intNumber(player1Number[i]);
                        winResult = "player1 wins - high card:" + sig;
                        break;
                    } else {
                        winResult = "tie";
                    }
                }
            }
        }
        return winResult;
    }

    private String intNumber(int i) {
        String[] strNumber = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
        return strNumber[i - 2];
    }
}
