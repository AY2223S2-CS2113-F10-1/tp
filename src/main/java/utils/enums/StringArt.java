package utils.enums;

public enum StringArt {
    INKA(
            ".___        __            \n" +
            "|   | ____ |  | _______   \n" +
            "|   |/    \\|  |/ /\\__  \\  \n" +
            "|   |   |  \\    <  / __ \\_\n" +
            "|___|___|  /__|_ \\(____  /\n" +
            "         \\/     \\/     \\/ "),
    BYE(
            " ____  _  _  ____    _   \n" +
            "(  _ \\( \\/ )(  __)  / \\  \n" +
            " ) _ ( )  /  ) _)   \\_/  \n" +
            "(____/(__/  (____)  (_) "
    );

    public final String art;
    StringArt(String art) {
        this.art = art;
    }
}
