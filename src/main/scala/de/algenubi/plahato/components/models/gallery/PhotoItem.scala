package de.algenubi.plahato.components.models.gallery

import java.util.Calendar

/**
 * Photo item for gallery content description
 */
case class PhotoItem(imasrc: String,
    title: String = "",
    author: String = "",
    description: String = "",
    date: Option[Calendar] = None,
    location: String = "",
    instrument: String = "") {

  def this(imasrc: String,
    title: String,
    author: String,
    description: String,
    date: String,
    location: String,
    instrument: String) =
    this(imasrc, title, author, description, PhotoItem.parseDate(date), location, instrument)

    
  def dateStr: String = {
    date match {
      case Some(cal) => f"${cal.get(Calendar.DAY_OF_MONTH)}%02d-${cal.get(Calendar.MONTH)}%02d-${cal.get(Calendar.YEAR)}%04d"
      case _ => ""
    }
  }
}

object PhotoItem {

  def apply(imasrc: String,
    title: String,
    author: String,
    description: String,
    date: String,
    location: String,
    instrument: String): PhotoItem = new PhotoItem(imasrc, title, author, description, date, location, instrument)
  
  def parseDate(dateStr: String): Option[Calendar] = {
    val datePattern = """(\d\d)-(\d\d)-(\d{4})""".r
    dateStr match {
      case datePattern(day, month, year) => Some(new Calendar.Builder().
          setCalendarType("iso8601").
          setFields(Calendar.YEAR, year.toInt, 
              Calendar.MONTH, month.toInt, 
              Calendar.DAY_OF_MONTH, day.toInt).build())
      case _ => None
    }
  }
  
}