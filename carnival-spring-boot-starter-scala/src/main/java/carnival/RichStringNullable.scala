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
private[carnival] class RichStringNullable(s: String) {

  // -----------------------------------------------------------------------------------------------------------------

  def isNullOrEmpty: Boolean = s match {
    case null => true
    case x => x.isEmpty
  }

  def isNullOrBlank: Boolean = s match {
    case null => true
    case x => x.isBlank
  }

}
