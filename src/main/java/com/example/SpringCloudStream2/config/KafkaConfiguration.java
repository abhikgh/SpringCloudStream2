package com.example.SpringCloudStream2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class KafkaConfiguration {

    private Random random = new Random();

    @Bean
    public Supplier<Flux<Integer>> producer(){
        return () -> Flux.interval(Duration.ofSeconds(5)).map(value -> random.nextInt(1000) + 1).log();
    }

    @Bean
    public Function<Flux<Integer>, Flux<String>> processor(){
        return integerFlux -> integerFlux.map(this::evaluateFizzBuzz).log();
    }

    @Bean
    public Consumer<String> consumer(){
        return value -> System.out.println("Consumer received :: " + value);
    }

    private String evaluateFizzBuzz(Integer value) {
        if (value % 15 == 0) {
            return "FizzBuzz";
        } else if (value % 5 == 0) {
            return "Buzz";
        } else if (value % 3 == 0) {
            return "Fizz";
        } else {
            return String.valueOf(value);
        }
    }


}
