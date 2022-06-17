package com.frosqh.paikeaserver.webserver.controllers;

import com.frosqh.paikeaserver.database.MyUserDAO;
import com.frosqh.paikeaserver.database.User;
import com.frosqh.paikeaserver.webserver.utils.Gravatar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @RequestMapping("/")
    public String index(Model model, OAuth2AuthenticationToken authentication){

        if (authentication.isAuthenticated()) {

            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

            String userInfoEndpointUri = client.getClientRegistration()
                    .getProviderDetails()
                    .getUserInfoEndpoint()
                    .getUri();

            if (!StringUtils.isEmpty(userInfoEndpointUri)) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                        .getTokenValue());

                HttpEntity<String> entity = new HttpEntity<String>("", headers);

                ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
                Map userAttributes = response.getBody();
                User user = new MyUserDAO().getUserByGoogleId(userAttributes);
                model.addAttribute("name", user.username);
                model.addAttribute("picture", Gravatar.getIconURL(user.mail));
            }
        }

        return "homepage";
    }

}
