package com.springbootexam.upandrunning_ch6.model;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.springbootexam.upandrunning_ch6.dao.AircraftRepository;

@EnableScheduling
@Component
public class PlaneFinderPoller {
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");
    private final RedisConnectionFactory connectionFactory;
    //private final RedisOperations<String, Aircraft> redisOperations; //템플릿에서 repository로 변경하면서 제거되는 부분
    private final AircraftRepository repository; //템플릿에서 repository로 변경하면서 추가된 부분

    PlaneFinderPoller(RedisConnectionFactory connectionFactory,
    // RedisOperations<String, Aircraft> redisOperations) //템플릿에서 repository로 변경하면서 제거되는 부분
        AircraftRepository repository) //템플릿에서 repository로 변경하면서 추가된 부분
    {
        this.connectionFactory = connectionFactory;
        //this.redisOperations = redisOperations; //템플릿에서 repository로 변경하면서 제거되는 부분
        this.repository = repository; //템플릿에서 repository로 변경하면서 추가된 부분
    }

    @Scheduled(fixedRate = 1000)
    private void pollPlanes(){
        connectionFactory.getConnection().serverCommands().flushDb(); //flushDb는 존재하는 모든 키를 지움

        client.get() //http://localhost:7634/aircraft 호출
                .retrieve() //값을 받아옴
                .bodyToFlux(Aircraft.class) //받아온 값을 Aircraft 객체로 변환
                .filter(plane -> !plane.getReg().isEmpty()) //reg가 없는 것은 제외하도록 필터처리
                .toStream() //flux에서 Aircraft타입의 stream으로 변환
                //.forEach(ac -> redisOperations.opsForValue().set(ac.getReg(), ac)); //템플릿에서 repository로 변경하면서 제거되는 부분
                .forEach(repository::save); //템플릿에서 repository로 변경하면서 추가된 부분

        //템플릿에서 repository로 변경하면서 제거되는 부분
        // redisOperations.opsForValue() 
        //         .getOperations()
        //         .keys("*")
        //         .forEach(ac -> System.out.println(redisOperations.opsForValue().get(ac)));
        
        //템플릿에서 repository로 변경하면서 추가된 부분
        repository.findAll().forEach(System.out::println);
    }
}
