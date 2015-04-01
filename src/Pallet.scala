import java.awt.{Color, Graphics}

class Pallet(var x:Double, var y:Double, var width: Double, var height: Double) extends Drawable{
  verticalSpeed = 200
  color = Color.BLUE
  override def draw(g:Graphics): Unit ={
    g.fillRect(x.toInt,y.toInt,width.toInt,height.toInt)
  }
}
