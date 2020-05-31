package de.algenubi.plahato.components.renderers

trait UpdateListener {

  private var _needsUpdate = false

  def update { _needsUpdate = true }

  def needsUpdate: Boolean = {
    val value = _needsUpdate
    _needsUpdate = false
    value
  }
}