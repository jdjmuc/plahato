package de.algenubi.plahato.util

import java.io.InputStream
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileInputStream

/**
 * Helper for locating a resource either in a classpath or in a specified location
 */
trait ResourceLocator[T] {

  private var _contentLocator: Option[Class[T]] = None

  /**
   * Configured specified location
   */
  var datafileLocation: Option[String] = None
  
  /**
   * Content locator class for finding resources
   */
  def contentLocator_=(locClass: Class[T]) {
    _contentLocator = Option(locClass)
  }

  
  /**
   * Content locator class
   */
  def contentLocator: Class[T] = {
    _contentLocator match {
      case None => throw new IllegalStateException("Please set the content locator before loading content!")
      case Some(cls) => cls
    }
  }

  def getFile(path: String): Option[File] = {
    val relPath = path.stripPrefix("/")
    datafileLocation match {
      case Some(basepath) => 
        val file = new File(basepath+"/"+relPath)
        if (file.exists()) Some(file) else None
      case None => None
    }
  }
  
  def getResource(path: String): Option[InputStream] = {
    val relPath = path.stripPrefix("/")
    getFile(path) match {
      case Some(file) => Some((new FileInputStream(file)))
      case None => Option(contentLocator.getResourceAsStream(relPath))
    }
  }
}