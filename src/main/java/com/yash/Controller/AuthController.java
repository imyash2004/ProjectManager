package com.yash.Controller;

import com.yash.config.JwtProvider;
import com.yash.models.User;
import com.yash.repo.UserRepo;
import com.yash.request.LoginRequest;
import com.yash.response.AuthResponse;
import com.yash.service.CustomerUserDetailsImpl;
import com.yash.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsImpl customerUserDetailsImpl;

    @Autowired
    private SubscriptionService subscriptionService;


    //Checkinngggggg
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user)throws Exception{
        User isUserExist=userRepo.findByEmail(user.getEmail());
        if(isUserExist!=null){
            throw new Exception("email already exist     ");
        }

        User createUser=new User();
        createUser.setEmail(user.getEmail());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));

        createUser.setFullName(user.getFullName());
        User saved =userRepo.save(createUser);



        subscriptionService.createSubscription(saved);
        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt= jwtProvider.generateToken(authentication);
        AuthResponse res=new AuthResponse();
        res.setMessage("signup completed....");
        res.setJwt(jwt);
        return new ResponseEntity<>(res, HttpStatus.CREATED);


    }



    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>signin(@RequestBody LoginRequest loginRequest){


        String username= loginRequest.getEmail();
        String password= loginRequest.getPassword();


        Authentication authentication=authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully ...");
        return new ResponseEntity<>(authResponse,HttpStatus.OK);

    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails= customerUserDetailsImpl.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("invalid username...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid Password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }


}
