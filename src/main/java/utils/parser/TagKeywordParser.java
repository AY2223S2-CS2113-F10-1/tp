package utils.parser;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import utils.command.AddTagToDeckCommand;
import utils.command.Command;
import utils.command.DeleteTagCommand;
import utils.command.EditTagNameCommand;
import utils.command.ListCardsUnderTagCommand;
import utils.command.ListTagsCommand;
import utils.command.PrintHelpCommand;
import utils.exceptions.InkaException;
import utils.exceptions.UnrecognizedCommandException;

public class TagKeywordParser extends KeywordParser {

    public static final String TAG_MODEL = "tag";
    public static final String DELETE_ACTION = "delete";
    public static final String EDIT_ACTION = "edit";
    public static final String HELP_ACTION = "help";
    public static final String LIST_ACTION = "list";
    public static final String DECK_ACTION = "deck";
    private DefaultParser parser;

    public TagKeywordParser() {
        this.parser = new DefaultParser(false);
    }

    @Override
    protected Command handleAction(String action, List<String> tokens)
            throws ParseException, InkaException {
        switch (action) {
            case DECK_ACTION:
                return handleDeck(tokens);
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

    private Command handleDelete(List<String> tokens) throws ParseException {
        Options deleteOption = new OptionsBuilder(TAG_MODEL, DELETE_ACTION).buildOptions();
        CommandLine cmd = parser.parse(deleteOption, tokens.toArray(new String[0]));

        String tagName = cmd.getOptionValue("t");
        return new DeleteTagCommand(tagName);
    }

    private Command handleEdit(List<String> tokens) throws ParseException {
        Options editOption = new OptionsBuilder(TAG_MODEL, EDIT_ACTION).buildOptions();
        CommandLine cmd = parser.parse(editOption, tokens.toArray(new String[0]));

        String oldTagName = cmd.getOptionValue("o");
        String newTagName = cmd.getOptionValue("n");
        return new EditTagNameCommand(oldTagName, newTagName);
    }

    private Command handleHelp() {
        Options deleteOption = new OptionsBuilder(TAG_MODEL, DELETE_ACTION).buildOptions();
        Options editOption = new OptionsBuilder(TAG_MODEL, EDIT_ACTION).buildOptions();
        Options listOption = new OptionsBuilder(TAG_MODEL, LIST_ACTION).buildOptions();

        // For adding the deck help message for tag later
        Options deckOption = new OptionsBuilder(TAG_MODEL, DECK_ACTION).buildOptions();

        // Combine all actions
        String[] actionList = {EDIT_ACTION, DELETE_ACTION, LIST_ACTION};
        String[] headerList = {"Edit existing tags", "Delete tags", "List tags"};
        Options[] optionsList = {editOption, deleteOption, listOption};

        String helpMessage = formatHelpMessage("tag", actionList, headerList, optionsList);
        return new PrintHelpCommand(helpMessage);
    }

    private Command handleList(List<String> tokens) throws ParseException {
        Options listOption = new OptionsBuilder(TAG_MODEL, LIST_ACTION).buildOptions();
        CommandLine cmd = parser.parse(listOption, tokens.toArray(new String[0]));

        if (cmd.hasOption("t")) {
            String tagName = cmd.getOptionValue("t");
            return new ListCardsUnderTagCommand(tagName);
        } else {
            return new ListTagsCommand();
        }
    }

    private Command handleDeck(List<String> tokens) throws ParseException, InkaException {
        Options deckOption = new OptionsBuilder(TAG_MODEL, DECK_ACTION).buildOptions();
        CommandLine cmd = parser.parse(deckOption, tokens.toArray(new String[0]));

        String tagUUID = cmd.getOptionValue("t");
        String deckName = cmd.getOptionValue("d");

        return new AddTagToDeckCommand(deckName, tagUUID);
    }
}
