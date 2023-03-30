package utils.exceptions;

public class CardInTagException extends InkaException{
    public CardInTagException() {
        super("Card is already a part of the Tag");
    }
}
