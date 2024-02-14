package msjo.example.camunda.camundanzia;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.Topology;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class SimpleZeebeClientSample {
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

            System.out.println("Requesting topology with initial contact point " + ZEEBE_ADDRESS );
            
            // Client를 통해서 보낸 Zeebe API는 모든 것이 asynchronous하다.
            // 1. Client로부터 Command Object를 얻는다.
            // 2. Command Object 별로 Configure를 한다.
            // 3. Command를 보낸다.
            // 4. Future Object에 Join을 해서 기다린 후 response를 처리한다.
            final Topology topology = client.newTopologyRequest().send().join();

            System.out.println("Topology:");
            topology.getBrokers()
                        .forEach( b -> {
                            System.out.println("    " + b.getAddress());
                            b.getPartitions().forEach( p -> {
                                System.out.println("        " + p.getPartitionId() + " - " + p.getRole());
                            });
                        });
            
            System.out.println("Done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
