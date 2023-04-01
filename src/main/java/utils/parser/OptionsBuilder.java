package utils.parser;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import utils.exceptions.UnrecognizedCommandException;

public class OptionsBuilder {
    protected static final String FLAG_CARD_UUID = "c";
    protected static final String FLAG_LONG_CARD_UUID = "card";
    protected static final String FLAG_CARD_INDEX = "i";
    protected static final String FLAG_LONG_CARD_INDEX = "index";

    public static final String CARD_MODEl = "card";
    public static final String TAG_MODEL = "tag";
    public static final String DECK_MODEL = "deck";

    public static final String ADD_ACTION = "add";
    public static final String DELETE_ACTION = "delete";
    public static final String TAG_ACTION = "tag";
    public static final String VIEW_ACTION = "view";
    public static final String DECK_ACTION = "deck";
    public static final String EDIT_ACTION = "edit";
    public static final String LIST_ACTION = "list";


    private static String model = null;
    private static String action = null;

    public OptionsBuilder(String model, String action) {
        this.model = model;
        this.action = action;
    }

    public Options buildOptions () {
        switch (model) {
            case CARD_MODEl:
                switch (action) {
                    case ADD_ACTION:
                        return buildAddOptions();
                    case TAG_ACTION:
                        return buildTagOptions();
                    case VIEW_ACTION:
                        return buildViewOptions();
                    case DELETE_ACTION:
                        return buildDeleteOptions(CARD_MODEl);
                    case DECK_ACTION:
                        return buildDeckOptions(CARD_MODEl);
                }
            case TAG_MODEL:
                switch (action) {
                    case DELETE_ACTION:
                        return buildDeleteOptions(TAG_MODEL);
                    case DECK_ACTION:
                        return buildDeckOptions(TAG_MODEL);
                    case EDIT_ACTION:
                        return buildEditOptions(TAG_MODEL);
                    case LIST_ACTION:
                        return buildListOptions(TAG_MODEL);
                }
            case DECK_MODEL:
                switch (action) {
                    case DELETE_ACTION:
                        return buildDeleteOptions(DECK_MODEL);
                    case EDIT_ACTION:
                        return buildEditOptions(DECK_MODEL);
                    case LIST_ACTION:
                        return buildListOptions(DECK_MODEL);
                }
            default:
                return null;
        }
    }

    public static Options buildAddOptions() {
        Options options = new Options();

        Option questionOption = buildMultipleTokenOption("q", "question", true, "card question", true);
        options.addOption(questionOption);

        Option answerOption = buildMultipleTokenOption("a", "answer", true, "card answer", true);
        options.addOption(answerOption);

        return options;
    }

    public static Options buildTagOptions() {
        Options options = new Options();
        options.addOptionGroup(buildCardSelectOption());

        Option tag = buildMultipleTokenOption("t", "tag", true, "tag name", true);
        options.addOption(tag);

        return options;
    }

    public static Options buildViewOptions() {
        Options options = new Options();
        options.addOptionGroup(buildCardSelectOption());

        return options;
    }

    public static Options buildDeleteOptions(String model) {
        Options options = new Options();
        switch (model) {
            case CARD_MODEl:
                options.addOptionGroup(buildCardSelectOption());
            case TAG_MODEL:
                options.addRequiredOption("t", "tag", true, "tag name");
            case DECK_MODEL:
                options.addRequiredOption("d", "deck", true, "deck name");
                options.addOption("c", "card", true, "card name (optional)");
                options.addOption("t", "tag", true, "tag name (optional)");
        }
        return options;
    }

    public static Options buildDeckOptions(String model) {
        Options options = new Options();
        switch (model) {
            case CARD_MODEl:
                options.addOptionGroup(buildCardSelectOption());
            case TAG_MODEL:
                options.addRequiredOption("t", "tag", true, "tag name");
        }
        options.addRequiredOption("d", "deck", true, "deck name");
        return options;
    }

    private static Options buildEditOptions(String model) {
        Options options = new Options();
        switch (model) {
            case TAG_MODEL:
                options.addRequiredOption("o", "old", true, "Old tag name");
                Option newTag = buildMultipleTokenOption("n", "new", true, "New tag name", true);
                options.addOption(newTag);
            case DECK_MODEL:
                options.addRequiredOption("o", "old", true, "Old deck name");
                options.addRequiredOption("n", "new", true, "New deck name");
        }
        return options;
    }

    private static Options buildListOptions(String model) {
        Options options = new Options();
        switch (model) {
            case TAG_MODEL:
                options.addOption("t", "tag", true, "tag name (optional)");
            case DECK_MODEL:
                options.addOption("c", "cards", true, "deck name to list cards from (optional)");
                options.addOption("t", "tags", true, "deck name to list tags from (optional)");
        }
        return options;
    }

    /**
     * Build an {@link Option} for selecting Card based on either {@link model.CardUUID} or card index from list
     *
     * @return Configured OptionGroup
     */
    protected static OptionGroup buildCardSelectOption() {
        // Mutually exclusive options
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setRequired(true);

        Option cardUuidOption = new Option(FLAG_CARD_UUID, FLAG_LONG_CARD_UUID, true, "card UUID");
        optionGroup.addOption(cardUuidOption);

        Option cardIndexOption = new Option(FLAG_CARD_INDEX, FLAG_LONG_CARD_INDEX, true, "card index");
        cardIndexOption.setType(Number.class);
        optionGroup.addOption(cardIndexOption);

        return optionGroup;
    }

    /**
     * Wrapper around {@link Option} constructor to set option to accept multiple tokens (whitespace-separated
     * arguments). The arguments to this option should then be obtained using
     * {@link org.apache.commons.cli.CommandLine#getOptionValues(char)}.
     *
     * @param option      See {@link Option#Option(String, String, boolean, String)}
     * @param longOption  See {@link Option#Option(String, String, boolean, String)}
     * @param hasArg      See {@link Option#Option(String, String, boolean, String)}
     * @param description See {@link Option#Option(String, String, boolean, String)}
     * @param required    If Option is a required option
     * @return Configured Option
     */
    protected static Option buildMultipleTokenOption(String option, String longOption, boolean hasArg,
                                                     String description,
                                                     boolean required) {
        Option opt = new Option(option, longOption, hasArg, description);
        opt.setArgs(Option.UNLIMITED_VALUES);
        opt.setRequired(required);

        return opt;
    }
}
