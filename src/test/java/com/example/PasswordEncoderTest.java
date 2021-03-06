package com.example;

import com.example.domain.Member;
import com.example.domain.Role;
import com.example.persistence.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Commit
public class PasswordEncoderTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void testInsert(){
        Member member = new Member();
        member.setId("manager2");
        member.setPassword(encoder.encode("manager456"));
        member.setName("๋งค๋์ 2");
        member.setRole(Role.ROLE_MANAGER);
        member.setEnabled(true);
        memberRepository.save(member);
    }
}

