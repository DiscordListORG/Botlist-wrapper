package org.dicordlist.botlistwrapper.core.models;

public interface AuthenticationProvider {

    String getBotlistToken(Class<? extends Botlist> botlist);

}
