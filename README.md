# Examen Práctico: Control de Calidad y Pruebas Unitarias

**Curso:** Aseguramiento de la Calidad de Software  
**Duración:** 45 Minutos  
**Entorno:** Java 11, JUnit 5, Mockito, Spring Boot Test  
**Modalidad:** Individual (con auditoría de Git activa)

---

## Contexto del Problema: "SkyRoute Airlines"

La aerolínea **SkyRoute Airlines** necesita automatizar y asegurar el cálculo de las tarifas de equipaje en sus mostradores de facturación. Se te ha encomendado diseñar y validar mediante pruebas unitarias herméticas el servicio `BaggageFeeCalculator`, el cual interactúa con un servicio externo para verificar el estatus del pasajero.

---

## Reglas de Negocio a Implementar

El método `double calculateFee(double weight, int bagCount, Long passengerId)` debe calcular el costo total siguiendo estas reglas:

1. **Tarifa Base:** Cada maleta tiene un costo base de **$30.0**.
2. **Exceso de Peso:** Si una maleta pesa **más de 23 kg**, se le aplica un recargo único de **$50.0** (además de la tarifa base).
3. **Beneficio VIP:** Si el pasajero es clasificado como VIP por el sistema externo, la **primera maleta es completamente gratis** ($0.0), siempre y cuando el peso de esa maleta **no supere los 23 kg**. Si lleva más de una maleta, las siguientes se cobran con tarifa normal.
4. **Restricciones de Integridad (Excepciones):**
   - `weight` no puede ser menor o igual a 0 kg.
   - `bagCount` debe ser al menos 1.
   - `passengerId` no puede ser `null`.
   - Si no se cumple alguna restricción, lanzar `IllegalArgumentException` con el mensaje: `"Parámetros de equipaje inválidos"`.

---

## Estructura del Proyecto

```
src/main/java/ec/edu/epn/
├── SkyRouteApplication.java              ← Clase principal Spring Boot
└── skyroute/service/
    ├── PassengerService.java              ← Interfaz de dependencia externa (Mock)
    └── BaggageFeeCalculator.java          ← Clase a completar (IMPLEMENTAR)

src/test/java/ec/edu/epn/skyroute/service/
└── BaggageFeeCalculatorTest.java          ← Pruebas unitarias (CREAR)
```

---

## Tu Tarea

### Paso 1 — Diseño de Pruebas (AAA)

Crea el archivo `BaggageFeeCalculatorTest.java` en `src/test/java/ec/edu/epn/skyroute/service/`.

### Paso 2 — Casos de Prueba

Escribe **al menos 5 métodos de prueba** usando `@Mock`, `@InjectMocks` y aserciones de JUnit 5 que cubran:

| # | Caso | Entrada | Resultado esperado |
|---|------|---------|-------------------|
| 1 | Equipaje estándar | 1 maleta, 20 kg, pasajero regular | **$30.00** |
| 2 | Exceso de peso | 1 maleta, 25 kg, pasajero regular | **$80.00** |
| 3 | Beneficio VIP | 1 maleta, 15 kg, pasajero VIP | **$0.00** (requiere Mockito) |
| 4 | Caso límite VIP | 2 maletas, 15 kg c/u, pasajero VIP | **$30.00** (1ra gratis, 2da cobro normal) |
| 5 | Validación de excepción | `weight = 0` o negativo | `IllegalArgumentException` |

### Paso 3 — Ejecución

```bash
mvn test
```

Asegúrate de que **todos los casos se pinten en verde**.

---

## Criterios de Evaluación (10 puntos)

| Parámetro | Criterio | Puntaje |
|---|---|---|
| Integridad y Compilación | El código compila limpiamente y corre con `mvn test` | 2.0 |
| Manejo de Excepciones | Implementación y prueba con `assertThrows` | 2.0 |
| Mocking de Dependencias | Uso de `@Mock`, `@InjectMocks` y `when().thenReturn()` | 2.5 |
| Cobertura de Casos Borde | Validación de límites (exactamente 23 kg, VIP con múltiples maletas) | 2.5 |
| Estilo y Orden (Clean Code) | `@DisplayName` en español, nombres descriptivos `should...when...`, sin código muerto | 1.0 |

---

## Instrucciones de Uso

### 1. Hacer fork del repositorio

Haz clic en el botón **Fork** en la parte superior derecha de este repositorio en GitHub.

### 2. Abrir en GitHub Codespaces

1. Ve a tu fork en GitHub.
2. Haz clic en el botón verde **Code** → pestaña **Codespaces** → **Create codespace on main**.
3. Espera a que el contenedor se configure (instalación de dependencias automática).

### 3. Ejecutar el proyecto

```bash
mvn compile    # Compila el proyecto
mvn test       # Ejecuta las pruebas unitarias
```

### 4. Realizar commits periódicos

El proyecto incluye un script de **auto-commit** que registra tu avance cada 5 minutos automáticamente. También puedes hacer commits manuales:

```bash
git add .
git commit -m "Descripción del avance"
```

---

## Instrucciones de Entrega

1. Asegúrate de que el último estado de tu repositorio tenga todos los cambios subidos:
   ```bash
   git push origin main
   ```
2. Envía el **enlace público de tu repositorio** a través de la plataforma de entrega.

---

## Notas Importantes

- El entorno está configurado para **restringir el uso de asistentes de IA** (Copilot, Tabnine, etc.). No intentes instalar estas extensiones.
- La telemetría de Git registrará el progreso de tu examen. Los commits frecuentes son parte de la evaluación.
- La interfaz `PassengerService` **no debe ser implementada** — representa un servicio externo que debe ser simulado con Mockito.
- Usa `@DisplayName` con descripciones en español para cada prueba.
- Sigue el patrón **AAA** (Arrange, Act, Assert) en cada método de prueba.
