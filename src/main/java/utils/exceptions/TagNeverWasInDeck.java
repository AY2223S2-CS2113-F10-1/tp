package utils.exceptions;

public class TagNeverWasInDeck extends InkaException{
    public TagNeverWasInDeck() {
        super("Tag was never in deck");
    }
}
