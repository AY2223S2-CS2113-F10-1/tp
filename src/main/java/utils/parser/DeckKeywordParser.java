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
    public static final String DELETE_ACTION = "delete";
    public static final String EDIT_ACTION = "edit";
    public static final String HELP_ACTION = "help";
    public static final String LIST_ACTION = "list";
    private DefaultParser parser;
    public DeckKeywordParser() {
        this.parser = new DefaultParser(false);
    }

    private static Options buildDeleteOptions() {
        Options options = new Options();
        options.addRequiredOption("d", "deck", true, "deck name");
        options.addOption("c", "card", true, "card name (optional)");
        options.addOption("t", "tag", true, "tag name (optional)");

        return options;
    }
    private static Options buildEditOptions() {
        Options options = new Options();
        options.addRequiredOption("o", "old", true, "Old deck name");
        options.addRequiredOption("n", "new", true, "New deck name");

        return options;
    }
    private static Options buildListOptions() {
        Options options = new Options();
        options.addOption("c", "cards", true, "deck name to list cards from (optional)");
        options.addOption("t", "tags", true, "deck name to list tags from (optional)");
        return options;
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
        CommandLine cmd = parser.parse(buildDeleteOptions(), tokens.toArray(new String[0]));

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
        CommandLine cmd = parser.parse(buildListOptions(), tokens.toArray(new String[0]));

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
        CommandLine cmd = parser.parse(buildEditOptions(), tokens.toArray(new String[0]));

        String oldDeckName = cmd.getOptionValue("o");
        String newDeckName = cmd.getOptionValue("n");
        return new EditDeckNameCommand(oldDeckName, newDeckName);
    }

    private Command handleHelp() {
        // Combine all actions
        String[] actionList = {EDIT_ACTION, DELETE_ACTION, LIST_ACTION};
        String[] headerList = {"Edit existing decks", "Delete decks", "List decks"};
        Options[] optionsList = {buildEditOptions(), buildDeleteOptions(), buildListOptions()};

        String helpMessage = formatHelpMessage("deck", actionList, headerList, optionsList);
        return new PrintHelpCommand(helpMessage);
    }
}
