package de.algenubi.plahato.components.renderers.bootstrap

import de.algenubi.plahato.components.models.gallery.Gallery
import de.algenubi.plahato.components.renderers.Renderer

/**
  * Renders a gallery with bootstrap style classes
  */
class GalleryRenderer(gallery: Gallery, withAuthor: Boolean = true) extends Renderer[Gallery] {

  val model: Gallery = gallery

  def renderContent() {
    renderOverview()
  }

  private def renderOverview() {

    val photoList = gallery.photos.sortWith {
      (a, b) =>
        (a.date, b.date) match {
          case (Some(d1), Some(d2)) => d1.after(d2)
          case _ => false
        }
    }

    content ++=
      """
        |<div class="row">
        |""".stripMargin

    for (i <- photoList.indices) {
      val galleryItem = photoList(i)
      val photoId = "photo_" + i
      val prevId = "photo_" + (if (i > 0) i - 1 else photoList.length - 1)
      val nextId = "photo_" + (if (i < (photoList.length - 1)) i + 1 else 0)
      content ++=
        s"""|<div class="col-12 col-sm-6 col-md-6 col-lg-4 col-xl-4">
            |  <div class="gallery-item">
            |    <div class="gallery-image">
            |     <a href="#$photoId" data-toggle="modal">
            |       <img src="${galleryItem.imasrc}" class="img-fluid"></img>
            |     </a>
            |   </div>
            |   <div class="gallery-caption">
            |     <p>${galleryItem.title}</p>
            |   </div>
            |  </div>
            |</div>
            |
      |<div class="modal gallery-zoom" id="$photoId" role="modal">
            |  <div class="modal-dialog">
            |    <div class="modal-content">
            |      <div class="modal-header">
            |        <h3>${galleryItem.title}</h3>
            |        <button type="button" class="close" data-dismiss="modal" style="text-align: right">&times;</button>
            |      </div>
            |      <div class="modal-body">
            |        <img src="${galleryItem.imasrc}" class="img-fluid"></img>
            |      </div>
            |      <div class="modal-footer">
            |        <p style="text-align: left">
            |          <a href="#$prevId" data-dismiss="modal" data-toggle="modal" class="gallery-button">&larr;</a>
            |          <a href="#$nextId" data-dismiss="modal" data-toggle="modal" class="gallery-button">&rarr;</a>
            |          <br>
            |          ${galleryItem.location}, ${galleryItem.dateStr}<br>
            |          ${galleryItem.instrument}<br>
            |          <span style="font-style: italic">${if (withAuthor) galleryItem.author else ""}</span>
            |        </p>
            |      </div>
            |    </div>
            |  </div>
            |</div>
            |""".stripMargin('|')

    }
    content ++= "</div>\n"

  }

  private def renderPhoto(index: Int) {

  }

}