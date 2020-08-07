package com.trade.fxrates;

import com.trade.EnvProfile;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FxRateServer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(FxRateServer.class)
				.profiles(EnvProfile.DEV_PROFILE)
				.run(args);
	}
}
