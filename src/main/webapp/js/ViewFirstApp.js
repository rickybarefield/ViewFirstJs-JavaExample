(function() {

  require(["ViewFirst", "jquery"], function(ViewFirst, $) {
    return $(function() {
      var date, dayNames, daysInMonth, monthNames, viewFirst;
      daysInMonth = function(month, year) {
        return new Date(year, month, 0).getDate();
      };
      monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
      dayNames = ["sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"];
      date = new Date(2013, 2, 1);
      viewFirst = new ViewFirst("monthView");
      viewFirst.addSnippet("month", function(node) {
        node.html(monthNames[date.getMonth() - 1]);
        return node;
      });
      viewFirst.addSnippet("calendar", function(node) {
        var currentDayOfMonth, currentDayOfWeek, currentRow, dayName, daysInThisMonth, template;
        daysInThisMonth = daysInMonth(date.getMonth(), date.getYear());
        template = node.children();
        template.detach();
        currentRow = template.clone();
        node.append(currentRow);
        currentDayOfWeek = date.getDay();
        currentDayOfMonth = 1;
        while (currentDayOfMonth <= daysInThisMonth) {
          if (currentDayOfWeek === 7) {
            currentDayOfWeek = 0;
            currentRow = template.clone();
            node.append(currentRow);
          }
          dayName = dayNames[currentDayOfWeek];
          currentRow.find("." + dayName + " .date").html("<span>" + currentDayOfMonth + "</span>");
          currentDayOfWeek++;
          currentDayOfMonth++;
        }
        return node;
      });
      viewFirst.initialize();
      return viewFirst.render("monthView");
    });
  });

}).call(this);
