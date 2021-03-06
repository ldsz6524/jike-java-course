package week2;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @description: HttpClient实现
 * @author:
 * @create:
 * @other:
 **/

public class HttpClientTest {


    public static void main(String[] args) throws IOException {
        HttpClientTest.getHttpClient();
    }

    public static void getHttpClient() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8801");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode()==200) {
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity, "UTF-8");
            System.out.println(string);
        }
        response.close();
        httpClient.close();
    }
}
