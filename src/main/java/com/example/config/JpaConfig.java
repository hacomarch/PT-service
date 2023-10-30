package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //엔티티의 생성일시, 수정일시를 자동화하기 위해 사용
@Configuration
public class JpaConfig {
}
