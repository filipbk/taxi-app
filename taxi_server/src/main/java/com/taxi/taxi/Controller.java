package com.taxi.taxi;

import com.taxi.taxi.dto.test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/i")
    public ResponseEntity<?> hello() {
        return new ResponseEntity<String>("hello", HttpStatus.OK);
    }

    @GetMapping("/index1")
    public ResponseEntity<?> helloWorld() {
        return new ResponseEntity<>(new test("hello world"), HttpStatus.OK);
    }
}
