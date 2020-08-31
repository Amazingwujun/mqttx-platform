package com.jun.mqttxplatform;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class MqttxPlatformApplicationTests {

	@Test
	void contextLoads() {
		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			String token = JWT.create()
					.withIssuer("mqttx")
					.withClaim("userId",1)
					.sign(algorithm);

			JWTVerifier build = JWT.require(algorithm)
					.build();
			DecodedJWT verify = build.verify(token);
			System.out.println(verify.getClaim("userId").asInt());
		} catch (JWTCreationException exception){
			//Invalid Signing configuration / Couldn't convert Claims.
			exception.printStackTrace();
		}
	}

}
