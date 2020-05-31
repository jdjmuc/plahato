package de.algenubi.plahato.util

trait PropertyChangeSupport {
  val propChange = new java.beans.PropertyChangeSupport(this)
  
  
}