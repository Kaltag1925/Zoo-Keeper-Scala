package gui.windows

trait OpenableWindow {
  def prevWindow: Option[Window]
  def close(): Unit
  def openFrom(parent: Window): Unit
}
