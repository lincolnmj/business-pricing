📦 Microservicio de Consulta de Precios

    Este microservicio expone una API REST reactiva que permite consultar el precio aplicable de un producto en una fecha determinada, considerando el identificador de producto y de marca. Implementa arquitectura hexagonal y enfoque contract-first con OpenAPI.


📚 Tabla de Contenidos
  
    📦 Stack Tecnológico
    🧩 Componentes clave
    🔁 Flujo de ejecución típico
    🔄 Principios aplicados
    🧱 Aplicación de SOLID en el Microservicio de Consulta de Precios
    🚀 Ejecución Local
    🧪 Pruebas
    🔄 Arquitectura
    📄 API - OpenAPI


📦 Stack Tecnológico

    | Tecnología       | Descripción                                                                 |
    |------------------|------------------------------------------------------------------------------|
    | Java 17          | Lenguaje base del microservicio                                              |
    | Spring Boot 3.5  | Framework principal para crear microservicios modernos                       |
    | Spring WebFlux   | Programación reactiva no bloqueante con `Mono` y `Flux`                     |
    | R2DBC + H2       | Conector reactivo para persistencia en base de datos en memoria             |
    | Arquitectura Hexagonal | Patrón que separa dominio, aplicación y adaptadores                     |
    | OpenAPI v3       | Contrato de la API REST en formato `YAML`, usado para generar interfaces     |
    | JUnit 5          | Framework para pruebas unitarias                                             | 
    | JaCoCo           | Herramienta para reporte de cobertura de pruebas                             |
    | Caffeine         | Herramienta para manejo de caché para no consultar constantemente Marcas     |

🧩 Componentes clave

    Componente                  Descripción
    RetrievePriceUseCase	    Puerto de entrada que define el contrato del caso de uso
    RetrievePriceService	    Implementación del caso de uso que orquesta la lógica
    RetrievePriceRepositoryPort Puerto de salida que abstrae la persistencia
    H2RepositoryAdapter         Adaptador que implementa el puerto de salida usando R2DBC
    PriceController	            Adaptador de entrada generado a partir del contrato OpenAPI
    PriceSelectorService	    Servicio de dominio puro que encapsula la lógica para elegir la tarifa
    PriceMapper                 Conversor entre el modelo de dominio y los DTO expuestos por la API

🔁 Flujo de ejecución típico
    
    1. Entrada: El PriceController expone el endpoint /prices generado desde pricing-api.yml.
    2. Aplicación: El controller llama a RetrievePriceUseCase, implementado por RetrievePriceService.
    3. Dominio: El RetrievePriceService puede delegar lógica a PriceSelectorService para aplicar reglas de negocio (por ejemplo, seleccionar la tarifa con mayor prioridad).
    4. Persistencia: Se accede a la base de datos a través del puerto RetrievePriceRepositoryPort, implementado por un adaptador H2 usando R2DBC.
    5. Salida: El resultado se transforma a DTO mediante PriceMapper y se devuelve como respuesta HTTP.

🔄 Principios aplicados

    Principio                   Aplicación
    Hexagonal Architecture      Separación estricta entre dominio, puertos y adaptadores
    Contract-First              API definida con OpenAPI y generada automáticamente
    Reactive Programming        Todo el stack usa Mono<> y Flux<> con Spring WebFlux
    DDD Ligero                  Entidades, excepciones y servicios de dominio sin acoplamiento
    Separation of Concerns      Cada capa tiene una única responsabilidad

🧱 Aplicación de SOLID en el Microservicio de Consulta de Precios

| Principio                               | Aplicación                                                                                                                                                                                                                                                                                              |
| --------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **S** — Single Responsibility Principle | Cada clase tiene una única responsabilidad: <br> - `RetrievePriceService`: orquesta el caso de uso. <br> - `PriceSelectorService`: lógica de negocio pura. <br> - `PriceMapper`: conversión entre dominio y DTO. <br> - `PriceController`: manejo HTTP.                                                 |
| **O** — Open/Closed Principle           | El sistema está abierto a extensión, pero cerrado a modificación. <br> Puedes agregar nuevos adaptadores de persistencia (ej. PostgreSQL) implementando `RetrievePriceRepositoryPort` sin tocar el núcleo.                                                                                              |
| **L** — Liskov Substitution Principle   | Se pueden intercambiar implementaciones sin romper el sistema. <br> Por ejemplo, sustituir la implementación de `RetrievePriceRepositoryPort` sin afectar a `RetrievePriceService`.                                                                                                                     |
| **I** — Interface Segregation Principle | Las interfaces son pequeñas, específicas y enfocadas: <br> - `RetrievePriceUseCase` solo define `retrievePrice(...)`. <br> - `RetrievePriceRepositoryPort` solo define `findAllPrices(...)`.                                                                                                                |
| **D** — Dependency Inversion Principle  | Las clases de alto nivel dependen de abstracciones, no de detalles: <br> - `RetrievePriceService` depende del puerto `RetrievePriceRepositoryPort`. <br> - `PriceController` depende del puerto `RetrievePriceUseCase`. <br> Las dependencias se inyectan vía constructor (`@RequiredArgsConstructor`). |


