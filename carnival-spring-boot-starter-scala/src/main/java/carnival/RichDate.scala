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

import java.util.{Calendar, Date, TimeZone}

import org.apache.commons.lang3.time.{DateFormatUtils, DateUtils}

/**
  * @author 应卓
  */
private[carnival] class RichDate(date: Date) {

  assert(date != null)

  // -----------------------------------------------------------------------------------------------------------------

  def toCalendar: Calendar = DateUtils.toCalendar(date)

  def toCalendar(tz: TimeZone): Calendar = DateUtils.toCalendar(date, tz)

  // -----------------------------------------------------------------------------------------------------------------

  def format(pattern: String): String = DateFormatUtils.format(date, pattern)

  def toString(pattern: String): String = DateFormatUtils.format(date, pattern)

  // -----------------------------------------------------------------------------------------------------------------

  def isSameDay(that: Date): Boolean = DateUtils.isSameDay(date, that)

  def isNotSameDay(that: Date): Boolean = !DateUtils.isSameDay(date, that)

  // -----------------------------------------------------------------------------------------------------------------

  def addYears(amount: Int): Date = DateUtils.addYears(date, amount)

  def addMonths(amount: Int): Date = DateUtils.addMonths(date, amount)

  def addDays(amount: Int): Date = DateUtils.addDays(date, amount)

  def addHours(amount: Int): Date = DateUtils.addHours(date, amount)

  def addMinutes(amount: Int): Date = DateUtils.addMinutes(date, amount)

  def addSeconds(amount: Int): Date = DateUtils.addSeconds(date, amount)

  def addMilliseconds(amount: Int): Date = DateUtils.addMilliseconds(date, amount)

  def addWeeks(amount: Int): Date = DateUtils.addWeeks(date, amount)

  // -----------------------------------------------------------------------------------------------------------------

  def setYears(amount: Int): Date = DateUtils.setYears(date, amount)

  def setMonths(amount: Int): Date = DateUtils.setMonths(date, amount)

  def setDays(amount: Int): Date = DateUtils.setDays(date, amount)

  def setHours(amount: Int): Date = DateUtils.setHours(date, amount)

  def setMinutes(amount: Int): Date = DateUtils.setMinutes(date, amount)

  def setSeconds(amount: Int): Date = DateUtils.setSeconds(date, amount)

  def setMilliseconds(amount: Int): Date = DateUtils.setMilliseconds(date, amount)

}
