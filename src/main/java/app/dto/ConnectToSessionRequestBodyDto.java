package app.dto;

public class ConnectToSessionRequestBodyDto {
    private Integer qrCodeId;
    private Integer userId;

    public Integer getQrCodeId() {
        return qrCodeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setQrCodeId(Integer qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
