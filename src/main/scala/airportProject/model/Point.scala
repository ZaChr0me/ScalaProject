package airportProject.model

import scala.util.Try

enum Point:
  case Point2D(x: Long, y: Long)
  case Point3D(x: Long, y: Long, z: Long)

object Point:
  def fromCsvLine(line: Array[String]): Option[Point] =
    line.size match
      case 2 => parse2DPoint(line)
      case 3 => parse3DPoint(line)
      case _ => None

  //pattern matching is forbose so you can implement detailed error msg if you feel like it
  def parse2DPoint(line: Array[String]) =
    (Try(line(0).toLong).toOption, Try(line(1).toLong).toOption) match
      case (Some(x), Some(y)) => Some(Point2D(x, y))
      case (Some(x), None)    => None
      case (None, Some(y))    => None
      case (None, None)       => None

  //pattern matching is forbose so you can implement detailed error msg if you feel like it
  def parse3DPoint(line: Array[String]) =
    (
      Try(line(0).toLong).toOption,
      Try(line(1).toLong).toOption,
      Try(line(2).toLong).toOption
    ) match
      case (Some(x), Some(y), Some(z)) => Some(Point3D(x, y, z))
      case (Some(_), None, Some(_)) | (None, Some(_), Some(_)) |
          (Some(_), Some(_), None) =>
        None //1 error
      case (Some(_), None, None) | (None, Some(_), None) |
          (None, None, Some(_)) =>
        None // 2 errors
      case (None, None, None) => None // 3 errors
