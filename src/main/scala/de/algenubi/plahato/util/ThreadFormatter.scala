
package de.algenubi.plahato.util

import java.io.{ PrintWriter, StringWriter }
import java.text.SimpleDateFormat
import java.util.logging.{ Formatter, LogRecord }

/**
 * Formatter including thread information.
 *
 * @author Jeroen de Jong
 */
class ThreadFormatter extends Formatter {

  val dateFormatter = new SimpleDateFormat("dd-MMM-yy HH:mm:ss.SSS")

  private def getDate(record: LogRecord): String = dateFormatter.format(record.getMillis)

  private def getStackTrace(record: LogRecord): String = {
    val t = record.getThrown()
    if (t != null) {
      val writer = new StringWriter()
      t.printStackTrace(new PrintWriter(writer))
      writer.toString
    } else ""
  }

  override def format(record: LogRecord): String = {
    record.getLevel().getName() + " " + getDate(record) + " in " + Thread.currentThread().getName() +
      "(" + record.getThreadID() + ") at " + record.getSourceClassName() + " " +
      record.getSourceMethodName() + ":\n" + record.getMessage() + "\n" + getStackTrace(record);
  }
}
