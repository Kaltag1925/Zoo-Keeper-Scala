package gui.elements

import gui.rendering.TextContext
import gui.rendering.TextContext.TileCharacter
import gui.style.Color
import gui.windows.Window

class Label(private var text: String,
            private var _x: Int,
            private var _y: Int,
            private var _width: Int,
            private var _height: Int) extends Element{


  override def width: Int = _width

  override def height: Int = _height

  private var _parent = null.asInstanceOf[Window] //TODO: Turn to option?
  override def parent: Window = _parent
  def parent_=(newParent: Window): Unit = _parent = newParent

  override def x: Int = _x

  override def y: Int = _y

  private var _color: Color = Color.Black
  def color: Color = _color
  def color_=(newColor: Color): Unit = _color = newColor

  def getPrintLines(str: String, textLines: Int): List[String] = {
    if (textLines < height - 2) {
      if (str.length < width - 2) {
        List(str)
      } else {
        val (line, rest) = str.splitAt(width - 2)
        line :: getPrintLines(rest, textLines + 1)
      }
    } else {
      Nil
    }
  }

  override def render(tc: TextContext): Seq[TileCharacter] = {
    val (absX, absY) = localToWindowPosition

    val lines = getPrintLines(text, 0)
    val yStart = (height.toDouble / 2 - lines.length.toDouble / 2).toInt // removed (lines.length.toDouble-1)

    val ret = (for((str, i) <- lines.zipWithIndex) yield {
      tc.fillString(str, absX + (width.toDouble / 2 - str.length.toDouble / 2).toInt, absY + yStart + i, Color.Black, false)
    }).flatten ++ tc.strokeRect('#', absX, absY, width, height, Color.Black)
    tc.addObstruction(_x, _y, _width, _height)
    ret
  }

}
