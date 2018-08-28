/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */

import java.util.{Optional => JOption}

import scala.language.implicitConversions
import scala.util.Try

/**
  * @author 应卓
  */
package object carnival {

  implicit def any2Rich[T](o: T): RichAnyRef[T] = new RichAnyRef(o)

  implicit def any2NullableRich[T](o: T): RichNullableAnyRef[T] = new RichNullableAnyRef[T](o)

  implicit def try2Rich[T](t: Try[T]): RichTry[T] = new RichTry[T](t)

  implicit def throwable2Rich[T <: Throwable](t: T): RichThrowable[T] = new RichThrowable[T](t)

  implicit def optional2Rich[T](op: JOption[T]): RichOptional[T] = new RichOptional[T](op)

  implicit def option2Rich[T](op: Option[T]): RichOption[T] = new RichOption[T](op)

  implicit def char2Rich(ch: Char): RichChar = new RichChar(ch)

  implicit def string2Rich(s: String): RichString = new RichString(s)

  implicit def string2NullableRich(s: String): RichNullableString = new RichNullableString(s)

  implicit def enumCls2Rich[E <: Enum[E]](enumType: Class[E]): RichEnum[E] = new RichEnum[E](enumType)

}
