package de.algenubi.plahato.components.factories

import de.algenubi.plahato.components.models.gallery.Gallery
import de.algenubi.plahato.components.renderers.bootstrap.GalleryRenderer
import play.twirl.api.Html

object GalleryFactory extends RendererFactory[Gallery] {

  def makeGallery(name: String, gallery: Gallery, withAuthor: Boolean = false,
    lang: String = LanguageFactory.languages.default): Html = {
    render(name, gallery, new GalleryRenderer(_, withAuthor), lang)
  }

}