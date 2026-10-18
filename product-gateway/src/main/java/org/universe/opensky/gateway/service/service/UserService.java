package org.universe.opensky.gateway.service.service;

import org.springframework.stereotype.Service;
import org.universe.opensky.gateway.domain.User;


@Service
public class UserService {

    public User saveUser(User user){
        User user1;
        user1 = User.builder().id(1L).name("Madhu").address("Hyderabad").build();
        return user1;
    }
}
