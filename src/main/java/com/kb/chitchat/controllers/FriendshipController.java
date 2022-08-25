package com.kb.chitchat.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kb.chitchat.models.Friendship;
import com.kb.chitchat.models.User;
import com.kb.chitchat.services.FriendshipService;
import com.kb.chitchat.services.UserService;

@Controller
public class FriendshipController {

    @Autowired
    UserService userService;

    @Autowired
    FriendshipService friendshipService;

    private static final Logger logger = LoggerFactory.getLogger(FriendshipController.class);

    @GetMapping("/users/friends")
    public String friendsPage(Model model, HttpSession session) {
        model.addAttribute("loggedInUser", userService.findUserById((Long) session.getAttribute("id")));
        // # all users
        model.addAttribute("users", userService.allRegisteredUsers());
        

        // # all friends
        // List<Friendship> approvedFriendships = friendshipService.allFriendshipsByUserId((Long) session.getAttribute("id"));
        List<Friendship> approvedFriendships = friendshipService.allApprovedFriendshipsByUserId((Long) session.getAttribute("id"));
        model.addAttribute("approvedFriendships", approvedFriendships);
        // System.out.println("approvedFriendships: " + approvedFriendships);
        List<User> approvedFriends = new ArrayList<User>();
        for (Friendship approvedFriendship : approvedFriendships ) {
            if (approvedFriendship.getUser().getId() == (Long) session.getAttribute("id")) {
                approvedFriends.add(userService.findUser(approvedFriendship.getFriend().getId()));
            }
            else {
                approvedFriends.add(userService.findUser(approvedFriendship.getUser().getId()));
            }
        }
        model.addAttribute("approvedFriends", approvedFriends);
        // System.out.println(approvedFriends);

        // find all pending friends
        // List<Friendship> approvedFriendships = friendshipService.allFriendshipsByUserId((Long) session.getAttribute("id"));
        List<Friendship> pendingFriendships = friendshipService.allPendingFriendshipsByUserId((Long) session.getAttribute("id"));
        model.addAttribute("pendingFriendships", pendingFriendships);
        // List<User> pendingFriends = new ArrayList<User>();
        // for (Friendship pendingFriendship : pendingFriendships ) {
        //     if (pendingFriendship.getUser().getId() == (Long) session.getAttribute("id")) {
        //         pendingFriends.add(userService.findUser(pendingFriendship.getFriend().getId()));
        //     }
        //     else {
        //         pendingFriends.add(userService.findUser(pendingFriendship.getUser().getId()));
        //     }
        // }
        return "views/friends.jsp";

        
    }

    @PostMapping("/users/pendingConnect")
    public String pendingConnect(@RequestParam("loggedInUserId") Long loggedInUserId,
    @RequestParam("userId") Long userId) {
        Friendship friendship = new Friendship();
        friendship.setUser(userService.findUser(loggedInUserId));
        friendship.setFriend(userService.findUser(userId));
        friendship.setName(loggedInUserId + "_" + userId);
        friendship.setNickname(loggedInUserId + "_" + userId);
        friendship.setApproved(0);
        
        friendshipService.saveFriendship(friendship);
        
    	return "redirect:/users/friends";
    }
    
    @PostMapping("/users/approveConnect")
    public String approveConnect(@RequestParam("loggedInUserId") Long loggedInUserId,
    						 @RequestParam("userId") Long userId) {
    	Friendship friendship = friendshipService.findFriendshipBidirectional(loggedInUserId, userId);
        friendship.setApproved(1);
        friendshipService.saveFriendship(friendship);
    	return "redirect:/users/friends";
    }

    

    @PostMapping("/users/removeConnect")
    public String removeConnect(@RequestParam("loggedInUserId") Long loggedInUserId,
    						 @RequestParam("userId") Long userId) {

        // TODO: refactor this so it finds the frineds quickly without two lines
        Friendship friending = friendshipService.findFriendship(loggedInUserId, userId);
        Friendship friended = friendshipService.findFriendship(userId, loggedInUserId);

        if (friending != null) {
            friendshipService.deleteFriendship(friending);
        }
        else if (friended != null) {
            friendshipService.deleteFriendship(friended);
        }
        else {
            logger.info("No friendship found but tried to remove");
        }
    	return "redirect:/users/friends";
    }


    // Entering the private chat
    @PostMapping("/chatrooms/private/enter") 
    public String chatroomPrivateEnter(@RequestParam("loggedInUserId") Long loggedInUserId,  @RequestParam("userId") Long userId, HttpSession session, Model model) {
        System.out.println("loggedInUserId: " + loggedInUserId);
        System.out.println("userId: " + userId);

        // TODO: refactor this so it finds the frineds quickly without two lines
        Friendship friending = friendshipService.findFriendship(loggedInUserId, userId);
        Friendship friended = friendshipService.findFriendship(userId, loggedInUserId);

        if (friending != null) {
            session.setAttribute("chatroomName", friending.getName());
            return "redirect:/chatrooms/" + friending.getName();
        }
        else if (friended != null) {
            session.setAttribute("chatroomName", friended.getName());
            return "redirect:/chatrooms/" + friended.getName();
        }
        else {
            logger.info("No friendship found but tried to chat");
            return "redirect:/chatrooms/" + "nowhere";
        }
        
    }

    @GetMapping("/users/dashboard")
    public String userDashboard(Model model, HttpSession session) {
    	model.addAttribute("loggedInUser", userService.findUserById((Long) session.getAttribute("id")));
        // # all users
        model.addAttribute("users", userService.allRegisteredUsers());
        

        // # all friends
        // List<Friendship> approvedFriendships = friendshipService.allFriendshipsByUserId((Long) session.getAttribute("id"));
        List<Friendship> approvedFriendships = friendshipService.allApprovedFriendshipsByUserId((Long) session.getAttribute("id"));
        model.addAttribute("approvedFriendships", approvedFriendships);
        // System.out.println("approvedFriendships: " + approvedFriendships);
        List<User> approvedFriends = new ArrayList<User>();
        for (Friendship approvedFriendship : approvedFriendships ) {
            if (approvedFriendship.getUser().getId() == (Long) session.getAttribute("id")) {
                approvedFriends.add(userService.findUser(approvedFriendship.getFriend().getId()));
            }
            else {
                approvedFriends.add(userService.findUser(approvedFriendship.getUser().getId()));
            }
        }
        model.addAttribute("approvedFriends", approvedFriends);
        // System.out.println(approvedFriends);

        
        return "views/dashboard.jsp";
    }



}
