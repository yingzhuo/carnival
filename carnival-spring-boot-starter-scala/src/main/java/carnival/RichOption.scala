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

import java.util.{Optional => JOption}

private[carnival] class RichOption[T](op: Option[T]) {

  assert(op != null)

  def asJava: JOption[T] = op match {
    case Some(x) => JOption.of(x)
    case None => JOption.empty()
  }

}
