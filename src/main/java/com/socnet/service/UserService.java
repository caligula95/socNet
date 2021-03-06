package com.socnet.service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socnet.dto.BasicUserDto;
import com.socnet.dto.UserWithFollowingStatusDto;
import com.socnet.dto.UserWithFriendsDto;
import com.socnet.persistence.entities.Notification;
import com.socnet.persistence.entities.User;
import com.socnet.persistence.repository.UsersRepository;
import com.socnet.service.propertymaps.UserWithStatusMap;

@Service
public class UserService {
    private UsernameStorage principal;
    private UsersRepository usersRepository;
    private ModelMapper modelMapper;
    private OnlineUsersStorage onlineUsersStorage;

    @Autowired
    public UserService(UsernameStorage principal, UsersRepository usersRepository,
                       ModelMapper modelMapper, OnlineUsersStorage onlineUsersStorage) {
        this.principal = principal;
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.onlineUsersStorage = onlineUsersStorage;
    }

    @Transactional
    public User getCurrentUser() {
        if (principal.getUsername() == null) {
            return null;
        }
        return usersRepository.findByUsername(principal.getUsername());
    }

    @Transactional
    public User findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    //replaced by getCurrentUserWithFriends() -- RL
//    //get users with with just username, firstName, lastName field
//    //it is possibly stuped solution for self reference json formatting problem
//    @Transactional
//    public Set<User> getCurrentUserFriends() {
//        User user = usersRepository.findByUsername(principal.getUsername());
//        Set<User> friends = new HashSet<>();
//        User newUser;
//        for (User u : user.getFriends()) {
//            newUser = new User();
//            newUser.setId(u.getId());
//            newUser.setUsername(u.getUsername());
//            newUser.setFirstName(u.getFirstName());
//            newUser.setLastName(u.getLastName());
//            friends.add(newUser);
//        }
//
//        return friends;
//    }

    @Transactional
    public UserWithFriendsDto getCurrentUserWithFriends() {
        User user = usersRepository.findByUsername(principal.getUsername());
        return modelMapper.map(user, UserWithFriendsDto.class);
    }


//    //replaced by getUserWithFriends  RL
//    public Set<User> getUserFriends(String username) {
//
//        User user = usersRepository.findByUsername(username);
//        Set<User> friends = new HashSet<>();
//        User newUser;
//        for (User u : user.getFriends()) {
//            newUser = new User();
//            newUser.setId(u.getId());
//            newUser.setUsername(u.getUsername());
//            newUser.setFirstName(u.getFirstName());
//            newUser.setLastName(u.getLastName());
//            friends.add(newUser);
//        }
//        return friends;
//    }

    @Transactional
    public UserWithFriendsDto getUserWithFriends(String username) {

        User user = usersRepository.findByUsername(username);
        return modelMapper.map(user, UserWithFriendsDto.class);
    }


//    replaced by getAllUsers
//    @Transactional
//    public Set<User> getAllUsersInfo() {
//
//        Set<User> allUsers = new HashSet<>();
//        User newUser;
//        for (User u : usersRepository.findAll()) {
//            newUser = new User();
//            newUser.setId(u.getId());
//            newUser.setUsername(u.getUsername());
//            newUser.setFirstName(u.getFirstName());
//            newUser.setLastName(u.getLastName());
//            allUsers.add(newUser);
//        }
//
//        return allUsers;
//    }

    @Transactional
    public Set<BasicUserDto> getAllUsers() {

        Set<BasicUserDto> allUsers = new HashSet<>();
        for (User user : usersRepository.findAll()) {
            allUsers.add(modelMapper.map(user, BasicUserDto.class));
        }
        return allUsers;
    }

    @Transactional
    public Notification addNotificationToUser(String username, String eventType) {
        User receiver = findUserByUsername(username);
        User author = usersRepository.findByUsername(principal.getUsername());

        Notification notification = new Notification();
        notification.setAuthor(author);
        receiver.addNotification(notification);
        notification.setEventType(eventType);

        usersRepository.save(receiver);
        return notification;
    }

    @Transactional
    public void removeNotificationFromUser(String username, Long notificationId) {
        User user = usersRepository.findByUsername(username);
        user.getNotifications().removeIf(notification -> notification.getId().equals(notificationId));
        usersRepository.save(user);
    }

