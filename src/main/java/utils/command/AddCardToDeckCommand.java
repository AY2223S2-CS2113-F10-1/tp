package utils.command;

import java.util.UUID;
import model.Card;
import model.CardList;
import model.CardUUID;
import model.Deck;
import model.DeckList;
import model.DeckUUID;
import model.TagList;
import utils.UserInterface;
import utils.exceptions.CardInDeckException;
import utils.exceptions.InkaException;
import utils.exceptions.UUIDWrongFormatException;
import utils.storage.IDataStorage;

public class AddCardToDeckCommand extends Command {

    private String deckName;
    private DeckUUID deckUUID;
    private CardUUID cardUUID;

    public AddCardToDeckCommand(String deckName, String cardUUID) throws InkaException {
        this.deckName = deckName;
        try {
            this.cardUUID = new CardUUID(UUID.fromString(cardUUID));
        } catch (IllegalArgumentException e) {
            throw new UUIDWrongFormatException();
        }
    }

    private void addCardToDeck(DeckList deckList, CardList cardList, UserInterface ui) throws InkaException {
        //find the corresponding Deck and Card based on its deckName and card uuid
        Deck deckToAdd = deckList.findDeckFromName(deckName);
        Card cardToAdd = cardList.findCardFromUUID(cardUUID);
        assert cardToAdd != null;

        if (deckToAdd == null) {
            ui.printDeckCreationSuccess();
            deckToAdd = new Deck(deckName, cardUUID);
            deckList.addDeck(deckToAdd);
        } else if(deckToAdd.cardIsInDeck(cardUUID)) {
            throw new CardInDeckException();
        } else {
            deckToAdd.addCard(cardUUID);
        }

        //add the tag uuid to the card
        deckUUID = deckToAdd.getDeckUUID();
        cardToAdd.addDeck(deckUUID);
    }

    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList,UserInterface ui, IDataStorage storage)
            throws InkaException {
        addCardToDeck(deckList, cardList, ui);
        ui.printAddCardToDeckSuccess(cardUUID, deckUUID);
    }

}
