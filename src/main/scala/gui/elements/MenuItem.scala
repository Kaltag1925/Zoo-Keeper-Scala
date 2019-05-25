package gui.elements

import gui.rendering.TextContext
import gui.rendering.TextContext.TileCharacter
import gui.style.Color
import gui.windows.{OpenableWindow, Window}
import scalafx.scene.input.KeyCode

class MenuItem(val text: String, val key: KeyCode, val opens: OpenableWindow,
               private var _x: Int, private var _y: Int, private var _width: Int, private var _height: Int)
  extends ControlElement {

  def render(tc: TextContext): Seq[TileCharacter] = {
    tc.fillString(s"${key.name}: $text", _x, _y, Color.Black, false)
  }

  private var _parent: Window = _
  override def parent: Window = _parent
  override def parent_=(newParent: Window): Unit = _parent = newParent

  override def width: Int = _width
  override def height: Int = _height
  override def x: Int = _x
  override def y: Int = _y

  override def processKeyPress(kc: KeyCode): Boolean = {
    if (kc == key) {
      opens.openFrom(_parent)
      true
    } else {
      false
    }
  }
}
