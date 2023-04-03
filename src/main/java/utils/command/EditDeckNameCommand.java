package utils.command;

import model.CardList;
import model.DeckList;
import model.TagList;
import utils.UserInterface;
import utils.exceptions.DeckNotFoundException;
import utils.exceptions.InkaException;
import utils.storage.IDataStorage;
import model.Deck;

public class EditDeckNameCommand extends Command{
    private String oldDeckName;
    private String newDeckName;

    public EditDeckNameCommand(String oldDeckName, String newDeckName) {
        this.oldDeckName = oldDeckName;
        this.newDeckName = newDeckName;
    }

    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList,UserInterface ui, IDataStorage storage)
            throws InkaException {
        Deck deck = deckList.findDeckFromName(oldDeckName);
        if (deck == null) {
            throw new DeckNotFoundException();
        }

        deck.editDeckName(newDeckName);
        ui.printEditDeckNameSuccess(oldDeckName, deck);
    }
}
