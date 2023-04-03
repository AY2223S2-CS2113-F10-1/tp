package model;

import java.util.Optional;
import java.util.UUID;
import utils.exceptions.UUIDWrongFormatException;

public class CardSelector {
    private Optional<CardUUID> uuid = Optional.empty();
    private Optional<Integer> index = Optional.empty();

    public CardSelector(String uuid) throws UUIDWrongFormatException {
        try {
            this.uuid = Optional.of(new CardUUID(UUID.fromString(uuid)));
        } catch (IllegalArgumentException e) {
            throw new UUIDWrongFormatException();
        }
    }

    public CardSelector(int index) {
        this.index = Optional.of(index);
    }

    public Optional<CardUUID> getUuid() {
        return uuid;
    }

    public Optional<Integer> getIndex() {
        return index;
    }
}
