package model;

import java.util.ArrayList;
import java.util.List;

import utils.exceptions.TagNotFoundException;

public class TagList {
    private List<Tag> tags;

    // Initialize with no cards
    public TagList() {
        this.tags = new ArrayList<>();
    }

    // Initialize from existing container of cards
    public TagList(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    /**
     * Find the tag with tagName specified from the tagList.
     *
     * @param tagName
     * @return The tag with the tagName specified if the tag is found, returns null otherwise.
     * @throws TagNotFoundException
     */
    public Tag findTagFromName(String tagName) {
        for (Tag tag : tags) {
            if (tag.getTagName().equals(tagName)) {
                return tag;
            }
        }
        return null;
    }

    public Tag findTagFromUUID(TagUUID tagUUID) {
        for (Tag tag : tags) {
            if (tag.getUUID().equals(tagUUID)) {
                return tag;
            }
        }
        return null;
    }

    public boolean deleteTagByUUID(TagUUID uuid) {
        return tags.removeIf(tag -> (tag.getUUID().equals(uuid)));
    }

    public void delete(int id) {
        this.tags.remove(id);
    }

    public boolean isEmpty() {
        return tags.isEmpty();
    }

    public int size() {
        return this.tags.size();
    }

    public Tag get(int id) {
        return this.tags.get(id);
    }
}