package com.springbootexam.upandrunning_ch6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.springbootexam.upandrunning_ch6.model.Aircraft;

@SpringBootApplication
public class UpandrunningCh6Application {

	@Bean//컴포넌트를 스프링 빈으로 인스턴스화할때 사용하는 방법 중 @Configuration 어노테이션을 포함하는 메타 어노테이션을 통해서 @Bean 어노테이션이 달린 메서드를 생성하는 방법으로 적용함
	public RedisOperations<String, Aircraft> //레디스와 상호작용하는데 필요한 기능을 지정해줌
	redisOperations(RedisConnectionFactory factory){
		Jackson2JsonRedisSerializer<Aircraft> serializer = new Jackson2JsonRedisSerializer<>(Aircraft.class);//자바객체와 JSON 레코드간 변환시 사용할 serializer를 생성(직렬화/역직렬화)

		RedisTemplate<String, Aircraft> template = new RedisTemplate<>();//스프링부트는 자동설정으로 기본적인 redis템플릿을 기능을 제공한다
		
		template.setConnectionFactory(factory);//레디스db에 커넥션을 생성하고 조회할 수 있게
		template.setDefaultSerializer(serializer);//레디스템플릿에는 특별히 serializer를 지정하지 않을 경우, 기본 serializer로 지정되는 여러 serializer가 있다. 여기서는 특별히 지정했음
		template.setKeySerializer(new StringRedisSerializer());//기본 템플릿에 aircraft를 기대하도록 설정했으므로 String 타입의 키를 변환하기 위해 StringRedisSerializer를 지정한다.

		return template; //애플리케이션 내에서 RedisOperations 빈의 구현체가 요청될 때 사용할 빈으로 template을 반환한다.
	}

	public static void main(String[] args) {
		SpringApplication.run(UpandrunningCh6Application.class, args);
		System.out.println("hello");
	}

}
