package com.jun.mqttxplatform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "biz")
public class BizConfig {
    //@formatter:off

    /** jwt 密钥 */
    private String jwtKey;
}
