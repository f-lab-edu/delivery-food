package com.deliveryfood.controller;

import com.deliveryfood.model.UserInput;
import com.deliveryfood.model.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    private static Connection c;

    private final static String DB_TYPE = "MYSQL";

    @PostMapping("/certification")
    public void certification(@RequestParam int code) {
        // 입력한 코드로 본인 인증
    }

    @PostMapping("/signin")
    public void signin(UserInput userInput) {
        // 가게 회원 가입
    }

    @PostMapping("/signout")
    public void signout(UserRequest userRequest) {
        // 가게 회원 탈퇴 (session을 삭제할 뿐 정보의 변경은 없다.)
    }

    @PostMapping("/login")
    public void login(UserRequest userRequest) {
        // 가게 회원 로그인
    }

    @PostMapping("/logout")
    public void logout() {
        // 가게 회원 로그아웃
    }

    @GetMapping("/{userId}")
    public void findUser(@PathVariable int userId) {
        // 가게 회원 조회
    }

    @PutMapping("/{userId}")
    public void modifyUser(@PathVariable int userId) {
        // 가게 회원 정보 수정
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        String DB_NAME = "";
        String DB_URL = "";
        String DB_ID = "";
        String DB_PWD = "";
        if ("H2".equals(DB_TYPE)) {
            DB_NAME = "org.h2.Driver";
            DB_URL = "jdbc:h2:tcp://localhost/~/dev";
            DB_ID = "developer";
            DB_PWD = "Chldbsgur@1234";
        } else if ("MYSQL".equals(DB_TYPE)) {
            DB_NAME = "com.mysql.jdbc.Driver";
            DB_URL = "jdbc:mysql://localhost:3306/dev";
            DB_ID = "developer";
            DB_PWD = "Chldbsgur@1234";
        }
        Class.forName(DB_NAME);
        c = DriverManager.getConnection(DB_URL, DB_ID, DB_PWD);
        return c;
    }

    //레스토랑을 조회한다.
    @GetMapping("/restaurants/{restaurantId}")
    public Map searchRestaurants(@PathVariable int restaurantId) throws ClassNotFoundException, SQLException {
        getConnection();
        PreparedStatement ps = c.prepareStatement(
                "select * from RESTAURANT where restaurantId = ?"
        );
        ps.setInt(1, restaurantId);

        ResultSet rs = ps.executeQuery();
        rs.next();

        Map resultMap = new HashMap();
        resultMap.put("userId", rs.getInt("userId"));
        resultMap.put("name", rs.getString("name"));

        rs.close();
        ps.close();
        c.close();

        return resultMap;
    }

    //레스토랑을 생성한다.
    @PostMapping("/restaurants/{restaurantId}")
    public void createRestaurant(@PathVariable int restaurantId, int userId, String name) throws ClassNotFoundException, SQLException {
        getConnection();
        PreparedStatement ps = c.prepareStatement(
                "insert into RESTAURANT(restaurantId, userId, name) values(?,?,?)"
        );
        ps.setInt(1, restaurantId);
        ps.setInt(2, userId);
        ps.setString(3, name);

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    //메뉴를 생성한다.
    @PostMapping("/restaurants/{restaurantId}/menus")
    public void createMenus(@PathVariable int restaurantId, int menuId, String name) throws ClassNotFoundException, SQLException {
        getConnection();
        PreparedStatement ps = c.prepareStatement(
                "insert into MENU(menuId, restaurantId, name) values(?,?,?)"
        );
        ps.setInt(1, menuId);
        ps.setInt(2, restaurantId);
        ps.setString(3, name);

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    //레스토랑의 메뉴들을 조회한다.
    @GetMapping("/restaurants/{restaurantId}/menus")
    public List searchMenusByRestaurantId(@PathVariable int restaurantId) throws ClassNotFoundException, SQLException {
        getConnection();
        PreparedStatement ps = c.prepareStatement(
                "select b.* from RESTAURANT a, MENU b where b.restaurantId = b.restaurantId and a.restaurantId = ?"
        );
        ps.setInt(1, restaurantId);

        ResultSet rs = ps.executeQuery();

        List resultList = new ArrayList<>();
        while (rs.next()) {
            Map resultMap = new HashMap();
            resultMap.put("menuId", rs.getInt("menuId"));
            resultMap.put("restaurantId", rs.getInt("restaurantId"));
            resultMap.put("name", rs.getString("name"));

            resultList.add(resultMap);
        }

        rs.close();
        ps.close();
        c.close();

        return resultList;
    }

    //메뉴를 조회한다.
    @GetMapping("/restaurants/{restaurantId}/menus/{menuId}")
    public Map searchMenuByRestaurantIdAndMenuId(@PathVariable int restaurantId, @PathVariable int menuId) throws ClassNotFoundException, SQLException {
        getConnection();
        PreparedStatement ps = c.prepareStatement(
                "select b.* from RESTAURANT a, MENU b where a.restaurantId = b.restaurantId and b.restaurantId = ? and b.menuId = ?"
        );
        ps.setInt(1, restaurantId);
        ps.setInt(2, menuId);

        ResultSet rs = ps.executeQuery();
        rs.next();

        Map resultMap = new HashMap();
        resultMap.put("menuId", rs.getInt("menuId"));
        resultMap.put("restaurantId", rs.getInt("restaurantId"));
        resultMap.put("name", rs.getString("name"));

        rs.close();
        ps.close();
        c.close();

        return resultMap;
    }

    //메뉴 정보를 수정한다.
    @PutMapping("/menu/{restaurantId}/{menuId}")
    public void updateMenusByRestaurantIdAndMenuId(@PathVariable int restaurantId, @PathVariable int menuId, String name) throws ClassNotFoundException, SQLException {
        getConnection();
        PreparedStatement ps = c.prepareStatement(
                "update MENU set name = ? where menuId = ? and restaurantId = ?"
        );
        ps.setString(1, name);
        ps.setInt(2, menuId);
        ps.setInt(3, restaurantId);

        ps.executeUpdate();

        ps.close();
        c.close();
    }
}