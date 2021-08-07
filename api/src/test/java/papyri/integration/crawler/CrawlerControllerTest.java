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
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

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
        void token_provided_return_valid_results() {
//            var token = client.toBlocking().exchange(
//                    HttpRequest.GET("/auth/clientcredential").accept(MediaType.APPLICATION_JSON)).toString();

            var token = "BQDcbzTgp3m5MqnPy2swgRil6ooFIN36fiZZutL2Cmh0AqVVu23hXCqdkp8xARsM6rKu-722xfacKtH7UU3t0GGdywBnMzTf";
            var response = client.toBlocking().exchange(
                    //HttpRequest.GET("/playlists/127855684")
                    HttpRequest.GET("/me/tracks")
                            .bearerAuth(token)
                            .accept(MediaType.APPLICATION_JSON));

            assertTrue(response != null);
        }

//    @Test
//    public void testIndex() throws Exception {
//        try (RxHttpClient client = embeddedServer.getApplicationContext().createBean(RxHttpClient.class, embeddedServer.getURL())) {
//            assertEquals(HttpStatus.OK, client.toBlocking().exchange("/hello").status());
//        }
//    }

}

