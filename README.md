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
* [cite_start]**Motor Principal:** Implementación de Árboles Rojo-Negro (RedBlackBST) para garantizar operaciones de búsqueda, inserción y eliminación en tiempo $O(\log n)$, evitando la degradación a $O(n)$ de los árboles no balanceados (BST)[cite: 26, 453].
* [cite_start]**Lógica Transaccional:** Sistema de operaciones (PURCHASE, QUERY, LEND, RECEIVE, DISPOSE) [cite: 113, 116, 118, 119, 121] [cite_start]que previene inconsistencias de datos, como stock negativo o sobregiro de préstamos[cite: 102, 103].
* [cite_start]**Análisis de Rendimiento:** Procesamiento automatizado de hasta 524.288 operaciones simultáneas [cite: 280][cite_start], midiendo tiempos de ejecución en CPU para comparar el rendimiento empírico vs. teórico[cite: 41, 295].

## 🧠 Mi Contribución Técnica en el Equipo
* [cite_start][**OPCIÓN 1:** Si programaste el generador] Desarrollo del `DataGenerator.java`, creando secuencias determinísticas y reproducibles mediante semillas para simular escenarios de estrés[cite: 231, 234].
* [cite_start][**OPCIÓN 2:** Si programaste la lógica] Implementación de la interfaz `InventoryIndex` [cite: 143] [cite_start]y la delegación de operaciones en las clases adaptadoras `BSTInventoryIndex` y `RedBlackBSTInventoryIndex`[cite: 253, 254].
