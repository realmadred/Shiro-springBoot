package com.example.demo.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;

/**
 * @auther Administrator
 * @date 2017/9/14
 * @description 自定义的验证匹配
 */
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(final AuthenticationToken token, final AuthenticationInfo info) {
        Object tokenCredentials = token.getCredentials();
        Object accountCredentials = getCredentials(info);
        final SimpleAuthenticationInfo simpleAuthInfo = (SimpleAuthenticationInfo) info;
        final ByteSource salt = simpleAuthInfo.getCredentialsSalt();
        Md5Hash hash = new Md5Hash(tokenCredentials,salt);
        return equals(hash.toHex(),accountCredentials);
    }

    public static void main(String[] args) {
//        Md5Hash hash = new Md5Hash("123456","admin@8d78869f470951332959580424d4bf4f");
//        System.out.println(hash.toHex());
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        final ByteSource byteSource = generator.nextBytes();
        final String s = byteSource.toString();
        System.out.println(s);
    }
}
