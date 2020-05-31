package de.algenubi.plahato.util

import scala.util.Random

object RandomToken {

  /**
    * Generates a random token which can be used for identifications in sessions
    * @return token string
    */
  def generate(size: Int = 2): String = {
    List.fill(size)(Random.nextLong.toHexString).mkString("")
  }
}
