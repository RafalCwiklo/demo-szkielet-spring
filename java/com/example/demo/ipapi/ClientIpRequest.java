package com.example.demo.ipapi;

import lombok.Value;

@Value
public class ClientIpRequest {

    String ip;
    String format;
}
