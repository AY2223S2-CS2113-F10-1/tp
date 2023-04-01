package utils.command;

import model.Card;
import model.CardList;
import model.CardSelector;
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
    private CardSelector cardSelector;

    public AddCardToTagCommand(String tagName, CardSelector cardSelector) throws InkaException {
        this.tagName = tagName;
        try {
            this.cardSelector = cardSelector;
        } catch (IllegalArgumentException e) {
            throw new UUIDWrongFormatException();
        }
    }

    /**
     * Adds the tagName tag to the corresponding card, creates the tag beforehand if it currently does not exist.
     *
     * @param tagList   The tagList into which to add the tag that has been created
     * @param cardToAdd The card to add to tag
     * @param ui        The userInterface to print the success of the tag creation
     */
    private void addCardToTag(TagList tagList, Card cardToAdd, UserInterface ui) throws CardInTagException {
        //find the corresponding Tag and Card based on its tagName and card uuid
        Tag tagToAdd = tagList.findTagFromName(tagName);

        if (tagToAdd == null) {
            ui.printTagCreationSuccess(tagName);
            tagToAdd = new Tag(tagName, cardToAdd.getUuid());
            tagList.addTag(tagToAdd);
        } else if (tagToAdd.cardIsInTag(cardToAdd.getUuid())) {
            throw new CardInTagException();
        } else {
            tagToAdd.addCard(cardToAdd.getUuid());
        }

        //add the tag uuid to the card
        tagUUID = tagToAdd.getUUID();

        cardToAdd.addTag(tagUUID);
    }

    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList, UserInterface ui, IDataStorage storage)
            throws InkaException {
        Card cardToAdd = cardList.findCard(cardSelector);
        assert cardToAdd != null;

        addCardToTag(tagList, cardToAdd, ui);
        ui.printAddTagToCardSuccess(cardToAdd.getUuid(), tagUUID);
    }
}