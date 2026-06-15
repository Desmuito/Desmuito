import edu.princeton.cs.algs4.StopwatchCPU;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Out;
import java.util.ArrayList;

public class Experiment {

    private static final int[] T_VALUES    = {12, 13, 14, 15, 16, 17, 18, 19};
    private static final int   REPETITIONS = 30;

    private static final String CSV_HEADER =
        "instancia,estructura,m," +
        "purchase_total,query_total,lend_total,receive_total,dispose_total," +
        "query_successful,query_failed," +
        "lend_successful,lend_failed," +
        "receive_successful,receive_failed," +
        "final_size,final_height,elapsed_seconds";

    // encapsula los resultados de una ejecucion para evitar estado global estatico
    private static class ExperimentResult {
        int purchaseTotal;
        int queryTotal,   queryOk,   queryFail;
        int lendTotal,    lendOk,    lendFail;
        int receiveTotal, receiveOk, receiveFail;
        int disposeTotal;
        double elapsedTime;
    }

    public static void main(String[] args) {

        for (int t : T_VALUES) {
            int m = 1 << t; // 2^t
            System.out.println("=== m = " + m + " ===");

            Out csvOut = new Out("inventory_experiment_" + m + ".csv");
            csvOut.println(CSV_HEADER);

            for (int i = 1; i <= REPETITIONS; i++) {
                long seed        = (long) m + i;
                int  keyUniverse = 4 * m;

                // se genera la secuencia una sola vez y se reutiliza en ambas estructuras
                ArrayList<InventoryOperation> operations =
                    DataGenerator.generateOperations(m, keyUniverse, seed);

                // BST
                BSTInventoryIndex bstIndex = new BSTInventoryIndex();
                ExperimentResult bstResult = runExperiment(bstIndex, operations);

                csvOut.println(buildRow(i, "BST", m, bstResult, bstIndex));

                // RedBlackBST
                RedBlackInventoryIndex rbIndex = new RedBlackInventoryIndex();
                ExperimentResult rbResult = runExperiment(rbIndex, operations);

                csvOut.println(buildRow(i, "RedBlackBST", m, rbResult, rbIndex));

                boolean valid = validate(bstIndex, rbIndex, operations, bstResult, rbResult);

                System.out.printf(
                    "  instancia %2d | BST: %.6f s (h=%d) | RB: %.6f s (h=%d) | valid=%b%n",
                    i, bstResult.elapsedTime, bstIndex.height(),
                       rbResult.elapsedTime,  rbIndex.height(), valid);
            }

            csvOut.close();
            System.out.println("  CSV guardado: inventory_experiment_" + m + ".csv\n");
        }
    }

    // mide solo el tiempo de ejecutar las operaciones, sin incluir generacion ni escritura del CSV
    private static ExperimentResult runExperiment(InventoryIndex index,
                                                   ArrayList<InventoryOperation> operations) {
        ExperimentResult result = new ExperimentResult();
        StopwatchCPU timer = new StopwatchCPU();

        for (InventoryOperation op : operations) {
            switch (op.getType()) {
                case PURCHASE: executePurchase(index, op, result); break;
                case QUERY:    executeQuery   (index, op, result); break;
                case LEND:     executeLend    (index, op, result); break;
                case RECEIVE:  executeReceive (index, op, result); break;
                case DISPOSE:  executeDispose (index, op, result); break;
            }
        }

        result.elapsedTime = timer.elapsedTime();
        return result;
    }

    private static void executePurchase(InventoryIndex index, InventoryOperation op,
                                        ExperimentResult r) {
        r.purchaseTotal++;
        InventoryItem existing = index.get(op.getKey());
        if (existing == null) {
            // componente nuevo — se usa copia para no compartir objeto entre BST y RedBlack
            if (op.getItem() != null) index.put(op.getKey(), op.getItem().copy());
        } else {
            // reposicion de stock sobre el existente
            existing.addStock(op.getQuantity());
            index.put(op.getKey(), existing);
        }
    }

    private static void executeQuery(InventoryIndex index, InventoryOperation op,
                                     ExperimentResult r) {
        r.queryTotal++;
        if (index.get(op.getKey()) != null) r.queryOk++;
        else                                r.queryFail++;
    }

    private static void executeLend(InventoryIndex index, InventoryOperation op,
                                    ExperimentResult r) {
        r.lendTotal++;
        InventoryItem item = index.get(op.getKey());
        if (item != null && item.lend(op.getQuantity())) {
            index.put(op.getKey(), item);
            r.lendOk++;
        } else {
            r.lendFail++;
        }
    }

