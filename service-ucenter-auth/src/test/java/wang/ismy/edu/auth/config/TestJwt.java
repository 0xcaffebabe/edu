package wang.ismy.edu.auth.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;


import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MY
 * @date 2019/10/28 19:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJwt {

    @Test
    public void testCreate() {
        // 密钥文件
        String keystore = "xc.keystore";

        // 密钥库密码
        String keystorePassword = "xuechengkeystore";

        // 密钥别名
        String alias = "xckey";

        // 密钥访问密码
        String keyPassword = "xuecheng";

        ClassPathResource classPathResource = new ClassPathResource(keystore);
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(classPathResource, keystorePassword.toCharArray());

        // 密钥对
        KeyPair keyPair = factory.getKeyPair(alias, keyPassword.toCharArray());
        // 私钥
        RSAPrivateKey key = (RSAPrivateKey) keyPair.getPrivate();

        Map<String,String> map = new HashMap<>();
        map.put("user","my");
        Jwt jwt = JwtHelper.encode(map.toString(), new RsaSigner(key));
        System.out.println(jwt.getEncoded());
        //eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.e3VzZXI9bXl9.GxiOUiV9dmZMdW2Hj1-pOt2TjAZ615_KWOecDMKHaD-bgbhwjree_JdqmEjMcVuqEztp7ZT_SHXil9VIyt9zvzBs4gO9FAPqq8z6hWKzYqnjJ77UCRIkqTbT7MakNwghzObNH1fSj03l2dPfWSju1f2DyeHQrdwiKsgp20SvCyDwST9_WcJmG0QAnrtt_Nj0lMjXY81-vGHowCNoPq0tf13iVinV3dvCFKf4RUs3xVpGRkq33I-qr1J8IPGP1BWY7qa4xs9JeIohZpcRQ8sYE-6BSUmQG79GIPEdv8qgTUOiR2auu4lmrifM-pw6cqcH7shk0M9MmKEw2lRI7pBIhA
    }

    @Test
    public void testValid(){
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnASXh9oSvLRLxk901HANYM6KcYMzX8vFPnH/To2R+SrUVw1O9rEX6m1+rIaMzrEKPm12qPjVq3HMXDbRdUaJEXsB7NgGrAhepYAdJnYMizdltLdGsbfyjITUCOvzZ/QgM1M4INPMD+Ce859xse06jnOkCUzinZmasxrmgNV3Db1GtpyHIiGVUY0lSO1Frr9m5dpemylaT0BV3UwTQWVW9ljm6yR3dBncOdDENumT5tGbaDVyClV0FEB1XdSKd7VjiDCDbUAUbDTG1fm3K9sx7kO1uMGElbXLgMfboJ963HEJcU01km7BmFntqI5liyKheX+HBUCD4zbYNPw236U+7QIDAQAB-----END PUBLIC KEY-----";
        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.e3VzZXI9bXl9.GxiOUiV9dmZMdW2Hj1-pOt2TjAZ615_KWOecDMKHaD-bgbhwjree_JdqmEjMcVuqEztp7ZT_SHXil9VIyt9zvzBs4gO9FAPqq8z6hWKzYqnjJ77UCRIkqTbT7MakNwghzObNH1fSj03l2dPfWSju1f2DyeHQrdwiKsgp20SvCyDwST9_WcJmG0QAnrtt_Nj0lMjXY81-vGHowCNoPq0tf13iVinV3dvCFKf4RUs3xVpGRkq33I-qr1J8IPGP1BWY7qa4xs9JeIohZpcRQ8sYE-6BSUmQG79GIPEdv8qgTUOiR2auu4lmrifM-pw6cqcH7shk0M9MmKEw2lRI7pBIhA";

        Jwt decode = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));
        System.out.println(decode.getClaims());
    }

}
