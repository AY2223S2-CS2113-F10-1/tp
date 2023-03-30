package utils.command;

import java.util.UUID;
import model.Card;
import model.CardList;
import model.CardUUID;
import model.DeckList;
import model.Tag;
import model.TagList;
import model.TagUUID;
import utils.UserInterface;
import utils.exceptions.CardInTagException;
import utils.exceptions.InkaException;
import utils.exceptions.UUIDWrongFormatException;
import utils.storage.IDataStorage;

public class AddCardToTagCommand extends Command {
    private String tagName;
    private TagUUID tagUUID;
    private CardUUID cardUUID;

    public AddCardToTagCommand(String tagName, String cardUUID) throws InkaException {
        this.tagName = tagName;
        try {
            this.cardUUID = new CardUUID(UUID.fromString(cardUUID));
        } catch (IllegalArgumentException e) {
            throw new UUIDWrongFormatException();
        }
    }

    /**
     * Adds the tagName tag to the Card with cardUUID, creates the tag beforehand if it currently does not exist.
     *
     * @param tagList  The tagList into which to add the tag that has been created
     * @param cardList The cardList from which to find the card with cardUUID
     * @param ui       The userInterface to print the success of the tag creation
     * @throws InkaException
     */
    private void addCardToTag(TagList tagList, CardList cardList, UserInterface ui) throws InkaException {
        //find the corresponding Tag and Card based on its tagName and card uuid
        Tag tagToAdd = tagList.findTagFromName(tagName);
        Card cardToAdd = cardList.findCardFromUUID(cardUUID);
        assert cardToAdd != null;

        if (tagToAdd == null) {
            ui.printTagCreationSuccess(tagName);
            tagToAdd = new Tag(tagName, cardUUID);
            tagList.addTag(tagToAdd);
        } else if(tagToAdd.cardIsInTag(cardUUID)) {
            throw new CardInTagException();
        } else {
            tagToAdd.addCard(cardUUID);
        }

        //add the tag uuid to the card
        tagUUID = tagToAdd.getUUID();
        
        cardToAdd.addTag(tagUUID);
    }

    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList, UserInterface ui, IDataStorage storage)
            throws InkaException {
        addCardToTag(tagList, cardList, ui);
        ui.printAddTagToCardSuccess(cardUUID, tagUUID);
    }
}
