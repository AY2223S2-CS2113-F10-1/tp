package model;

import java.util.ArrayList;
import java.util.UUID;

//to be made into an abstract class containing a few types of Cards later, for now just a single Card will do
public class Card {
    private CardUUID uuid; //to be made into a hash later
    private String question;
    private String answer;
    private ArrayList<TagUUID> tags = new ArrayList<>();

    private ArrayList<DeckUUID> decks = new ArrayList<>();

    // Temporary constructor for Card, to be revised later to also consider uuid and tag etc
    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.uuid = new CardUUID(UUID.randomUUID());
    }

    /**
     * Constructor function where user specifies the UUID himself This is private and is only called by the static
     * factory method createCardWithUUID()
     *
     * @param question The question in the card.
     * @param answer   The answer in the card.
     * @param uuidStr  The custom UUID String that the user specifies for the card.
     */
    private Card(String question, String answer, String uuidStr) {
        this.question = question;
        this.answer = answer;
        this.uuid = new CardUUID(UUID.fromString(uuidStr));
    }

    /**
     * A method that calls a private constructor function where user can specify the UUID himself. This method is mainly
     * used for unit-testing purpose
     *
     * @param question The question in the card
     * @param answer   The answer in the card
     * @param uuidStr  The custom UUID String that the user wants for the card
     * @return
     */
    public static Card createCardWithUUID(String question, String answer, String uuidStr) {
        return new Card(question, answer, uuidStr);
    }

    public CardUUID getUuid() {
        return this.uuid;
    }

    public ArrayList<TagUUID> getTagsUUID() {
        return this.tags;
    }
    public ArrayList<DeckUUID> getDecksUUID() {
        return this.decks;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void addTag(TagUUID tagUUID) {
        tags.add(tagUUID);
    }
    public void addDeck(DeckUUID deckUUID) {
        decks.add(deckUUID);
    }

    public void removeTag(TagUUID tagUUID) {
        tags.remove(tagUUID);
    }

    public void removeDecks(DeckUUID deckUUID) {
        decks.remove(deckUUID);
    }
    public boolean deckEmpty() {
        return this.decks.isEmpty();
    }

    public boolean tagEmpty() {
        return this.tags.isEmpty();
    }

    @Override
    public String toString() {
        return "Qn: " + this.question + "\nAns: " + this.answer + "\nUUID:  " + this.uuid;
    } // return the card
}
