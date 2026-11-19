// ADDITIONAL CLASS 3 - handles paying for the order.
// There are no setters: after a payment is made, nobody can change it.
public class Payment {

    private double amountDue;
    private String method;       // Cash or E-Wallet
    private double amountPaid;
    private double change;
    private boolean paid;

    public Payment(double amountDue, String method) {
        this.amountDue = amountDue;
        this.method = method;
        this.amountPaid = 0;
        this.change = 0;
        this.paid = false;
    }

    // ---------- Getters only ----------
    public double getAmountDue() {
        return amountDue;
    }

    public String getMethod() {
        return method;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public double getChange() {
        return change;
    }

    public boolean isPaid() {
        return paid;
    }

    // Two methods with the same name but different inputs (overloading):
    // pay(cash) is for Cash, pay() is for E-Wallet.

    public boolean pay(double cash) {
        if (paid || cash < amountDue) {
            return false;
        }
        amountPaid = cash;
        change = Math.round((cash - amountDue) * 100) / 100.0;
        paid = true;
        return true;
    }

    public boolean pay() {
        if (paid) {
            return false;
        }
        amountPaid = amountDue;
        change = 0;
        paid = true;
        return true;
    }
}
