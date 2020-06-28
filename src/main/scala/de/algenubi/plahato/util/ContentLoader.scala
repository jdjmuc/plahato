package de.algenubi.plahato.util

import akka.util.ByteString
import de.algenubi.plahato.actions.LanguageHandling.getLanguage
import de.algenubi.plahato.components.factories.LanguageFactory
import org.apache.commons.io.IOUtils
import play.api.http.HeaderNames.{CACHE_CONTROL, CONTENT_DISPOSITION, ETAG, IF_NONE_MATCH}
import play.api.http.HttpEntity
import play.api.http.Status.OK
import play.api.mvc._
import play.twirl.api.Html

import scala.io.Source
import scala.math.abs
import scala.util.Random

/**
  * Content loader utility class to be mixed in any Application class
  *
  * This trait handles loading content from a resource and providing this
  * to a template function.
  */
trait ContentLoader extends BaseController with LogCapable with Results with ResourceLocator {

  lazy val serverID = {
    val newVal = abs(Random.nextLong).toHexString
    logger.info("New running server ID: " + newVal)
    newVal
  }

  var htmlTransformer: HtmlTransformer = new DefaultHtmlTransformer()

  /**
    * Abstract function which needs to be filled by the template usage
    * in the application code.
    *
    * Also provides the name of an optional menu if applicable
    *
    */
  def template(pageTitle: String, content: Html, lang: String): Html

  /**
    * load any type of content from source file and mix it with a template
    */
  def loadContent(path: String, prefix: String = ""): Action[AnyContent] = {
    Action {

      request =>
        // load according to type
        val lang = getLanguage(request)
        val modPath = prefix + "/" + path
        val optPrevEtag = request.headers.get(IF_NONE_MATCH)
        logger.fine("Loading content of path " + modPath)
        ContentType(path) match {
          case ContentType.Html => {
            val content = loadHtml(modPath, lang)
            val title = ContentLoader.getTitleFromPath(path)
            val html = template(title, content, lang)
            val etag = Checksum.getChecksum(html)
            if (optPrevEtag.isDefined && optPrevEtag.get == etag) {
              Result(ResponseHeader(304), HttpEntity.NoEntity)
            } else {
              Ok(html).withSession(request.session).withHeaders(
                ETAG -> etag,
                CACHE_CONTROL -> "no-cache"
              )
            }
          }
          case ContentType.Jpeg => {
            loadIma(modPath, "image/jpeg", lang, optPrevEtag)
          }
          case ContentType.Png => {
            loadIma(modPath, "image/png", lang, optPrevEtag)
          }
          case ContentType.Gif => {
            loadIma(modPath, "image/gif", lang, optPrevEtag)
          }
          case ContentType.Pdf => {
            loadIma(modPath, "application/pdf", lang, optPrevEtag)
          }
          case ContentType.Svg => {
            loadIma(modPath, "image/svg+xml", lang, optPrevEtag)
          }
          case ContentType.NoSuffix => {
            val indexPath = modPath +
              (if (!modPath.endsWith("/")) "/" else "") + "index.html"
            Redirect(indexPath)
          }
          case _ => {
            Ok(template("Not supported", Html("Content not supported: " + path), lang))
          }
        }
    }
  }


  private def loadHtml(path: String, lang: String = LanguageFactory.languages.default): Html = {

    // language suffix
    val isDefLang = lang == LanguageFactory.languages.default
    val langSuffix = if (isDefLang) "" else "." + lang
    val modPath = """(\.html{0,1})$""".r replaceAllIn(path, m => langSuffix + (m group 1))
    logger.fine("Loading HTML from " + modPath)
    val resource = getResource(modPath)
    resource match {
      case None =>
        if (!isDefLang) loadHtml(path) else
          Html("ERROR: no content found at " + modPath)
      case Some(stream) =>
        Html(htmlTransformer.transform(Source.fromInputStream(stream).getLines() mkString ("\n")))
    }
  }

  def loadIma(path: String, contentType: String, lang: String, optPrevEtag: Option[String] = None): Result = {
    val resource = getResource(path)
    resource match {
      case None => {
        NotFound(template("Not supported", Html("Content not supported: " + path), lang))
      }
      case Some(stream) => {
        val buffer: Array[Byte] = IOUtils.toByteArray(stream)
        val entity: HttpEntity = HttpEntity.Strict(ByteString(buffer), Some(contentType))
        val etag = Checksum.getChecksum(buffer, Some(path))
        if (optPrevEtag.isDefined && optPrevEtag.get == etag) {
          Result(ResponseHeader(304), HttpEntity.NoEntity)
        } else {
          Result(ResponseHeader(OK, Map(
            CONTENT_DISPOSITION -> "inline",
            ETAG -> etag,
            CACHE_CONTROL -> "no-cache")), entity).as(contentType)
        }
      }
    }
  }

  private def quote(s: String): String = s""""$s""""
}

object ContentLoader {

  def getTitleFromPath(path: String): String = {
    path.split("/").last.split("\\.").head
  }
}
