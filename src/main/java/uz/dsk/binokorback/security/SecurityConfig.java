package uz.dsk.binokorback.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.dsk.binokorback.filter.CustomAuthenticationFilter;
import uz.dsk.binokorback.filter.CustomAuthorizationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter((authenticationManagerBean()));
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(
                "/api/token/refresh/**",
                "/login/**",
                "/meneger/**",
                "/catalog/**",
                "/imagecatalog/download/catalogs/*",
                "/imagecatalog/get",
                "/imagecatalog/save",
                "/kompleks/**",
                "/make/**",
                "/house/get",
                "/api/les/**",
                "/news/**",
                "/job/**",
                "/dom/**",
                "/orderb/**",
                "/ligthuser/**",
                "/imagedata/**").permitAll();
//        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLL_USER");
        http.authorizeRequests().antMatchers(GET, "/api/users/**").hasAnyAuthority("ADMIN", "SUPER_ADMIN");
//        http.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/les/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAnyAuthority("ADMIN");
//        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLL_MENEGER");
//        http.authorizeRequests().antMatchers(POST, ).permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
