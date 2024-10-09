/**
 * Taken from Duo Security Demonstration Clients
 * https://github.com/duosecurity/duo_client_java
 */

package com.example.charma.api.duoclient;

public class Auth extends Http {
    private Auth(String inMethod, String inHost, String inUri, int timeout) {
        super(inMethod, inHost, inUri, timeout);
    }

    public static class AuthBuilder extends ClientBuilder<Auth> {

        public AuthBuilder(String method, String host, String uri) {
            super(method, host, uri);
        }

        @Override
        protected Auth createClient(String method, String host, String uri, int timeout) {
            return new Auth(method, host, uri, timeout);
        }

    }
}