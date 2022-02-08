package airportProject.service


import airportProject.model.Point
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class CSVSpec extends AnyFlatSpec with Matchers:
  import Point._

  "CSV" should "read a valid csv" in {
    CSV.read("validLines.csv", Point.fromCsvLine).nbInvalidLine must be(0)
    CSV.read("validLines.csv", Point.fromCsvLine).lines.toSeq must contain theSameElementsAs List(
      Point2D(42, 21),
      Point3D(21, 42, 84),
      Point2D(0,0),
      Point2D(0, 42),
      Point3D(42, 0, 0)
    )
  }

  it should "read valid lines from mixed file" in {
    CSV.read("mixedLines.csv", Point.fromCsvLine).nbInvalidLine must be(2)
    CSV.read("mixedLines.csv", Point.fromCsvLine).lines.toSeq must contain theSameElementsAs List(Point3D(42, 42, 42), Point2D(21,21))
  }

  it should "not read valid lines from mixed file" in {
    CSV.read("invalidLines.csv", Point.fromCsvLine).nbInvalidLine must be(4)
    CSV.read("invalidLines.csv", Point.fromCsvLine).lines.toSeq mustBe empty
  }


