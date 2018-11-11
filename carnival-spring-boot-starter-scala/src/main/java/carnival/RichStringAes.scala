/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package carnival

import com.github.yingzhuo.carnival.common.util.AESEncrypter

/**
  * @author 应卓
  */
private[carnival] class RichStringAes(s: String) {

  require(s != null)

  def aesEncrypt(key: String): String = {
    require(key != null)
    val e = new AESEncrypter(key)
    e.encrypt(s)
  }

  def aes(key: String): String = {
    require(key != null)
    val e = new AESEncrypter(key)
    e.decrypt(key)
  }

}
