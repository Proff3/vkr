package ru.pronin.study.vkr;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("prop")
public class ConfigurationProp {

    private String tinkoffToken;

    public String getTinkoffToken() {
        return tinkoffToken;
    }

    public void setTinkoffToken(String tinkoffToken) {
        this.tinkoffToken = tinkoffToken;
    }
}
