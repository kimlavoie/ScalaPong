import java.awt.{Color, Graphics}

class Ball(var x: Double, var y: Double, var width: Double, var height: Double) extends Drawable{
  val r = scala.util.Random
  var goesRight, goesDown = false
  color = Color.RED
  verticalSpeed = 150 + r.nextInt(100)
  horizontalSpeed = 150 + r.nextInt(100)
  override def draw(g:Graphics): Unit ={
    g.fillOval(x.toInt,y.toInt,width.toInt,height.toInt)
  }
}
