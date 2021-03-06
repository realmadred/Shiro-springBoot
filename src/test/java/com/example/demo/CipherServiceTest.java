package com.example.demo;

import com.example.demo.util.Constant;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.junit.Test;

import java.security.Key;

/**
 * @auther Administrator
 * @date 2017/9/15
 * @description 描述
 */
public class CipherServiceTest {

    @Test
    public void cipherService(){
        AesCipherService cipherService = new AesCipherService();
        cipherService.setKeySize(128);

        //创建一个测试密钥：
        final Key key = cipherService.generateNewKey();
        String text = "hello";
        //加密
        String encrypt = cipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String decrypt = new String(cipherService.decrypt(Hex.decode(encrypt), key.getEncoded()).getBytes());

        System.out.println(encrypt+" : "+decrypt);
    }

    @Test
    public void test2() {
        String string = "perms[permsAdd]";
        final int start = string.indexOf(Constant.SECTION_PREFIX);
        final int end = string.indexOf(Constant.SECTION_SUFFIX);
        System.out.println(string.substring(start+1,end).toLowerCase());
    }
}
