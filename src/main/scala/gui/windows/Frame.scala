package gui.windows

import gui.rendering.TextContext
import gui.rendering.TextContext.TileCharacter
import scalafx.scene.input.KeyCode
//TODO: Change width and height to character cells
class Frame(val tc: TextContext, val width: Int, val height: Int,
            val baseWindow: Window) {

  def getTileInfo(): Seq[TileCharacter] = {
    tc.clearObstructions()
    render(tc)
  }

  def render(tc: TextContext): Seq[TileCharacter] = {
    baseWindow.render(tc)
  }

  def processKeyPress(kc: KeyCode): Unit = {
    baseWindow.processKeyBind(kc)
  }
}
