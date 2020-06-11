package de.algenubi.plahato.util

import java.io.File

import org.apache.commons.io.FileUtils
import play.api.Configuration
import play.api.mvc.BaseController

import scala.io.Source

/**
 * Loader of simple files, mainly for configuration
 */
trait SimpleDataFileLoader[T] extends BaseController with ResourceLocator[T] with LogCapable {

  protected val configuration: Configuration

  protected val basedirProperty: String

  /**
   * load basedir property and create directory if needed
   */
  datafileLocation = configuration.getOptional[String](basedirProperty)

  datafileLocation match {
    case Some(basedir) =>
      FileUtils.forceMkdir(new File(basedir))
    case None => throw new IllegalStateException("Property " + basedirProperty + " not defined.")
  }

  /**
   * Loads a public data file from a relative path as defined in the config location, or
   * returns None when the file does not exist.
   */
  def loadDataFile(path: String): Option[String] = {
    val file = getDataFile(path)
    logger.fine("Try to read file with absolute path " + file)
    if (file.exists) {
      Some(FileUtils.readFileToString(file, "UTF-8"))
    } else {
      getResource(path) match {
        case Some(stream) =>
          logger.fine(s"Loading path ${path} from resource.")
          Some(Source.fromInputStream(stream).getLines() mkString ("\n"))
        case None =>
          logger.severe(s"File at path $path could not be loaded from either resource or file.")
          None
      }
    }
  }

  def getDataFile(path: String): File = {
    if (datafileLocation == None) {
      throw new IllegalStateException("Please define property " + basedirProperty)
    }
    new File(datafileLocation.get + "/" + path)
  }
}