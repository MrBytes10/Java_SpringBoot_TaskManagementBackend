// // src/main/java/com/taskmanagementbackend/taskmgtappv1/config/JwtProperties.java
// package com.taskmanagementbackend.taskmgtappv1.config;

// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.stereotype.Component;

// @Component
// @ConfigurationProperties(prefix = "jwt")
// public class JwtProperties {
//     private String secret;
//     private long expirationMs;
//     private String issuer;

//     // Getters and setters
//     public String getSecret() {
//         return secret;
//     }

//     public void setSecret(String secret) {
//         this.secret = secret;
//     }

//     public long getExpirationMs() {
//         return expirationMs;
//     }

//     public void setExpirationMs(long expirationMs) {
//         this.expirationMs = expirationMs;
//     }

//     public String getIssuer() {
//         return issuer;
//     }

//     public void setIssuer(String issuer) {
//         this.issuer = issuer;
//     }
// }