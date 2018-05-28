package application.filters;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.NotAuthorizedException;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

public class TokenManager {

    private static Key key = new SecretKeySpec("itsakey".getBytes(),0,"itsakey".length(),"DES");


    private static Date[] generateIssuedAtAndExpirationInArray(){
        Date[] issuedAtAndExpiration = new Date[2];

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        Date issuedAt = calendar.getTime();
        calendar.add(Calendar.DATE,2);
        Date expiration = calendar.getTime();

        issuedAtAndExpiration[0] = issuedAt;
        issuedAtAndExpiration[1] = expiration;

        return issuedAtAndExpiration;
    }

    public static String generateToken(String login){

        Date[] dates = generateIssuedAtAndExpirationInArray();

        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(login+"/staff")
                .setIssuedAt(dates[0])
                .setExpiration(dates[1])
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }

    public static String generateTokenWithRole(String login, String role){

        Date[] dates = generateIssuedAtAndExpirationInArray();

        String jwtToken = Jwts.builder()
            .setSubject(login)
            .setIssuedAt(dates[0])
            .setExpiration(dates[1])
            .claim("role",role)
            .signWith(SignatureAlgorithm.HS512, key)
            .compact();
        return jwtToken;
    }

    public static boolean verifyToken(String token){

        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            Date expiration = claimsJws.getBody().getExpiration();
            return expiration.after(new Date());
        }catch (ExpiredJwtException e){
            throw new NotAuthorizedException("");
        }

    }

    public static boolean verifyTokenWithRole(String token, String role){

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        String roleClaim = (String)claimsJws.getBody().get("role");
        return verifyToken(token) && roleClaim.equals(role);
    }

    public static String getTokenFromAuthorizationHeader(String authorization){
        try {
            String token = authorization.substring("Bearer".length());
            return token;
        }catch(NullPointerException e){
            throw new NotAuthorizedException("You are not authorized");
        }
    }
}
