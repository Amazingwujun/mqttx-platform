package com.jun.mqttxplatform;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@SpringBootTest
class MqttxPlatformApplicationTests {

    @Test
    void contextLoads() {
        try {
            Algorithm algorithm = Algorithm.HMAC256(" ");

            byte[] sign = algorithm.sign("123456".getBytes(StandardCharsets.UTF_8));
            System.out.println(new String(sign, StandardCharsets.UTF_8));

            String token = JWT.create()
                    .withIssuer("mqttx")
                    .withClaim("userId", 1)
                    .sign(algorithm);


            JWTVerifier build = JWT.require(algorithm)
                    .build();
            DecodedJWT verify = build.verify(token);
            System.out.println(verify.getClaim("userId").asInt());
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
        }
    }

    @Autowired
    private ResourceLoader resourceLoader;

    private KeyStore keyStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void SslUtils() throws IOException, KeyStoreException,
            CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeySpecException {
        System.out.println(passwordEncoder.encode("mqttx123"));

        Resource pk = resourceLoader.getResource("classpath:tls/mqttx.keystore");

        keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(pk.getInputStream(), "123456".toCharArray());

        RSAPrivateCrtKey key = (RSAPrivateCrtKey) keyStore.getKey("mqttx", "123456".toCharArray());
        RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);

        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, key);
        String token = JWT.create()
                .withIssuer("mqttx")
                .withClaim("userId", 1)
                .sign(algorithm);

        System.out.println(token);
        JWTVerifier build = JWT.require(algorithm)
                .build();
        DecodedJWT verify = build.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzUxMiJ9.eyJpc3MiOiJtcXR0eCIsInVzZXJJZCI6MX0.KhIFlPtRHJGVjUYf04qON_NsQ188T94U1S3hO2mHaYL72Lv2CAmuoSi86eP1ImUqu_3YdCi0qPSELCJobWoV5uF1iQpQ6ENzZTdbJFQ-NxfCGL71fAS9vnOvNLWkt7HINCMNOl9ZSMUOal2rXllCbR4gIOw0VRyLvmd41NZZMyaIFttJUZPqflhJ0rXGr8SWXnTb6_f8Xcl0fAT1Vo9U7q3zT0Xr0UYy52rzLB8qeisGy88_gki8nD7J6_ZfMhmIMOVA63ltG933YH8rfIe0H16VJ5YqFvkIYBBT6c-bDIhjHAoRerUxhObNmV90CRWzwRYmVCPGuJB96eEdfUAB7g");
        System.out.println(new String(Base64.getDecoder().decode(verify.getHeader())));
        System.out.println(verify.getClaim("userId").asInt());
    }

}
