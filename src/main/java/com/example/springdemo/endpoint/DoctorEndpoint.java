package com.example.springdemo.endpoint;


import com.example.springdemo.service.DoctorService;
import com.example.springdemo.soap.DoctorRequest;
import com.example.springdemo.soap.DoctorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class DoctorEndpoint {

    private static final String NAMESPACE = "http://www.example.com/springdemo/soap";

    @Autowired
    private DoctorService service;

    @PayloadRoot(namespace = NAMESPACE, localPart = "DoctorRequest")
    @ResponsePayload
    public DoctorResponse getActivities(@RequestPayload DoctorRequest doctorRequest)
    {
        return service.viewActivityHistory(doctorRequest);
    }
}
