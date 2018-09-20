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
private[carnival] class RichAnyRefNullable[T <: AnyRef](any: T) {

  def option: Option[T] = Option(any)

  // -----------------------------------------------------------------------------------------------------------------

  def isNull: Boolean = null eq any

  def isNotNull: Boolean = null ne any

  // -----------------------------------------------------------------------------------------------------------------

  def ?!(t: => T): T = any match {
    case null => t
    case _ => any
  }

  def defaultIfNull(t: => T): T = any ?! t

}
