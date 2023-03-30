package utils.command;

import java.util.ArrayList;
import model.Card;
import model.CardList;
import model.CardUUID;
import model.Deck;
import model.DeckList;
import model.TagList;
import utils.UserInterface;
import utils.exceptions.DeckNotFoundException;
import utils.exceptions.InkaException;
import utils.storage.IDataStorage;

public class ListCardsUnderDeckCommand extends Command{

    private String deckName;

    public ListCardsUnderDeckCommand(String deckName) {
        this.deckName = deckName;
    }

    private CardList findCardsUnderDeck(CardList cardList, DeckList deckList) throws InkaException {
        Deck foundDeck = deckList.findDeckFromName(deckName);
        if(foundDeck==null) {
            throw new DeckNotFoundException();
        }
        ArrayList<CardUUID> cardsUUID = foundDeck.getCardsUUID();
        CardList foundCardList = new CardList();

        for (Card card : cardList.getCards()) {
            for (CardUUID cardUUID : cardsUUID) {
                if (cardUUID.equals(card.getUuid())) {
                    foundCardList.addCard(card);
                }
            }
        }
        return foundCardList;
    }

    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList, UserInterface ui, IDataStorage storage)
            throws InkaException {
        CardList foundCardList = findCardsUnderDeck(cardList, deckList);
        ui.printCardList(foundCardList);
    }
}
