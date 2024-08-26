package com.app.service;

import com.app.domain.AppHeaders;
import com.app.domain.ServiceData;
import com.app.util.AppUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Log4j2
public class ApiLoadService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final AppHeaders appHeaders;
    private final String apiToken;
    private final Integer apiCallLimit;
    private final ServiceData serviceData;


    public ApiLoadService(@Value("${api.url}") String apiUrl, @Value("${api.token}") String apiToken, @Value("${api.limit}") Integer apiLimit,ServiceData serviceData, RestTemplate restTemplate, AppHeaders appHeaders) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
        this.appHeaders = appHeaders;
        this.serviceData=serviceData;
        this.apiToken = "Bearer " + apiToken;
        this.apiCallLimit = apiLimit;
    }

    public void callServiceByPostHttpMethod() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.info(serviceData.getData());

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(serviceData.getData(), headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, String.class);
        log.info("SERVICE RESPONSE {}",response.getBody());
    }

    public void callServiceByGetHttpMethod() {
        HttpHeaders headers = new HttpHeaders();

        appHeaders.getHeader().forEach(headers::set);

        headers.set("Authorization", apiToken);

        String tempApiUrl = apiUrl;
        String data = "S2406106N1007241200017";
        for (int i = 0; i < apiCallLimit; i++) {
            //  log.info("data before increment {}", data);
            data = AppUtil.incrementNumbersInString(data);
            //  log.info("data after increment {}", data);

            try {

                ResponseEntity<String> response = restTemplate.exchange(tempApiUrl + data, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                log.info("SERVICE RESPONSE {}", response.getBody());
                break;
            } catch (HttpClientErrorException.Unauthorized exception) {
                log.error("UnAuthorized exception occurred {}", exception.getMessage());
                break;

            } catch (HttpClientErrorException exception) {
                log.error("Error Response From Service {}", exception.getMessage());
            }

        }

    }
}
