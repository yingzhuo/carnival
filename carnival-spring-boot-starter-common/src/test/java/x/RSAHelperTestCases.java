package x;

import com.github.yingzhuo.carnival.secret.rsa.RSAHelper;
import com.github.yingzhuo.carnival.secret.rsa.RSAKeyPair;
import lombok.val;
import org.junit.Test;

public class RSAHelperTestCases {

    @Test
    public void test1() {
        RSAHelper helper = RSAHelper.of(RSAKeyPair.createRandom(1024));

        String ed = helper.encryptByPublicKey("133810");
        val s = helper.decryptByPrivateKey(ed);
        System.out.println(s);
    }

}
