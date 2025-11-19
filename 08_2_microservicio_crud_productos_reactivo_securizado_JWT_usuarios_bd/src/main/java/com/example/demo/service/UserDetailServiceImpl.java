package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUserName(String user) {
        return userRepository.findByUser(user) //Mono<Usuario>
                .flatMap((Usuario us) -> rolRepository.findByIdUser(user)   //Flux<Rol>
                        .map(r->r.getId().getRol()) //Flux<String>
                        .collectList()//Mono<List<String>>
                        .map(roles -> User.withUsername(us.getUser())
                                .password(us.getPwd())
                                .roles(roles.toArray(new String[0]))
                                .build())) //Mono<UserDetails>
                .switchIfEmpty(Mono.empty());
    }
}
