package com.loadbalancer.user.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RibbonClient(
  name = "say-hello",
  configuration = RibbonConfiguration.class)
public class UserLoadBalancerRibbonApplication {

    @LoadBalanced
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/hi")
    public String serverLocation() {
        return this.restTemplate.getForObject(
          "http://say-hello/greeting", String.class);
    }
    

	/*
	 * @RequestMapping("/hi") public Mono<String> hi(@RequestParam(value = "name",
	 * defaultValue = "Mary") String name) { return
	 * loadBalancedWebClientBuilder.build().get().uri("http://say-hello/greeting")
	 * .retrieve().bodyToMono(String.class) .map(greeting ->
	 * String.format("%s, %s!", greeting, name)); }
	 * 
	 * @RequestMapping("/hello") public Mono<String> hello(@RequestParam(value =
	 * "name", defaultValue = "John") String name) { return WebClient.builder()
	 * .filter(lbFunction) .build().get().uri("http://say-hello/greeting")
	 * .retrieve().bodyToMono(String.class) .map(greeting ->
	 * String.format("%s, %s!", greeting, name)); }
	 */

    public static void main(String[] args) {
        SpringApplication.run(UserLoadBalancerRibbonApplication.class, args);
    }
}
