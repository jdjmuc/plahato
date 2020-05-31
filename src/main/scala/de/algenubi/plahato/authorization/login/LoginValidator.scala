package de.algenubi.plahato.authorization.login

import org.apache.commons.codec.digest.DigestUtils
import play.api.mvc.Action

/**
  * Validates user logins using SHA-256 hash comparison
  */
trait LoginValidator {

  /**
    * user map to be loaded in the application
    */
  val userMap: Map[String,String]

  /**
    * validate method which checks agains SHA-256 hashes
    * @param user user
    * @param passwd password
    * @return true if login is valid
    */
  def validate(user: String, passwd: String): Boolean = {
    userMap.get(user) match {
      case Some(hashCode) => DigestUtils.sha256Hex(passwd) == hashCode
      case None => false
    }
  }

}
