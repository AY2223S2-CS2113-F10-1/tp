package utils.parser;

import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import utils.command.Command;
import utils.command.DeleteDeckCommand;
import utils.command.EditDeckNameCommand;
import utils.command.ListCardsUnderDeckCommand;
import utils.command.ListDecksCommand;
import utils.command.ListTagsUnderDeckCommand;
import utils.command.PrintHelpCommand;
import utils.command.RemoveCardFromDeckCommand;
import utils.command.RemoveTagFromDeckCommand;
import utils.exceptions.InkaException;
import utils.exceptions.UnrecognizedCommandException;

public class DeckKeywordParser extends KeywordParser{

    public static final String DECK_MODEL = "deck";
    public static final String DELETE_ACTION = "delete";
    public static final String EDIT_ACTION = "edit";
    public static final String HELP_ACTION = "help";
    public static final String LIST_ACTION = "list";
    private DefaultParser parser;
    public DeckKeywordParser() {
        this.parser = new DefaultParser(false);
    }

    @Override
    protected Command handleAction(String action, List<String> tokens)
            throws ParseException, UnrecognizedCommandException, InkaException {
        switch (action) {
        case DELETE_ACTION:
            return handleDelete(tokens);
        case EDIT_ACTION:
            return handleEdit(tokens);
        case HELP_ACTION:
            return handleHelp();
        case LIST_ACTION:
            return handleList(tokens);
        default:
            throw new UnrecognizedCommandException();
        }
    }

    private Command handleDelete(List<String> tokens) throws ParseException, InkaException {
        Options deleteOptions =  new OptionsBuilder(DECK_MODEL, DELETE_ACTION).buildOptions();
        CommandLine cmd = parser.parse(deleteOptions, tokens.toArray(new String[0]));

        String deckName = cmd.getOptionValue("d");
        if(cmd.hasOption("c")) {
            return new RemoveCardFromDeckCommand(cmd.getOptionValue("c"), deckName);
        } else if (cmd.hasOption("t")) {
            return new RemoveTagFromDeckCommand(cmd.getOptionValue("t"), deckName);
        } else {
            return new DeleteDeckCommand(deckName);
        }
    }

    private Command handleList(List<String> tokens) throws ParseException {
        Options listOptions =  new OptionsBuilder(DECK_MODEL, LIST_ACTION).buildOptions();
        CommandLine cmd = parser.parse(listOptions, tokens.toArray(new String[0]));

        if (cmd.hasOption("c")) {
            String deckName = cmd.getOptionValue("c");
            return new ListCardsUnderDeckCommand(deckName);
        } else if(cmd.hasOption("t")) {
            String deckName = cmd.getOptionValue("t");
            return new ListTagsUnderDeckCommand(deckName);
        } else {
            return new ListDecksCommand();
        }
    }
    private Command handleEdit(List<String> tokens) throws ParseException {
        Options editOptions =  new OptionsBuilder(DECK_MODEL, EDIT_ACTION).buildOptions();
        CommandLine cmd = parser.parse(editOptions, tokens.toArray(new String[0]));

        String oldDeckName = cmd.getOptionValue("o");
        String newDeckName = cmd.getOptionValue("n");
        return new EditDeckNameCommand(oldDeckName, newDeckName);
    }

    private Command handleHelp() {
        Options editOptions =  new OptionsBuilder(DECK_MODEL, EDIT_ACTION).buildOptions();
        Options deleteOptions =  new OptionsBuilder(DECK_MODEL, DELETE_ACTION).buildOptions();
        Options listOptions =  new OptionsBuilder(DECK_MODEL, LIST_ACTION).buildOptions();
        // Combine all actions
        String[] actionList = {EDIT_ACTION, DELETE_ACTION, LIST_ACTION};
        String[] headerList = {"Edit existing decks", "Delete decks", "List decks"};
        Options[] optionsList = {editOptions, deleteOptions, listOptions};

        String helpMessage = formatHelpMessage("deck", actionList, headerList, optionsList);
        return new PrintHelpCommand(helpMessage);
    }
}
