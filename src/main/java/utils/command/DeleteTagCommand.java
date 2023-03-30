package utils.command;

import model.Card;
import model.CardList;
import model.CardUUID;
import model.Deck;
import model.DeckList;
import model.DeckUUID;
import model.Tag;
import model.TagList;
import model.TagUUID;
import utils.UserInterface;
import utils.exceptions.InkaException;
import utils.exceptions.TagNotFoundException;
import utils.storage.IDataStorage;

public class DeleteTagCommand extends Command {
    private String tagName;
    private TagUUID tagUUID;
    private Tag tag;

    public DeleteTagCommand(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Remove the tagName tag from the tagList and all the cards that currently have the tagName tag.
     *
     * @param cardList The cardList from which to look for the cards with tagName tag.
     * @param tagList  The tagList from which to delete the tagName tag.
     * @param ui       The userInterface to print the success of removal of the tag from the cards.
     */
    private void removeTagFromCards(CardList cardList, TagList tagList, UserInterface ui) throws InkaException {
        this.tag = tagList.findTagFromName(tagName);
        if (tag == null) {
            throw new TagNotFoundException();
        }
        this.tagUUID = tag.getUUID();

        //for each card whose uuid is listed under the tag, remove the tag uuid from that card
        if(!tag.cardEmpty()) {
            for (CardUUID cardUUID : tag.getCardsUUID()) {
                Card affectedCard = cardList.findCardFromUUID(cardUUID);
                affectedCard.removeTag(tagUUID);
                ui.printRemoveTagFromCard(affectedCard.getUuid(), tagUUID);
            }
        }

    }

    private void removeTagsFromDecks(DeckList deckList) {
        for(DeckUUID deckUUID: this.tag.getDecks()) {
            Deck deckToDeleteTagFrom = deckList.findDeckFromUUID(deckUUID);
            deckToDeleteTagFrom.getTagsUUID().remove(tag.getUUID());
        }
    }

    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList, UserInterface ui, IDataStorage storage)
            throws InkaException {
        removeTagFromCards(cardList, tagList, ui);
        removeTagsFromDecks(deckList);
        tagList.deleteTagByUUID(this.tagUUID);
        ui.printRemoveTagFromTagList(tagUUID);
    }
}
