package com.asep.app.service;

import com.asep.app.entity.Postcode;
import com.asep.app.entity.Property;
import com.asep.app.entity.RadiusRequest;
import com.asep.app.exceptions.DuplicatePostCodeException;
import com.asep.app.exceptions.LimitExceededException;
import com.asep.app.exceptions.NoPropertiesFoundException;
import com.asep.app.exceptions.PostcodeNotFoundException;
import com.asep.app.helper.Constant;
import com.asep.app.helper.DistanceCalculator;
import com.asep.app.helper.JavaGeocode;
import com.asep.app.repository.PostcodeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostcodeServiceImpl implements PostcodeService, Constant {
    @Autowired
    PostcodeRepository postcodeRepository;

    JavaGeocode latLongGetter = new JavaGeocode();
    DistanceCalculator calculator = new DistanceCalculator();

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public Postcode getPostcode(String postcode) throws PostcodeNotFoundException {
        Postcode code = null;
        try {
            code = postcodeRepository.findByPostcode(postcode);
            if (code == null) {
                throw new PostcodeNotFoundException(ERROR_POSTCODE_NOT_FOUND);
            }
            String value = new ObjectMapper().writeValueAsString(code);
            LOG.info("Returned Post Code Value is:" + value);
        } catch (JsonProcessingException jsonParseException) {
            LOG.error(jsonParseException.getMessage());
        }
        return code;
    }

    public List<Postcode> addPostcode(List<Postcode> postcodes) {
        List<Postcode> codes = new ArrayList<>();

        postcodes.forEach(code -> {
            if (!postcodeRepository.existsByPostcode(code.getPostcode())) {
                code.getProperties().forEach(p -> {
                    double[] coordinates = latLongGetter.addressConversion(p.getAddress() + ", " + code.getPostcode());
                    p.setLatitude(coordinates[0]);
                    p.setLongitude(coordinates[1]);
                });
                codes.add(code);
                postcodeRepository.save(code);
            } else {
                try {
                    throw new DuplicatePostCodeException("The Post code Already exists ");
                } catch (DuplicatePostCodeException e) {
                    e.printStackTrace();
                }
            }
        });

        return codes;
    }

    public List<Property> test(int amount) {
        List<Property> properties = new ArrayList<>();
        try {
            if (amount > 100) {
                throw new LimitExceededException(ERROR_LIMIT_EXCEEDED);
            }
            List<Property> temp;
            Postcode postcode;
            int total = ((List<Postcode>) postcodeRepository.findAll()).size();
            int index = 0;

            while (properties.size() < amount && index != total) {
                postcode = postcodeRepository.findByPostcodeNotNull(PageRequest.of(index, 1)).get(0);
                if (postcode.getProperties().size() >= 5) {
                    temp = postcode.getProperties().subList(0, 5);
                } else {
                    temp = postcode.getProperties();
                }
                if (temp.size() <= amount - properties.size()) {
                    properties.addAll(temp);
                } else {
                    properties.addAll(temp.subList(0, amount - properties.size()));
                }
                index++;
            }
            LOG.info("Returned properties: " + properties.size());
        } catch (LimitExceededException e) {
            LOG.error(e.getMessage());
        }

        return properties;
    }

    public List<Property> getPropertiesWithinRadius(RadiusRequest request) {
        List<Property> properties = new ArrayList<>();
        double[] coordinates = new double[]{request.getLatitude(), request.getLongitude()};
        double[] c = new double[2];
        postcodeRepository.findAll().forEach(postcode -> {
            postcode.getProperties().forEach(prop -> {
                c[0] = prop.getLatitude();
                c[1] = prop.getLongitude();
                double distance = calculator.getDistance(coordinates, c);
                if (distance < request.getRadius()) {
                    properties.add(prop);
                }
            });
        });
        try {
            if (properties.isEmpty()) {
                throw new NoPropertiesFoundException(ERROR_NO_PROPERTIES);
            }
        } catch(NoPropertiesFoundException e) {
            e.printStackTrace();
        }
        LOG.info("Properties found:" + properties.size());

        return properties;
    }
}
