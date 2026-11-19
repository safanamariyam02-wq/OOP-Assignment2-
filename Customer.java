// ADDITIONAL CLASS 1 - stores customer details (same 7 attributes as Assignment 1).
// Members get 10% discount and 1 loyalty point for every RM10 spent.
public class Customer {

    private String customerID;
    private String customerName;
    private String phoneNumber;
    private String email;
    private int age;
    private boolean member;
    private int loyaltyPoints;

    public Customer(String customerID, String customerName, String phoneNumber,
                    String email, int age, boolean member) {
        this.customerID = customerID;
        this.member = member;
        this.loyaltyPoints = 0;
        if (!setCustomerName(customerName)) {
            this.customerName = "Guest";
        }
        if (!setPhoneNumber(phoneNumber)) {
            this.phoneNumber = "Not Provided";
        }
        if (!setEmail(email)) {
            this.email = "Not Provided";
        }
        if (!setAge(age)) {
            this.age = 0;
        }
    }

    // ---------- Getters ----------
    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public boolean isMember() {
        return member;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    // ---------- Setters ----------
    // There is no setLoyaltyPoints(): points can only be added by addLoyaltyPoints(),
    // so nobody can give themselves free points.

    public boolean setCustomerName(String customerName) {
        if (customerName != null && customerName.length() > 0) {
            this.customerName = customerName;
            return true;
        }
        return false;
    }

    // Phone must start with 01 and have 10 or 11 digits, e.g. 0123456789.
    public boolean setPhoneNumber(String phoneNumber) {
        if (phoneNumber.equals("Not Provided")) {
            this.phoneNumber = phoneNumber;
            return true;
        }
        if (!phoneNumber.startsWith("01")) {
            return false;
        }
        if (phoneNumber.length() < 10 || phoneNumber.length() > 11) {
            return false;
        }
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) {
                return false;
            }
        }
        this.phoneNumber = phoneNumber;
        return true;
    }

    public boolean setEmail(String email) {
        if (email.equals("Not Provided") || (email.contains("@") && email.contains("."))) {
            this.email = email;
            return true;
        }
        return false;
    }

    public boolean setAge(int age) {
        if (age >= 0 && age <= 120) {
            this.age = age;
            return true;
        }
        return false;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    // ---------- Other methods ----------

    public double calculateDiscount(double subtotal) {
        if (member) {
            return subtotal * 0.10;
        }
        return 0;
    }

    // Returns how many points were earned this time.
    public int addLoyaltyPoints(double totalPaid) {
        if (member) {
            int earned = (int) (totalPaid / 10);
            loyaltyPoints = loyaltyPoints + earned;
            return earned;
        }
        return 0;
    }

    // Hides the middle of the phone number on the receipt to protect privacy.
    // Example: 0123456789 -> 012****789
    public String getHiddenPhone() {
        if (phoneNumber.equals("Not Provided")) {
            return phoneNumber;
        }
        return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(phoneNumber.length() - 3);
    }
}
