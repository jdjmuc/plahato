package de.algenubi.plahato.util

import de.algenubi.plahato.components.UnitTest

class BootstrapTransformerTest extends UnitTest {

  val transformer = new BootstrapTransformer()

  val origHtml = """
      |<img ref="img.jpg"></img>
      |<h1>test</h1>""".stripMargin

  val expTrans = """
      |<img ref="img.jpg" class="img-fluid"></img>
      |<h1>test</h1>""".stripMargin

  val origHtmlWithAtt = """
      |<img ref="img.jpg type="x" class="btn"></img>
      |<h1>test</h1>""".stripMargin

  val expTransWithAtt = """
      |<img ref="img.jpg type="x" class="btn img-fluid"></img>
      |<h1>test</h1>""".stripMargin

  "img" should "get class=img-fluid added" in {
    val transHtml = transformer.transform(origHtml)
    transHtml mustBe expTrans
  }

  it should "not get it added again" in {
    val transHtml = transformer.transform(expTrans)
    transHtml mustBe expTrans
  }

  "img with existing class" should "get img-fluid added" in {
    val transHtml = transformer.transform(origHtmlWithAtt)
    transHtml mustBe expTransWithAtt
  }
}