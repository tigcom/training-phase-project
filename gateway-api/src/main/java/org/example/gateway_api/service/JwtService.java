//package org.example.gateway_api.service;
//
//
//import java.security.Key;
//import java.util.Date;
//import java.util.Map;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//
//@Service
//public class JwtService {
//    /**
//     * Tạo JWS (JSON Web Token)
//     * @param userDetails là User chứa thông tin để tạo token
//     * @param expirySeconds là thời hạn có hiệu lực (tính bằng giây)
//     */
//    public String createToken(UserDetails userDetails, int expirySeconds) {
//        long now = System.currentTimeMillis();
//        return Jwts.builder()
//                .setClaims(Map.of("publisher", "Nguyễn Nghiệm"))
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(now))
//                .setExpiration(new Date(now + 1000 *expirySeconds))
//                .signWith(this.getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//    /**
//     * Xác minh user
//     * @param token là JWT gốc
//     * @param userDetails là user đăng nhập
//     * @return true nếu user được sử dụng để token đã sinh ra trước đó
//     */
//    public boolean validateToken(String token, UserDetails userDetails) {
//        Claims claims = this.getClaims(token);
//        String username = claims.getSubject();
//        Date expiration = claims.getExpiration();
//        return (username.equals(userDetails.getUsername()) && !expiration.before(new Date()));
//    }
//    /**
//     * Lấy lại UserDetails được sử dụng để tạo JWT
//     */
//    public String getUsername(String token) {
//        Claims claims = this.getClaims(token);
//        return claims.getSubject();
//    }
//    /**
//     * Tạo khóa bí mật (secret key)
//     * @return đối tượng chứa khóa bí mật
//     */
//    private Key getSignKey() {
//        String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
//        byte[] keyBytes= Decoders.BASE64.decode(SECRET); // 32 bytes
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//    /**
//     * Chuyển đổi JWT sang dữ liệu gốc
//     * @param token là JWT
//     * @return đối tượng chứa dữ liệu gốc
//     */
//    private Claims getClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(this.getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}