package gui.elements

import gui.rendering.TextContext
import gui.rendering.TextContext.TileCharacter
import gui.style.Color
import gui.windows.Window
import scalafx.scene.input.KeyCode

import scala.collection.mutable.ArrayBuffer

class ScrollList(val text: String,
                 private var _x: Int, private var _y: Int, private var _width: Int, private var _height: Int)
  extends ControlElement {

  private var _items: ArrayBuffer[ScrollItem] = ArrayBuffer()

  def render(tc: TextContext): Seq[TileCharacter] = {
    (for (item <- _items) yield {
      item.render(tc)
    }).flatten
  }

  def addItem(str: String): Unit = {
    _items += new ScrollItem(str, 0, _items.length, 10, 1)
  }

  def insideBounds(x: Int, y: Int): Boolean = {
    x >= 0 && x < _width - 1 && y >= yOffset && y < yOffset + _height - 1
  }

  //TODO: Make basic text element but its not really an element
  //TODO: You mean abstract?

  class ScrollItem(name: String, var x: Int,
                   var y: Int,
                   var width: Int,
                   var height: Int) {

    def render(tc: TextContext): Seq[TileCharacter] = {
      (for ((ch, i) <- name.zipWithIndex; if insideBounds(x + i, y)) yield {
        tc.fillChar(ch, x + i, ScrollList.this._y + y - yOffset, color, false)
      }).flatten
    }

    var color: Color = Color.Black
  }

  private var _parent: Window = _
  override def parent: Window = _parent
  override def parent_=(newParent: Window): Unit = _parent = newParent

  override def width: Int = _width
  override def height: Int = _height
  override def x: Int = _x
  override def y: Int = _y

  private var yOffset = 0
  private var itemsIndex = 0


  def setOffset(newIndex: Int): Unit = {
    val newTotalHeight = (for (i <- 0 until newIndex) yield _items(i).height).sum
    println(newTotalHeight, yOffset)
    if (newTotalHeight < yOffset) {
      var i = itemsIndex
      while (i >= newIndex && newTotalHeight < yOffset) {
        yOffset -= _items(i).height
        i -= 1
      }
    } else if (newTotalHeight + 1 >= yOffset + _height - 1) {
      var i = itemsIndex
      while (i <= newIndex && newTotalHeight + 1 >= yOffset + _height - 1) {
        yOffset += _items(i).height
        i += 1
      }
    }
  }

  def scroll(amount: Int): Unit = {
    def getNewIndex: Int = {
      if (itemsIndex + amount < 0) {
        val remainder = itemsIndex + amount // This will be negative
        _items.length + remainder
      } else {
        (itemsIndex + amount) % _items.length
      }
    }

    val newIndex = getNewIndex
    setOffset(newIndex)
    _items(itemsIndex).color = Color.Black
    itemsIndex = newIndex
    _items(itemsIndex).color = Color.Green
  }

  override def processKeyPress(kc: KeyCode): Boolean = {
    kc match {
      case KeyCode.Up =>
        scroll(-1)
        true
      case KeyCode.Down =>
        scroll(1)
        true
      case _ =>
        false
    }
  }
}
