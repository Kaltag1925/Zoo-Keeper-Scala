package gui.elements

import gui.Renderable
import gui.windows.Window

trait Element extends Renderable {
  def parent: Window
  def parent_=(newParent: Window): Unit
}
