package com.csci5308.medinteract.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server")
public class ResponseController {
    @GetMapping("/healthCheck")
    public ResponseEntity healthCheck() {
        Response res = new Response("", false, "Backend server is running on the port 6969");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }
}
