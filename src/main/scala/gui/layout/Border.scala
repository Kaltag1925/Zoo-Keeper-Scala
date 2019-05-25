package gui.layout

case class Border(ch: Char, insets: Insets)

object Border {
  def apply(ch: Char, leftUpRightDown: Int): Border = {
    new Border(ch, Insets(leftUpRightDown, leftUpRightDown, leftUpRightDown, leftUpRightDown))
  }

  def apply(ch: Char, left: Int, up: Int, right: Int, down: Int): Border = {
    new Border(ch, Insets(left, up, right, down))
  }

  def apply(ch: Char): Border = {
    new Border(ch, Insets(1, 1, 1, 1))
  }
}
