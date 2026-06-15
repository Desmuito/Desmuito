// representa una operacion que se ejecutara sobre el inventario
public class InventoryOperation {

    private final OperationType type;
    private final int key;
    private final int quantity;
    private final InventoryItem item; // solo se usa en PURCHASE de componente nuevo, en otro caso es null

    public InventoryOperation(OperationType type, int key, int quantity, InventoryItem item) {
        this.type     = type;
        this.key      = key;
        this.quantity = quantity;
        this.item     = item;
    }

    public OperationType getType()     { return type; }
    public int           getKey()      { return key; }
    public int           getQuantity() { return quantity; }
    public InventoryItem getItem()     { return item; }

    @Override
    public String toString() {
        return String.format("InventoryOperation{type=%s, key=%d, quantity=%d, item=%s}",
                type, key, quantity, item != null ? item.getName() : "null");
    }
}
