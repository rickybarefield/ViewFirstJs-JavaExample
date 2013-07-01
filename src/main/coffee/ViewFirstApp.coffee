require ["ViewFirst", "jquery", "Appointment"], (ViewFirst, $, Appointment) ->

  viewFirst = new ViewFirst("monthView")

  #Create some appointments

  goToTheDentist = new Appointment()
  goToTheDentist.set("date", new Date(2013, 2, 5))
  goToTheDentist.set("title", "Go to the dentist")

  date = new Date(2013, 3, 1)

  viewFirst.setNamedModel("startOfCurrentMonth", date)

  daysInMonth = (month, year) -> return new Date(year, month + 1, 0).getDate();

  monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ]
  dayNames = ["sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"]

  viewFirst.addSnippet "createAppointment", (node) ->

    doBind = ->
      newAppointment = new Appointment()
      viewFirst.bindInputs node, newAppointment
      return false

    node.find("button").click(doBind)
    doBind()

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

    appointmentsForDay = Appointment.createCollection (appointment) ->
       appDate = appointment.get("date")
       return appDate? && appDate.getTime() == date.getTime()

    viewFirst.bindCollection appointmentsForDay, node, ->
      eventTemplate.clone()


  viewFirst.addSnippet "calendar", (node) ->

    template = node.children()
    template.detach()

    renderCalendar = (startOfCurrentMonth) ->

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
        $(cell.get(0)).data("populated", true)
        appointmentsSpan = cell.find(".events")
        bindAppointments(appointmentsSpan, new Date(startOfCurrentMonth.getFullYear(), startOfCurrentMonth.getMonth(), currentDayOfMonth))
        cell.find(".date").html("<span>#{currentDayOfMonth}</span>")

        currentDayOfWeek++
        currentDayOfMonth++

      node.find("td").filter(-> return !$(this).data("populated")?).addClass("unused").find(".events").html("")


    viewFirst.onNamedModelChange("startOfCurrentMonth", (oldDate, newDate) -> renderCalendar(newDate))
    renderCalendar(viewFirst.getNamedModel("startOfCurrentMonth"))

    return node

  $ ->

    viewFirst.initialize()

    #Until routing is working...
    viewFirst.render("main")