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
import utils.exceptions.DeckNotFoundException;
import utils.exceptions.InkaException;
import utils.storage.IDataStorage;

public class DeleteDeckCommand extends Command{
    private String deckName;
    private DeckUUID deckUUID;
    private TagUUID tagUUID;

    public DeleteDeckCommand(String deckName) {
        this.deckName = deckName;
    }

    private void removeDeckFromCards(CardList cardList ,DeckList deckList, UserInterface userInterface)
            throws InkaException {
        Deck deck = deckList.findDeckFromName(deckName);
        if(deck==null) {
            throw new DeckNotFoundException();
        }
        deckUUID = deck.getDeckUUID();
        for (CardUUID cardUUID : deck.getCardsUUID()) {
            Card affectedCard = cardList.findCardFromUUID(cardUUID);
            affectedCard.removeDecks(deckUUID);
            userInterface.printRemoveDeckFromCard(affectedCard.getUuid(), deckUUID);
        }
    }

    private void removeDeckFromTags(TagList tagList ,DeckList deckList, UserInterface userInterface)
            throws InkaException {
        Deck deck = deckList.findDeckFromName(deckName);
        if(deck==null) {
            throw new DeckNotFoundException();
        }
        deckUUID = deck.getDeckUUID();
        for (TagUUID tgUUID : deck.getTagsUUID()) {
            Tag affectedTag = tagList.findTagFromUUID(tgUUID);
            affectedTag.removeDecks(deckUUID);
            userInterface.printRemoveDeckFromTag(affectedTag.getUUID(), deckUUID);
        }
    }
    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList, UserInterface ui, IDataStorage storage)
            throws InkaException {
        removeDeckFromCards(cardList, deckList, ui);
        removeDeckFromTags(tagList, deckList, ui);
        ui.printRemoveDeckFromDeckList(deckUUID);
    }
}
