package airportProject.uiService

import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Text
import scalafx.scene.control._
import scalafx.scene.layout.Priority
import scalafx.scene.layout.Border
import scalafx.scene.control.TabPane.TabClosingPolicy

import airportProject.model._
import scalafx.scene.layout.VBox
import scalafx.collections.ObservableBuffer

class ReportPane(
    val panel: TabPane,
    val reportTopCountriesQuery: Button,
    val reportTypesofSurfaceQuery: Button,
    val reportTopCommonLatitudeQuery: Button
):
  def setTopBottomCountries(countriesRankings: List[(Country, Int)]) = {
    panel
      .tabs(0)
      .setContent(new ScrollPane {
        vgrow = Priority.Always
        hgrow = Priority.Always
        content = new VBox {
          vgrow = Priority.Always
          hgrow = Priority.Always
          children = List(
            reportTopCountriesQuery,
            new ListView[String] {
              items = ObservableBuffer.from(
                countriesRankings.map((country, runwaysAmount) =>
                  country.name.toString + " with " + runwaysAmount + " runways"
                )
              )
            }
          )
        }
      })
  }
  def setAllTypesOfSurface(
      countriesAndTheirSurface: List[(Country, List[String])]
  ) = {
    panel
      .tabs(1)
      .setContent(new ScrollPane {
        vgrow = Priority.Always
        hgrow = Priority.Always
        content = new VBox {
          vgrow = Priority.Always
          hgrow = Priority.Always
          vgrow = Priority.Always
          hgrow = Priority.Always
          children = List(reportTypesofSurfaceQuery)
        }
      })
  }
  def setTopCommonLatitude(topCommonLatitudes: List[(String, Int)]) = {
    panel
      .tabs(2)
      .setContent(new ScrollPane {
        vgrow = Priority.Always
        hgrow = Priority.Always
        content = new VBox {
          vgrow = Priority.Always
          hgrow = Priority.Always
          children = List(
            reportTopCommonLatitudeQuery,
            new ListView[String] {
              items = ObservableBuffer.from(
                topCommonLatitudes.map((latitude, amount) =>
                  latitude + " with " + amount + " amount."
                )
              )
            }
          )
        }

      })
  }
object ReportPane:
  def getContent: ReportPane = {
    val reportTopCountries = new Button {
      text =
        "Show the top 10 and bottom 10 countries with highest number of airports"
    }
    val reportTypesofSurface = new Button {
      text = "Show all types of runways(surfaces) per country"
    }
    val reportTopCommonLatitude = new Button {
      text = "Get the top 10 most common runway latitudes"
    }

    ReportPane(
      new TabPane {
        vgrow = Priority.Always
        hgrow = Priority.Always
        id = "reportPanel"
        tabClosingPolicy = TabClosingPolicy.Unavailable
        tabs = Seq(
          new Tab {
            text = "10 Top and 10 Bottom Countries by number of airports"
            content = reportTopCountries
          },
          new Tab {
            text = "types of runways surfaces per country"
            content = reportTypesofSurface
          },
          new Tab {
            text = "Top most common latitudes"
            content = reportTopCommonLatitude
          }
        )
      },
      reportTopCountries,
      reportTypesofSurface,
      reportTopCommonLatitude
    )
  }

class CountriesAndRunways(val content: VBox)
object CountriesAndRunways:
  def getContent(
      country: Country,
      surface: List[String]
  ): CountriesAndRunways = {
    val displayLabel = Label(country.name.toString)
    CountriesAndRunways(new VBox {
      vgrow = Priority.Always
      hgrow = Priority.Always
      children = Seq(
        displayLabel,
        new ListView[String] {
          items = ObservableBuffer.from(
            surface
          )
        }
      )
    })
  }
