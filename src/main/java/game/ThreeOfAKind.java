package game;

/**
 * @author 孙继峰
 * @since 2021/3/13
 */
public class ThreeOfAKind extends AbstractCard {
    public ThreeOfAKind(String cardList, String playerName , String type, int[] cardNumList) {
        super(cardList, playerName, type, cardNumList);
    }

    @Override
    public String doCompareTo(AbstractCard anotherCard) {
        if (this.noOrRepeatNumber(0)[0] < anotherCard.noOrRepeatNumber(0)[0]) {
            String sig = map2Card(anotherCard.noOrRepeatNumber(0)[0]);
            return anotherCard.getPlayerName() + WINS_HIGH_CARD + sig;
        } else {
            String sig = map2Card(this.noOrRepeatNumber(0)[0]);
            return this.getPlayerName() + WINS_HIGH_CARD + sig;
        }
    }
}