📄 API - OpenAPI

    La especificación OpenAPI se encuentra en:
    src/main/resources/openapi/pricing-api.yml


🚀 Ejecución Local
  
    Requisitos
        JDK 17+
        Maven 3.8+

    # Clona el proyecto
    git clone https://github.com/lincolnmj/business-princing.git
    cd pricing-service
  
    # Genera recursos, compila y ejecuta
    mvn generate-sources
    mvn clean spring-boot:run
    La aplicación estará disponible en http://localhost:8080.


🧪 Pruebas
    
    # Ejecutar pruebas unitarias y de integración
    mvn clean test

    # Para generar reporte JaCoCo
    mvn clean verify

Puedes ver el reporte de cobertura JaCoCo en:

    /target/site/jacoco/index.html

🔄 Arquitectura
    
    El microservicio sigue una arquitectura hexagonal (Ports & Adapters), con separación clara entre:
    
    Capa	        Descripción
    domain	        Entidades, objetos de valor y lógica de negocio pura
    application     Casos de uso (ej. RetrievePriceUseCase) y puertos (ej. RetrievePriceRepositoryPort)
    infrastructure	Adaptadores entrantes (WebFlux Controllers) y salientes (R2DBC Repositories)




           ┌──────────────────────┐
           │  REST Controller     │◄─────── Entrada (WebFlux)
           └──────────────────────┘
                     │
                     ▼
           ┌──────────────────────┐
           │ Use Case: ConsultPrice│
           └──────────────────────┘
                     │
                     ▼
           ┌──────────────────────┐
           │  PriceRetrievalPort  │───────► H2 R2DBC Repository
           └──────────────────────┘

Conceptos utilizados:

    . Programación reactiva: Toda la cadena es Mono-based.
    . Contract-first: Se genera el controlador a partir del openapi.yml.
    . DDD ligero: Separación de dominio, con entidades y servicios de dominio independientes de frameworks.

📂 Estructura de paquetes

    com.inditex.pricing
    ├── application           # Lógica de aplicación: casos de uso y orquestación
    │   ├── port              # Puertos que definen los contratos de entrada y salida
    │   │   ├── in            # Entrada: definidos por controladores o interfaces
    │   │   └── out           # Salida: definidos por adaptadores hacia BD, APIs, etc.
    │   └── service           # Implementaciones del caso de uso 
    ├── config                # Beans y configuración del contenedor de Spring
    ├── domain                # Núcleo de negocio, sin dependencia de frameworks
    │   ├── exception         # Excepciones propias del dominio
    │   ├── model             # Entidades y objetos de valor
    │   └── service           # Servicios de dominio puro (ej: lógica de selección)
    ├── infrastructure.adapter  # Adaptadores entrantes (Web) y salientes (DB)
    │   ├── in.web            # Adaptador HTTP (WebFlux Controller)
    │   │   └── mapper        # Conversión DTO ↔ Dominio
    │   └── out               # Adaptadores de salida (ej: repositorios R2DBC)
    ├── PricingApplication    # Clase principal del microservicio

📄 API - OpenAPI
El contrato de la API REST se encuentra en:

    /resources/openapi/pricing-api.yml

Se utiliza OpenAPI Generator para crear la interfaz PricesApiSpec, implementada por el controlador.


🗃️ Caché con Caffeine

Este proyecto utiliza Caffeine como motor de caché en memoria, con una política de expiración por acceso de 12 horas y un tamaño máximo de 100 elementos. Se usa específicamente para evitar consultas repetidas a la tabla de marcas (Brand):
    
    private final AsyncCache<Long, BrandEntity> cache = Caffeine.newBuilder()
    .maximumSize(100)
    .expireAfterAccess(Duration.ofHours(12))
    .buildAsync();

📊 Resultado de pruebas de performance
⚙ Ejecutado con Autocannon simulando carga sobre WebFlux.

    . Rendimiento promedio: 499.60 solicitudes/segundo
