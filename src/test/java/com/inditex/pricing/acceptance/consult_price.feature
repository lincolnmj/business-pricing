Feature: Consultar precio de un producto

  Scenario: No se encuentra un precio válido para la fecha, marca y producto
    Given la fecha es "2021-01-01T00:00:00"
    And el productId es "35455"
    And el brandId es "1"
    When consulto el endpoint de precios
    Then la respuesta debe ser 204