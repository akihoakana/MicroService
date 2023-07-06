package com.example.UserSpringJPA8081.controller;


import com.example.UserSpringJPA8081.facade.NotificationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationFacade notificationFacade;

    @RequestMapping("/hello")
    public ResponseEntity<?> helloWord() {
        return ResponseEntity.ok("helloWord I'm notification");
    }

    @PostMapping("/Notification")
    public ResponseEntity<?> getNotification() {
        return ResponseEntity.ok(notificationFacade.getNotification());
    }

    @PostMapping("/deleteNotification/{id}")
    public ResponseEntity<?> deleteNotificationById(@PathVariable int id) {
        notificationFacade.deleteNotificationById(id);
        return ResponseEntity.ok("Đã delete");
    }

    @PostMapping("/notification/findById/{id}")
    public ResponseEntity<?> notificationFindById(@PathVariable int id) {
        return ResponseEntity.ok(notificationFacade.notificationFindById(id));
    }

    @PostMapping("/findByUsersEntity/{id}")
    public ResponseEntity<?> findByUsersEntity(@PathVariable int id) {
        return ResponseEntity.ok(notificationFacade.findByUsersEntity(id));
    }

    @PostMapping("/addNotification")
    public ResponseEntity<?> addNotification(@RequestParam(name = "userId") int userId,
                                             @RequestParam(name = "detail") String detail) {
        return ResponseEntity.ok(notificationFacade.addNotification(userId, detail));
    }

    @PostMapping("/updateNotificationById")
    public ResponseEntity<?> updateNotificationById(@RequestParam("id") int id
            , @RequestParam("detail") String detail) {
        notificationFacade.updateNotificationById(id, detail);
        return ResponseEntity.ok("Đã updated");
    }

}
