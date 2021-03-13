package game;

import com.google.common.collect.HashMultiset;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 孙继峰
 * @since 2021/3/13
 */
@Data
public abstract class AbstractCard {
    private String cardList;
    private String playerName;
    private String type;
    private int[] cardNum;
    public AbstractCard(String cardList, String playerName, String type, int[] cardNum) {
        this.cardList = cardList;
        this.playerName = playerName;
        this.type = type;
        this.cardNum = cardNum;
    }

    /**
     * 卡牌点数, 从小到大
     */
    private static List<String> compareNumList = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A");
    public static final String WINS_HIGH_CARD = " wins - high card:";

    public static AbstractCard of(String card, String name) {
        int[] cardNumList = strNumber(card);
        //判断是什么牌
        if (isDiff(card, 5)) {
            if (isStraight(card) && isFlush(card)) {//五个相邻的数字且花色一样——同花顺
                return new StraightFlush(card, name, "StraightFlush", cardNumList);
            } else if (isStraight(card)) {//五个相邻数字——顺子
                return new Straight(card, name, "Straight", cardNumList);
            } else if (isFlush(card)) {//同一花色——同花
                return new Flush(card, name, "Flush", cardNumList);
            } else {//五个不相邻的数字——散牌
                return new HighCard(card, name, "HighCard", cardNumList);
            }
        } else if (isDiff(card, 4)) {//一对相同，其余三个数字不同——对子
            return new OnePair(card, name, "OnePair", cardNumList);
        } else if (isDiff(card, 3)) {
            if (maxCardIs(card, 2)) {//两对
                return new TwoPair(card, name, "TwoPair", cardNumList);
            } else {//三个数字相同，另外两个数字不同——三条
                return new ThreeOfAKind(card, name, "ThreeOfAKind", cardNumList);
            }
        } else {
            if (maxCardIs(card, 4)) {//四个数字相同——铁支
                return new FourOfAKind(card, name, "FourOfAKind", cardNumList);
            } else {//三个数字相同，另外两个数字相同——葫芦
                return new FullHouse(card, name, "FullHouse", cardNumList);
            }
        }
    }

    /**
     * 相同大小牌最多的张数是 n
     */
    private static boolean maxCardIs(String cardList, int i) {
        LinkedList<String> numberList = Arrays.stream(cardList.split(" "))
                .map(oneCard -> oneCard.substring(0, 1))
                .sorted(Comparator.comparingInt(a -> compareNumList.indexOf(a)))
                .collect(Collectors.toCollection(LinkedList::new));
        HashMultiset<String> cardCounter = numberList.stream().collect(Collectors.toCollection(HashMultiset::create));

        return numberList.stream().mapToInt(cardCounter::count).max().orElse(0) == i;
    }

    /**
     * 有几张不同的牌
     */
    private static boolean isDiff(String card, int size) {
        Set<String> numberSet = Arrays.stream(card.split(" "))
                .map(oneCard -> oneCard.substring(0, 1))
                .collect(Collectors.toSet());
        return numberSet.size() == size;
    }

    /**
     * 同花的
     */
    private static boolean isFlush(String cardList) {
        Set<String> colorSet = Arrays.stream(cardList.split(" "))
                .map(oneCard -> oneCard.substring(1))
                .collect(Collectors.toSet());
        return colorSet.size() == 1;
    }

    /**
     * 连续的
     */
    private static boolean isStraight(String cardList) {
        LinkedList<String> numberList = Arrays.stream(cardList.split(" "))
                .map(oneCard -> oneCard.substring(0, 1))
                .sorted(Comparator.comparingInt(a -> compareNumList.indexOf(a)))
                .collect(Collectors.toCollection(LinkedList::new));
        return map2Int(numberList.getLast()) - map2Int(numberList.getFirst()) == 4;
    }

    private static int map2Int(String card) {
        return compareNumList.indexOf(card) + 2;
    }

    protected String map2Card(int i) {
        return compareNumList.get(i - 2);
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

    public int getTypeIndex() {
        int index = -1;
        String[] type = {"StraightFlush", "FourOfAKind", "FullHouse", "Flush", "Straight", "ThreeOfAKind", "TwoPair", "OnePair", "HighCard"};
        for (int i = 0; i < 9; i++) {
            if (type[i].equals(this.type)) {
                index = i;
            }
        }
        return index;
    }

    public int[] sort() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int j : cardNum) {
            map.merge(j, 1, Integer::sum);
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((arg0, arg1) -> arg1.getValue().compareTo(arg0.getValue()));
        int[] arrayresult = new int[list.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : list) {
            arrayresult[i] = entry.getKey();
            i++;
        }
        return arrayresult;
    }

    public int[] noOrRepeatNumber(int flag) {//先获得数组中每个元素出现的次数，然后再进行计算出现次数大于1的和出现次数等于1的
        Map<Integer, Integer> map = new HashMap<>();
        for (int j : cardNum) {
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

    public String compareTo(AbstractCard anotherCard) {
        if (this.getTypeIndex() < anotherCard.getTypeIndex()) {
            return this.getPlayerName() + " wins - " + this.getType();
        } else if (this.getTypeIndex() > anotherCard.getTypeIndex()) {
            return anotherCard.getPlayerName() + " wins - " + anotherCard.getType();
        } else {
            return doCompareTo(anotherCard);
        }
    }

    public abstract String doCompareTo(AbstractCard anotherCard);

}
