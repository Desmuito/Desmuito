import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.HashSet;

public class DataGenerator {

    private static final String[] CATEGORIES = {
        "Sensor", "Motor", "Microcontrolador", "Cable",
        "Bateria", "Herramienta", "Modulo", "Kit"
    };

    private static final String[] LOCATIONS = {
        "Estante_A", "Estante_B", "Caja_1",
        "Caja_2", "Laboratorio", "Bodega"
    };

    public static InventoryItem generateItem(int id) {
        String category = CATEGORIES[StdRandom.uniformInt(CATEGORIES.length)];
        String location = LOCATIONS[StdRandom.uniformInt(LOCATIONS.length)];
        int stockTotal  = StdRandom.uniformInt(1, 21);
        return new InventoryItem(id, "Componente_" + id, category, location,
                                 stockTotal, stockTotal, 0);
    }

    public static ArrayList<InventoryOperation> generateOperations(int m, int keyUniverse, long seed) {
        StdRandom.setSeed(seed);

        ArrayList<InventoryOperation> ops = new ArrayList<>(m);

        // se lleva registro de las claves presentes para saber si un PURCHASE es nuevo o reposicion
        HashSet<Integer> presentKeys = new HashSet<>();

        for (int i = 0; i < m; i++) {
            double r   = StdRandom.uniformDouble();
            int    key = StdRandom.uniformInt(1, keyUniverse + 1);

            InventoryOperation op;

            if (r < 0.35) {
                // PURCHASE
                int quantity = StdRandom.uniformInt(1, 6);
                if (!presentKeys.contains(key)) {
                    InventoryItem newItem = generateItem(key);
                    presentKeys.add(key);
                    op = new InventoryOperation(OperationType.PURCHASE, key, quantity, newItem);
                } else {
                    // componente ya existe, solo se repone stock
                    op = new InventoryOperation(OperationType.PURCHASE, key, quantity, null);
                }

            } else if (r < 0.65) {
                // QUERY
                op = new InventoryOperation(OperationType.QUERY, key, 0, null);

            } else if (r < 0.80) {
                // LEND
                int quantity = StdRandom.uniformInt(1, 6);
                op = new InventoryOperation(OperationType.LEND, key, quantity, null);

            } else if (r < 0.90) {
                // RECEIVE
                int quantity = StdRandom.uniformInt(1, 6);
                op = new InventoryOperation(OperationType.RECEIVE, key, quantity, null);

            } else {
                // DISPOSE
                presentKeys.remove(key);
                op = new InventoryOperation(OperationType.DISPOSE, key, 0, null);
            }

            ops.add(op);
        }

        return ops;
    }
}
