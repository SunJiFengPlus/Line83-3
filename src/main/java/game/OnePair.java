package game;

/**
 * @author 孙继峰
 * @since 2021/3/13
 */
public class OnePair extends AbstractCard {
    public OnePair(String cardList, String playerName, String type, int[] cardNumList) {
        super(cardList, playerName, type, cardNumList);
    }

    @Override
    public String doCompareTo(AbstractCard anotherCard) {
        String winResult = "";
        if (this.noOrRepeatNumber(0)[0] < anotherCard.noOrRepeatNumber(0)[0]) {
            String sig = map2Card(anotherCard.noOrRepeatNumber(0)[0]);
            winResult = anotherCard.getPlayerName() + " wins - high card:" + sig;
        } else if (this.noOrRepeatNumber(0)[0] > anotherCard.noOrRepeatNumber(0)[0]) {
            String sig = map2Card(this.noOrRepeatNumber(0)[0]);
            winResult = this.getPlayerName() + " wins - high card:" + sig;
        } else {
            for (int i = 0; i < 3; i++) {
                if (this.noOrRepeatNumber(1)[i] < anotherCard.noOrRepeatNumber(1)[i]) {
                    String sig = map2Card(anotherCard.noOrRepeatNumber(1)[i]);
                    winResult = anotherCard.getPlayerName() + " wins - high card:" + sig;
                    break;
                } else if (this.noOrRepeatNumber(1)[i] > anotherCard.noOrRepeatNumber(1)[i]) {
                    String sig = map2Card(this.noOrRepeatNumber(1)[i]);
                    winResult = this.getPlayerName() + " wins - high card:" + sig;
                    break;
                } else {
                    winResult = "tie";
                }
            }
        }
        return winResult;
    }
}
