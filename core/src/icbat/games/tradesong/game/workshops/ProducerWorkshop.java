package icbat.games.tradesong.game.workshops;

import icbat.games.tradesong.game.Item;
import icbat.games.tradesong.game.Workshop;

import java.util.ArrayList;
import java.util.List;

public class ProducerWorkshop implements Workshop {

    private final Item itemProduced;
    List<Item> outputQueue = new ArrayList<Item>();

    public ProducerWorkshop(Item itemProduced) {
        this.itemProduced = itemProduced;
    }

    @Override
    public void takeTurn() {
        outputQueue.add(itemProduced);
    }

    @Override
    public boolean hasOutput() {
        return !outputQueue.isEmpty();
    }

    @Override
    public Item getNextOutput() {
        if (outputQueue.isEmpty()) {
            throw new IllegalStateException("Dev error, " + this.getClass().getName() + " was accessed when there was no output");
        }
        return outputQueue.remove(0);
    }
}
