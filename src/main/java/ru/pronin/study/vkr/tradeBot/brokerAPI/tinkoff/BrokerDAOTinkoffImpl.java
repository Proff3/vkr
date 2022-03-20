package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.pronin.study.vkr.ConfigurationProp;
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
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
public class BrokerDAOTinkoffImpl implements BrokerDAO {

    private String tinkoffToken;
    private String accountID;
    private final InstrumentsDataDAOTinkoffImpl instrumentsDataDAO;
    private final SubscriptionDAOTinkoffImpl subscriptionDAO;
    private final TradingDAOTinkoffImpl tradingDAO;
    private final PortfolioDAOTinkoffImpl portfolioDAO;
    private final ConfigurationProp properties;

    @Autowired
    public BrokerDAOTinkoffImpl(InstrumentsDataDAOTinkoffImpl instrumentsDataDAO,
                                SubscriptionDAOTinkoffImpl subscriptionDAO,
                                TradingDAOTinkoffImpl tradingDAO,
                                PortfolioDAOTinkoffImpl portfolioDAO,
                                ConfigurationProp properties) {
        this.instrumentsDataDAO = instrumentsDataDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.tradingDAO = tradingDAO;
        this.portfolioDAO = portfolioDAO;
        this.properties = properties;
    }

    public BrokerDAO init(boolean sandboxMode) {
        tinkoffToken = properties.getTinkoffToken();
        System.out.println(tinkoffToken + "token");
        OpenApi api = new OkHttpOpenApi(tinkoffToken, sandboxMode);
        instrumentsDataDAO.setMARKET(api.getMarketContext());
        SandboxRegisterRequest registerRequest = new SandboxRegisterRequest();
        registerRequest.setBrokerAccountType(BrokerAccountType.TINKOFF);
        try {
            if (sandboxMode) {
                accountID = api.getSandboxContext()
                        .performRegistration(registerRequest)
                        .join()
                        .getBrokerAccountId();
                SandboxSetCurrencyBalanceRequest balanceRequest = new SandboxSetCurrencyBalanceRequest();
                balanceRequest.setBalance(BigDecimal.valueOf(100_000));
                balanceRequest.setCurrency(SandboxCurrency.USD);
                api.getSandboxContext().setCurrencyBalance(balanceRequest, accountID).get();
            } else {
                //Плохой код
                accountID = api.getUserContext().getAccounts().get().getAccounts().get(0).getBrokerAccountId();
            }
            subscriptionDAO.setSTREAM(api.getStreamingContext());
            tradingDAO.setORDERS(api.getOrdersContext());
            portfolioDAO.setPORTFOLIO(api.getPortfolioContext());
        } catch (StreamInitializationException |
                ExecutionException |
                InterruptedException |
                OrdersContextInitializationException |
                PortfolioInitializationException e) {
            e.printStackTrace();
        }
        tradingDAO.setBrokerAccountID(accountID);
        portfolioDAO.setBrokerAccountID(accountID);
        return this;
    }

    @Override
    public SubscriptionDAOTinkoffImpl getSubscriptionDAO() {
        return subscriptionDAO;
    }

    @Override
    public TradingDAOTinkoffImpl getTradingDAO() {
        return tradingDAO;
    }

    @Override
    public InstrumentsDataDAO getInstrumentDAO() {
        return instrumentsDataDAO;
    }

    @Override
    public PortfolioDAOTinkoffImpl getPortfolioDAO() {
        return portfolioDAO;
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

