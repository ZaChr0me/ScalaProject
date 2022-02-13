package airportProject.uiService

import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Text
import scalafx.scene.control._
import scalafx.scene.layout.Priority
import scalafx.scene.layout.VBox
import scalafx.beans.Observable
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.HBox
import scalafx.event.ActionEvent

import airportProject.model.Airport
import airportProject.model.Runway

enum TypeOfQuery {
  case Code, Name
}

class QueryPane(
    val panel: BorderPane,
    val queryButton: Button,
    val queryText: TextArea,
    val queryType: ComboBox[String]
):
  def setContent(airportsAndTheirRunways: List[(Airport, List[Runway])]) = {
    panel.center = new ScrollPane {
      vgrow = Priority.Always
      hgrow = Priority.Always
      content = new VBox {
        vgrow = Priority.Always
        hgrow = Priority.Always
        children = airportsAndTheirRunways
          .map((airport, l: List[Runway]) => {
            AirportAndRunways
              .getContent(
                airport,
                l
              )
              .content
          })
      }
    }
  }
object QueryPane:
  def getContent: QueryPane = {
    val startQuery = new Button {
      text = "query"
    }
    val strings: ObservableBuffer[String] =
      ObservableBuffer("Name", "Code")
    val queryText = new TextArea {
      prefHeight = 40
      prefWidth = 50
      vgrow = Priority.Always
      hgrow = Priority.Always
      id = "queryText"
      text = ""
    }
    val queryTypeComboBox = new ComboBox[String] {
      items = strings
      promptText = "select the type of entry"
    }
    QueryPane(
      new BorderPane {
        vgrow = Priority.Always
        hgrow = Priority.Always
        id = "queryPanel"
        top = new ToolBar {
          prefHeight = 40
          vgrow = Priority.Always
          hgrow = Priority.Always
          id = "navigationToolBar"

          content = List(
            new Label("Query using a country name or code"),
            queryText,
            queryTypeComboBox,
            startQuery
          )
        }
        center = new VBox {
          vgrow = Priority.Always
          hgrow = Priority.Always
          id = "queryPanelContent"

        }
      },
      startQuery,
      queryText,
      queryTypeComboBox
    )
  }

class AirportAndRunways(val content: VBox)
object AirportAndRunways:
  def getContent(
      airport: Airport,
      airportsRunways: List[Runway]
  ): AirportAndRunways = {
    val displayLabel = Label(airport.name.toString)
    AirportAndRunways(new VBox {
      vgrow = Priority.Always
      hgrow = Priority.Always
      children = Seq(
        displayLabel,
        new ListView[String] {
          items = ObservableBuffer.from(
            airportsRunways.map(runway => runway.id.toString)
          )
        }
      )
    })
  }
