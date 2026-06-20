public class Payment {

    private String paymentId;
    private String reservationId;
    private double amount;
    private boolean paid;

    public Payment(String paymentId,
                   String reservationId,
                   double amount) {

        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.paid = false;
    }

    public void processPayment() {

        paid = true;

        System.out.println("\nPayment Successful");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Amount Paid: $" + amount);
    }
}