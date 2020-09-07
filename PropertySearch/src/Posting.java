import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Posting {
    private final static int ONE_BEDROOM_SUITE = 1;
    private final static int TWO_BEDROOM_SUITE = 2;
    private final static int THREE_BEDROOM_SUITE = 3;
    private final static int PRICE_ONE_BEDROOM = 1000;
    private final static int PRICE_TWO_BEDROOM = 1400;
    private final static int PRICE_THREE_BEDROOM = 1800;

    private double interestRate = 1.99;
    String address;
    int price;
    List<Integer> suites;
    int leftOverPayment;
    String url;
    String description;

    public Posting(String address, int price, String url, String description) {
        this.address = address;
        this.price = price;
        suites = new ArrayList<>();
        this.url = url;
        this.description = description;
        setLeftOverPayment();
    }

    private void setLeftOverPayment(){
        setSuites();
        int totalRentalIncome = 0;

        for(int suite : suites){
            if(suite == ONE_BEDROOM_SUITE){
                totalRentalIncome += PRICE_ONE_BEDROOM;
            } if(suite == TWO_BEDROOM_SUITE){
                totalRentalIncome += PRICE_TWO_BEDROOM;
            } if(suite == THREE_BEDROOM_SUITE){
                totalRentalIncome += PRICE_THREE_BEDROOM;
            }
        }

        double monthlyPayment = calculatedMortgage();

        this.leftOverPayment = (int) (monthlyPayment - totalRentalIncome);

    }

    private double calculatedMortgage(){
        double downPayment = 0.2 * this.price;
        double loanAmount = this.price - downPayment;

        NumberFormat currency = NumberFormat.getCurrencyInstance();

        double rate = interestRate /= 100.0;
        double monthlyRate = rate / 12.0;

        int termYears = 30 * 12;

        double monthlyPayment = (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -termYears));

        return monthlyPayment;
    }

    private void setSuites(){
        String[] lines = this.description.split("\\.");
        int number = 0;

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].matches(".*\\d.*") && lines[i].contains("suite") && !lines[i].contains("ensuite")|| lines[i].contains("basement") || lines[i].contains("mortgage helper") || lines[i].contains("rental unit")) {
                String check = lines[i].replaceAll("[^0-9]", "");
                if(check.equals("")){
                    continue;
                }
                number += Integer.parseInt(check);

            }
        }
        if(number != 0){
            suites.add(number);
        }
    }
}
