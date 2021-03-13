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

        String[] type = {"StraightFlush", "FourOfAKind", "FullHouse", "Flush", "Straight", "ThreeOfAKind", "TwoPair", "OnePair", "HighCard"};
        int[] player1Number = player1Card.getCardNum();
        int[] player2Number = player2Card.getCardNum();
        int player1Index = player1Card.getTypeIndex();
        int player2Index = player2Card.getTypeIndex();
        int[] player1ArraySort = player1Card.sort();
        int[] player2ArraySort = player2Card.sort();
        int[] player1Repeat = noOrRepeatNumber(player1Number, 0);
        int[] player2Repeat = noOrRepeatNumber(player2Number, 0);
        int[] player1NoRepeat = noOrRepeatNumber(player1Number, 1);
        int[] player2NoRepeat = noOrRepeatNumber(player2Number, 1);

        if (player1Index < player2Index) {
            winResult = "player1 wins - " + type[player1Index];
        } else if (player1Index > player2Index) {
            winResult = "player2 wins - " + type[player2Index];
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

    private int[] noOrRepeatNumber(int[] number, int flag) {//先获得数组中每个元素出现的次数，然后再进行计算出现次数大于1的和出现次数等于1的
        Map<Integer, Integer> map = new HashMap<>();
        for (int j : number) {
            map.merge(j, 1, Integer::sum);
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((arg0, arg1) -> arg1.getValue().compareTo(arg0.getValue()));
        int[] repeatnumber = new int[list.size()];
        int[] norepeatnumber = new int[list.size()];
        int i = 0;
        if (flag == 0) {
            for (Map.Entry<Integer, Integer> entry : list) {
                if (entry.getValue() > 1) {
                    repeatnumber[i] = entry.getKey();
                    i++;
                }
            }
        } else {
            for (Map.Entry<Integer, Integer> entry : list) {
                if (entry.getValue() == 1) {
                    norepeatnumber[i] = entry.getKey();
                    i++;
                }
            }
        }
        HashSet<Integer> hashSet = new HashSet<>();
        if (flag == 0) {
            for (i = 0; i < repeatnumber.length; i++) {
                hashSet.add(repeatnumber[i]);
            }
        } else {
            for (i = 0; i < norepeatnumber.length; i++) {
                hashSet.add(norepeatnumber[i]);
            }
        }
        hashSet.remove(0);
        int[] result = new int[hashSet.size()];
        i = 0;
        for (Integer integer : hashSet) {
            result[i] = integer;
            i++;
        }
        int[] reResult = new int[result.length];
        for (i = 0; i < result.length; i++) {
            reResult[i] = result[result.length - i - 1];
        }
        return reResult;
    }
}
