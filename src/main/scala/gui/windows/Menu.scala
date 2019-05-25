package gui.windows

import gui.elements.{ControlElement, Element}
import gui.rendering.TextContext
import gui.rendering.TextContext.TileCharacter
import scalafx.scene.input.KeyCode

class Menu(private var _x: Int, private var _y: Int, private var _width: Int, private var _height: Int) extends Window with OpenableWindow {

  private var _children = Seq[Element]()
  override def children: Seq[Element] = _children
  override def children_=(newChildren: Seq[Element]): Unit = {
    _children = newChildren
    newChildren.foreach(_.parent = this)
  }
  private var _childWindow: Option[Window] = None
  override def childWindow: Option[Window] = _childWindow
  override def childWindow_=(newChildWindow: Option[Window]): Unit = _childWindow = newChildWindow

  def render(tc: TextContext): Seq[TileCharacter] = {
    childWindow match {
      case Some(window) =>
        window.render(tc)
      case None =>
        children.flatMap(_.render(tc))
    }
  }

  private def insideBounds(ox: Int, oy: Int, oWidth: Int, oHeight: Int): Unit = {

  }


  private var _prevWindow: Option[Window] = None
  override def prevWindow: Option[Window] = _prevWindow

  override def close(): Unit = {
    _prevWindow match {
      case Some(parent) => parent.childWindow = None
      case None => println("Tried to close window, but it had no parent.")
    }
  }
  override def openFrom(parent: Window): Unit = {
    _prevWindow = Some(parent)
    parent.childWindow = Some(this)
  }

  override def x: Int = _x
  override def y: Int = _y
  override def width: Int = _width
  override def height: Int = _height

  override def processKeyBind(kc: KeyCode): Unit = {

    childWindow match {
      case Some(window) =>
        window.processKeyBind(kc)
      case None =>
        if (kc == KeyCode.Escape) {
          close()
        } else {
          children.foreach({
            case control: ControlElement => control.processKeyPress(kc)
            case _ =>
          })
        }
    }
  }
}