package gui.elements

import scalafx.scene.input.KeyCode

trait ControlElement extends Element {
  def processKeyPress(kc: KeyCode): Boolean
}
