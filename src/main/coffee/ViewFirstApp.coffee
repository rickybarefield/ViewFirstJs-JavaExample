ViewFirst = require ("ViewFirstJs")
Appointment = require("./Appointment")

viewFirst = new ViewFirst("ws://localhost:8080/viewfirstjs-java-example-0.0.1-SNAPSHOT/websocket")

date = new Date(2013, 3, 1)

viewFirst.setNamedModel("startOfCurrentMonth", date)

daysInMonth = (month, year) -> return new Date(year, month + 1, 0).getDate();
monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ]
dayNames = ["sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"]

allAppointments = viewFirst.Appointment.createCollection()
global.allAppointments = allAppointments

currentAppointment = new viewFirst.Appointment()
viewFirst.setNamedModel("currentAppointment", currentAppointment)

viewFirst.addSnippet "createAppointment", (node) ->


  doBind = (model) ->

    viewFirst.bindInputs node, model

  node.find("button").click (event) ->
    event.preventDefault()
    viewFirst.getNamedModel("currentAppointment").save()
    viewFirst.setNamedModel("currentAppointment",  new viewFirst.Appointment())
    return false

  doBind(viewFirst.getNamedModel("currentAppointment"))
  viewFirst.onNamedModelChange("currentAppointment", (old, newModel) -> doBind(newModel))

  return node

viewFirst.addSnippet "previousMonth", (node) ->

  node.click ->
    currentDate = viewFirst.getNamedModel("startOfCurrentMonth")
    newDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1)
    viewFirst.setNamedModel("startOfCurrentMonth", newDate)

  return node

viewFirst.addSnippet "nextMonth", (node) ->

  node.click ->
    currentDate = viewFirst.getNamedModel("startOfCurrentMonth")
    newDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1)
    viewFirst.setNamedModel("startOfCurrentMonth", newDate)

  return node

viewFirst.addSnippet "calendarHeader", (node) ->

  renderMonth = (startOfCurrentMonth) -> node.html(monthNames[startOfCurrentMonth.getMonth()] + " " + startOfCurrentMonth.getFullYear())

  viewFirst.onNamedModelChange("startOfCurrentMonth", (oldDate, newDate) -> renderMonth(newDate))
  renderMonth(viewFirst.getNamedModel("startOfCurrentMonth"))
  return node

bindAppointments = (node, date) ->

  eventTemplate = node.children()
  eventTemplate.detach()

  appointmentsForDay = allAppointments.filter (appointment) ->
     appDate = appointment.get("date")
     return appDate? && appDate.getTime() == date.getTime()

  viewFirst.bindCollection appointmentsForDay, node, (appointment) ->
    events = eventTemplate.clone()
    events.click (event) ->
      event.preventDefault()
      viewFirst.setNamedModel("currentAppointment", appointment)
      return false

  return appointmentsForDay


viewFirst.addSnippet "calendar", (node) ->

  template = node.children()
  template.detach()

  appointmentCollections = []

  renderCalendar = (startOfCurrentMonth) ->

    allAppointments.removeFilteredCollection(appointmentCollections)

    node.children().detach()

    daysInThisMonth = daysInMonth(startOfCurrentMonth.getMonth(), startOfCurrentMonth.getFullYear())

    currentRow = template.clone()
    node.append(currentRow)
    currentDayOfWeek = startOfCurrentMonth.getDay()
    currentDayOfMonth = 1

    while currentDayOfMonth <= daysInThisMonth

      if currentDayOfWeek == 7
        currentDayOfWeek = 0
        currentRow = template.clone()
        node.append(currentRow)

      dayName = dayNames[currentDayOfWeek]
      cell = currentRow.find(".#{dayName}")

      do ->

        cellsDate = new Date(startOfCurrentMonth.getFullYear(), startOfCurrentMonth.getMonth(), currentDayOfMonth)

        cell.click ->

          appointment = new viewFirst.Appointment()
          appointment.set("date", cellsDate)
          viewFirst.setNamedModel("currentAppointment", appointment)

        $(cell.get(0)).data("populated", true)
        appointmentsSpan = cell.find(".events")
        appointmentCollections.push bindAppointments(appointmentsSpan, cellsDate)
        cell.find(".date").html("<span>#{currentDayOfMonth}</span>")

      currentDayOfWeek++
      currentDayOfMonth++

    node.find("td").filter(-> return !$(this).data("populated")?).addClass("unused").find(".events").html("")

  viewFirst.onNamedModelChange("startOfCurrentMonth", (oldDate, newDate) -> renderCalendar(newDate))
  renderCalendar(viewFirst.getNamedModel("startOfCurrentMonth"))

  return node

$ ->

  onceInitialized = ->
    allAppointments.activate()
    viewFirst.render("main")

  viewFirst.initialize(onceInitialized)
