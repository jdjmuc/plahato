package de.algenubi.plahato.util

import java.io.InputStream

import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileInputStream

import scala.reflect.ClassTag

/**
 * Helper for locating a resource either in a classpath or in a specified location
 */
trait ResourceLocator {

  protected val contentLocator: Class[_]

  /**
   * Configured specified location
   */
  val datafileLocation: Option[String]

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
      case Some(file) => Some(new FileInputStream(file))
      case None => Option(contentLocator.getResourceAsStream(relPath))
    }
  }
}