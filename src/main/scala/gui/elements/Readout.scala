package gui.elements

import gui.rendering.TextContext
import gui.rendering.TextContext.TileCharacter
import gui.style.Color
import gui.windows.Window

class Readout(text: String,
              private var _x: Int,
              private var _y: Int,
              private var _width: Int,
              private var _height: Int,
              val sources: (() => String)*) extends Element{
  //TODO: Enhance with regex. Lets do it now!

  override def width: Int = _width

  override def height: Int = _height

  private var _parent: Window = _
  override def parent: Window = _parent
  override def parent_=(newParent: Window): Unit = _parent = newParent

  override def x: Int = _x

  override def y: Int = _y

  def buildString(): String = {
    var ret = text
    for ((src, i) <- sources.zipWithIndex) {
      ret = text.replaceAll(s"%$i", src())
    }
    ret
  }

  override def render(tc: TextContext): Seq[TileCharacter] = {
    tc.fillString(buildString(),_x, _y, Color.Black, false)
  }

}
