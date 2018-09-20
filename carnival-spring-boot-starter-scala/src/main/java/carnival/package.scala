/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */

import java.util.{Calendar, Date, Optional => JOption}

import scala.language.{implicitConversions, reflectiveCalls}
import scala.util.Try

/**
  * @author 应卓
  */
package object carnival {

  implicit def any2Rich[T <: AnyRef](o: T): RichAnyRef[T] = new RichAnyRef(o)

  implicit def any2RichNullable[T <: AnyRef](o: T): RichAnyRefNullable[T] = new RichAnyRefNullable[T](o)

  implicit def try2Rich[T](t: Try[T]): RichTry[T] = new RichTry[T](t)

  implicit def throwable2Rich[T <: Throwable](t: T): RichThrowable[T] = new RichThrowable[T](t)

  implicit def optional2Rich[T](op: JOption[T]): RichOptional[T] = new RichOptional[T](op)

  implicit def option2Rich[T](op: Option[T]): RichOption[T] = new RichOption[T](op)

  implicit def char2Rich(ch: Char): RichChar = new RichChar(ch)

  implicit def string2Rich(s: String): RichString = new RichString(s)

  implicit def string2RichDigest(s: String): RichStringDigest = new RichStringDigest(s)

  implicit def string2RichStringBase64(s: String): RichStringBase64 = new RichStringBase64(s)

  implicit def string2RichNullable(s: String): RichStringNullable = new RichStringNullable(s)

  implicit def enumCls2Rich[E <: Enum[E]](enumType: Class[E]): RichEnumClass[E] = new RichEnumClass[E](enumType)

  implicit def date2Rich(date: Date): RichDate = new RichDate(date)

  implicit def calendar2Rich(calendar: Calendar): RichCalendar = new RichCalendar(calendar)

  implicit def closeable2Rich[C <: {def close() : Unit}](c: C): RichCloseable[C] = new RichCloseable[C](c)

}
