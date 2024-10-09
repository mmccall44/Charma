/**
 * Taken from Duo Security Demonstration Clients
 * https://github.com/duosecurity/duo_client_java
 */

package com.example.charma.api.duoclient;

public class Accounts extends Http {
    private Accounts(String inMethod, String inHost, String inUri, int timeout) {
        super(inMethod, inHost, inUri, timeout);
    }

    public static class AccountsBuilder extends ClientBuilder<Accounts> {

        public AccountsBuilder(String method, String host, String uri) {
            super(method, host, uri);
        }

        @Override
        protected Accounts createClient(String method, String host, String uri, int timeout) {
            return new Accounts(method, host, uri, timeout);
        }
    }
}