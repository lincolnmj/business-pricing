package com.inditex.pricing.acceptance.steps;

import com.inditex.pricing.PricingApplication;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PricingApplication.class)
@CucumberContextConfiguration
public class ConsultPriceSteps {

    @Autowired
    private WebTestClient webTestClient;

    private String date;
    private String productId;
    private String brandId;

    private WebTestClient.ResponseSpec response;

    @Given("la fecha es {string}")
    public void la_fecha_es(String fecha) {
        this.date = fecha;
    }

    @Given("el productId es {string}")
    public void el_product_id_es(String productId) {
        this.productId = productId;
    }

    @Given("el brandId es {string}")
    public void el_brand_id_es(String brandId) {
        this.brandId = brandId;
    }

    @When("consulto el endpoint de precios")
    public void consulto_el_endpoint_de_precios() {
        response = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("date", date)
                        .queryParam("productId", productId)
                        .queryParam("brandId", brandId)
                        .build())
                .exchange();
    }

    @Then("la respuesta debe ser {int}")
    public void la_respuesta_debe_ser(Integer status) {
        response.expectStatus().isEqualTo(status);
    }
}