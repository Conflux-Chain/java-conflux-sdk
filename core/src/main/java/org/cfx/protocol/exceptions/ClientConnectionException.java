package org.cfx.protocol.exceptions;

/** Client connection exception. */
public class ClientConnectionException extends RuntimeException {
    public ClientConnectionException(String message) {
        super(message);
    }
}
