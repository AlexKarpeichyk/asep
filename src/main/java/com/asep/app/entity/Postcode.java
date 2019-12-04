package com.asep.app.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "property")
public class Postcode extends BaseEntity {
    private String postcode;
    private List<Property> properties;

    public Postcode() {

    }

    public Postcode(String postcodeId, String postcode, List<Property> properties) {
        super.id = postcodeId;
        this.postcode = postcode;
        this.properties = properties;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }


    public static class PostcodeBuilder {

        private String postcodeId;
        private String postcode;
        private List<Property> properties;

        public PostcodeBuilder setPostcodeId(String postcodeId) {
            this.postcodeId = postcodeId;
            return this;
        }

        public PostcodeBuilder setPostcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public PostcodeBuilder setProperties(List<Property> properties) {
            this.properties = properties;
            return this;
        }

        public Postcode build() {
            return new Postcode(postcodeId, postcode, properties);
        }
    }


}
