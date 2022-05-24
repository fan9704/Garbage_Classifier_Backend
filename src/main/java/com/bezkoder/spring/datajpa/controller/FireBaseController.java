package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Note;
import com.bezkoder.spring.datajpa.service.FireBaseService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class FireBaseController {
    @Autowired
    private FireBaseService firebaseService;
    @RequestMapping("/send-notification")
    @ResponseBody
    public String sendNotification(@RequestBody Note note,
                                   @RequestParam String token) throws FirebaseMessagingException {

        return firebaseService.sendNotification(note, token);
    }
}
