var Router = require("vertx-web-js/router");
var StaticHandler = require("vertx-web-js/static_handler");
var SockJSHandler = require("vertx-web-js/sock_js_handler");

var app = Router.router(vertx);

// eventbus bridge route
var sockJSHandler = SockJSHandler.create(vertx);
var outboundPermittedMarket = {
  "address" : "market"
};
var options = {
    outboundPermitteds: [
        outboundPermittedMarket
    ]
};
sockJSHandler.bridge(options);

app.route("/eventbus/*").handler(sockJSHandler.handle);

// route to static resources
app.route("/*").handler(StaticHandler.create().handle);

vertx
// create a HTTP server
.createHttpServer()
// on each request pass it to our APP
.requestHandler(function (req) {
    app.accept(req);
})
// listen on port 8080
.listen(8080);

