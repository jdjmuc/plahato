package de.algenubi.plahato.util

import java.util.logging.Logger
import java.util.logging.LogManager
import java.io.File
import java.io.FileInputStream

import scala.util.Properties

trait LogCapable {

  val logger = LogCapable.getLogger(getClass.getName)

}

object LogCapable {

  /**
   * Directory for logfiles
   */
  val logDirname = System.getProperty("user.home") + "/tmp/webapp/logs"

  val confFilename = Properties.propOrElse("logging.config", "logging.properties")

  var initialized = false

  scala.sys.addShutdownHook({
    Logger.getLogger("shutdown").info("Shutting down.")
    LogManager.getLogManager.reset
  })

  def getLogger(name: String): Logger = {
    lazy val logger = Logger.getLogger(name)

    if (!initialized) {
      val confFile = new File(confFilename)
      val manager = LogManager.getLogManager
      if (confFile.exists() && confFile.isFile()) {
        manager.readConfiguration(new FileInputStream(confFile))
        logger.info("Log manager initialized from user provided file " + confFilename)
      } else {
        val inStream = getClass.getResourceAsStream(confFilename)
        if (inStream == null) {
          throw new IllegalStateException("Default logging configuration file " + confFilename + " could not be found in the resources.")
        }
        manager.readConfiguration(inStream)
        logger.info("Log manager initialized from default configuration in application resources.")
      }
      val path = new File(logDirname)
      if (!path.exists()) {
        path.mkdirs()
        logger.info("Created path for logfiles: " + logDirname)
      }
      for (handler <- logger.getParent.getHandlers) {
        handler.setFormatter(new ThreadFormatter())
      }
      initialized = true
    }
    logger.info("Creating logger with name " + name)
    logger
  }
}