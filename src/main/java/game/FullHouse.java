package game;

/**
 * @author 孙继峰
 * @since 2021/3/13
 */
public class FullHouse extends AbstractCard {
    public FullHouse(String cardList, String playerName, String type, int[] cardNumList) {
        super(cardList, playerName, type, cardNumList);
    }

    @Override
    public String doCompareTo(AbstractCard anotherCard) {
        if (this.sort()[0] < anotherCard.sort()[0]) {
            String sig = map2Card(anotherCard.sort()[0]);
            return anotherCard.getPlayerName() + " wins - high card:" + sig;
        } else {
            String sig = map2Card(this.sort()[0]);
            return this.getPlayerName() + " wins - high card:" + sig;
        }
    }
}
