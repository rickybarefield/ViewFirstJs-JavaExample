(function() {

  define(["ViewFirst"], function(ViewFirst) {
    var Appointment;
    return ViewFirst.Model.extend(Appointment = (function() {

      function Appointment() {
        this.createProperty("title");
        this.createProperty("date");
      }

      return Appointment;

    })());
  });

}).call(this);
