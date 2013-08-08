(function() {

  define(["ViewFirst"], function(ViewFirst) {
    var Appointment;
    return ViewFirst.Model.extend(Appointment = (function() {

      Appointment.url = "appointments";

      function Appointment() {
        this.createProperty("title", String);
        this.createProperty("date", Date);
      }

      return Appointment;

    })());
  });

}).call(this);
