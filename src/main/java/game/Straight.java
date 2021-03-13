package game;

/**
 * @author 孙继峰
 * @since 2021/3/13
 */
public class Straight extends AbstractCard {
    public Straight(String cardList, String playerName, String type, int[] cardNumList) {
        super(cardList, playerName, type, cardNumList);
    }

    @Override
    public String doCompareTo(AbstractCard anotherCard) {
        if (this.getCardNum()[0] < anotherCard.getCardNum()[0]) {
            String sig = map2Card(anotherCard.getCardNum()[0]);
            return anotherCard.getPlayerName() +WINS_HIGH_CARD + sig;
        } else if (this.getCardNum()[0] > anotherCard.getCardNum()[0]) {
            String sig = map2Card(this.getCardNum()[0]);
            return this.getPlayerName() + WINS_HIGH_CARD + sig;
        } else {
            return  "tie";
        }
    }
}
