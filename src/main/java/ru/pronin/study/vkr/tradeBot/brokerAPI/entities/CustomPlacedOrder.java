package ru.pronin.study.vkr.tradeBot.brokerAPI.entities;

import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOperationType;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOrderStatus;
import ru.pronin.study.vkr.tradeBot.brokerAPI.enums.CustomOrderType;

public class CustomPlacedOrder extends AbstractOrder {

    private String rejectReason = null;
    private String message = null;
    private CustomMoneyAmount commission = null;

    public CustomPlacedOrder(String orderId,
                             CustomOperationType operation,
                             CustomOrderStatus status,
                             String rejectReason,
                             String message,
                             Integer requestedLots,
                             Integer executedLots,
                             CustomMoneyAmount commission,
                             CustomOrderType customOrderType) {
        super(orderId, operation, status, requestedLots, executedLots, customOrderType);
        this.rejectReason = rejectReason;
        this.message = message;
        this.commission = commission;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomMoneyAmount getCommission() {
        return commission;
    }

    public void setCommission(CustomMoneyAmount commission) {
        this.commission = commission;
    }

}
