package utils.command;

import java.util.ArrayList;
import java.util.UUID;
import model.CardList;
import model.Deck;
import model.DeckList;
import model.TagList;
import model.TagUUID;
import utils.UserInterface;
import utils.exceptions.DeckNotFoundException;
import utils.exceptions.InkaException;
import utils.exceptions.TagNeverWasInDeck;
import utils.exceptions.UUIDWrongFormatException;
import utils.storage.IDataStorage;

public class RemoveTagFromDeckCommand extends Command {
    private TagUUID tagUUID;
    private String deckName;

    public RemoveTagFromDeckCommand(String tagUUID, String deckName) throws InkaException {
        this.deckName = deckName;
        //this.tagName = tagName;
        try {
            this.tagUUID = new TagUUID(UUID.fromString(tagUUID));
        } catch (IllegalArgumentException e) {
            throw new UUIDWrongFormatException();
        }
    }

    public void removeTagFromDeck(DeckList deckList,String deckName, TagUUID tagUUID)
            throws InkaException {
        Deck deck = deckList.findDeckFromName(deckName);
        if(deck==null) {
            throw new DeckNotFoundException();
        }
        ArrayList<TagUUID> deckTagList = deck.getTagsUUID();
        boolean wasTagInDeck = deckTagList.removeIf(tag -> tag.equals(tagUUID));
        if(wasTagInDeck==false) {
            throw new TagNeverWasInDeck();
        }
        deck.setTags(deckTagList);
    }
    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList, UserInterface ui, IDataStorage storage)
            throws InkaException {
        removeTagFromDeck(deckList, deckName, tagUUID);
        ui.printRemoveTagFromDeckSuccess(tagUUID, deckName);
    }
}
