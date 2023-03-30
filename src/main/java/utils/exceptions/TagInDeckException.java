package utils.exceptions;

public class TagInDeckException extends InkaException{
    public TagInDeckException() {
        super("Tag is already a part of the deck");
    }
}
