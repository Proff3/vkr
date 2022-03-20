package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import ru.pronin.study.vkr.tradeBot.brokerAPI.*;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.OrdersContextInitializationException;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.PortfolioInitializationException;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.StreamInitializationException;
import ru.tinkoff.invest.openapi.*;
import ru.tinkoff.invest.openapi.model.rest.*;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApi;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class BrokerDAOTinkoffImpl extends BrokerDAO {

    public MarketContext MARKET;
    public SandboxContext SANDBOX;
    public OrdersContext ORDERS;
    public PortfolioContext PORTFOLIO;
    public StreamingContext STREAM;
    private String accountID;
    private final InstrumentsDataDAO instrumentsDataDAO = new InstrumentsDataDAOTinkoffImpl();
    private final SubscriptionDAO subscriptionDAO = new SubscriptionDAOTinkoffImpl();
    private final TradingDAO tradingDAO = new TradingDAOTinkoffImpl();
    private final PortfolioDAO portfolioDAO = new PortfolioDAOTinkoffImpl();


    public BrokerDAOTinkoffImpl(Boolean sandboxMode,
                                InstrumentsDataDAOTinkoffImpl instrumentsDataDAO,
                                SubscriptionDAOTinkoffImpl subscriptionDAO,
                                TradingDAOTinkoffImpl tradingDAO,
                                PortfolioDAOTinkoffImpl portfolioDAO) {
        super(instrumentsDataDAO, subscriptionDAO, tradingDAO, portfolioDAO);
        OpenApi api = new OkHttpOpenApi(getToken(), sandboxMode);
        SANDBOX = api.getSandboxContext();
        MARKET = api.getMarketContext();
        ORDERS = api.getOrdersContext();
        PORTFOLIO = api.getPortfolioContext();
        STREAM = api.getStreamingContext();
        instrumentsDataDAO.setMARKET(MARKET);
        SandboxRegisterRequest registerRequest = new SandboxRegisterRequest();
        registerRequest.setBrokerAccountType(BrokerAccountType.TINKOFF);
        try {
            if (sandboxMode) {
                accountID = SANDBOX.performRegistration(registerRequest).join().getBrokerAccountId();
                SandboxSetCurrencyBalanceRequest balanceRequest = new SandboxSetCurrencyBalanceRequest();
                balanceRequest.setBalance(BigDecimal.valueOf(100_000));
                balanceRequest.setCurrency(SandboxCurrency.USD);
                SANDBOX.setCurrencyBalance(balanceRequest, accountID).get();
            } else {
                //Плохой код
                accountID = api.getUserContext().getAccounts().get().getAccounts().get(0).getBrokerAccountId();
            }
            subscriptionDAO.setSTREAM(STREAM);
            tradingDAO.setORDERS(ORDERS);
            portfolioDAO.setPORTFOLIO(PORTFOLIO);
        } catch (StreamInitializationException |
                ExecutionException |
                InterruptedException |
                OrdersContextInitializationException |
                PortfolioInitializationException e) {
            e.printStackTrace();
        }
        tradingDAO.setBrokerAccountID(accountID);
        portfolioDAO.setBrokerAccountID(accountID);
    }

    private String getToken() {
        Properties prop = new Properties();
        FileReader fr;
        try {
            fr = new FileReader("./src/main/resources/application.properties");
            prop.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty("tinkoff.sandbox.token");
    }
}

