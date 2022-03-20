package ru.pronin.study.vkr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.pronin.study.vkr.controllers.entities.req.LimitOrderModel;
import ru.pronin.study.vkr.tradeBot.brokerAPI.BrokerDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomCandle;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomOrder;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomPlacedOrder;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomPortfolio;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCandleResolution;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("/api/tinkoff")
@RestController
public class TinkoffBrokerController {

    @Autowired
    @Qualifier("tinkoffBrokerDAO")
    BrokerDAO broker;

    @GetMapping("/getRequiredNumberOfCandles")
    public List<CustomCandle> getRequiredNumberOfCandles(@RequestParam boolean USASession,
                                                         @RequestParam String figi,
                                                         @RequestParam int numberOfCandles,
                                                         @RequestParam String resolutionName) throws Exception {
        return broker
                .getInstrumentDAO()
                .getRequiredNumberOfCandles(USASession, figi, numberOfCandles, CustomCandleResolution.getResolution(resolutionName));
    }

    @GetMapping("/getCandlesFromDateTime")
    public List<CustomCandle> getCandlesFromDateTime(@RequestParam boolean USASession,
                                                     @RequestParam String figi,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDate,
                                                     @RequestParam String resolutionName) throws Exception {
        return broker
                .getInstrumentDAO()
                .getCandlesFromDateTime(
                        USASession,
                        figi,
                        startDate,
                        CustomCandleResolution.getResolution(resolutionName));
    }

    @GetMapping("/getPortfolio")
    public CustomPortfolio getPortfolio() {
        return broker.getPortfolioDAO().getPortfolio();
    }

    @GetMapping("/getOrders")
    public List<CustomOrder> getOrders() throws ExecutionException, InterruptedException {
        return broker.getTradingDAO().getOrders();
    }

    @PostMapping("/placeLimitOrder")
    public CustomPlacedOrder placeLimitOrder(@RequestBody LimitOrderModel limitOrderModel) throws ExecutionException, InterruptedException {
        return broker.getTradingDAO().placeLimitOrder(
                limitOrderModel.getFigi(),
                limitOrderModel.getLots(),
                limitOrderModel.getOperationType(),
                limitOrderModel.getActivationPrice()
        );
    }

    @PostMapping("/placeMarketOrder")
    public CustomPlacedOrder placeMarketOrder(@RequestBody LimitOrderModel limitOrderModel) throws ExecutionException, InterruptedException {
        return broker.getTradingDAO().placeMarketOrder(
                limitOrderModel.getFigi(),
                limitOrderModel.getLots(),
                limitOrderModel.getOperationType()
        );
    }

    @GetMapping("/cancelOrder")
    public void cancelOrder(@RequestParam String orderID) {
        broker.getTradingDAO().cancelOrder(orderID);
    }
}