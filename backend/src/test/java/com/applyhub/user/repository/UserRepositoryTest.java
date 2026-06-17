package com.applyhub.user.repository;

import static org.assertj.core.api.Assertions.assertThat;                                                                                                                                                         
                                                                                                                                                                                                                    
import com.applyhub.user.domain.User;                                                                                                                                                                             
import org.junit.jupiter.api.DisplayName;                                                                                                                                                                         
import org.junit.jupiter.api.Test;                                                                                                                                                                                
import org.springframework.beans.factory.annotation.Autowired;                                                                                                                                                    
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;                                                                                                                                           
                                                                                                                                                                                                                    
@DataJpaTest                                                                                                                                                                                                      
public class UserRepositoryTest {                                                                                                                                                                                        
                                                                                                                                                                                                                    
    @Autowired                                                                                                                                                                                                    
    private UserRepository userRepository;                                                                                                                                                                        
                                                                                                                                                                                                                    
    @Test                                                                                                                                                                                                         
    @DisplayName("사용자를 저장하고 ID로 조회할 수 있다")                                                                                                                                                         
    void saveAndFindById() {                                                                                                                                                                                      
        User user = new User("test@example.com", "hashed-password", "테스터");                                                                                                                                    
                                                                                                                                                                                                                    
        User savedUser = userRepository.save(user);                                                                                                                                                               
                                                                                                                                                                                                                    
        assertThat(savedUser.getId()).isNotNull();                                                                                                                                                                
        assertThat(userRepository.findById(savedUser.getId()))                                                                                                                                                    
            .isPresent()                                                                                                                                                                                          
            .get()                                                                                                                                                                                                
            .extracting(User::getEmail)                                                                                                                                                                           
            .isEqualTo("test@example.com");                                                                                                                                                                       
    }                                                                                                                                                                                                             
}