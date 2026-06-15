# Desmuito

Técnico de Nivel Medio en Programación y estudiante de Ingeniería civl en informatica y telecomunicaciones. Orientado al desarrollo de software, eficiencia algorítmica y la integración de fundamentos matemáticos complejos en soluciones tecnológicas.

## 🛠️ Stack Técnico y Conocimientos
* **Lenguajes y Bases de Datos:** C++, Java, SQL.
* **Desarrollo Web:** HTML, CSS, JavaScript.
* **Ciencias de la Computación:** Estructuras de Datos, Diseño de Algoritmos, Arquitectura de Redes de Datos.
* **Matemáticas Aplicadas:** Álgebra Lineal, Cálculo Multivariable, Ecuaciones Diferenciales Ordinarias.

## 🚀 Proyectos y Repositorios Destacados

# Motor de Inventario de Alto Rendimiento (Árboles Rojo-Negro)

[cite_start]Sistema transaccional de gestión de inventario desarrollado en Java[cite: 540]. [cite_start]El proyecto simula un entorno de alta demanda mediante la implementación y evaluación de tablas de símbolos basadas en árboles de búsqueda [cite: 21][cite_start], procesando cientos de miles de transacciones de stock con validación de estado[cite: 391, 392].

## ⚙️ Arquitectura y Estructuras de Datos
* **Motor Principal:** Implementación de Árboles Rojo-Negro (`RedBlackBST`) para garantizar operaciones de búsqueda, inserción y eliminación en tiempo $O(\log n)$, evitando la degradación a $O(n)$ que sufren los árboles binarios de búsqueda no balanceados (`BST`).
* **Lógica Transaccional:** Sistema basado en un enumerador de operaciones (`PURCHASE`, `QUERY`, `LEND`, `RECEIVE`, `DISPOSE`) diseñado bajo reglas de negocio estrictas que previenen de forma activa inconsistencias en el estado del inventario, como el stock negativo o el sobregiro de préstamos.
* **Módulo de Experimentación:** Procesamiento automatizado de pruebas masivas de hasta 524.288 operaciones simultáneas con medición de tiempos de ejecución en CPU (`StopwatchCPU`) para comparar el rendimiento empírico frente al análisis asintótico teórico.

## 🛠️ Desarrollo Integral del Proyecto (Autoría Completa)
Diseñé e implementé la totalidad de los componentes del sistema transaccional y de análisis:
* **Estructuras y Adaptadores:** Definición de la interfaz funcional `InventoryIndex` e implementación de las clases adaptadoras `BSTInventoryIndex` y `RedBlackBSTInventoryIndex` para la delegación limpia de operaciones sobre las estructuras base.
* **Generador de Carga Estocástica:** Programación de `DataGenerator.java` para modelar un entorno de alta demanda mediante la generación aleatoria de transacciones con probabilidades fijas y control de reproducibilidad mediante semillas fijadas en `StdRandom`.
* **Pipeline Experimental y Validación:** Creación del motor principal en `Experiment.java` encargado de procesar las secuencias de operaciones, verificar la paridad de estados entre estructuras (invariantes de stock y tamaños finales) y exportar automáticamente los microdatos experimentales a archivos CSV.
