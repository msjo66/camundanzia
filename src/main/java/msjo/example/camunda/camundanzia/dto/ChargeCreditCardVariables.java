package msjo.example.camunda.camundanzia.dto;

public class ChargeCreditCardVariables {
    private String reference;
    private Double amount;
    private String cardNumber;
    private String cardExpiry; // MM/YYYY
    private String CVC;

    //private String confirmation;

    @Override
    public String toString() {
        return "ChargeCreditCardVariables [reference=" + reference + ", cardNumber=" + cardNumber + "]";
    }
    public String getReference() {
        return reference;
    }
    public ChargeCreditCardVariables setReference(String reference) {
        this.reference = reference;
        return this;
    }
    public Double getAmount() {
        return amount;
    }
    public ChargeCreditCardVariables setAmount(Double amount) {
        this.amount = amount;
        return this;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public ChargeCreditCardVariables setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }
    public String getCardExpiry() {
        return cardExpiry;
    }
    public ChargeCreditCardVariables setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
        return this;
    }
    public String getCVC() {
        return CVC;
    }
    public ChargeCreditCardVariables setCVC(String cVC) {
        CVC = cVC;
        return this;
    }
    // public String getConfirmation() {
    //     return confirmation;
    // }
    // public ChargeCreditCardVariables setConfirmation(String confirmation) {
    //     this.confirmation = confirmation;
    //     return this;
    // }


    
}