    private static void executeReceive(InventoryIndex index, InventoryOperation op,
                                       ExperimentResult r) {
        r.receiveTotal++;
        InventoryItem item = index.get(op.getKey());
        if (item != null && item.receive(op.getQuantity())) {
            index.put(op.getKey(), item);
            r.receiveOk++;
        } else {
            r.receiveFail++;
        }
    }

    private static void executeDispose(InventoryIndex index, InventoryOperation op,
                                       ExperimentResult r) {
        r.disposeTotal++;
        index.delete(op.getKey());
    }

    private static boolean validate(InventoryIndex bst, InventoryIndex rb,
                                    ArrayList<InventoryOperation> ops,
                                    ExperimentResult bstResult, ExperimentResult rbResult) {
        boolean ok = true;
        int m = ops.size();

        // 1. ambas estructuras deben tener el mismo tamaño final
        if (bst.size() != rb.size()) {
            System.err.println("  [FAIL] tamaños distintos: BST=" + bst.size() + " RB=" + rb.size());
            ok = false;
        }

        // 2. para 100 claves al azar, get debe retornar lo mismo en ambas
        StdRandom.setSeed(999999L);
        int keyUniverse = 4 * m;
        for (int j = 0; j < Math.min(100, keyUniverse); j++) {
            int key = StdRandom.uniformInt(1, keyUniverse + 1);
            InventoryItem b = bst.get(key);
            InventoryItem r = rb.get(key);
            if ((b == null) != (r == null)) {
                System.err.printf("  [FAIL] clave %d difiere: BST=%s RB=%s%n",
                        key,
                        b == null ? "null" : String.valueOf(b.getId()),
                        r == null ? "null" : String.valueOf(r.getId()));
                ok = false;
                break;
            }
        }

        // 3. cantidad exacta de operaciones ejecutadas
        int totalBST = bstResult.purchaseTotal + bstResult.queryTotal + bstResult.lendTotal
                     + bstResult.receiveTotal  + bstResult.disposeTotal;
        if (totalBST != m) {
            System.err.println("  [FAIL] BST no ejecuto exactamente m operaciones");
            ok = false;
        }
        int totalRB = rbResult.purchaseTotal + rbResult.queryTotal + rbResult.lendTotal
                    + rbResult.receiveTotal  + rbResult.disposeTotal;
        if (totalRB != m) {
            System.err.println("  [FAIL] RB no ejecuto exactamente m operaciones");
            ok = false;
        }

        // 4. sin stock negativo y stockAvailable + stockOnLoan == stockTotal
        ok = checkStock(bst, "BST") && ok;
        ok = checkStock(rb,  "RB")  && ok;

        return ok;
    }

    private static boolean checkStock(InventoryIndex index, String name) {
        boolean ok = true;
        for (Integer key : index.keys()) {
            InventoryItem item = index.get(key);
            if (item == null) {
                System.err.println("  [FAIL] " + name + ": valor null en clave " + key);
                ok = false;
                continue;
            }
            if (item.getStockAvailable() < 0 || item.getStockOnLoan() < 0 || item.getStockTotal() < 0) {
                System.err.println("  [FAIL] " + name + ": stock negativo en clave " + key);
                ok = false;
            }
            if (item.getStockAvailable() + item.getStockOnLoan() != item.getStockTotal()) {
                System.err.println("  [FAIL] " + name + ": inconsistencia de stock en clave " + key);
                ok = false;
            }
        }
        return ok;
    }

    private static String buildRow(int instancia, String estructura, int m,
                                   ExperimentResult r, InventoryIndex index) {
        return String.join(",",
            String.valueOf(instancia), estructura, String.valueOf(m),
            String.valueOf(r.purchaseTotal), String.valueOf(r.queryTotal),
            String.valueOf(r.lendTotal),     String.valueOf(r.receiveTotal),
            String.valueOf(r.disposeTotal),
            String.valueOf(r.queryOk),       String.valueOf(r.queryFail),
            String.valueOf(r.lendOk),        String.valueOf(r.lendFail),
            String.valueOf(r.receiveOk),     String.valueOf(r.receiveFail),
            String.valueOf(index.size()),    String.valueOf(index.height()),
            String.valueOf(r.elapsedTime));
    }
}