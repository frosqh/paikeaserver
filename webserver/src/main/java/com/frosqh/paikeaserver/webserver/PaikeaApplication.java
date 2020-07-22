package com.frosqh.paikeaserver.webserver;

import com.frosqh.daolibrary.ConnectionNotInitException;
import com.frosqh.daolibrary.ConnectionSQLite;
import com.frosqh.daolibrary.DataBase;
import com.frosqh.paikeaserver.database.*;
import com.frosqh.paikeaserver.player.Player;
import com.frosqh.paikeaserver.settings.Settings;
import com.frosqh.paikeaserver.starter.Starter;
import com.frosqh.paikeaserver.webserver.controllers.PlayListController;
import com.frosqh.paikeaserver.webserver.dataManagementServices.SongByPlayListManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Map;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@EnableOAuth2Sso
public class PaikeaApplication {
    public static Player player;

    public static void start(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring/application-config.xml");
        SpringApplication.run(PaikeaApplication.class, args);
    }

    public static void main(String[] args) throws Exception {
        Starter.start(args);
        PaikeaApplication.player = Starter.player;
        new Thread(() -> {
            try {
                start(args);
            } catch (Exception e) {
                e.printStackTrace();
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
}
