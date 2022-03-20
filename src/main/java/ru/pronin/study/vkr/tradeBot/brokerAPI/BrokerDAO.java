package ru.pronin.study.vkr.tradeBot.brokerAPI;

public interface BrokerDAO {
    InstrumentsDataDAO getInstrumentDAO();
    PortfolioDAO getPortfolioDAO();
    SubscriptionDAO getSubscriptionDAO();
    TradingDAO getTradingDAO();
}
