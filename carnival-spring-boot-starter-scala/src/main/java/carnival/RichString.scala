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

import org.apache.commons.lang3.StringUtils

/**
  * @author 应卓
  */
private[carnival] class RichString(s: String) {

  assert(s != null)

  // -----------------------------------------------------------------------------------------------------------------


  def ~==(that: String): Boolean = s equalsIgnoreCase that

  def !~==(that: String): Boolean = !(s equalsIgnoreCase that)


  // -----------------------------------------------------------------------------------------------------------------

  def isNotEmpty: Boolean = !s.isEmpty

  // -----------------------------------------------------------------------------------------------------------------

  def isBlank: Boolean = s match {
    case null => throw new NullPointerException
    case x if x.length == 0 => true
    case x => x.forall(_.isWhitespace)
  }

  def isNotBlank: Boolean = !s.isBlank

  // -----------------------------------------------------------------------------------------------------------------

  def stripWhitespace: String = s.strip(Character.isWhitespace)

  def stripIn(chars: String): String = StringUtils.strip(s, chars)

  def strip(f: Char => Boolean): String = {
    val buff = new StringBuilder
    s.foreach { c =>
      if (!f(c)) buff.append(c)
    }
    buff.toString()
  }

  // -----------------------------------------------------------------------------------------------------------------

  def abbreviate(max: Int, abbrevMarker: String = "..."): String = StringUtils.abbreviate(s, abbrevMarker, max)

}
