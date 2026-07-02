package com.archiving.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SpringEnvironmentHolder implements EnvironmentAware {

	private static volatile Environment environment;

	/* Spring Environment 반환(없으면 null) */
	public static Environment getEnvironment() {
		return environment;
	}

	@Override
	/* Spring Environment 주입 시 정적 홀더에 저장 */
	public void setEnvironment(Environment environment) {
		SpringEnvironmentHolder.environment = environment;
	}
}

