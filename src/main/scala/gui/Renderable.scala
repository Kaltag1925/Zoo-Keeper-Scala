package gui

import gui.elements.Element
import gui.rendering.TextContext
import gui.rendering.TextContext.TileCharacter
import gui.windows.{OpenableWindow, Window}

trait Renderable {
  def render(tc: TextContext): Seq[TileCharacter]
  def x: Int
  def y: Int
  def width: Int
  def height: Int
  def localToWindowPosition: (Int, Int) = {
    def helper(renderable: Renderable): (Int, Int) = {
      renderable match {
        case elem: Element =>
          if (elem.parent != null) {
            val (absX, absY) = helper(elem.parent)
            (absX + elem.x) -> (absY + elem.y)
          } else {
            0 -> 0
          }
        case wind: Window =>
          wind match {
            case openable: OpenableWindow =>
              openable.prevWindow match {
                case Some(parent) =>
                  val (absX, absY) = helper(parent)
                  (absX + openable.x) -> (absY + openable.y)
                case None =>
                  0 -> 0
              }
            case _ =>
              0 -> 0
          }
      }
    }

    helper(this)
  }
}
