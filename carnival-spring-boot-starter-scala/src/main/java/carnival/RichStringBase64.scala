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

import java.nio.charset.StandardCharsets

import org.apache.commons.codec.binary.Base64

/**
  * @author 应卓
  */
private[carnival] class RichStringBase64(s: String) {

  require(s != null)

  def encodeBase64(): String = Base64.encodeBase64String(s.getBytes(StandardCharsets.UTF_8))

  def encodeBase64URLSafe(): String = Base64.encodeBase64URLSafeString(s.getBytes(StandardCharsets.UTF_8))

  def decodeBase64(): String = new String(Base64.decodeBase64(s), StandardCharsets.UTF_8)

}
