package com.epam.esm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
@ComponentScan(basePackages = "com.epam.esm")
@PropertySource("classpath:security.properties")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired private Environment environment;

  @Autowired private AuthenticationManager authenticationManager;

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(environment.getProperty("signing.key"));
    return converter;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

    configurer
        .inMemory()
        .withClient(environment.getProperty("client.id"))
        .secret(environment.getProperty("client.secret"))
        .authorizedGrantTypes(
            environment.getProperty("grant.type.password"),
            environment.getProperty("autorization.code"),
            environment.getProperty("refresh.token"),
            environment.getProperty("implicit"))
        .scopes(
            environment.getProperty("scope.read"),
            environment.getProperty("scope.write"),
            environment.getProperty("trust"))
        .accessTokenValiditySeconds(
            Integer.parseInt(environment.getProperty("access.token.validity.seconds")))
        .refreshTokenValiditySeconds(
            Integer.parseInt(environment.getProperty("refresh.token.validity.seconds")));
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .tokenStore(tokenStore())
        .authenticationManager(authenticationManager)
        .accessTokenConverter(accessTokenConverter());
  }
}
