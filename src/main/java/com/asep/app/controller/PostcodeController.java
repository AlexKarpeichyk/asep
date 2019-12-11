package com.asep.app.controller;

import com.asep.app.entity.Postcode;
import com.asep.app.entity.Property;
import com.asep.app.entity.RadiusRequest;
import com.asep.app.exceptions.LimitExceededException;
import com.asep.app.exceptions.PostcodeNotFoundException;
import com.asep.app.helper.Constant;
import com.asep.app.service.PostcodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/property", "/"})
@CrossOrigin(origins = "*")
public class PostcodeController implements Constant {
    @Autowired
    PostcodeService postcodeService;

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/property/add", headers = "Accept=application/json")
    public ResponseEntity addPostcode(@RequestBody List<Postcode> postcodes) {
        List<Postcode> codes = postcodeService.addPostcode(postcodes);
        if (codes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Postcodes already exist.");
        }
        return new ResponseEntity<>(codes, HttpStatus.OK);
    }

    @GetMapping(value = "/property/{postcode}", headers = "Accept=application/json")
    public Postcode getByPostcode(@PathVariable("postcode") String postcode) throws PostcodeNotFoundException {
        return postcodeService.getPostcode(postcode);
    }

    @GetMapping(value = "/property/test/{amount}", headers = "Accept=application/json")
    public ResponseEntity testDataFetch(@PathVariable("amount") int amount) {
        if (amount < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LimitExceededException(ERROR_NO_PROPERTIES_REQUESTED));
        }
        List<Property> properties = postcodeService.test(amount);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @PostMapping(value = "/property/radius", headers = "Accept=application/json")
    public ResponseEntity propertiesWithinRadius(@RequestBody RadiusRequest request) {
        if(request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RuntimeException(ERROR_REQUEST_NULL));
        }
        List<Property> properties = postcodeService.getPropertiesWithinRadius(request);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }
}