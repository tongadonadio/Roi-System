package com.roi.roiauthenticationproxy.authentication.service;

import com.roi.roiauthenticationproxy.authentication.ApplicationUtil;
import com.roi.roiauthenticationproxy.authentication.AuthenticationException;
import com.roi.roiauthenticationproxy.authentication.RoiLogger;
import com.roi.roiauthenticationproxy.authentication.SettingsHelper;
import com.roi.roiauthenticationproxy.authentication.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class JwtHelper {
    
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final Integer EXPIRATION_DAYS = 1;
    
    @Inject
    private RoiLogger logger;
    
    @Inject
    private SettingsHelper settings;
    
    public String encodeUser(UserDTO user) {
       Calendar calendar = Calendar.getInstance();
       calendar.add(Calendar.DATE, EXPIRATION_DAYS);
       String token = null;
       try {
            token = Jwts.builder()
                    .setExpiration(calendar.getTime())
                    .claim("userId", user.getId())
                    .claim("username", user.getUsername())
                    .signWith(SIGNATURE_ALGORITHM, getAppSecret().getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException e) {
            logger.error("Enconding not supported", e, JwtHelper.class);
        } catch(MalformedJwtException e) {
            logger.error("Invalid Token", e, JwtHelper.class);
        }
        return token;
    }
    
    public Long decodeId(String token) throws AuthenticationException {
      String id = decodeValue("userId", token);
      return id != null && ApplicationUtil.isNumeric(id)? Long.parseLong(id) : null;
    }
    
    public String decodeUsername(String token) throws AuthenticationException {
      return decodeValue("username", token);
    }

    private String decodeValue(String name, String token) throws AuthenticationException {
      String decodedValue = null;
      try {
        Jws<Claims> claims = Jwts.parser()
                              .setSigningKey(getAppSecret().getBytes("UTF-8"))
                              .parseClaimsJws(token);
        decodedValue = claims.getBody().get(name).toString();
      } catch (UnsupportedEncodingException e) {
         logger.error("Enconding not supported", JwtHelper.class);
      } catch (SignatureException | MalformedJwtException e) {
         logger.error("Error while trying to decode the token provided", e, JwtHelper.class);
      } 
      return decodedValue;
    }
    
    private String getAppSecret() {
        return settings.getProperty("roi.authentication.appsecret");
    }
}
