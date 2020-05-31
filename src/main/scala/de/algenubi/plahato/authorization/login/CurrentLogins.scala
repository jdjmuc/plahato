package de.algenubi.plahato.authorization.login

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

import de.algenubi.plahato.util.RandomToken

import scala.collection.mutable

object CurrentLogins {

  private val loginMap = mutable.Map.empty[String, LocalDateTime]

  /**
    * How long a session is valid (in days)
    */
  var validDuration: Int = 60

  /**
    * Create a new login session with token
    * @return
    */
  def newLogin: String = synchronized {
    val session = RandomToken.generate()
    val date = LocalDateTime.now()
    loginMap.put(session, date)
    session
  }

  /**
    * Is a session valid?
    * @param session session key to check
    * @return true if valid
    */
  def isValid(session: String): Boolean = synchronized {
    loginMap.get(session) match {
      case Some(date) =>
        val now = LocalDateTime.now()
        val expDate = date.plus(validDuration, ChronoUnit.MINUTES)
        if (now.isBefore(expDate)) {
          true
        } else {
          loginMap.remove(session)
          false
        }
      case None => false
    }
  }

  /**
    * Delete a session
    * @param session session key
    * @return true if session was present
    */
  def deleteSession(session: String): Boolean = synchronized {
    loginMap.remove(session).isDefined
  }

}