    @Transactional
    public Set<Notification> getUserNotifications(String username) {
        User user = usersRepository.findByUsername(username);

        Set<Notification> result = new TreeSet<>(new Comparator<Notification>() {
            @Override
            public int compare(Notification o1, Notification o2) {
                return (int) (o1.getId() - o2.getId());
            }
        });

        for (Notification n : user.getNotifications()) {
            result.add(n);
        }

        return result;
    }

    @Transactional
    public Set<Notification> getCurrentUserNotifications() {
        User user = getCurrentUser();

        Set<Notification> result = new TreeSet<>(new Comparator<Notification>() {
            @Override
            public int compare(Notification o1, Notification o2) {
                return (int) (o1.getId() - o2.getId());
            }
        });

        for (Notification n : user.getNotifications()) {
            result.add(n);
        }

        return result;
    }

    @Transactional
    public boolean isCurrentUserFriendOf(User maybeFriend) {
        return getCurrentUser().getFriends().contains(maybeFriend);
    }

    @Transactional
    public void addCurrentUserFollowing(String username) {
        User currentUser = getCurrentUser();
        User following = usersRepository.findByUsername(username);
        currentUser.addFollowing(following);
        usersRepository.save(currentUser);
    }
    
    @Transactional
	public void removeCurrentUserFollowing(String followingUsername) {
    	User currentUser = getCurrentUser();
    	User following = usersRepository.findByUsername(followingUsername);
    	currentUser.removeFollowing(following);
    	usersRepository.save(currentUser);
	}

    @Transactional
    public boolean isCurrentUserFollowing(User maybeFollowing) {
        return getCurrentUser().getFollowings().contains(maybeFollowing);
    }

    @Transactional
    public boolean isUserFollowsCurrentUser(String username) {
        User user = usersRepository.findByUsername(username);
        return user.getFollowings().contains(getCurrentUser());
    }

    @Transactional
    public boolean isCurrentUserFollowerOf(User maybeFollower) {
        return getFollowersOfUser(principal.getUsername()).contains(maybeFollower);
    }

    @Transactional
    public List<BasicUserDto> getFollowersOfCurrentUser() {
        return getFollowersOfUser(getCurrentUser().getUsername());
    }
    
    // TODO: move getFollowersOfUser logic to UsersRepository
    // TODO: test for N+1 query problem
    @Transactional
    public List<BasicUserDto> getFollowersOfUser(String username) {
    	User user = usersRepository.findByUsername(username);
    	Set<User> followers = new HashSet<>(user.getFollowers());
    	Set<User> followings = user.getFollowings();
    	followers.removeAll(followings);
    	
    	List<BasicUserDto> userDtos = followers.stream()
    	        .map(u -> modelMapper.map(u, BasicUserDto.class))
    	        .collect(Collectors.toList());
    	    	
   
        return userDtos;
    }
    
    @Transactional
    public List<UserWithFollowingStatusDto> getAllUsersWithCurrentUserFollowingStatus(){
    	User currentUser = usersRepository.findByUsername(principal.getName());
    	Set<User> allUsers = usersRepository.findAll();
    	
    	if(modelMapper.getTypeMap(User.class, UserWithFollowingStatusDto.class) == null){
    		modelMapper.addMappings(new UserWithStatusMap(currentUser));
    	}
    	
    	return allUsers.stream()
    				   		   .map(u -> modelMapper.map(u , UserWithFollowingStatusDto.class))
    				   		   .collect(Collectors.toList());
    }

    
    
    
    
    
    
    
    
    
    
    //      replaced 28.11 RL
//    @Transactional
//    public Set<User> getFollowersOfUser(String username) {
//        User user = usersRepository.findByUsername(username);
//        Set<User> follower = new HashSet<>();
//        User newUser = new User();
//        Set<Notification> notifications = user.getNotifications();
//        for (Notification n : notifications) {
//            newUser = n.getAuthor();
//            follower.add(newUser);
//        }
//        Set<User> friends = getUserWithFriends(username).getFriends();
//        follower.removeAll(friends);
//        return follower;
//    }

    public boolean isUserOnline(String username) {
        return onlineUsersStorage.isOnline(username);
    }


}