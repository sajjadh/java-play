package Models;

public class LibraryItemFee {


    public boolean isFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(boolean feeStatus) {
        this.feeStatus = feeStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



    boolean feeStatus = false;
    double  amount;

    public LibraryItemFee(boolean feeStatus, double amount) {
        this.feeStatus = feeStatus;
        this.amount = amount;
    }

}
