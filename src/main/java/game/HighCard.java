package game;

/**
 * @author 孙继峰
 * @since 2021/3/13
 */
public class HighCard extends AbstractCard {
    public HighCard(String card, String name, String highCard, int[] cardNumList) {
        super(card, name, highCard, cardNumList);
    }

    @Override
    public String doCompareTo(AbstractCard anotherCard) {
        for (int i = 0; i < 5; i++) {
            if (this.getCardNum()[i] < anotherCard.getCardNum()[i]) {
                String sig = map2Card(anotherCard.getCardNum()[i]);
                return anotherCard.getPlayerName() + " wins - high card:" + sig;
            } else if (this.getCardNum()[i] > anotherCard.getCardNum()[i]) {
                String sig = map2Card(this.getCardNum()[i]);
                return this.getPlayerName() + " wins - high card:" + sig;
            }
        }
        return "tie";
    }
}
