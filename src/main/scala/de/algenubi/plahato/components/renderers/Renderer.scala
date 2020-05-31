package de.algenubi.plahato.components.renderers

import de.algenubi.plahato.components.models.language.Internationalized

abstract class Renderer[T] extends Internationalized {

  protected val content = new StringBuilder()

  private var isCached = false

  /**
   * The model which this renderer renders
   */
  val model: T
    
  /**
   * render this component
   */
  final def render: String = {
    if (!cached) {
      content.clear()
      renderContent
      cached = true
    } 
    content.toString
  }

  /**
   * Content renderer which needs to be defined in the specific renderer subclass
   */
  protected def renderContent(): Unit

  /**
   * Has the content been generated and cached?
   */
  def cached: Boolean = {
    isCached
  }

  /**
   * Sets the cached flag. If set to false then the content will be cleared.
   */
  protected def cached_=(value: Boolean): Unit = {
    isCached = value
    if (!value) {
      content.clear();
    }
  }

  /**
   * Resets the cache when the language is updated
   */
  override def handleUpdate() {
    cached = false
  }
}
