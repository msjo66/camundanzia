package msjo.example.camunda.camundanzia;

import java.time.Duration;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.Topology;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import msjo.example.camunda.camundanzia.handler.CreditCardServiceHandler;

public class PlainCreditCardWorker {
    private static final String ZEEBE_ADDRESS = "cfa2d83d-7c86-4eec-88a5-84c51963f218.syd-1.zeebe.camunda.io:443";
    private static final String ZEEBE_CLIENT_ID = "2dO3qtHXo.yB4QuMP~JB5sXzAGxD41VB";
    private static final String ZEEBE_CLIENT_SECRET = "XNrfH0WqFiqlC0m7o_Ic8iy7bK9Hk2DyIscQsL1A7ZzpqIecxj9aYgAD-qwbiC5s";
    private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
    private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";

    public static void main(String[] args) {
        final OAuthCredentialsProvider credentialProvider = 
                new OAuthCredentialsProviderBuilder()
                .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                .audience(ZEEBE_TOKEN_AUDIENCE)
                .clientId(ZEEBE_CLIENT_ID)
                .clientSecret(ZEEBE_CLIENT_SECRET)
                .build();
        
        try (final ZeebeClient client = ZeebeClient.newClientBuilder()
                    .gatewayAddress(ZEEBE_ADDRESS)
                    .credentialsProvider(credentialProvider)
                    .build()) {
            
            // TODO : 뭔 일인 지 한 번 딱 되고 안됨
            final JobWorker creditCardWorker = client.newWorker()
                                    .jobType("chargeCreditCard")
                                    .handler(new CreditCardServiceHandler())
                                    .timeout(Duration.ofSeconds(10).toMillis())
                                    .open();
            Thread.sleep(10,000);
            System.out.println("Why????");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
