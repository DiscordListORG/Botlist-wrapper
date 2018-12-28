package org.dicordlist.botlistwrapper.core.exceptions;

import org.dicordlist.botlistwrapper.core.models.Botlist;

public class InvalidResponseException extends RuntimeException {

    private final int httpCode;
    private final Botlist botlist;

    /**
     * Exception that indicates that the HTTP Request to the Botlist failed
     * @param httpCode The given httpCode
     * @param message The message
     * @param botlist The botlists where the request was sent to
     */
    public InvalidResponseException(int httpCode, String message, Botlist botlist) {
        super(message);
        this.httpCode = httpCode;
        this.botlist = botlist;
    }

    /**
     * @return The HTTP code
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * @return The botlist
     */
    public Botlist getBotlist() {
        return botlist;
    }
}
