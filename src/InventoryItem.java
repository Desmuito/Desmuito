public class InventoryItem {

    private int id;
    private String name;
    private String category;
    private String location;
    private int stockTotal;
    private int stockAvailable;
    private int stockOnLoan;

    public InventoryItem(int id, String name, String category, String location,
                         int stockTotal, int stockAvailable, int stockOnLoan) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.location = location;
        this.stockTotal = stockTotal;
        this.stockAvailable = stockAvailable;
        this.stockOnLoan = stockOnLoan;
    }

    public int getId()             { return id; }
    public String getName()        { return name; }
    public String getCategory()    { return category; }
    public String getLocation()    { return location; }
    public int getStockTotal()     { return stockTotal; }
    public int getStockAvailable() { return stockAvailable; }
    public int getStockOnLoan()    { return stockOnLoan; }

    public void addStock(int quantity) {
        if (quantity <= 0) return;
        stockTotal     += quantity;
        stockAvailable += quantity;
    }

    // devuelve false si no hay suficiente stock disponible
    public boolean lend(int quantity) {
        if (quantity <= 0 || quantity > stockAvailable) return false;
        stockAvailable -= quantity;
        stockOnLoan    += quantity;
        return true;
    }

    // devuelve false si se intenta devolver mas de lo prestado
    public boolean receive(int quantity) {
        if (quantity <= 0 || quantity > stockOnLoan) return false;
        stockAvailable += quantity;
        stockOnLoan    -= quantity;
        return true;
    }

    public InventoryItem copy() {
        return new InventoryItem(id, name, category, location,
                                 stockTotal, stockAvailable, stockOnLoan);
    }

    @Override
    public String toString() {
        return String.format("InventoryItem{id=%d, name='%s', category='%s', " +
                "location='%s', total=%d, available=%d, onLoan=%d}",
                id, name, category, location, stockTotal, stockAvailable, stockOnLoan);
    }
}
