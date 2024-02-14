package msjo.example.camunda.camundanzia.process;

import java.util.Map;
import java.time.Duration;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import msjo.example.camunda.camundanzia.dto.ChargeCreditCardVariables;
import msjo.example.camunda.camundanzia.service.CreditCardService;
import msjo.example.camunda.camundanzia.service.CreditCardServiceException;
import msjo.example.camunda.camundanzia.service.InvalidCreditCardException;

@Component
public class ChargeCreditCardWorker {
    final CreditCardService creditService = new CreditCardService();

    @JobWorker(type="chargeCreditCard")
    public Map<String, Object> handleChargeCreditCard(@VariablesAsType ChargeCreditCardVariables variables, 
                                final ActivatedJob job, final JobClient client) throws RuntimeException {
        try {
            final String confirmation = creditService.chargeCreditCard(variables);

            Map<String, Object> returnFromWorker = new HashMap<String, Object>();
            returnFromWorker.put("confirmation", confirmation);
            return returnFromWorker;
            //variables.setConfirmation(confirmation);
        } catch (CreditCardServiceException e) {
            //BPMN Incident 테스트 용도
            int retries = job.getRetries();
            client.newFailCommand(job.getKey())
                .retries(retries-1)
                .retryBackoff(Duration.ofSeconds(20))
                .errorMessage(e.getMessage())
                .send()
                .join();
            return null;
            //e.printStackTrace();
        } catch (InvalidCreditCardException e) {
            //BPMN Error 테스트 용도
            client.newThrowErrorCommand(job.getKey())
                .errorCode("cardExpiryDateError")
                .send()
                .join();
            return null;
        }

    }
}
