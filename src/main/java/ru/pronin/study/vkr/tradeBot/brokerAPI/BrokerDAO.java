package ru.pronin.study.vkr.tradeBot.brokerAPI;

public abstract class BrokerDAO {

    private final InstrumentsDataDAO instrumentsDataDAO;
    private final SubscriptionDAO subscriptionDAO;
    private final TradingDAO tradingDAO;
    private final PortfolioDAO portfolioDAO;

    public BrokerDAO(
            InstrumentsDataDAO instrumentsDataDAO,
            SubscriptionDAO subscriptionDAO,
            TradingDAO tradingDAO,
            PortfolioDAO portfolioDAO) {
        this.instrumentsDataDAO = instrumentsDataDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.tradingDAO = tradingDAO;
        this.portfolioDAO = portfolioDAO;
    }

    public InstrumentsDataDAO getInstrumentsDataDAO() {
        return instrumentsDataDAO;
    }

    public SubscriptionDAO getSubscriptionDAO() {
        return subscriptionDAO;
    }

    public TradingDAO getTradingDAO() {
        return tradingDAO;
    }

    public PortfolioDAO getPortfolioDAO() {
        return portfolioDAO;
    }
}