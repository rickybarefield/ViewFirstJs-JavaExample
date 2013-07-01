define ["ViewFirst"], (ViewFirst) ->

  ViewFirst.Model.extend class Appointment

    constructor: ->
      @createProperty("title")
      @createProperty("date")