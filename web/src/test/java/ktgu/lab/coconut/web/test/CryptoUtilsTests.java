package ktgu.lab.coconut.web.test;

 import ktgu.lab.coconut.web.util.CryptoUtils;
 import org.apache.commons.codec.DecoderException;
 import org.apache.commons.codec.binary.Base64;
 import org.apache.commons.codec.binary.Hex;
 import org.junit.Assert;
 import org.junit.Test;
 import org.springframework.util.DigestUtils;

 import javax.crypto.KeyGenerator;
 import java.security.NoSuchAlgorithmException;
 import java.util.Date;

/**
 * Created by ktgu on 15/6/28.
 */
public class CryptoUtilsTests {

    @Test
    public void testMd5( ) throws NoSuchAlgorithmException {
       String s1 = CryptoUtils.md5AsHex("aaa");
       String s2 =  DigestUtils.md5DigestAsHex("aaa".getBytes());
        Assert.assertTrue(s1.equalsIgnoreCase(s2));
    }

    @Test
    public void testDES() {
        String s = "29283212292832122928321229283212292832122928321229283212292832122928321229283212292832122928321229283212292832122928321229283212292832122928321229283212292832122928321229283212292832122928321229283212@qq.com,"+new Date().getTime();

        byte[] encrypted = CryptoUtils.desEncrypt(s, "aaaaaaaa");

        String s1 = CryptoUtils.bytes2HexString(encrypted);

        byte[] bytes2 = CryptoUtils.desDecrypt(encrypted, "aaaaaaaa");

        String s2 = new String(bytes2);
    }

    @Test
    public void test11() throws DecoderException {
        String s = "29283212@qq.com,"+ new Date().getTime();

        String s1 = CryptoUtils.bytes2HexString(s.getBytes());

        String s2  = Hex.encodeHexString(s.getBytes());

        byte[] bytes = Hex.decodeHex(s2.toCharArray());
        String s3 = new String(bytes);
     }

    @Test
    public void testBase64() {
        String s = "29283212@qq.com,"+ new Date().getTime();
       String s1 =  Base64.encodeBase64String(s.getBytes());
    }

    @Test
    public void testAES() {

         // 执行DES加密32393238333231324071712e636f6d31343335353030363839333932
        byte[] bytes = CryptoUtils.aesEncrypt("aaa", "password");

        // 解密由DES加密过的密文
        byte[] decryptedBytes = CryptoUtils.aesDecrypt(bytes, "password");

        // 检查是否能正确的解密
        Assert.assertTrue("aaa".equals(new String(decryptedBytes)));
    }

    @Test
    public void test1() throws NoSuchAlgorithmException {
        KeyGenerator KeyGen = KeyGenerator.getInstance("AES");
        KeyGen.init(128);
        System.out.println("here");
    }
}
