package utils.command;

import java.util.ArrayList;
import java.util.UUID;
import model.CardList;
import model.CardUUID;
import model.Deck;
import model.DeckList;
import model.TagList;
import utils.UserInterface;
import utils.exceptions.CardNeverWasInDeck;
import utils.exceptions.DeckNotFoundException;
import utils.exceptions.InkaException;
import utils.exceptions.UUIDWrongFormatException;
import utils.storage.IDataStorage;

public class RemoveCardFromDeckCommand extends Command{
    private CardUUID cardUUID;

    private String deckName;

    public RemoveCardFromDeckCommand(String cardUUID, String deckName) throws InkaException {
        this.deckName = deckName;
        //this.tagName = tagName;
        try {
            this.cardUUID = new CardUUID(UUID.fromString(cardUUID));
        } catch (IllegalArgumentException e) {
            throw new UUIDWrongFormatException();
        }
    }

    public void removeCardFromDeck(DeckList deckList,String deckName, CardUUID cardUUID)
            throws InkaException {
        Deck deck = deckList.findDeckFromName(deckName);
        if(deck==null) {
            throw new DeckNotFoundException();
        }
        ArrayList<CardUUID> deckCardList = deck.getCardsUUID();
        boolean wasCardInDeck = deckCardList.removeIf(card -> card.equals(cardUUID));
        if(wasCardInDeck==false) {
            throw new CardNeverWasInDeck();
        }
        deck.setCards(deckCardList);
    }
    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList, UserInterface ui, IDataStorage storage)
            throws InkaException {
        removeCardFromDeck(deckList, deckName, cardUUID);
        ui.printRemoveCardFromDeckSuccess(cardUUID, deckName);
    }

}
