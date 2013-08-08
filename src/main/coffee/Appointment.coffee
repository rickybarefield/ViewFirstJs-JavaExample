define ["ViewFirst"], (ViewFirst) ->

  ViewFirst.Model.extend class Appointment

    @url: "appointments"

    constructor: ->
      @createProperty("title", String)
      @createProperty("date", Date)