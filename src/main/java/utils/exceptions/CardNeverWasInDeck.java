package utils.exceptions;

public class CardNeverWasInDeck extends InkaException {
    public CardNeverWasInDeck() {
        super("The card was never in the deck");
    }
}
