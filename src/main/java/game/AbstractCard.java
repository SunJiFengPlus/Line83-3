package game;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author 孙继峰
 * @since 2021/3/13
 */
@Data
@AllArgsConstructor
public abstract class AbstractCard {
    private String playerName;
    private String cardList;
    private String type;

    public static AbstractCard of(String card, String name) {
        String judgeType = judgeType(card);
        switch (judgeType) {
            case "StraightFlush":
                return new StraightFlush(card, name, "StraightFlush");
            case "Straight":
                return new Straight(card, name, "Straight");
            case "Flush":
                return new Flush(card, name, "Flush");
            case "HighCard":
                return new HighCard(card, name, "HighCard");
            case "OnePair":
                return new OnePair(card, name, "OnePair");
            case "TwoPair":
                return new TwoPair(card, name, "TwoPair");
            case "ThreeOfAKind":
                return new ThreeOfAKind(card, name, "ThreeOfAKind");
            case "FourOfAKind":
                return new FourOfAKind(card, name, "FourOfAKind");
            case "FullHouse":
                return new FullHouse(card, name, "FullHouse");
        }
        throw new IllegalArgumentException(card);
    }

    private static String judgeType(String str) {//判断是什么牌
        String type;
        String[] strArray = str.split("");
        int[] number = strNumber(str);
        int i;
        String[] color = new String[5];
        for (i = 0; i < 5; i++) {
            color[i] = strArray[i * 3 + 1];
        }
        HashSet<Integer> hashSetNumber = new HashSet<>();
        for (i = 0; i < 5; i++) {
            hashSetNumber.add(number[i]);
        }
        HashSet<String> hashSetType = new HashSet<>();
        for (i = 0; i < 5; i++) {
            hashSetType.add(color[i]);
        }
        if (hashSetNumber.size() == 5) {
            if ((number[0] - number[4] == 4) && (hashSetType.size() == 1)) {//五个相邻的数字且花色一样——同花顺
                type = "StraightFlush";
            } else if (number[0] - number[4] == 4 ) {//五个相邻数字——顺子
                type = "Straight";
            } else if (hashSetType.size() == 1) {//同一花色——同花
                type = "Flush";
            } else {//五个不相邻的数字——散牌
                type = "HighCard";
            }
        } else if (hashSetNumber.size() == 4) {//一对相同，其余三个数字不同——对子
            type = "OnePair";
        } else if (hashSetNumber.size() == 3) {
            if ((number[0] == number[1] && number[2] == number[3]) || (number[1] == number[2] && number[3] == number[4]) || (number[0] == number[1] && number[3] == number[4])) {//两对
                type = "TwoPair";
            } else {//三个数字相同，另外两个数字不同——三条
                type = "ThreeOfAKind";
            }
        } else {
            if (number[0] != number[1] || number[3] != number[4]) {//四个数字相同——铁支
                type = "FourOfAKind";
            } else {//三个数字相同，另外两个数字相同——葫芦
                type = "FullHouse";
            }
        }
        return type;
    }

    private static int[] strNumber(String str) {//数字转化并将其从大到小排序
        int[] number = new int[5];
        String[] strArray = str.split("");
        int i;
        for (i = 0; i < 5; i++) {
            String c = strArray[i * 3];
            switch (c) {
                case "T":
                    number[i] = 10;
                    break;
                case "J":
                    number[i] = 11;
                    break;
                case "Q":
                    number[i] = 12;
                    break;
                case "K":
                    number[i] = 13;
                    break;
                case "A":
                    number[i] = 14;
                    break;
                default:
                    number[i] = Integer.parseInt(c);
                    break;
            }
        }

        Arrays.sort(number);
        int[] renumber = new int[number.length];
        for (i = 0; i < number.length; i++) {
            renumber[i] = number[number.length - i - 1];
        }
        return renumber;
    }
}
