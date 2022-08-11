package com.hnp.testingspringboot.security.ldap;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class LdapAuthProvider implements AuthenticationProvider {


    private LdapContextSource ldapContextSource;
    private LdapTemplate ldapTemplate;

    private void init() {

        this. ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl("ldap://localhost:8389/dc=springframework,dc=org");
        ldapContextSource.setAnonymousReadOnly(true);
        ldapContextSource.setUserDn("uid={0},ou=people");
        ldapContextSource.afterPropertiesSet();

        ldapTemplate = new LdapTemplate(ldapContextSource);

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        System.out.println("================================");
        System.out.println("start ldpa auth");
        init();
        Filter filter = new EqualsFilter("uid", authentication.getName());
        Boolean authenticate = ldapTemplate.authenticate(LdapUtils.emptyLdapName(), filter.encode(), authentication.getCredentials().toString());
        System.out.println("user: " + authentication.getName() + "pass: " + authentication.getCredentials().toString());
        System.out.println("=================================================");
        return null;

//        if (authenticate)
//        {
//            UserDetails userDetails = new User(authentication.getName(), authentication.getCredentials().toString()
//                    , new ArrayList<>());
//            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails,
//                    authentication.getCredentials().toString(), new ArrayList<>());
//            return auth;
//        }
//        else
//        {
//            return null;
//        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
