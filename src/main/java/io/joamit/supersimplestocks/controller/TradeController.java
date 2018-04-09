package io.joamit.supersimplestocks.controller;

import io.joamit.supersimplestocks.domain.Trade;
import io.joamit.supersimplestocks.service.TradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Trade controller to handle all the operations based on Trade domain,
 * CRUD operations on Trade object are being exposed by its repository.
 */

@Api(value = "Trade API")
@RestController
@RequestMapping("/trade")
public class TradeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @ApiOperation(value = "Record new trades.", response = Trade[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully recorded a new trade."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public List<Trade> recordTrades(@RequestBody List<Trade> trades) {
        LOGGER.info("Received request to record {} new trades. ", trades.size());
        trades = trades.stream()
                .map(this.tradeService::recordTrade)
                .collect(Collectors.toList());
        LOGGER.info("Request processed.");
        return trades;
    }

    @ApiOperation(value = "Calculate Volume Weighted Stock Price of trades recorded in given minutes.", response = Double.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated the volume weighted stock price."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/volumeWeightedStockPrice/{minutes}", method = RequestMethod.GET)
    public Double calculateVolumeWeightedStockPrice(@PathVariable("minutes") Integer minutes) {
        LOGGER.info("Received request to calculate volume weighted stock price for trades recorded in past {} minutes.", minutes);
        Double price = this.tradeService.calculateVolumeWeightedStockPrice(minutes);
        LOGGER.info("Request Processed.");
        return price;
    }


    @ApiOperation(value = "Fetch All Trades.", response = Trade[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully processed request."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Trade> fetchAllTrades() {
        LOGGER.info("Received request to fetch all trades.");
        List<Trade> trades = this.tradeService.fetchAllTrades();
        LOGGER.info("Processed request successfully!");
        return trades;
    }

}
