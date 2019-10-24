package app.dto;

public class GetNewQrCodeRequestBodyDto {
    private Integer shopId;
    private  Integer tableNumber;

    public Integer getShopId() {
        return shopId;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }
}
