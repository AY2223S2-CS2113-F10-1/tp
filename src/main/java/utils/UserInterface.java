package utils;

import java.util.Scanner;
import model.Card;
import model.CardList;
import model.CardUUID;
import model.Deck;
import model.DeckList;
import model.DeckUUID;
import model.Tag;
import model.TagList;
import model.TagUUID;
import utils.enums.StringArt;
import utils.exceptions.InkaException;

public class UserInterface {
    private static final int LINE_LENGTH = 100;
    private final Scanner scanner;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public String getUserInput() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    public void printDivider() {
        System.out.println("_".repeat(LINE_LENGTH));
    }

    public void printGreeting() {
        printDivider();
        System.out.println(StringArt.INKA.art);
        System.out.println("Welcome to Inka ! Say no more to failing exams as Inka will be your lord and saviour!");
        printDivider();
    }

    public void printBye() {
        printDivider();
        System.out.println(StringArt.BYE.art);
        System.out.println("\n Bye! All the best for your exams man!!!");
        printDivider();
    }

    public void printHelp(String helpMessage) {
        printDivider();
        System.out.print(helpMessage);
        printDivider();
    }

    public void printNumOfQuestions(CardList cardList) {
        System.out.println("You now have " + cardList.size() + " questions in the bank.");
        printDivider();
    }

    public void printAddQuestionSuccess() {
        System.out.println("That's a good question for revision later!");
    }

    public void printDeleteSuccess() {
        System.out.println("Too easy ha? You won't see that question again!");
    }

    public void printWrongCommand() {
        System.out.println("Please re-enter a valid command!");
    }

    public void printNoSaveFile() {
        System.out.println("No savedata detected! make new one for uuuuuuuuuuuuuuu");
    }

    public void printSaveSuccess() {
        System.out.println("Deck exported liao");
    }

    public void printLoadSuccess() {
        System.out.println("Deck loaded!");
    }

    public void printCard(CardList cardlist, int id) {
        System.out.println(cardlist.get(id));
    }

    public void printCard(Card card) {
        System.out.println(card);
    }

    public void printException(InkaException e) {
        System.out.println(e.getUiMessage());
    }

    public void printCardList(CardList cardList) {
        if (cardList.isEmpty()) {
            System.out.println("Your current list is empty.");
        } else {
            System.out.println("Here is a list of your cards :\n");
            for (int i = 0; i < cardList.size(); ++i) {
                System.out.println("\t" + (i + 1) + "." + cardList.get(i).toTruncatedString()); // 1. question  answer
            }
        }
        printDivider();
    }

    public void printTagList(TagList tagList) {
        if (tagList.isEmpty()) {
            System.out.println("Your current list of tags is empty.");
        } else {
            System.out.println("Here is your current list of tags:");
            for (int i = 0; i < tagList.size(); ++i) {
                System.out.println((i + 1) + "." + tagList.get(i));
            }
        }
        printDivider();
    }

    public void printTags(TagList tagList) {
        if (tagList.isEmpty()) {
            System.out.println("\tThere is no tag.");
        } else {
            System.out.println("\tHere are your tags:");
            for (int i = 0; i < tagList.size(); ++i) {
                System.out.println("\t" + (i + 1) + "." + tagList.get(i));
            }
        }
        System.out.println("");
    }

    public void printDeckList(DeckList deckList) {
        if (deckList.isEmpty()) {
            System.out.println("Your current list of decks is empty.");
        } else {
            System.out.println("Here is your current list of decks:");
            for (int i = 0; i < deckList.size(); ++i) {
                System.out.println((i + 1) + "." + deckList.get(i));
            }
        }
        printDivider();
    }

    public void printDecks(DeckList deckList) {
        if (deckList.isEmpty()) {
            System.out.println("\tThere is no deck.");
        } else {
            System.out.println("\tHere are your decks:");
            for (int i = 0; i < deckList.size(); ++i) {
                System.out.println("\t" + (i + 1) + "." + deckList.get(i));
            }
        }
        printDivider();
    }

    public void printAddTagToCardSuccess(CardUUID cardUUID, TagUUID tagUUID) {
        System.out.println("Successfully added tag " + tagUUID + " to card " + cardUUID);
        printDivider();
    }

    public void printAddCardToDeckSuccess(CardUUID cardUUID, DeckUUID deckUUID) {
        System.out.println("Successfully added card " + cardUUID + " to deck " + deckUUID);
        printDivider();
    }

    public void printRemoveCardFromDeckSuccess(CardUUID cardUUID, String deckName) {
        System.out.println("Successfully removed card " + cardUUID + " from deck " + deckName);
        printDivider();
    }

    public void printRemoveTagFromDeckSuccess(TagUUID tagUUID, String deckName) {
        System.out.println("Successfully removed tag " + tagUUID + " from deck " + deckName);
        printDivider();
    }

    public void printAddTagToDeckSuccess(TagUUID tagUUID, DeckUUID deckUUID) {
        System.out.println("Successfully added tag " + tagUUID + " to deck " + deckUUID);
        printDivider();
    }

    public void printTagCreationSuccess(String tagName) {
        System.out.println("Tag does not exist.. creating a new tag: " + tagName);
    }

    public void printDeckCreationSuccess() {
        System.out.println("Deck does not exist.. creating a new one");
    }

    public void printRemoveTagFromCard(CardUUID cardUUID, TagUUID tagUUID) {
        System.out.println("Successfully removed tag " + tagUUID + " from card " + cardUUID);
    }

    public void printRemoveDeckFromCard(CardUUID cardUUID, DeckUUID deckUUID) {
        System.out.println("Successfully removed deck " + deckUUID + " from card " + cardUUID);
    }

    public void printRemoveDeckFromTag(TagUUID tagUUID, DeckUUID deckUUID) {
        System.out.println("Successfully removed deck " + deckUUID + " from tag " + tagUUID);
    }

    public void printRemoveTagFromTagList(TagUUID tagUUID) {
        System.out.println("Successfully removed tag " + tagUUID + " from the tag list.");
        printDivider();
    }

    public void printRemoveDeckFromDeckList(DeckUUID deckUUID) {
        System.out.println("Successfully removed deck " + deckUUID + " from the deck list.");
        printDivider();
    }

    public void printEditTagNameSuccess(String oldTagName, Tag tag) {
        System.out.println(
                "Tag " + tag.getUUID() + "tag name has been changed from " + oldTagName + " to " + tag.getTagName());
        printDivider();
    }

    public void printEditDeckNameSuccess(String oldDeckName, Deck deck) {
        System.out.println(
                "Deck " + deck.getDeckUUID() + "deck name has been changed from " + oldDeckName
                        + " to " + deck.getDeckName());
        printDivider();
    }
}
