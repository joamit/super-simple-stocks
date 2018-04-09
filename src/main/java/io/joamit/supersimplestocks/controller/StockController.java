package io.joamit.supersimplestocks.controller;

import io.joamit.supersimplestocks.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Stock controller to handle all the operations based on Stock domain,
 * CRUD operations on Stock object are being exposed by its repository.
 */

@Api(value = "Stock API")
@RestController
@RequestMapping("/stock")
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @ApiOperation(value = "Calculate Dividend Yield for given stock with a market price.", response = Double.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated the dividend yield."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/dividendYield/{symbol}/{marketPrice}", method = RequestMethod.GET)
    public Double calculateDividendYield(@PathVariable("symbol") String symbol, @PathVariable("marketPrice") Double marketPrice) {
        LOGGER.info("Received request to calculate dividend yield for {} at {}", symbol, marketPrice);
        Double dividendYield = this.stockService.calculateDividendYield(symbol, marketPrice);
        LOGGER.info("Request successfully processed.");
        return dividendYield;
    }

    @ApiOperation(value = "Calculate P/E Ratio for given stock at a market price.", response = Double.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated the P/E Ratio."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/PERatio/{symbol}/{marketPrice}", method = RequestMethod.GET)
    public Double calculatePERatio(@PathVariable("symbol") String symbol, @PathVariable("marketPrice") Double marketPrice) {
        LOGGER.info("Received request to calculate PE Ratio for {} at {}", symbol, marketPrice);
        Double dividendYield = this.stockService.calculatePERatio(symbol, marketPrice);
        LOGGER.info("Request successfully processed.");
        return dividendYield;
    }

    @ApiOperation(value = "Calculate GBCE All Share Index.", response = Double.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated GBCE All Share Index."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/GBCEShareIndex", method = RequestMethod.GET)
    public Double calculateGBCEAllShareIndex() {
        LOGGER.info("Received request to calculate GBCE All Share Index.");
        Double gbceAllShareIndex = this.stockService.calculateGBCEAllShareIndex();
        LOGGER.info("Request successfully processed.");
        return gbceAllShareIndex;
    }
}
