import java.awt.{Color, Graphics}

trait Drawable {
  var verticalSpeed = 1
  var horizontalSpeed = 1
  var x,y,width,height:Double
  var color = Color.BLACK

  def draw(g:Graphics): Unit

  def moveUp(delta: Double): Unit ={
    y -= verticalSpeed * delta
  }

  def moveDown(delta:Double): Unit ={
    y += verticalSpeed * delta
  }

  def moveRight(delta:Double): Unit ={
    x += horizontalSpeed * delta
  }

  def moveLeft(delta:Double): Unit ={
    x -= horizontalSpeed * delta
  }
}
