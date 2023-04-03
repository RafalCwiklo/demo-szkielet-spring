package com.example.demo.ipapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/client")
public class ClientIpController {

    final private RestTemplate restTemplate = new RestTemplate();
    final private String url = "https://ipapi.co/%s/%s/";

    @GetMapping("/country/{ip}")
    public ResponseEntity<ClientIpResponse> getClientIp(@PathVariable("ip") String ip) {
        // 1. Przygotowanie zapytania
        final String requestUrl = String.format(url, ip, "json");

        // 2. Odpytanie zewnętrznego API oraz odebranie wyniku
        final ClientIpResponse response = restTemplate.getForObject(requestUrl, ClientIpResponse.class);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/country")
    public ResponseEntity<ClientIpResponse> getClientIpWithBody(@RequestBody ClientIpRequest request) {
        // 1. Przygotowanie zapytania
        final String requestUrl = String.format(url, request.getIp(), request.getFormat());

        // 2. Odpytanie zewnętrznego API oraz odebranie wyniku
        final ClientIpResponse response = restTemplate.getForObject(requestUrl, ClientIpResponse.class);

        return ResponseEntity.ok(response);
    }

}
