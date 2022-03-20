package ru.pronin.study.vkr.tradeBot.brokerAPI.tinkoff;

import ru.pronin.study.vkr.tradeBot.brokerAPI.PortfolioDAO;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomMoneyAmount;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomPortfolio;
import ru.pronin.study.vkr.tradeBot.brokerAPI.entities.CustomPortfolioPosition;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomCurrency;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomInstrumentType;
import ru.pronin.study.vkr.tradeBot.brokerAPI.exceptions.PortfolioInitializationException;
import ru.tinkoff.invest.openapi.PortfolioContext;
import ru.tinkoff.invest.openapi.model.rest.*;

import java.util.stream.Collectors;

public class PortfolioDAOTinkoffImpl implements PortfolioDAO {

    private String brokerAccountID;
    private PortfolioContext PORTFOLIO;

    @Override
    public CustomPortfolio getPortfolio() {
        Portfolio originPortfolio = PORTFOLIO.getPortfolio(brokerAccountID).join();
        return fromOriginPortfolioToCustom(originPortfolio);
    }

    public void setBrokerAccountID(String brokerAccountID) {
        this.brokerAccountID = brokerAccountID;
    }

    public void setPORTFOLIO(PortfolioContext PORTFOLIO) throws PortfolioInitializationException {
        if (this.PORTFOLIO != null) throw new PortfolioInitializationException();
        this.PORTFOLIO = PORTFOLIO;
    }

    private CustomPortfolio fromOriginPortfolioToCustom(Portfolio portfolio){
        CustomPortfolio customPortfolio = new CustomPortfolio();
        customPortfolio.setPositions(
                portfolio.getPositions()
                        .stream()
                        .map(PortfolioDAOTinkoffImpl::fromOriginPortfolioPositionToCustom)
                        .collect(Collectors.toList())
        );
        return customPortfolio;
    }

    private static PortfolioPosition fromCustomPortfolioPositionToOrigin(CustomPortfolioPosition customPosition){
        PortfolioPosition position = new PortfolioPosition();
        position.setName(customPosition.getName());
        position.setTicker(customPosition.getTicker());
        position.setFigi(customPosition.getFigi());
        position.setIsin(customPosition.getIsin());
        position.setLots(customPosition.getLots());
        position.setBalance(customPosition.getBalance());
        position.setBlocked(customPosition.getBlocked());
        position.setInstrumentType(InstrumentType.fromValue(customPosition.getInstrumentType().toString()));
        if (customPosition.getAveragePositionPrice() != null) {
            MoneyAmount averagePositionPrice = new MoneyAmount();
            averagePositionPrice.setCurrency(Currency.fromValue(customPosition.getAveragePositionPrice().getCurrency().getValue()));
            averagePositionPrice.setValue(customPosition.getAveragePositionPrice().getValue());
            position.setAveragePositionPrice(averagePositionPrice);
        }
        if (customPosition.getAveragePositionPriceNoNkd() != null){
            MoneyAmount averagePositionPriceNoNkd = new MoneyAmount();
            averagePositionPriceNoNkd.setCurrency(Currency.fromValue(customPosition.getAveragePositionPriceNoNkd().getCurrency().getValue()));
            averagePositionPriceNoNkd.setValue(customPosition.getAveragePositionPriceNoNkd().getValue());
            position.setAveragePositionPriceNoNkd(averagePositionPriceNoNkd);
        }
        if (customPosition.getExpectedYield() != null){
            MoneyAmount expectedYield = new MoneyAmount();
            expectedYield.setCurrency(Currency.fromValue(customPosition.getExpectedYield().getCurrency().getValue()));
            expectedYield.setValue(customPosition.getExpectedYield().getValue());
            position.setExpectedYield(expectedYield);
        }
        return position;
    }

    private static CustomPortfolioPosition fromOriginPortfolioPositionToCustom(PortfolioPosition originPosition){
        CustomPortfolioPosition position = new CustomPortfolioPosition();
        position.setName(originPosition.getName());
        position.setTicker(originPosition.getTicker());
        position.setFigi(originPosition.getFigi());
        position.setIsin(originPosition.getIsin());
        position.setLots(originPosition.getLots());
        position.setBalance(originPosition.getBalance());
        position.setBlocked(originPosition.getBlocked());
        position.setInstrumentType(CustomInstrumentType.fromValue(originPosition.getInstrumentType().toString()));
        if (originPosition.getAveragePositionPrice() != null) {
            CustomMoneyAmount averagePositionPrice = new CustomMoneyAmount(
                    CustomCurrency.fromValue(originPosition.getAveragePositionPrice().getCurrency().getValue()),
                    originPosition.getAveragePositionPrice().getValue()
            );
            position.setAveragePositionPrice(averagePositionPrice);
        }
        if (originPosition.getAveragePositionPriceNoNkd() != null){
            CustomMoneyAmount averagePositionPriceNoNkd = new CustomMoneyAmount(
                    CustomCurrency.fromValue(originPosition.getAveragePositionPriceNoNkd().getCurrency().getValue()),
                    originPosition.getAveragePositionPriceNoNkd().getValue()
            );
            position.setAveragePositionPriceNoNkd(averagePositionPriceNoNkd);
        }
        if (originPosition.getExpectedYield() != null){
            CustomMoneyAmount expectedYield = new CustomMoneyAmount(
                    CustomCurrency.fromValue(originPosition.getExpectedYield().getCurrency().getValue()),
                    originPosition.getExpectedYield().getValue()
            );
            position.setExpectedYield(expectedYield);
        }
        return position;
    }

}
