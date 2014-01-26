package com.icbat.game.tradesong.gameObjects;

import com.icbat.game.tradesong.PersistantData;
import com.icbat.game.tradesong.Tradesong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Inventory with persistence
 */
public class Inventory extends PersistantData {
    private int maxSize = 18;
    private List<Item> items = new ArrayList<Item>(maxSize);

    public Inventory() {
        // TODO remove this, it's for testing!
        for (Item item : Tradesong.itemPrototypes.getAll()) {
            addItem(item);
        }
    }

    public boolean addItem(Item newItem) {
         return addItem(items.size(), newItem);
    }

    public boolean addItem(int position, Item newItem) {
        if (canAdd()) {
            position = (position <= items.size()) ? position :  items.size();
            items.add(position, newItem);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to remove the item. Will return payload regardless of removal's success.
     *
     * @return the same payload you gave it.
     * */
    public Item takeOutItem(Item payload) {
        items.remove(payload);

        return payload;
    }

    public List<Item> getCopyOfInventory() {
        return new ArrayList<Item>(items);
    }

    public boolean canAdd() {
        return items.size() + 1 < maxSize;
    }

    public void save() {
        // TODO impl
    }

    public void load() {
        // TODO impl
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getCurrentSize() {
        return items.size();
    }

    /**
     * Sorts inventory based on item name
     * */
    public void sort() {
        Collections.sort(items);
    }


}
