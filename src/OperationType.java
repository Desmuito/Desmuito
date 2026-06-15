// tipos de operaciones que se pueden hacer sobre el inventario
public enum OperationType {
    PURCHASE,   // compra o ingreso de componentes
    QUERY,      // consulta de disponibilidad
    LEND,       // prestamo a un estudiante
    RECEIVE,    // devolucion de un componente
    DISPOSE     // baja por daño, perdida u obsolescencia
}
