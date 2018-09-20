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

/**
  * @author 应卓
  */
private[carnival] class RichChar(ch: Char) {

//  require(ch != null)

  def isAlphabetic: Boolean = Character.isAlphabetic(ch)

  def isAlphabeticSpace: Boolean = ch.isAlphabetic || ch == ' '

  def isNumeric: Boolean = Character.isDigit(ch)

  def isAlphanumeric: Boolean = ch.isNumeric || ch.isAlphabetic

  def isAlphanumericSpace: Boolean = ch.isAlphanumeric || ch == ' '

}
