package msjo.example.camunda.camundanzia.service;

import msjo.example.camunda.camundanzia.dto.ChargeCreditCardVariables;

public class CreditCardService {

    private static final String CARD_NUMBER_ERROR = "error";
    private static final String CARD_EXPIRY_ERROR = "error";
    
    public String chargeCreditCard(ChargeCreditCardVariables variables) {
        System.out.println("Starting Transaction: " + variables.getReference());
        System.out.println("Card Number: " + variables.getCardNumber());
        System.out.println("Card Expiry: " + variables.getCardExpiry());
        System.out.println("Card CVC: " + variables.getCVC());
        System.out.println("Amount: " + variables.getAmount());

        if (variables.getCardNumber().equalsIgnoreCase(CARD_NUMBER_ERROR)) {
            throw new CreditCardServiceException("Credit Card Service: Internal Error");
        }

        if (variables.getCardExpiry().equalsIgnoreCase(CARD_EXPIRY_ERROR)) {
            throw new InvalidCreditCardException("Invalid Expiry: "+ variables.getCardExpiry());
        }

        final String confirmation = String.valueOf(System.currentTimeMillis());
        System.out.println("Successful Transaction: "+ confirmation);
        return confirmation;
    }
}
