package msjo.example.camunda.camundanzia.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import msjo.example.camunda.camundanzia.service.CreditCardService;

/*
 * service.CreditCardService는 Domain Service이고, 이것을 수행하기 위한 Adapter의 역할을 하는 Handler를 만든다. 이 Handler를 Worker를 통해서 Zeebe에 붙이는(subscribe) 것이다.
 */
public class CreditCardServiceHandler implements JobHandler{

    CreditCardService creditCardService = new CreditCardService();

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        creditCardService.chargeCreditCard(null);

        // job이 끝났음을 Zeebe에게 알려줌
        client.newCompleteCommand(job.getKey()).send().join();
    }
    
}
