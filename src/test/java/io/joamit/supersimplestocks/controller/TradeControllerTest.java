package io.joamit.supersimplestocks.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.joamit.supersimplestocks.SuperSimpleStocksApplication;
import io.joamit.supersimplestocks.domain.Direction;
import io.joamit.supersimplestocks.domain.Trade;
import io.joamit.supersimplestocks.domain.TradeBuilder;
import io.joamit.supersimplestocks.repository.TradeRepository;
import org.joda.time.DateTime;
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

import java.sql.Timestamp;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperSimpleStocksApplication.class)
@WebAppConfiguration
public class TradeControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public static Trade getDummyTrade() {
        return TradeBuilder.aTrade()
                .withClosedAt(new Timestamp(new DateTime().getMillis()))
                .withCreatedAt(new Timestamp(new DateTime().getMillis()))
                .withDirection(Direction.BUY)
                .withId(1L)
                .withPrice(234D)
                .withQuantity(10L)
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
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.tradeRepository.deleteAllInBatch();

    }

    @Test
    public void testGetAllTrades() throws Exception {
        mockMvc.perform(get("/trade/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
        ;
    }

    @Test
    public void testInsertNewTrade() throws Exception {

        String trade = "[{\"id\": 1,\"quantity\": 100,\"direction\": \"BUY\",\"price\": 345.78}]";

        //assert if the trade was saved successfully
        mockMvc.perform(post("/trade/record")
                .content(trade)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].price", is(345.78)));
    }

    @Test
    public void calculateVolumeWeightedStockPriceTest() throws Exception {
        Trade trade = getDummyTrade();
        this.tradeRepository.save(trade);
        mockMvc.perform(get("/trade/volumeWeightedStockPrice/15"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", is(234.0)))
        ;
    }
}