require ["ViewFirst", "jquery"], (ViewFirst, $) ->

  $ ->

    viewFirst = new ViewFirst("basicView")

    viewFirst.initialize()

    #Until routing is working...
    viewFirst.render("basicView")