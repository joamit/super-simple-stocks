package io.joamit.supersimplestocks.controller;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.joamit.supersimplestocks.SuperSimpleStocksApplication;
import io.joamit.supersimplestocks.domain.Stock;
import io.joamit.supersimplestocks.domain.StockBuilder;
import io.joamit.supersimplestocks.domain.StockType;
import io.joamit.supersimplestocks.repository.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperSimpleStocksApplication.class)
@WebAppConfiguration
public class StockControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public static Stock getDummyStock() {
        return StockBuilder.aStock()
                .withFixedDividend(2D)
                .withId("TEA")
                .withLastDividend(8D)
                .withParValue(100D)
                .withPrice(45D)
                .withType(StockType.PREFERRED)
                .build();
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.stockRepository.deleteAllInBatch();

    }

    @Test
    public void testGetAllStocks() throws Exception {
        Stock stock = getDummyStock();
        this.stockRepository.save(stock);
        mockMvc.perform(get("/stock/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].price", is(45.0)))
        ;
    }

    @Test
    public void calculateDividendYieldTest() throws Exception {
        Stock stock = getDummyStock();
        this.stockRepository.save(stock);
        mockMvc.perform(get("/stock/dividendYield/TEA/23"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", is(8.695652173913043)))
        ;
    }

    @Test
    public void calculatePERationTest() throws Exception {
        Stock stock = getDummyStock();
        this.stockRepository.save(stock);
        mockMvc.perform(get("/stock/PERatio/TEA/14"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", is(1.75)))
        ;
    }

    @Test
    public void calculateGBCEShareIndexTest() throws Exception {
        Stock stock = getDummyStock();
        this.stockRepository.save(stock);
        mockMvc.perform(get("/stock/GBCEShareIndex"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", is(6.708203932499369)))
        ;
    }


}