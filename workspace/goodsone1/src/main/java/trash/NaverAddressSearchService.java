package service;

import java.net.URI;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import dto.NaverAddressApiResponse;

@PropertySource("classpath:application.properties")
@Service
public class NaverAddressSearchService {

  @Value("${naver-ai-geocoder.clientId}")
  static String clientId;
  
  @Value("${naver-ai-geocoder.clientSecret}")
  static String clientSecret;
  
  @PostConstruct
  static public void init() {
      System.out.println("clientId: " + clientId);
      System.out.println("clientSecret: " + clientSecret);
  }
  
  public void searchAddress(String query) {
    
    init();
    
    System.out.println(query);
    
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    
    String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";

    URI uri = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("query", query)
        .build()
        .encode()
        .toUri();
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-NCP-APIGW-API-KEY-ID ", clientId);
    headers.set("X-NCP-APIGW-API-KEY", clientSecret);
    headers.add("Content-Type", "application/json; charset=UTF-8");
    
    HttpEntity<String> entity = new HttpEntity<>(headers);
    
 // GET 요청 보내기
    ResponseEntity<NaverAddressApiResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, NaverAddressApiResponse.class);

    // 응답 처리
    if (response.getStatusCode().is2xxSuccessful()) {
      NaverAddressApiResponse responseBody = response.getBody();
        System.out.println("Status: " + responseBody.getStatus());
        if (responseBody.getMeta() != null) {
            System.out.println("Total Count: " + responseBody.getMeta().getTotalCount());
            System.out.println("Page: " + responseBody.getMeta().getPage());
            System.out.println("Count: " + responseBody.getMeta().getCount());
        }
        if (responseBody.getAddresses() != null) {
            for (NaverAddressApiResponse.Address address : responseBody.getAddresses()) {
                System.out.println("Road Address: " + address.getRoadAddress());
                System.out.println("Jibun Address: " + address.getJibunAddress());
                // 주소 요소 출력
                if (address.getAddressElements() != null) {
                    for (NaverAddressApiResponse.AddressElement element : address.getAddressElements()) {
                        // AddressElement 출력 로직
                    }
                }
            }
        }
    } else {
        System.out.println("Error: " + response.getStatusCode());
        if (response.getBody() != null) {
            System.out.println("Error Message: " + response.getBody().getErrorMessage());
        }
    }
    
  }
}
