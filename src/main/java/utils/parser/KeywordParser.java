package utils.parser;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.CardSelector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import utils.command.Command;
import utils.exceptions.InkaException;
import utils.exceptions.InvalidSyntaxException;
import utils.exceptions.UnrecognizedCommandException;

/**
 * Abstract class for parsing keyword-specific commands
 */
public abstract class KeywordParser {
    protected static final String FLAG_CARD_UUID = "c";
    protected static final String FLAG_LONG_CARD_UUID = "card";
    protected static final String FLAG_CARD_INDEX = "i";
    protected static final String FLAG_LONG_CARD_INDEX = "index";
    protected static final int FORMAT_HELP_WIDTH = 200;
    protected static final int FORMAT_HELP_LEFT_PAD = 0;
    protected static final int FORMAT_HELP_DESC_PAD = 10;

    protected static CardSelector getSelectedCard(CommandLine cmd) throws ParseException {

        if (cmd.hasOption(FLAG_CARD_UUID)) {
            String cardUUID = cmd.getOptionValue(FLAG_CARD_UUID);
            return new CardSelector(cardUUID);
        } else if (cmd.hasOption(FLAG_CARD_INDEX)) {
            int index = ((Long) cmd.getParsedOptionValue(FLAG_CARD_INDEX)).intValue();
            return new CardSelector(index);
        }

        // Shouldn't be called
        assert false;
        return null;
    }

    @SuppressWarnings("unchecked") // Safe, CLI library just returns List instead of List<String>
    public Command parseTokens(List<String> tokens) throws InkaException {
        if (tokens.size() == 0) {
            throw InvalidSyntaxException.buildGenericMessage();
        }

        String action = tokens.get(0);
        List<String> flags = tokens.subList(1, tokens.size());

        try {
            return handleAction(action, flags);
        } catch (MissingArgumentException e) {
            String missingArgumentOption = e.getOption().getArgName();
            throw InvalidSyntaxException.buildMissingArgumentMessage(missingArgumentOption);
        } catch (MissingOptionException e) {
            // Apache will return a List containing either String or OptionGroup
            List<String> missingOptions = new ArrayList<>();
            for (Object obj : e.getMissingOptions()) {
                if (obj instanceof String) {
                    missingOptions.add((String) obj);
                } else if (obj instanceof OptionGroup) {
                    OptionGroup optionGroup = (OptionGroup) obj;
                    String optionsInGroup = optionGroup.getOptions().stream().map(option -> "-" + option.getOpt())
                            .collect(Collectors.joining("/"));
                    missingOptions.add(optionsInGroup);
                }
            }

            String missingOptionsStr = String.join(", ", missingOptions);
            throw InvalidSyntaxException.buildMissingOptionMessage(missingOptionsStr);
        } catch (ParseException e) {
            throw InvalidSyntaxException.buildGenericMessage();
        }
    }

    /**
     * Derived classes should override this method and handle the action corresponding
     *
     * @param action Action of the corresponding keyword
     * @param tokens Rest of the user input
     * @return Parsed command
     * @throws ParseException               Syntax errors in command
     * @throws UnrecognizedCommandException Unrecognized action for this keyword
     */
    protected abstract Command handleAction(String action, List<String> tokens)
            throws ParseException, InkaException;

    /**
     * Combines help messages for all actions into a single message
     *
     * @param keyword     Associated keyword
     * @param actionList  Actions
     * @param headerList  Descriptions of actions
     * @param optionsList All action Options for this keyword
     * @return Formatted help string
     */
    protected String formatHelpMessage(String keyword, String[] actionList, String[] headerList,
            Options[] optionsList) {
        assert optionsList.length == headerList.length;
        assert optionsList.length == actionList.length;

        HelpFormatter formatter = new HelpFormatter();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        for (int i = 0; i < optionsList.length; i++) {
            String syntax = "`" + keyword + " " + actionList[i] + "`";
            formatter.printHelp(printWriter, FORMAT_HELP_WIDTH, syntax, headerList[i], optionsList[i],
                    FORMAT_HELP_LEFT_PAD, FORMAT_HELP_DESC_PAD, "\n",
                    false);
        }

        // Fix for extra newlines at end
        return stringWriter.toString().trim() + System.lineSeparator();
    }
}
