package au.com.telstra.simcardactivator.SimCard;

public class SimCard {

    private String iccid;
    private String customerEmail;
    private boolean activated;

    public SimCard(String iccid, String customerEmail, boolean activated) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.activated = activated;
    }

    public SimCard(String iccid, String customerEmail) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
    }

    public SimCard(String iccid) {
        this.iccid = iccid;
    }

    public SimCard() {
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return "SimCard [iccid=" + iccid + ", customerEmail=" + customerEmail + ", activated=" + activated + "]";
    }

}
