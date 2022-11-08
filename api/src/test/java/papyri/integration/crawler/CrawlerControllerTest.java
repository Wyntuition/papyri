package papyri.integration.crawler;

import com.papyri.AuthApiRequest;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class CrawlerControllerTest {

        @Inject
        @Client("/")
        HttpClient client;

        @Inject
        AuthApiRequest authApiRequest;

        @Test
        void token_not_provided_return_unauthorized() {
            HttpClientResponseException e = assertThrows(HttpClientResponseException.class, () -> {
                client.toBlocking().exchange(HttpRequest.GET("/playlists/127855684").accept(MediaType.TEXT_PLAIN));
            });

            assertEquals(HttpStatus.NOT_ACCEPTABLE,  e.getStatus()); //todo: secure my endpoints
        }

        @Test
        void token_provided_return_valid_results() throws UnsupportedEncodingException {
            // can't use clientcredentials as it doesn't have access to user resources
//            var token = client.toBlocking().exchange(
//                    HttpRequest.GET("/auth/clientcredential").accept(MediaType.APPLICATION_JSON)).body();

            // fake oauth server?
//            var token = client.toBlocking().exchange(
//                    HttpRequest.GET("/auth/login").accept(MediaType.APPLICATION_JSON)).body();
//            System.out.println("Token: " + token);


            // Getting token automatically - how do I get the value from the callback?
            // var code = authApiRequest.authorizeUrl()
//            var tokens = authApiRequest.token(code);
//            System.out.printf("Tokens response: " + tokens);
//            var token = new JSONObject(tokens).getString("access_token");

            // to get token, browse to https://accounts.spotify.com/authorize?response_type=code&client_id=07c793d9bd344b6092042b523c74de7c&scope=user-read-private,user-library-read,playlist-read-private&redirect_uri=http://localhost:8080/auth/callback&state=1234
            var token = "BQBjoe0THrR79j9MreBmyl4xnF92TU7j5ZfnM-3W72BA_ThfucmfuDYcTsxj3MOgUEUKNU3OMnnMhlI4JkJ9BaLb_2VWopmkmwF9cKUqoKDD4BbIMjQjPEQ7u8APv5_Hjnqada4fbEF2LGbmgdcNbk1lxjAq9Z5DbYM_kvUht4laac-4yfR-zcVch2CpFsIQ2O154xTSmRFUcCHKD-Q";

            var response = client.toBlocking().exchange(
                    //HttpRequest.GET("/playlists/127855684")
                    HttpRequest.GET("/me/tracks")
                            .bearerAuth(token.toString())
                            .accept(MediaType.APPLICATION_JSON));

            System.out.printf("RESPONSE:" + response.body());

            assertTrue(response != null);
        }

//    @Test
//    public void testIndex() throws Exception {
//        try (RxHttpClient client = embeddedServer.getApplicationContext().createBean(RxHttpClient.class, embeddedServer.getURL())) {
//            assertEquals(HttpStatus.OK, client.toBlocking().exchange("/hello").status());
//        }
//    }

}

