package gui.windows

import gui.Renderable
import gui.elements.Element
import scalafx.scene.input.KeyCode

trait Window extends Renderable {
  def processKeyBind(kc: KeyCode): Unit

  def children: Seq[Element]
  def children_=(newChildren: Seq[Element]): Unit
  def childWindow: Option[Window]
  def childWindow_=(newChildWindow: Option[Window]): Unit
}
