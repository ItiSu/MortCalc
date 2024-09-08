import java.text.NumberFormat;

public class MortgageReport {
    private final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private final MortgageCalculator calculator;

    public MortgageReport(MortgageCalculator calculator) {
        this.calculator = calculator;
    }

    public void printMortgage() {
        double mortgage = calculator.calculateMortgage();
        String mortgageCurrency = currency.format(mortgage);
        System.out.println("\nMORTGAGE\n--------\nMonthly Payments: " + mortgageCurrency + "\n");
    }

    public void printPaymentSchedule() {
        System.out.println("PAYMENT SCHEDULE\n----------------");
        for (double balance : calculator.getRemainingBalances()) {
            System.out.println(currency.format(balance));
        }
    }
}
