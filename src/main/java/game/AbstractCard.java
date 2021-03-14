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
        
        if (isDiff(card, 5)) {
            if (isStraight(card) && isFlush(card)) {
                return new StraightFlush(card, name, "StraightFlush", cardNumList);
            } else if (isStraight(card)) {
                return new Straight(card, name, "Straight", cardNumList);
            } else if (isFlush(card)) {
                return new Flush(card, name, "Flush", cardNumList);
            } else {
                return new HighCard(card, name, "HighCard", cardNumList);
            }
        } else if (isDiff(card, 4)) {
            return new OnePair(card, name, "OnePair", cardNumList);
        } else if (isDiff(card, 3)) {
            if (maxCardIs(card, 2)) {
                return new TwoPair(card, name, "TwoPair", cardNumList);
            } else {
                return new ThreeOfAKind(card, name, "ThreeOfAKind", cardNumList);
            }
        } else {
            if (maxCardIs(card, 4)) {
                return new FourOfAKind(card, name, "FourOfAKind", cardNumList);
            } else {
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

    private static int[] strNumber(String str) {
        return Arrays.stream(str.split(" "))
                .map(card -> card.substring(0, 1))
                .map(AbstractCard::map2Int)
                .sorted(Comparator.reverseOrder())
                .mapToInt(a -> a)
                .toArray();
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

    public int[] noOrRepeatNumber(int flag) {
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
