package items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public Item get(int index) {
        return items.get(index);
    }

    public Item remove(int index) {
        return items.remove(index);
    }

    public String summary() {
        if (items.isEmpty()) {
            return "None";
        }
        List<String> names = new ArrayList<>();
        for (Item item : items) {
            names.add(item.getName());
        }
        return String.join(", ", names);
    }
}
