package io.joamit.supersimplestocks.service;

import io.joamit.supersimplestocks.SuperSimpleStocksApplication;
import io.joamit.supersimplestocks.domain.Trade;
import io.joamit.supersimplestocks.repository.TradeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.joamit.supersimplestocks.controller.TradeControllerTest.getDummyTrade;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperSimpleStocksApplication.class)
@WebAppConfiguration
public class TradeServiceTest {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeRepository tradeRepository;

    @Before
    public void setUp() throws Exception {
        this.tradeRepository.deleteAllInBatch();
        Trade trade = getDummyTrade();
        this.tradeRepository.save(trade);
    }

    @Test
    public void recordTrade() {
        this.tradeRepository.deleteAllInBatch();
        assertEquals(0, this.tradeService.fetchAllTrades().size());
        Trade trade = this.tradeService.recordTrade(getDummyTrade());
        assertEquals(1, this.tradeService.fetchAllTrades().size());
        assertEquals(234D, trade.getPrice(), 0.0);
    }

    @Test
    public void calculateVolumeWeightedStockPrice() {
        assertEquals(234.0, this.tradeService.calculateVolumeWeightedStockPrice(15), 0.0);
    }

    @Test
    public void fetchAllTrades() {
        assertEquals(1, this.tradeService.fetchAllTrades().size());
    }
}