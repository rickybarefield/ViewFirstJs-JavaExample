ViewFirst = require("ViewFirstJs")

module.exports = ViewFirst.Model.extend class Appointment

    @type: "Appointment"

    constructor: ->
      @createProperty("id", Number)
      @createProperty("title", String)
      @createProperty("date", Date)