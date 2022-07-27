package com.bezkoder.spring.datajpa.service;
import com.google.firebase.messaging.*;

import org.springframework.stereotype.Service;
import com.bezkoder.spring.datajpa.model.Note;

@Service
public class FireBaseService {

    private final FirebaseMessaging firebaseMessaging;

    public FireBaseService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }


    public String sendNotification(Note note, String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();

        return firebaseMessaging.send(message);
    }

}