package io.joamit.supersimplestocks.service;

import io.joamit.supersimplestocks.SuperSimpleStocksApplication;
import io.joamit.supersimplestocks.domain.Stock;
import io.joamit.supersimplestocks.repository.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static io.joamit.supersimplestocks.controller.StockControllerTest.getDummyStock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperSimpleStocksApplication.class)
@WebAppConfiguration
public class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Before
    public void setUp() throws Exception {
        this.stockRepository.deleteAllInBatch();
        Stock stock = getDummyStock();
        this.stockRepository.save(stock);

    }

    @Test
    public void calculateGBCEAllShareIndex() {
        Double gbceAllShareIndex = this.stockService.calculateGBCEAllShareIndex();
        assertEquals(6.708203932499369, gbceAllShareIndex.doubleValue(), 0.0);
    }

    @Test
    public void calculatePERatio() {
        Double peRatio = this.stockService.calculatePERatio("TEA", 13D);
        assertEquals(1.625, peRatio, 0.0);
    }

    @Test
    public void calculateDividendYield() {
        Double dividendYield = this.stockService.calculateDividendYield("TEA", 12.4D);
        assertEquals(16.129032258064516, dividendYield, 0.0);
    }

    @Test
    public void fetchAllStocks() {
        List<Stock> stocks = this.stockService.fetchAllStocks();
        assertNotNull(stocks);
        assertEquals(1, stocks.size());
    }
}