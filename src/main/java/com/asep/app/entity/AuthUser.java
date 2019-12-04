package com.asep.app.entity;

public class AuthUser extends BaseEntity {
    private String name;
    private String password;

    public AuthUser() {

    }

    public AuthUser(String id, String name, String password) {
        super.id = id;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String email) {
        this.name = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class AuthUserBuilder {
        private String id;
        private String name;
        private String password;

        public AuthUserBuilder() {
        }

        public AuthUserBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public AuthUserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AuthUserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public AuthUser build() {
            return new AuthUser(id, name, password);
        }
    }
}