package com.bezkoder.spring.datajpa.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CookieController {

    @RequestMapping(value = "/setCookie/{username}", method = RequestMethod.GET)
    public String setCookie(HttpServletResponse response,@PathVariable("username") String username) {

        //CreateCookie
        Cookie cookie = new Cookie("username", username);
        // Session Life Cycle
        cookie.setMaxAge(7*24*60*60);
        // Restrict Use Https
//        cookie.setSecure(true);
        // Place Cookie into Response
        response.addCookie(cookie);
        return "add Cookie";
    }
    @RequestMapping(value = "/getCookie", method = RequestMethod.GET)
    public String getCookie(@CookieValue(value = "username") String username) {

        return "Hello! " + username;
    }
    @RequestMapping(value = "/getCookies", method = RequestMethod.GET)
    public String getCookies(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue())
                    .collect(Collectors.joining(", "));
        }
        return "No cookies";
    }
    @RequestMapping(value = "/deleteCookie", method = RequestMethod.GET)
    public String deleteCookie(HttpServletResponse response) {

        // Set Cookie Value is Null
        Cookie cookie = new Cookie("username", null);

        // Set Expiration is 0
        cookie.setMaxAge(0);

        // Place Cookie into Response
        response.addCookie(cookie);

        return "delete Cookie";
    }
}
