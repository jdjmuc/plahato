package de.algenubi.plahato.authorization.users

trait UserMap {

  def getUser(name: String): User

  def addUser(user: User)
}
