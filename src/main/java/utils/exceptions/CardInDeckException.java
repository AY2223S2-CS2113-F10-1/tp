package utils.exceptions;

public class CardInDeckException extends InkaException{
    public CardInDeckException() {
        super("Card is already a part of the deck");
    }
}
