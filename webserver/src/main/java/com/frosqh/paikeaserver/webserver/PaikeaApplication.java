package com.frosqh.paikeaserver.webserver;

import com.frosqh.paikeaserver.player.Player;
import com.frosqh.paikeaserver.starter.Starter;
import com.frosqh.paikeaserver.webserver.controllers.PlayListController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@EnableOAuth2Sso
public class PaikeaApplication {
    public static Player player = Starter.player;
    public static String sep = "myownsep";

    public static void start(String[] args) {
        SpringApplication.run(PaikeaApplication.class, args);
    }

    public static void main(String[] args) throws Exception {
        sep = generateRandomSep();
        Starter.start(new String[]{sep});
        player = Starter.player;
        new Thread(() -> {
            try {
                start(args);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(e.hashCode());
            }
        }).start();
        new PlayListController().completeModel(new Model() {
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        });

    }

    private static String generateRandomSep() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }

}
