package com.yata.backend.auth.service;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.auth.dto.MemberPrincipal;
import com.yata.backend.domain.member.repository.JpaMemberRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
   private final JpaMemberRepository memberRepository;

   public CustomUserDetailService(JpaMemberRepository memberRepository) {
      this.memberRepository = memberRepository;
   }

   /**
    * Locates the user based on the username. In the actual implementation, the search
    * may possibly be case sensitive, or case insensitive depending on how the
    * implementation instance is configured. In this case, the <code>UserDetails</code>
    * object that comes back may have a username that is of a different case than what
    * was actually requested..
    *
    * @param username the username identifying the user whose data is required.
    * @return a fully populated user record (never <code>null</code>)
    * @throws UsernameNotFoundException if the user could not be found or the user has no
    *                                   GrantedAuthority
    */
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Member member =  memberRepository.findByEmail(username)
              .orElseThrow(() -> new CustomLogicException(ExceptionCode.MEMBER_NONE));
         return  MemberPrincipal.create(member);
   }
}
