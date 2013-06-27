(function() {

  require(["ViewFirst", "jquery"], function(ViewFirst, $) {
    return $(function() {
      var viewFirst;
      viewFirst = new ViewFirst("basicView");
      viewFirst.initialize();
      return viewFirst.render("basicView");
    });
  });

}).call(this);
