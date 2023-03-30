package model;

import java.util.ArrayList;
import java.util.List;

public class DeckList {
    private List<Deck> deckList;

    public DeckList() {
        this.deckList = new ArrayList<>();
    }

    public List<Deck> getTags() {
        return this.deckList;
    }

    public void addDeck(Deck deck) {
        this.deckList.add(deck);
    }

    public boolean deleteDeckByUUID(DeckUUID uuid) {
        return deckList.removeIf(deck -> (deck.getDeckUUID().equals(uuid)));
    }
    public boolean isEmpty() {
        return deckList.isEmpty();
    }

    public int size() {
        return this.deckList.size();
    }

    public Deck get(int id) {
        return this.deckList.get(id);
    }

    public Deck findDeckFromName(String deckName) {
        for (Deck deck : deckList) {
            if (deck.getDeckName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public Deck findDeckFromUUID(DeckUUID deckUUID) {
        for (Deck deck : deckList) {
            if (deck.getDeckUUID().equals(deckUUID)) {
                return deck;
            }
        }
        return null;
    }
    public void delete(int id) {
        this.deckList.remove(id);
    }
}
