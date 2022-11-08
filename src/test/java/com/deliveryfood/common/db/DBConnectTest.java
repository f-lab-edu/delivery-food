package com.deliveryfood.common.db;

import com.deliveryfood.config.JasyptConfig;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class DBConnectTest {

    @Autowired
    private JasyptConfig jasypt;

    @Test
    public void testConnection() throws Exception{
        StringEncryptor encryptor = jasypt.stringEncryptor();
        String password = encryptor.decrypt("spIXF1Au+HxIdZngAoLFMQqeACylznX+");
        String url = "jdbc:mysql://118.67.128.73:3306/df_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
        String user = "LHK";

        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println(connection);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
