(function() {

  require(["ViewFirst", "jquery", "Appointment"], function(ViewFirst, $, Appointment) {
    var bindAppointments, date, dayNames, daysInMonth, goToTheDentist, monthNames, viewFirst;
    viewFirst = new ViewFirst("monthView");
    goToTheDentist = new Appointment();
    goToTheDentist.set("date", new Date(2013, 2, 5));
    goToTheDentist.set("title", "Go to the dentist");
    date = new Date(2013, 3, 1);
    viewFirst.setNamedModel("startOfCurrentMonth", date);
    daysInMonth = function(month, year) {
      return new Date(year, month + 1, 0).getDate();
    };
    monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    dayNames = ["sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"];
    viewFirst.addSnippet("createAppointment", function(node) {
      var doBind;
      doBind = function() {
        var newAppointment;
        newAppointment = new Appointment();
        viewFirst.bindInputs(node, newAppointment);
        return false;
      };
      node.find("button").click(doBind);
      doBind();
      return node;
    });
    viewFirst.addSnippet("previousMonth", function(node) {
      node.click(function() {
        var currentDate, newDate;
        currentDate = viewFirst.getNamedModel("startOfCurrentMonth");
        newDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1);
        return viewFirst.setNamedModel("startOfCurrentMonth", newDate);
      });
      return node;
    });
    viewFirst.addSnippet("nextMonth", function(node) {
      node.click(function() {
        var currentDate, newDate;
        currentDate = viewFirst.getNamedModel("startOfCurrentMonth");
        newDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1);
        return viewFirst.setNamedModel("startOfCurrentMonth", newDate);
      });
      return node;
    });
    viewFirst.addSnippet("calendarHeader", function(node) {
      var renderMonth;
      renderMonth = function(startOfCurrentMonth) {
        return node.html(monthNames[startOfCurrentMonth.getMonth()] + " " + startOfCurrentMonth.getFullYear());
      };
      viewFirst.onNamedModelChange("startOfCurrentMonth", function(oldDate, newDate) {
        return renderMonth(newDate);
      });
      renderMonth(viewFirst.getNamedModel("startOfCurrentMonth"));
      return node;
    });
    bindAppointments = function(node, date) {
      var appointmentsForDay, eventTemplate;
      eventTemplate = node.children();
      eventTemplate.detach();
      appointmentsForDay = Appointment.createCollection(function(appointment) {
        var appDate;
        appDate = appointment.get("date");
        return (appDate != null) && appDate.getTime() === date.getTime();
      });
      return viewFirst.bindCollection(appointmentsForDay, node, function() {
        return eventTemplate.clone();
      });
    };
    viewFirst.addSnippet("calendar", function(node) {
      var renderCalendar, template;
      template = node.children();
      template.detach();
      renderCalendar = function(startOfCurrentMonth) {
        var appointmentsSpan, cell, currentDayOfMonth, currentDayOfWeek, currentRow, dayName, daysInThisMonth;
        node.children().detach();
        daysInThisMonth = daysInMonth(startOfCurrentMonth.getMonth(), startOfCurrentMonth.getFullYear());
        currentRow = template.clone();
        node.append(currentRow);
        currentDayOfWeek = startOfCurrentMonth.getDay();
        currentDayOfMonth = 1;
        while (currentDayOfMonth <= daysInThisMonth) {
          if (currentDayOfWeek === 7) {
            currentDayOfWeek = 0;
            currentRow = template.clone();
            node.append(currentRow);
          }
          dayName = dayNames[currentDayOfWeek];
          cell = currentRow.find("." + dayName);
          $(cell.get(0)).data("populated", true);
          appointmentsSpan = cell.find(".events");
          bindAppointments(appointmentsSpan, new Date(startOfCurrentMonth.getFullYear(), startOfCurrentMonth.getMonth(), currentDayOfMonth));
          cell.find(".date").html("<span>" + currentDayOfMonth + "</span>");
          currentDayOfWeek++;
          currentDayOfMonth++;
        }
        return node.find("td").filter(function() {
          return !($(this).data("populated") != null);
        }).find(".events").html("");
      };
      viewFirst.onNamedModelChange("startOfCurrentMonth", function(oldDate, newDate) {
        return renderCalendar(newDate);
      });
      renderCalendar(viewFirst.getNamedModel("startOfCurrentMonth"));
      return node;
    });
    return $(function() {
      viewFirst.initialize();
      return viewFirst.render("main");
    });
  });

}).call(this);
