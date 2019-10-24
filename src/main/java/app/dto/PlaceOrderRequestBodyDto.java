package app.dto;

public class PlaceOrderRequestBodyDto {
    private Integer sessionId;
    private Integer itemToOrderId;
    private Integer userId;

    public Integer getSessionId() {
        return sessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getItemToOrderId() {
        return itemToOrderId;
    }

    public void setItemToOrderId(Integer itemToOrderId) {
        this.itemToOrderId = itemToOrderId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
