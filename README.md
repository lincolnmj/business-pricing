📦 Microservicio de Consulta de Precios
Microservicio que expone una API REST para consultar el precio aplicable de un producto en una fecha determinada, según marca y producto.

📚 Tabla de Contenidos
  📦 Stack Tecnológico
  🚀 Ejecución Local
  🧪 Pruebas
  🔄 Arquitectura
  📄 API - OpenAPI


📦 Stack Tecnológico
  Java 17
  Spring Boot 3.5.0
  Spring WebFlux
  R2DBC + H2 (modo en memoria)
  Arquitectura Hexagonal
  OpenAPI v3 (contract-first)
  Cucumber (pruebas BDD)
  JUnit 5

📄 API - OpenAPI
La especificación OpenAPI se encuentra en:
src/main/resources/openapi/pricing-api.yml


🚀 Ejecución Local
  Prerrequisitos
    JDK 17+
    Maven 3.8+

  # Clona el proyecto
  git clone https://github.com/lincolnmj/business-princing.git
  
  cd pricing-service
  
  # Genera recursos, compila y ejecuta
  mvn generate-sources
  
  mvn clean spring-boot:run


🧪 Pruebas
  # Ejecutar pruebas unitarias y de integración
  mvn clean test 

  # Para generar reporte JaCoCo
  mvn clean verify

  # Ver reporte JaCoCo en /business-princing/target/site/jacoco/index.html

🔄 Arquitectura
  Este microservicio sigue una arquitectura hexagonal (ports & adapters):
  domain: entidades y lógica de negocio pura
  application: casos de uso (ConsultPriceService) y puertos (PriceRetrievalPort)
  infrastructure: adaptadores REST (entrada) y base de datos (salida)

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



🔄 Resultado de pruebas de performance

  Rendimiento 499.60 solicitudes / sec
