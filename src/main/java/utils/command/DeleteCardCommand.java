package utils.command;

import model.Card;
import model.CardList;
import model.Deck;
import model.DeckList;
import model.DeckUUID;
import model.Tag;
import model.TagList;
import model.TagUUID;
import utils.UserInterface;
import utils.exceptions.UnknownItem;
import utils.exceptions.InkaException;
import utils.storage.IDataStorage;

public class DeleteCardCommand extends Command {

    private int index;
    private Card card;

    public DeleteCardCommand(int index) {
        this.index = index;
    }

    //Assume this is silent removal, no UI message is displayed
    public void cardRemovalFromDecks (CardList cardList, DeckList deckList)  {
        this.card = cardList.get(this.index-1);
        for(DeckUUID deckUUID: card.getDecksUUID()) {
            Deck deckToDeleteCardFrom = deckList.findDeckFromUUID(deckUUID);
            deckToDeleteCardFrom.getCardsUUID().remove(card.getUuid());
        }
    }
    public void cardRemovalFromTag(TagList tagList) {
        for(TagUUID tagUUID: card.getTagsUUID()) {
            Tag tagToDeleteCardFrom = tagList.findTagFromUUID(tagUUID);
            tagToDeleteCardFrom.getCardsUUID().remove(card.getUuid());
        }
    }

    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList,UserInterface ui, IDataStorage storage)
            throws InkaException {
        try {
            cardRemovalFromDecks(cardList, deckList);
            cardRemovalFromTag(tagList);
            cardList.delete(this.index - 1);
            ui.printDeleteSuccess();
            ui.printNumOfQuestions(cardList);
        } catch (IndexOutOfBoundsException  | NullPointerException e ) {
            throw new UnknownItem();
        }
    }
}
