package com.kh.oneway.auth.model.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public org.springframework.security.core.userdetails.UserDetails
    loadUserByUsername(String username) {

        // TODO: 사용자 조회 로직 (JWT/인증 담당자 구현 예정)
        throw new org.springframework.security.core.userdetails.UsernameNotFoundException(
                "UserDetailsServiceImpl 미구현 상태"
        );
    }
}

