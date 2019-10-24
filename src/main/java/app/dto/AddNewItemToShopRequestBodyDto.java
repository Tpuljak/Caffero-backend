package app.dto;

public class AddNewItemToShopRequestBodyDto {
    private String itemName;
    private Double itemPrice;
    private Integer createdByUserId;

    private Integer shopId;

    public Double getItemPrice() {
        return itemPrice;
    }

    public int getShopId() {
        return shopId;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
