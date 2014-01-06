ViewFirst = require("ViewFirstJs")

module.exports = ViewFirst.Model.extend class Appointment

    @type: "appointment"

    constructor: ->
      @createProperty("id", Long)
      @createProperty("title", String)
      @createProperty("date", Date)