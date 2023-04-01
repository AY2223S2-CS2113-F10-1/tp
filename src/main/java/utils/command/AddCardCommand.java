package utils.command;

import model.Card;
import model.CardList;
import model.DeckList;
import model.TagList;
import utils.UserInterface;
import utils.exceptions.InkaException;
import utils.storage.IDataStorage;

public class AddCardCommand extends Command {
    private final Card card;

    public AddCardCommand(Card card) {
        this.card = card;
    }

    @Override
    public void execute(CardList cardList, TagList tagList, DeckList deckList, UserInterface ui, IDataStorage storage)
            throws InkaException {
        cardList.addCard(card);
        ui.printAddQuestionSuccess();
        ui.printNumOfQuestions(cardList);
    }
}
