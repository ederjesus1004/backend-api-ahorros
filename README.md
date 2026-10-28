# API de Ahorros

API REST para registrar ahorristas y sus depósitos a plazo fijo. Al crear un depósito, el sistema calcula la tasa, el monto final, el interés ganado y el rendimiento.

## Tecnologías

- Java 21
- Spring Boot 4.1.2 (Web MVC, Data JPA, Validation)
- PostgreSQL
- Lombok
- Maven

## Estructura del proyecto

```
src/main/java/dev/eder/ahorros/
├── calculo/        CalculadoraDeposito: tasas, monto final, interés y rendimiento
├── controller/     Endpoints REST (AhorristaController, DepositoController)
├── dto/            Request y Response de cada recurso, con validaciones
├── exception/      Excepciones propias y manejador global de errores
├── mapper/         Conversión entre entidades y DTOs
├── model/          Entidades JPA (Ahorrista, Deposito)
├── repository/     Repositorios Spring Data JPA
└── service/        Interfaces y su implementación (impl/) con las reglas de negocio
```

## Cómo ejecutarlo

1. Crear la base de datos en PostgreSQL:
   ```sql
   CREATE DATABASE ahorros_db;
   ```
2. Revisar el usuario y la contraseña en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ahorros_db
   spring.datasource.username=postgres
   spring.datasource.password=TU_PASSWORD
   ```
   Las tablas se crean automáticamente (`ddl-auto=update`).
3. Levantar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```
   La API queda disponible en `http://localhost:8080`.

## Reglas de negocio

### Ahorrista
- El DNI, el email y el celular deben ser únicos.
- El email se guarda en minúsculas y sin espacios.
- No se puede eliminar un ahorrista que tenga depósitos registrados.

### Depósito
- El capital debe estar entre S/ 1,000.00 y S/ 200,000.00 y ser múltiplo de 100.
- El plazo debe estar entre 3 y 36 meses.
- La tasa mensual depende del plazo:

| Plazo           | Tasa mensual |
|-----------------|--------------|
| 3 a 11 meses    | 0.40 %       |
| 12 a 23 meses   | 0.60 %       |
| 24 a 36 meses   | 0.80 %       |

- Fórmulas (interés compuesto):
  - **Monto final** = capital × (1 + tasa / 100) ^ plazo
  - **Interés ganado** = monto final − capital
  - **Rendimiento %** = interés ganado / capital × 100

## Endpoints

### Ahorristas: `/api/ahorrista`

| Método | Ruta                    | Descripción               | Respuesta |
|--------|-------------------------|---------------------------|-----------|
| GET    | `/api/ahorrista`        | Listar ahorristas         | 200       |
| GET    | `/api/ahorrista/{id}`   | Buscar por id             | 200 / 404 |
| POST   | `/api/ahorrista`        | Crear ahorrista           | 201 / 400 / 409 |
| PUT    | `/api/ahorrista/{id}`   | Actualizar ahorrista      | 200 / 400 / 404 / 409 |
| DELETE | `/api/ahorrista/{id}`   | Eliminar ahorrista        | 204 / 404 / 409 |

**Ejemplo de request (POST / PUT):**
```json
{
  "dni": "12345678",
  "nombres": "Luis Torres Huamán",
  "email": "luis.torres@gmail.com",
  "celular": "987654321"
}
```

Validaciones:
- `dni`: 8 dígitos.
- `nombres`: obligatorio, máximo 100 caracteres.
- `email`: formato válido, máximo 100 caracteres.
- `celular`: 9 dígitos y empieza con 9.

### Depósitos: `/api/deposito`

| Método | Ruta                                   | Descripción                    | Respuesta |
|--------|----------------------------------------|--------------------------------|-----------|
| GET    | `/api/deposito`                        | Listar depósitos               | 200       |
| GET    | `/api/deposito/{id}`                   | Buscar por id                  | 200 / 404 |
| GET    | `/api/deposito/ahorrista/{ahorristaId}`| Depósitos de un ahorrista      | 200 / 404 |
| POST   | `/api/deposito`                        | Crear depósito                 | 201 / 400 / 404 / 409 |
| DELETE | `/api/deposito/{id}`                   | Eliminar depósito              | 204 / 404 |

**Ejemplo de request (POST):**
```json
{
  "ahorristaId": 1,
  "capital": 10000.00,
  "plazoMeses": 12
}
```

**Ejemplo de respuesta:**
```json
{
  "id": 1,
  "ahorristaId": 1,
  "ahorrista": "Luis Torres Huamán",
  "capital": 10000.00,
  "plazoMeses": 12,
  "tasaMensual": 0.60,
  "montoFinal": 10744.24,
  "interesGanado": 744.24,
  "rendimientoPorcentaje": 7.44,
  "fechaRegistro": "2026-10-28T10:00:00"
}
```

## Manejo de errores

Todos los errores responden con este formato:

```json
{
  "fecha": "2026-10-28T10:00:00",
  "estado": 400,
  "mensaje": "Hay datos invalidos en la peticion",
  "errores": {
    "capital": "La capital minimo es de S/ 1000.00"
  }
}
```

| Código | Cuándo ocurre |
|--------|---------------|
| 400    | Datos inválidos o JSON mal formado |
| 404    | El ahorrista o el depósito no existe |
| 409    | Dato duplicado (DNI, email, celular) o regla de negocio incumplida |
| 500    | Error inesperado del servidor |
