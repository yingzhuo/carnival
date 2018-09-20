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

/**
  * @author 应卓
  */
private[carnival] class RichOptional[T](op: JOption[T]) {

  require(op != null)

  def asScala: Option[T] = if (op.isPresent) Some(op.get()) else None

}
