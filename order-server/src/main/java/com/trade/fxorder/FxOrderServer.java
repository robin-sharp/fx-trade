package com.trade.fxorder;

import com.trade.EnvProfile;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FxOrderServer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(FxOrderServer.class)
				.profiles(EnvProfile.DEV_PROFILE)
				.run(args);
	}
}

