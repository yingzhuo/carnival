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

import org.apache.commons.codec.digest.DigestUtils

/**
  * @author 应卓
  */
private[carnival] class RichStringDigest(s: String) {

  require(s != null)

  def md2(): String = DigestUtils.md2Hex(s)

  def md5(): String = DigestUtils.md5Hex(s)

  def sha1(): String = DigestUtils.sha1Hex(s)

  def sha256(): String = DigestUtils.sha256Hex(s)

  def sha384(): String = DigestUtils.sha384Hex(s)

  def sha512(): String = DigestUtils.sha512Hex(s)

}
